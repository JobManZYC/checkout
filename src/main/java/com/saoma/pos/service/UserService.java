package com.saoma.pos.service;

import com.saoma.pos.entity.User;
import java.util.List;

public interface UserService {

    List<User> findAll();

    List<User> findByMerchantId(Long merchantId);

    User findById(Long id);

    User login(String username, String password);

    int save(User user);

    int deleteById(Long id);
}
