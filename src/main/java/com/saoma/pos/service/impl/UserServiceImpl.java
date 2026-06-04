package com.saoma.pos.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.saoma.pos.entity.User;
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
