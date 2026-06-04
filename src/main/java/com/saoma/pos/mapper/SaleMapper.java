package com.saoma.pos.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.saoma.pos.pojo.entity.Sale;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.Date;
import java.util.List;

@Mapper
public interface SaleMapper extends BaseMapper<Sale> {

    Sale findByOrderNo(String orderNo);

    List<Sale> findByDate(String date);

    List<Sale> findByMerchantAndDate(@Param("merchantId") Long merchantId, @Param("date") String date);

    List<Sale> findByDateRange(@Param("start") Date start, @Param("end") Date end);

    List<Sale> findByStatus(Integer status);

    int updateStatus(@Param("id") Long id, @Param("status") Integer status);
}
