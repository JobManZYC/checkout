package com.saoma.pos.mapper;

import com.saoma.pos.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface ProductMapper {

    List<Product> findAll();

    Product findByBarcode(String barcode);

    Product findById(Long id);

    List<Product> findByCategory(String category);

    List<Product> search(String keyword);

    int insert(Product product);

    int update(Product product);

    int deleteById(Long id);

    int decreaseStock(@Param("id") Long id, @Param("quantity") Integer quantity);

    int increaseStock(@Param("id") Long id, @Param("quantity") Integer quantity);

    List<String> findAllCategories();
}
