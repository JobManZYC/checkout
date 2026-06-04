package com.saoma.pos.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.saoma.pos.pojo.entity.Product;
import java.util.List;

public interface ProductService {

    List<Product> findAll();

    List<Product> findByMerchantId(Long merchantId);

    Page<Product> pageByMerchant(Long merchantId, int page, int pageSize, String keyword, String category);

    Product findByBarcode(String barcode);

    Product findByMerchantAndBarcode(Long merchantId, String barcode);

    Product findById(Long id);

    List<Product> findByCategory(String category);

    List<Product> search(String keyword);

    List<Product> searchByMerchant(Long merchantId, String keyword);

    List<String> findAllCategories();

    List<String> findCategoriesByMerchantId(Long merchantId);

    int save(Product p);

    int deleteById(Long id);

    int decreaseStock(Long id, Integer qty);

    int increaseStock(Long id, Integer qty);
}
