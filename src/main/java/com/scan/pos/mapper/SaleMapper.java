package com.scan.pos.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scan.pos.pojo.entity.Sale;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface SaleMapper extends BaseMapper<Sale> {

    Sale findByOrderNo(String orderNo);

    List<Sale> findByDate(String date);

    List<Sale> findByMerchantAndDate(@Param("merchantId") Long merchantId, @Param("date") String date);

    List<Sale> findByDateRange(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    List<Sale> findByStatus(Integer status);

    int updateStatus(@Param("id") Long id, @Param("status") Integer status);

    List<Sale> findAllSales();

    List<Sale> findSalesByMerchantId(@Param("merchantId") Long merchantId);

    Page<Sale> selectSalePage(Page<Sale> page,
                              @Param("merchantId") Long merchantId,
                              @Param("keyword") String keyword,
                              @Param("startDate") String startDate,
                              @Param("endDate") String endDate);
}
