package com.scan.pos.converter;

import com.scan.pos.pojo.dto.ProductSaveDTO;
import com.scan.pos.pojo.entity.Product;
import com.scan.pos.pojo.vo.ProductVO;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ProductConverter {

    public static Product toEntity(ProductSaveDTO dto) {
        if (dto == null) return null;
        Product p = new Product();
        p.setId(dto.getId());
        p.setMerchantId(dto.getMerchantId());
        p.setBarcode(dto.getBarcode());
        p.setName(dto.getName());
        p.setCategory(dto.getCategory());
        p.setPrice(dto.getPrice());
        p.setCost(dto.getCost());
        p.setStock(dto.getStock());
        p.setUnit(dto.getUnit());
        p.setRemark(dto.getRemark());
        return p;
    }

    public static ProductVO toVO(Product entity) {
        if (entity == null) return null;
        ProductVO vo = new ProductVO();
        vo.setId(entity.getId());
        vo.setMerchantId(entity.getMerchantId());
        vo.setBarcode(entity.getBarcode());
        vo.setName(entity.getName());
        vo.setCategory(entity.getCategory());
        vo.setPrice(entity.getPrice());
        vo.setCost(entity.getCost());
        vo.setStock(entity.getStock());
        vo.setUnit(entity.getUnit());
        vo.setRemark(entity.getRemark());
        vo.setStatus(entity.getStatus());
        vo.setCreateTime(entity.getCreateTime());
        vo.setUpdateTime(entity.getUpdateTime());
        return vo;
    }

    public static List<ProductVO> toVOList(List<Product> list) {
        if (list == null) return Collections.emptyList();
        return list.stream().map(ProductConverter::toVO).collect(Collectors.toList());
    }
}
