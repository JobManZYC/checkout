package com.saoma.pos.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.saoma.pos.pojo.entity.User;
import com.saoma.pos.mapper.UserMapper;
import com.saoma.pos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> findAll() {
        return userMapper.selectList(new LambdaQueryWrapper<User>().orderByDesc(User::getId));
    }

    @Override
    public List<User> findByMerchantId(Long merchantId) {
        return userMapper.selectList(
                new LambdaQueryWrapper<User>()
                        .eq(User::getMerchantId, merchantId)
                        .orderByDesc(User::getId));
    }

    @Override
    public Page<User> page(Long merchantId, int page, int pageSize, String keyword) {
        Page<User> pageParam = new Page<>(page, pageSize);
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        if (merchantId != null) {
            wrapper.eq(User::getMerchantId, merchantId);
        }
        if (StringUtils.hasText(keyword)) {
            wrapper.and(w -> w.like(User::getUsername, keyword)
                    .or().like(User::getRealName, keyword)
                    .or().like(User::getPhone, keyword));
        }
        wrapper.orderByDesc(User::getId);
        return userMapper.selectPage(pageParam, wrapper);
    }

    @Override
    public User findById(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public User login(String username, String password) {
        return userMapper.login(username, password);
    }

    @Override
    public int save(User user) {
        if (user.getId() == null) {
            user.setStatus(1);
            return userMapper.insert(user);
        }
        return userMapper.updateById(user);
    }

    @Override
    public int deleteById(Long id) {
        return userMapper.deleteById(id);
    }
}
