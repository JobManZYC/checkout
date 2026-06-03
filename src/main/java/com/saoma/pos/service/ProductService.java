package com.saoma.pos.service;

import com.saoma.pos.entity.Product;

import java.util.List;

/**
 * @author Coen
 * @date 2026-06-03 15:04
 * @description:
 */
public interface ProductService {
    
    List<Product> findAll();

    Product findByBarcode(String barcode);

    Product findById(Long id);

    List<Product> findByCategory(String category);

    List<Product> search(String keyword);

    List<String> findAllCategories();

    int save(Product p);

    int deleteById(Long id);

    int decreaseStock(Long id, Integer qty);
}
