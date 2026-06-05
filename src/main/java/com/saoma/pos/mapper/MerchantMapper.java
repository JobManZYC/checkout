package com.saoma.pos.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.saoma.pos.pojo.entity.Merchant;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface MerchantMapper extends BaseMapper<Merchant> {

    List<Merchant> findAll();

    Page<Merchant> selectMerchantPage(Page<Merchant> page,
                                      @Param("keyword") String keyword);
}
