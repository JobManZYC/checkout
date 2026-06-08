package com.scan.pos.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scan.pos.common.BusinessException;
import com.scan.pos.converter.MerchantConverter;
import com.scan.pos.mapper.UserMapper;
import com.scan.pos.pojo.dto.MerchantSaveDTO;
import com.scan.pos.pojo.entity.Merchant;
import com.scan.pos.pojo.entity.User;
import com.scan.pos.pojo.vo.MerchantVO;
import com.scan.pos.mapper.MerchantMapper;
import com.scan.pos.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MerchantServiceImpl extends ServiceImpl<MerchantMapper, Merchant> implements MerchantService {

    @Override
    public List<MerchantVO> findAll() {
        List<Merchant> list = baseMapper.findAll();
        return MerchantConverter.toVOList(list);
    }

    @Override
    public MerchantVO findById(Long id) {
        Merchant entity = baseMapper.selectById(id);
        return MerchantConverter.toVO(entity);
    }

    @Override
    public Page<MerchantVO> page(int page, int pageSize, String keyword) {
        Page<Merchant> pageParam = new Page<>(page, pageSize);
        Page<Merchant> result = baseMapper.selectMerchantPage(pageParam, keyword);
        Page<MerchantVO> voPage = new Page<>(result.getCurrent(), result.getSize(), result.getTotal());
        voPage.setRecords(MerchantConverter.toVOList(result.getRecords()));
        return voPage;
    }

    @Override
    public int save(MerchantSaveDTO dto) {
        Merchant entity = MerchantConverter.toEntity(dto);
        if (entity.getId() == null) {
            entity.setStatus(1);
            return baseMapper.insert(entity);
        }
        // 修改：如果未传 status，保持原有状态
        Merchant existById = baseMapper.selectById(entity.getId());
        if (existById == null) {
            throw new BusinessException("商户不存在");
        }
        if (entity.getStatus() == null) {
            entity.setStatus(existById.getStatus());
        }
        return baseMapper.updateById(entity);
    }

    @Override
    public int deleteById(Long id) {
        Merchant merchant = baseMapper.selectById(id);
        if (merchant == null) {
            throw new BusinessException("商户不存在");
        }
        merchant.setDeleted(true);
        return baseMapper.updateById(merchant);
    }
}
