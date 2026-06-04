package com.saoma.pos.service.impl;

import com.saoma.pos.entity.Merchant;
import com.saoma.pos.mapper.MerchantMapper;
import com.saoma.pos.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MerchantServiceImpl implements MerchantService {

    @Autowired
    private MerchantMapper merchantMapper;

    @Override
    public List<Merchant> findAll() {
        return merchantMapper.findAll();
    }

    @Override
    public Merchant findById(Long id) {
        return merchantMapper.findById(id);
    }

    @Override
    public int save(Merchant merchant) {
        if (merchant.getId() == null) {
            merchant.setStatus(1);
            return merchantMapper.insert(merchant);
        }
        return merchantMapper.update(merchant);
    }

    @Override
    public int deleteById(Long id) {
        return merchantMapper.deleteById(id);
    }
}
