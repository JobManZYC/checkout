package com.saoma.pos.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.saoma.pos.pojo.dto.SaleCheckoutDTO;
import com.saoma.pos.pojo.vo.SaleItemVO;
import com.saoma.pos.pojo.vo.SaleVO;

import java.util.List;

public interface SaleService {

    int createSale(SaleCheckoutDTO dto);

    List<SaleVO> findAll();

    List<SaleVO> findByMerchantId(Long merchantId);

    Page<SaleVO> pageByMerchant(Long merchantId, int page, int pageSize, String keyword, String startDate, String endDate);

    SaleVO findById(Long id);

    SaleVO findByOrderNo(String orderNo);

    List<SaleVO> findByDate(String date);

    List<SaleVO> findByMerchantAndDate(Long merchantId, String date);

    List<SaleItemVO> getSaleItems(Long saleId);
}
