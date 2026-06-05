package com.saoma.pos.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.saoma.pos.pojo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    User findByUsername(@Param("username") String username);

    User login(@Param("username") String username, @Param("password") String password);

    List<User> findAllUsers();

    List<User> findUsersByMerchantId(@Param("merchantId") Long merchantId);

    Page<User> selectUserPage(Page<User> page,
                              @Param("merchantId") Long merchantId,
                              @Param("keyword") String keyword);
}
