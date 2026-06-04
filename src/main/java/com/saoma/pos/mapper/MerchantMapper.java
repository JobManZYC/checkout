package com.saoma.pos.mapper;

import com.saoma.pos.entity.Merchant;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface MerchantMapper {

    List<Merchant> findAll();

    Merchant findById(Long id);

    int insert(Merchant merchant);

    int update(Merchant merchant);

    int deleteById(Long id);
}
