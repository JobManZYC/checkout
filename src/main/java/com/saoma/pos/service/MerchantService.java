package com.saoma.pos.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.saoma.pos.pojo.dto.MerchantSaveDTO;
import com.saoma.pos.pojo.vo.MerchantVO;

import java.util.List;

public interface MerchantService {
    List<MerchantVO> findAll();
    MerchantVO findById(Long id);
    Page<MerchantVO> page(int page, int pageSize, String keyword);
    int save(MerchantSaveDTO dto);
    int deleteById(Long id);
}
