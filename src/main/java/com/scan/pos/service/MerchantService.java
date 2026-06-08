package com.scan.pos.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.scan.pos.pojo.dto.MerchantSaveDTO;
import com.scan.pos.pojo.entity.Merchant;
import com.scan.pos.pojo.vo.MerchantVO;

import java.util.List;

public interface MerchantService extends IService<Merchant> {

    List<MerchantVO> findAll();

    MerchantVO findById(Long id);

    Page<MerchantVO> page(int page, int pageSize, String keyword);

    int save(MerchantSaveDTO dto);

    int deleteById(Long id);
}
