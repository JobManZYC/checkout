package com.saoma.pos.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.saoma.pos.pojo.entity.Sale;
import com.saoma.pos.pojo.entity.SaleItem;
import com.saoma.pos.mapper.SaleItemMapper;
import com.saoma.pos.mapper.SaleMapper;
import com.saoma.pos.service.ProductService;
import com.saoma.pos.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
            item.setMerchantId(sale.getMerchantId());
            saleItemMapper.insert(item);
            productService.decreaseStock(item.getProductId(), item.getQuantity());
        }
        return 1;
    }

    @Override
    public List<Sale> findAll() {
        return saleMapper.selectList(
                new LambdaQueryWrapper<Sale>().orderByDesc(Sale::getId).last("LIMIT 100"));
    }

    @Override
    public List<Sale> findByMerchantId(Long merchantId) {
        return saleMapper.selectList(
                new LambdaQueryWrapper<Sale>()
                        .eq(Sale::getMerchantId, merchantId)
                        .orderByDesc(Sale::getId)
                        .last("LIMIT 100"));
    }

    @Override
    public Page<Sale> pageByMerchant(Long merchantId, int page, int pageSize, String keyword, String date) {
        Page<Sale> pageParam = new Page<>(page, pageSize);
        LambdaQueryWrapper<Sale> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Sale::getMerchantId, merchantId);
        if (StringUtils.hasText(keyword)) {
            wrapper.and(w -> w.like(Sale::getOrderNo, keyword).or().like(Sale::getCashierName, keyword));
        }
        if (StringUtils.hasText(date)) {
            wrapper.apply("DATE(create_time) = {0}", date);
        }
        wrapper.orderByDesc(Sale::getId);
        return saleMapper.selectPage(pageParam, wrapper);
    }

    @Override
    public Sale findById(Long id) {
        return saleMapper.selectById(id);
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
    public List<Sale> findByMerchantAndDate(Long merchantId, String date) {
        return saleMapper.findByMerchantAndDate(merchantId, date);
    }

    @Override
    public List<SaleItem> getSaleItems(Long saleId) {
        return saleItemMapper.findBySaleId(saleId);
    }
}
