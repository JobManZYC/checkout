package com.saoma.pos.mapper;

import com.saoma.pos.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface UserMapper {

    List<User> findAll();

    User findById(Long id);

    User findByUsername(String username);

    User login(@Param("username") String username, @Param("password") String password);

    int insert(User user);

    int update(User user);

    int deleteById(Long id);
}
