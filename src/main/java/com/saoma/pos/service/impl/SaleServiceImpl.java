package com.saoma.pos.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.saoma.pos.converter.SaleConverter;
import com.saoma.pos.pojo.dto.SaleCheckoutDTO;
import com.saoma.pos.pojo.entity.Sale;
import com.saoma.pos.pojo.entity.SaleItem;
import com.saoma.pos.pojo.vo.SaleItemVO;
import com.saoma.pos.pojo.vo.SaleVO;
import com.saoma.pos.mapper.SaleItemMapper;
import com.saoma.pos.mapper.SaleMapper;
import com.saoma.pos.service.ProductService;
import com.saoma.pos.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class SaleServiceImpl implements SaleService {

    @Autowired
    private SaleMapper saleMapper;
    @Autowired
    private SaleItemMapper saleItemMapper;
    @Autowired
    private ProductService productService;

    @Override
    @Transactional
    public int createSale(SaleCheckoutDTO dto) {
        Sale sale = SaleConverter.toEntity(dto);
        List<SaleItem> items = SaleConverter.toItemEntityList(dto.getItems());

        String orderNo = "POS" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        sale.setOrderNo(orderNo);
        sale.setStatus(1);
        saleMapper.insert(sale);

        for (SaleItem item : items) {
            item.setSaleId(sale.getId());
            item.setMerchantId(sale.getMerchantId());
            saleItemMapper.insert(item);
            productService.decreaseStock(item.getProductId(), item.getQuantity());
        }
        return 1;
    }

    @Override
    public List<SaleVO> findAll() {
        List<Sale> list = saleMapper.selectList(
                new LambdaQueryWrapper<Sale>().orderByDesc(Sale::getId).last("LIMIT 100"));
        return SaleConverter.toVOList(list);
    }

    @Override
    public List<SaleVO> findByMerchantId(Long merchantId) {
        List<Sale> list = saleMapper.selectList(
                new LambdaQueryWrapper<Sale>()
                        .eq(Sale::getMerchantId, merchantId)
                        .orderByDesc(Sale::getId)
                        .last("LIMIT 100"));
        return SaleConverter.toVOList(list);
    }

    @Override
    public Page<SaleVO> pageByMerchant(Long merchantId, int page, int pageSize, String keyword, String date) {
        Page<Sale> pageParam = new Page<>(page, pageSize);
        LambdaQueryWrapper<Sale> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Sale::getMerchantId, merchantId);
        if (StringUtils.hasText(keyword)) {
            wrapper.and(w -> w.like(Sale::getOrderNo, keyword).or().like(Sale::getCashierName, keyword));
        }
        if (StringUtils.hasText(date)) {
            wrapper.apply("DATE(create_time) = {0}", date);
        }
        wrapper.orderByDesc(Sale::getId);
        Page<Sale> result = saleMapper.selectPage(pageParam, wrapper);
        Page<SaleVO> voPage = new Page<>(result.getCurrent(), result.getSize(), result.getTotal());
        voPage.setRecords(SaleConverter.toVOList(result.getRecords()));
        return voPage;
    }

    @Override
    public SaleVO findById(Long id) {
        Sale entity = saleMapper.selectById(id);
        SaleVO vo = SaleConverter.toVO(entity);
        if (vo != null) {
            vo.setItems(SaleConverter.toItemVOList(getSaleItemsRaw(id)));
        }
        return vo;
    }

    @Override
    public SaleVO findByOrderNo(String orderNo) {
        Sale entity = saleMapper.findByOrderNo(orderNo);
        return SaleConverter.toVO(entity);
    }

    @Override
    public List<SaleVO> findByDate(String date) {
        List<Sale> list = saleMapper.findByDate(date);
        return SaleConverter.toVOList(list);
    }

    @Override
    public List<SaleVO> findByMerchantAndDate(Long merchantId, String date) {
        List<Sale> list = saleMapper.findByMerchantAndDate(merchantId, date);
        return SaleConverter.toVOList(list);
    }

    @Override
    public List<SaleItemVO> getSaleItems(Long saleId) {
        List<SaleItem> list = getSaleItemsRaw(saleId);
        return SaleConverter.toItemVOList(list);
    }

    private List<SaleItem> getSaleItemsRaw(Long saleId) {
        return saleItemMapper.findBySaleId(saleId);
    }
}
