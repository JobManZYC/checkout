package com.saoma.pos.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.saoma.pos.entity.Product;
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
}
