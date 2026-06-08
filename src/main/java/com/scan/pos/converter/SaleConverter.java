package com.scan.pos.converter;

import com.scan.pos.pojo.dto.SaleCheckoutDTO;
import com.scan.pos.pojo.dto.SaleItemDTO;
import com.scan.pos.pojo.entity.Sale;
import com.scan.pos.pojo.entity.SaleItem;
import com.scan.pos.pojo.vo.SaleItemVO;
import com.scan.pos.pojo.vo.SaleVO;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SaleConverter {

    /** DTO → Entity（仅基础字段，不含 items） */
    public static Sale toEntity(SaleCheckoutDTO dto) {
        if (dto == null) return null;
        Sale s = new Sale();
        s.setMerchantId(dto.getMerchantId());
        s.setTotalAmount(dto.getTotalAmount());
        s.setDiscountAmount(dto.getDiscountAmount());
        s.setPayAmount(dto.getPayAmount());
        s.setPayType(dto.getPayType());
        s.setChangeAmount(dto.getChangeAmount());
        s.setRemark(dto.getRemark());
        s.setCashierId(dto.getCashierId());
        s.setCashierName(dto.getCashierName());
        return s;
    }

    /** DTO items → Entity items */
    public static List<SaleItem> toItemEntityList(List<SaleItemDTO> dtoList) {
        if (dtoList == null) return Collections.emptyList();
        return dtoList.stream().map(d -> {
            SaleItem si = new SaleItem();
            si.setProductId(d.getProductId());
            si.setProductName(d.getProductName());
            si.setBarcode(d.getBarcode());
            si.setPrice(d.getPrice());
            si.setQuantity(d.getQuantity());
            si.setSubtotal(d.getSubtotal());
            return si;
        }).collect(Collectors.toList());
    }

    /** Entity → VO */
    public static SaleVO toVO(Sale entity) {
        if (entity == null) return null;
        SaleVO vo = new SaleVO();
        vo.setId(entity.getId());
        vo.setMerchantId(entity.getMerchantId());
        vo.setOrderNo(entity.getOrderNo());
        vo.setTotalAmount(entity.getTotalAmount());
        vo.setDiscountAmount(entity.getDiscountAmount());
        vo.setPayAmount(entity.getPayAmount());
        vo.setPayType(entity.getPayType());
        vo.setPayTypeName(payTypeName(entity.getPayType()));
        vo.setChangeAmount(entity.getChangeAmount());
        vo.setRemark(entity.getRemark());
        vo.setStatus(entity.getStatus());
        vo.setStatusName(statusName(entity.getStatus()));
        vo.setCashierId(entity.getCashierId());
        vo.setCashierName(entity.getCashierName());
        vo.setCreateTime(entity.getCreateTime());
        return vo;
    }

    /** Entity list → VO list */
    public static List<SaleVO> toVOList(List<Sale> list) {
        if (list == null) return Collections.emptyList();
        return list.stream().map(SaleConverter::toVO).collect(Collectors.toList());
    }

    /** SaleItem Entity → VO */
    public static SaleItemVO toItemVO(SaleItem entity) {
        if (entity == null) return null;
        SaleItemVO vo = new SaleItemVO();
        vo.setId(entity.getId());
        vo.setSaleId(entity.getSaleId());
        vo.setMerchantId(entity.getMerchantId());
        vo.setProductId(entity.getProductId());
        vo.setProductName(entity.getProductName());
        vo.setBarcode(entity.getBarcode());
        vo.setPrice(entity.getPrice());
        vo.setQuantity(entity.getQuantity());
        vo.setSubtotal(entity.getSubtotal());
        return vo;
    }

    public static List<SaleItemVO> toItemVOList(List<SaleItem> list) {
        if (list == null) return Collections.emptyList();
        return list.stream().map(SaleConverter::toItemVO).collect(Collectors.toList());
    }

    private static String payTypeName(Integer payType) {
        if (payType == null) return null;
        switch (payType) {
            case 1: return "现金";
            case 2: return "微信";
            case 3: return "支付宝";
            case 4: return "刷卡";
            default: return "未知";
        }
    }

    private static String statusName(Integer status) {
        if (status == null) return null;
        return status == 1 ? "已完成" : "已取消";
    }
}
