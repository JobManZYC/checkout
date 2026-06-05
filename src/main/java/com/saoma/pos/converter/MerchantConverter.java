package com.saoma.pos.converter;

import com.saoma.pos.pojo.dto.MerchantSaveDTO;
import com.saoma.pos.pojo.entity.Merchant;
import com.saoma.pos.pojo.vo.MerchantVO;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MerchantConverter {

    public static Merchant toEntity(MerchantSaveDTO dto) {
        if (dto == null) return null;
        Merchant m = new Merchant();
        m.setId(dto.getId());
        m.setName(dto.getName());
        m.setContactName(dto.getContactName());
        m.setContactPhone(dto.getContactPhone());
        m.setAddress(dto.getAddress());
        m.setRemark(dto.getRemark());
        if (dto.getStatus() != null) {
            m.setStatus(dto.getStatus());
        }
        return m;
    }

    public static MerchantVO toVO(Merchant entity) {
        if (entity == null) return null;
        MerchantVO vo = new MerchantVO();
        vo.setId(entity.getId());
        vo.setName(entity.getName());
        vo.setContactName(entity.getContactName());
        vo.setContactPhone(entity.getContactPhone());
        vo.setAddress(entity.getAddress());
        vo.setRemark(entity.getRemark());
        vo.setStatus(entity.getStatus());
        vo.setCreateTime(entity.getCreateTime());
        vo.setUpdateTime(entity.getUpdateTime());
        return vo;
    }

    public static List<MerchantVO> toVOList(List<Merchant> list) {
        if (list == null) return Collections.emptyList();
        return list.stream().map(MerchantConverter::toVO).collect(Collectors.toList());
    }
}
