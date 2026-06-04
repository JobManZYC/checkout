package com.saoma.pos.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.saoma.pos.entity.Merchant;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface MerchantMapper extends BaseMapper<Merchant> {
}
