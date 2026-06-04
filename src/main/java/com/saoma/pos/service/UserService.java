package com.saoma.pos.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.saoma.pos.pojo.dto.UserLoginDTO;
import com.saoma.pos.pojo.dto.UserSaveDTO;
import com.saoma.pos.pojo.vo.LoginVO;
import com.saoma.pos.pojo.vo.UserVO;

import java.util.List;

public interface UserService {

    List<UserVO> findAll();

    List<UserVO> findByMerchantId(Long merchantId);

    Page<UserVO> page(Long merchantId, int page, int pageSize, String keyword);

    UserVO findById(Long id);

    LoginVO login(UserLoginDTO dto);

    int save(UserSaveDTO dto);

    int deleteById(Long id);
}
