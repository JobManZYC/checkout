package com.scan.pos.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scan.pos.converter.ProductConverter;
import com.scan.pos.pojo.dto.ProductSaveDTO;
import com.scan.pos.pojo.entity.Product;
import com.scan.pos.pojo.vo.ProductVO;
import com.scan.pos.mapper.ProductMapper;
import com.scan.pos.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<ProductVO> findAll() {
        List<Product> list = productMapper.findAllProducts();
        return ProductConverter.toVOList(list);
    }

    @Override
    public List<ProductVO> findByMerchantId(Long merchantId) {
        List<Product> list = productMapper.findProductsByMerchantId(merchantId);
        return ProductConverter.toVOList(list);
    }

    @Override
    public Page<ProductVO> pageByMerchant(Long merchantId, int page, int pageSize, String keyword, String category) {
        Page<Product> pageParam = new Page<>(page, pageSize);
        Page<Product> result = productMapper.selectProductPage(pageParam, merchantId, keyword, category);
        Page<ProductVO> voPage = new Page<>(result.getCurrent(), result.getSize(), result.getTotal());
        voPage.setRecords(ProductConverter.toVOList(result.getRecords()));
        return voPage;
    }

    @Override
    public ProductVO findByBarcode(String barcode) {
        Product p = productMapper.findByBarcode(barcode);
        return ProductConverter.toVO(p);
    }

    @Override
    public ProductVO findByMerchantAndBarcode(Long merchantId, String barcode) {
        Product p = productMapper.findByMerchantAndBarcode(merchantId, barcode);
        return ProductConverter.toVO(p);
    }

    @Override
    public ProductVO findById(Long id) {
        return ProductConverter.toVO(productMapper.selectById(id));
    }

    @Override
    public List<ProductVO> findByCategory(String category) {
        List<Product> list = productMapper.findProductsByCategory(category);
        return ProductConverter.toVOList(list);
    }

    @Override
    public List<ProductVO> search(String keyword) {
        List<Product> list = productMapper.searchProducts(keyword);
        return ProductConverter.toVOList(list);
    }

    @Override
    public List<ProductVO> searchByMerchant(Long merchantId, String keyword) {
        List<Product> list = productMapper.searchProductsByMerchant(merchantId, keyword);
        return ProductConverter.toVOList(list);
    }

    @Override
    public List<String> findAllCategories() {
        return productMapper.findAllCategories();
    }

    @Override
    public List<String> findCategoriesByMerchantId(Long merchantId) {
        return productMapper.findCategoriesByMerchantId(merchantId);
    }

    @Override
    public int save(ProductSaveDTO dto) {
        Product entity = ProductConverter.toEntity(dto);
        if (entity.getId() == null) {
            entity.setStatus(1);
            return productMapper.insert(entity);
        }
        return productMapper.updateById(entity);
    }

    @Override
    public int deleteById(Long id) {
        return productMapper.deleteById(id);
    }

    @Override
    public int decreaseStock(Long id, Integer qty) {
        return productMapper.decreaseStock(id, qty);
    }

    @Override
    public int increaseStock(Long id, Integer qty) {
        return productMapper.increaseStock(id, qty);
    }
}
