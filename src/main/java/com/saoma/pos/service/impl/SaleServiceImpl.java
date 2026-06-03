package com.saoma.pos.service.impl;

import com.saoma.pos.entity.Sale;
import com.saoma.pos.entity.SaleItem;
import com.saoma.pos.mapper.SaleItemMapper;
import com.saoma.pos.mapper.SaleMapper;
import com.saoma.pos.service.ProductService;
import com.saoma.pos.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class SaleServiceImpl implements SaleService {

    @Autowired
    private SaleMapper saleMapper;
    @Autowired
    private SaleItemMapper saleItemMapper;
    @Autowired
    private ProductService productService;

    @Override
    @Transactional
    public int createSale(Sale sale, List<SaleItem> items) {
        String orderNo = "POS" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        sale.setOrderNo(orderNo);
        sale.setStatus(1);
        saleMapper.insert(sale);
        for (SaleItem item : items) {
            item.setSaleId(sale.getId());
            saleItemMapper.insert(item);
            productService.decreaseStock(item.getProductId(), item.getQuantity());
        }
        return 1;
    }

    @Override
    public List<Sale> findAll() {
        return saleMapper.findAll();
    }

    @Override
    public Sale findById(Long id) {
        return saleMapper.findById(id);
    }

    @Override
    public Sale findByOrderNo(String orderNo) {
        return saleMapper.findByOrderNo(orderNo);
    }

    @Override
    public List<Sale> findByDate(String date) {
        return saleMapper.findByDate(date);
    }

    @Override
    public List<SaleItem> getSaleItems(Long saleId) {
        return saleItemMapper.findBySaleId(saleId);
    }
}
