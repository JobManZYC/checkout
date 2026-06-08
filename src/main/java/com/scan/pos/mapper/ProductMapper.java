package com.scan.pos.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scan.pos.pojo.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface ProductMapper extends BaseMapper<Product> {

    Product findByBarcode(@Param("barcode") String barcode);

    Product findByMerchantAndBarcode(@Param("merchantId") Long merchantId, @Param("barcode") String barcode);

    int decreaseStock(@Param("id") Long id, @Param("quantity") Integer quantity);

    int increaseStock(@Param("id") Long id, @Param("quantity") Integer quantity);

    List<String> findAllCategories();

    List<String> findCategoriesByMerchantId(@Param("merchantId") Long merchantId);

    List<Product> findAllProducts();

    List<Product> findProductsByMerchantId(@Param("merchantId") Long merchantId);

    Page<Product> selectProductPage(Page<Product> page,
                                    @Param("merchantId") Long merchantId,
                                    @Param("keyword") String keyword,
                                    @Param("category") String category);

    List<Product> findProductsByCategory(@Param("category") String category);

    List<Product> searchProducts(@Param("keyword") String keyword);

    List<Product> searchProductsByMerchant(@Param("merchantId") Long merchantId,
                                           @Param("keyword") String keyword);
}
