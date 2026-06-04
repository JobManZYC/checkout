package com.saoma.pos.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.saoma.pos.entity.Merchant;
import java.util.List;

public interface MerchantService {
    List<Merchant> findAll();
    Merchant findById(Long id);
    Page<Merchant> page(int page, int pageSize, String keyword);
    int save(Merchant merchant);
    int deleteById(Long id);
}
