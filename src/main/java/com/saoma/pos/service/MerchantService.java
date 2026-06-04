package com.saoma.pos.service;

import com.saoma.pos.entity.Merchant;
import java.util.List;

public interface MerchantService {
    List<Merchant> findAll();
    Merchant findById(Long id);
    int save(Merchant merchant);
    int deleteById(Long id);
}
