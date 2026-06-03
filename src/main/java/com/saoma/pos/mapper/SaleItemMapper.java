package com.saoma.pos.mapper;

import com.saoma.pos.entity.SaleItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface SaleItemMapper {

    int insert(SaleItem item);

    int batchInsert(@Param("items") List<SaleItem> items);

    List<SaleItem> findBySaleId(Long saleId);
}
