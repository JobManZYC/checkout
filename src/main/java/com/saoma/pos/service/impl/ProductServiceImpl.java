package com.saoma.pos.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.saoma.pos.pojo.entity.Product;
import com.saoma.pos.mapper.ProductMapper;
import com.saoma.pos.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<Product> findAll() {
        return productMapper.selectList(
                new LambdaQueryWrapper<Product>().orderByDesc(Product::getId));
    }

    @Override
    public List<Product> findByMerchantId(Long merchantId) {
        return productMapper.selectList(
                new LambdaQueryWrapper<Product>()
                        .eq(Product::getMerchantId, merchantId)
                        .orderByDesc(Product::getId));
    }

    @Override
    public Page<Product> pageByMerchant(Long merchantId, int page, int pageSize, String keyword, String category) {
        Page<Product> pageParam = new Page<>(page, pageSize);
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Product::getMerchantId, merchantId);
        if (StringUtils.hasText(keyword)) {
            wrapper.and(w -> w.like(Product::getName, keyword).or().like(Product::getBarcode, keyword));
        }
        if (StringUtils.hasText(category)) {
            wrapper.eq(Product::getCategory, category);
        }
        wrapper.orderByDesc(Product::getId);
        return productMapper.selectPage(pageParam, wrapper);
    }

    @Override
    public Product findByBarcode(String barcode) {
        return productMapper.findByBarcode(barcode);
    }

    @Override
    public Product findByMerchantAndBarcode(Long merchantId, String barcode) {
        return productMapper.findByMerchantAndBarcode(merchantId, barcode);
    }

    @Override
    public Product findById(Long id) {
        return productMapper.selectById(id);
    }

    @Override
    public List<Product> findByCategory(String category) {
        return productMapper.selectList(
                new LambdaQueryWrapper<Product>()
                        .eq(Product::getCategory, category)
                        .orderByDesc(Product::getId));
    }

    @Override
    public List<Product> search(String keyword) {
        return productMapper.selectList(
                new LambdaQueryWrapper<Product>()
                        .and(w -> w.like(Product::getName, keyword).or().like(Product::getBarcode, keyword))
                        .orderByDesc(Product::getId));
    }

    @Override
    public List<Product> searchByMerchant(Long merchantId, String keyword) {
        return productMapper.selectList(
                new LambdaQueryWrapper<Product>()
                        .eq(Product::getMerchantId, merchantId)
                        .and(w -> w.like(Product::getName, keyword).or().like(Product::getBarcode, keyword))
                        .orderByDesc(Product::getId));
    }

    @Override
    public List<String> findAllCategories() {
        return productMapper.findAllCategories();
    }

    @Override
    public List<String> findCategoriesByMerchantId(Long merchantId) {
        return productMapper.findCategoriesByMerchantId(merchantId);
    }

    @Override
    public int save(Product p) {
        if (p.getId() == null) {
            p.setStatus(1);
            return productMapper.insert(p);
        }
        return productMapper.updateById(p);
    }

    @Override
    public int deleteById(Long id) {
        return productMapper.deleteById(id);
    }

    @Override
    public int decreaseStock(Long id, Integer qty) {
        return productMapper.decreaseStock(id, qty);
    }

    @Override
    public int increaseStock(Long id, Integer qty) {
        return productMapper.increaseStock(id, qty);
    }
}
