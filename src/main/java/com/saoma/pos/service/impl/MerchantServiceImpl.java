package com.saoma.pos.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.saoma.pos.entity.Merchant;
import com.saoma.pos.mapper.MerchantMapper;
import com.saoma.pos.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class MerchantServiceImpl implements MerchantService {

    @Autowired
    private MerchantMapper merchantMapper;

    @Override
    public List<Merchant> findAll() {
        return merchantMapper.selectList(
                new LambdaQueryWrapper<Merchant>().orderByDesc(Merchant::getId));
    }

    @Override
    public Merchant findById(Long id) {
        return merchantMapper.selectById(id);
    }

    @Override
    public Page<Merchant> page(int page, int pageSize, String keyword) {
        Page<Merchant> pageParam = new Page<>(page, pageSize);
        LambdaQueryWrapper<Merchant> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(keyword)) {
            wrapper.and(w -> w.like(Merchant::getName, keyword)
                    .or().like(Merchant::getContactName, keyword)
                    .or().like(Merchant::getContactPhone, keyword));
        }
        wrapper.orderByDesc(Merchant::getId);
        return merchantMapper.selectPage(pageParam, wrapper);
    }

    @Override
    public int save(Merchant merchant) {
        if (merchant.getId() == null) {
            merchant.setStatus(1);
            return merchantMapper.insert(merchant);
        }
        return merchantMapper.updateById(merchant);
    }

    @Override
    public int deleteById(Long id) {
        return merchantMapper.deleteById(id);
    }
}
