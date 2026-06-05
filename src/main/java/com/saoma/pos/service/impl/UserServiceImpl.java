package com.saoma.pos.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.saoma.pos.converter.UserConverter;
import com.saoma.pos.pojo.dto.UserLoginDTO;
import com.saoma.pos.pojo.dto.UserSaveDTO;
import com.saoma.pos.pojo.entity.User;
import com.saoma.pos.pojo.vo.LoginVO;
import com.saoma.pos.pojo.vo.UserVO;
import com.saoma.pos.mapper.UserMapper;
import com.saoma.pos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<UserVO> findAll() {
        List<User> list = userMapper.findAllUsers();
        return UserConverter.toVOList(list);
    }

    @Override
    public List<UserVO> findByMerchantId(Long merchantId) {
        List<User> list = userMapper.findUsersByMerchantId(merchantId);
        return UserConverter.toVOList(list);
    }

    @Override
    public Page<UserVO> page(Long merchantId, int page, int pageSize, String keyword) {
        Page<User> pageParam = new Page<>(page, pageSize);
        Page<User> result = userMapper.selectUserPage(pageParam, merchantId, keyword);
        Page<UserVO> voPage = new Page<>(result.getCurrent(), result.getSize(), result.getTotal());
        voPage.setRecords(UserConverter.toVOList(result.getRecords()));
        return voPage;
    }

    @Override
    public UserVO findById(Long id) {
        return UserConverter.toVO(userMapper.selectById(id));
    }

    @Override
    public LoginVO login(UserLoginDTO dto) {
        User user = userMapper.login(dto.getUsername(), dto.getPassword());
        return UserConverter.toLoginVO(user);
    }

    @Override
    public int save(UserSaveDTO dto) {
        User entity = UserConverter.toEntity(dto);
        if (entity.getId() == null) {
            entity.setStatus(1);
            return userMapper.insert(entity);
        }
        return userMapper.updateById(entity);
    }

    @Override
    public int deleteById(Long id) {
        User user = new User();
        user.setId(id);
        user.setStatus(0);
        return userMapper.updateById(user);
    }
}
