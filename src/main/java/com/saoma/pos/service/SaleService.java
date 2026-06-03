package com.saoma.pos.service;

import com.saoma.pos.entity.Sale;
import com.saoma.pos.entity.SaleItem;

import java.util.List;

public interface SaleService {

    int createSale(Sale sale, List<SaleItem> items);

    List<Sale> findAll();

    Sale findById(Long id);

    Sale findByOrderNo(String orderNo);

    List<Sale> findByDate(String date);

    List<SaleItem> getSaleItems(Long saleId);
}
