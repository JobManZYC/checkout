package com.saoma.pos.mapper;

import com.saoma.pos.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface ProductMapper {

    List<Product> findAll();

    List<Product> findByMerchantId(@Param("merchantId") Long merchantId);

    Product findByBarcode(@Param("barcode") String barcode);

    Product findByMerchantAndBarcode(@Param("merchantId") Long merchantId, @Param("barcode") String barcode);

    Product findById(Long id);

    List<Product> findByCategory(@Param("category") String category);

    List<Product> search(@Param("keyword") String keyword);

    List<Product> searchByMerchant(@Param("merchantId") Long merchantId, @Param("keyword") String keyword);

    int insert(Product product);

    int update(Product product);

    int deleteById(Long id);

    int decreaseStock(@Param("id") Long id, @Param("quantity") Integer quantity);

    int increaseStock(@Param("id") Long id, @Param("quantity") Integer quantity);

    List<String> findAllCategories();

    List<String> findCategoriesByMerchantId(@Param("merchantId") Long merchantId);
}
