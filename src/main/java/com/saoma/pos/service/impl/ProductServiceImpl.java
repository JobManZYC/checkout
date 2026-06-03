package com.saoma.pos.service.impl;

import com.saoma.pos.entity.Product;
import com.saoma.pos.mapper.ProductMapper;
import com.saoma.pos.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<Product> findAll() {
        return productMapper.findAll();
    }

    @Override
    public Product findByBarcode(String barcode) {
        return productMapper.findByBarcode(barcode);
    }

    @Override
    public Product findById(Long id) {
        return productMapper.findById(id);
    }

    @Override
    public List<Product> findByCategory(String category) {
        return productMapper.findByCategory(category);
    }

    @Override
    public List<Product> search(String keyword) {
        return productMapper.search(keyword);
    }

    @Override
    public List<String> findAllCategories() {
        return productMapper.findAllCategories();
    }

    @Override
    public int save(Product p) {
        if (p.getId() == null) {
            p.setStatus(1);
            return productMapper.insert(p);
        }
        return productMapper.update(p);
    }

    @Override
    public int deleteById(Long id) {
        return productMapper.deleteById(id);
    }

    @Override
    public int decreaseStock(Long id, Integer qty) {
        return productMapper.decreaseStock(id, qty);
    }
}
