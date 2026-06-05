package com.saoma.pos.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.saoma.pos.converter.MerchantConverter;
import com.saoma.pos.pojo.dto.MerchantSaveDTO;
import com.saoma.pos.pojo.entity.Merchant;
import com.saoma.pos.pojo.vo.MerchantVO;
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
    public List<MerchantVO> findAll() {
        List<Merchant> list = merchantMapper.findAll();
        return MerchantConverter.toVOList(list);
    }

    @Override
    public MerchantVO findById(Long id) {
        Merchant entity = merchantMapper.selectById(id);
        return MerchantConverter.toVO(entity);
    }

    @Override
    public Page<MerchantVO> page(int page, int pageSize, String keyword) {
        Page<Merchant> pageParam = new Page<>(page, pageSize);
        Page<Merchant> result = merchantMapper.selectMerchantPage(pageParam, keyword);
        Page<MerchantVO> voPage = new Page<>(result.getCurrent(), result.getSize(), result.getTotal());
        voPage.setRecords(MerchantConverter.toVOList(result.getRecords()));
        return voPage;
    }

    @Override
    public int save(MerchantSaveDTO dto) {
        Merchant entity = MerchantConverter.toEntity(dto);
        if (entity.getId() == null) {
            entity.setStatus(1);
            return merchantMapper.insert(entity);
        }
        return merchantMapper.updateById(entity);
    }

    @Override
    public int deleteById(Long id) {
        return merchantMapper.deleteById(id);
    }
}
