package com.scan.pos.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scan.pos.pojo.dto.ProductSaveDTO;
import com.scan.pos.pojo.vo.ProductVO;

import java.util.List;

public interface ProductService {

    List<ProductVO> findAll();

    List<ProductVO> findByMerchantId(Long merchantId);

    Page<ProductVO> pageByMerchant(Long merchantId, int page, int pageSize, String keyword, String category);

    ProductVO findByBarcode(String barcode);

    ProductVO findByMerchantAndBarcode(Long merchantId, String barcode);

    ProductVO findById(Long id);

    List<ProductVO> findByCategory(String category);

    List<ProductVO> search(String keyword);

    List<ProductVO> searchByMerchant(Long merchantId, String keyword);

    List<String> findAllCategories();

    List<String> findCategoriesByMerchantId(Long merchantId);

    int save(ProductSaveDTO dto);

    int deleteById(Long id);

    int decreaseStock(Long id, Integer qty);

    int increaseStock(Long id, Integer qty);
}
