package com.saoma.pos.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.saoma.pos.converter.UserConverter;
import com.saoma.pos.mapper.UserMapper;
import com.saoma.pos.pojo.dto.UserLoginDTO;
import com.saoma.pos.pojo.dto.UserSaveDTO;
import com.saoma.pos.pojo.entity.User;
import com.saoma.pos.pojo.vo.LoginVO;
import com.saoma.pos.pojo.vo.UserVO;
import com.saoma.pos.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public List<UserVO> findAll() {
        List<User> list = baseMapper.findAllUsers();
        return UserConverter.toVOList(list);
    }

    @Override
    public List<UserVO> findByMerchantId(Long merchantId) {
        List<User> list = baseMapper.findUsersByMerchantId(merchantId);
        return UserConverter.toVOList(list);
    }

    @Override
    public Page<UserVO> page(Long merchantId, int page, int pageSize, String keyword) {
        Page<User> pageParam = new Page<>(page, pageSize);
        Page<User> result = baseMapper.selectUserPage(pageParam, merchantId, keyword);
        Page<UserVO> voPage = new Page<>(result.getCurrent(), result.getSize(), result.getTotal());
        voPage.setRecords(UserConverter.toVOList(result.getRecords()));
        return voPage;
    }

    @Override
    public UserVO findById(Long id) {
        return UserConverter.toVO(baseMapper.selectById(id));
    }

    @Override
    public LoginVO login(UserLoginDTO dto) {
        User user = baseMapper.login(dto.getUsername(), dto.getPassword());
        return UserConverter.toLoginVO(user);
    }

    @Override
    public int save(UserSaveDTO dto) {
        User entity = UserConverter.toEntity(dto);
        if (entity.getId() == null) {
            // 新增：同一商户下不允许有用户名相同的用户
            User existByName = baseMapper.selectOne(
                    new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<User>()
                            .eq(User::getUsername, entity.getUsername())
                            .eq(User::getMerchantId, entity.getMerchantId()));
            if (existByName != null) {
                throw new RuntimeException("同一商户下已存在相同用户名的用户");
            }
            entity.setStatus(1);
            return baseMapper.insert(entity);
        }
        // 修改：检查用户是否存在
        User existById = baseMapper.selectById(entity.getId());
        if (existById == null) {
            throw new RuntimeException("用户不存在");
        }
        // 用户名不允许修改，保持原有用户名
        entity.setUsername(existById.getUsername());
        // 如果未传 status，保持原有状态
        if (entity.getStatus() == null) {
            entity.setStatus(existById.getStatus());
        }
        return baseMapper.updateById(entity);
    }

    @Override
    public int deleteById(Long id) {
        User user = new User();
        user.setId(id);
        user.setDeleted(true);
        return baseMapper.updateById(user);
    }
}
