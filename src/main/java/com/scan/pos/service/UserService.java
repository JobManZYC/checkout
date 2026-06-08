package com.scan.pos.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.scan.pos.pojo.dto.UserLoginDTO;
import com.scan.pos.pojo.dto.UserSaveDTO;
import com.scan.pos.pojo.entity.User;
import com.scan.pos.pojo.vo.LoginVO;
import com.scan.pos.pojo.vo.UserVO;

import java.util.List;

public interface UserService extends IService<User>  {

    List<UserVO> findAll();

    List<UserVO> findByMerchantId(Long merchantId);

    Page<UserVO> page(Long merchantId, int page, int pageSize, String keyword);

    UserVO findById(Long id);

    LoginVO login(UserLoginDTO dto);

    int save(UserSaveDTO dto);

    int deleteById(Long id);
}
