package com.saoma.pos.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.saoma.pos.common.Result;
import com.saoma.pos.pojo.entity.Product;
import com.saoma.pos.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Api(tags = "商品管理")
@RestController
@RequestMapping("/api/product")
@CrossOrigin
public class ProductController {
    @Autowired
    private ProductService productService;

    @ApiOperation("获取全部商品列表（超级管理员）")
    @GetMapping("/list")
    public Result<List<Product>> list() { return Result.success(productService.findAll()); }

    @ApiOperation("根据商户 ID 获取商品列表")
    @GetMapping("/listByMerchant")
    public Result<List<Product>> listByMerchant(
            @ApiParam(value = "商户 ID", required = true)
            @RequestParam Long merchantId) {
        return Result.success(productService.findByMerchantId(merchantId));
    }

    @ApiOperation("根据商户 ID 分页获取商品列表")
    @GetMapping("/pageByMerchant")
    public Result<Page<Product>> pageByMerchant(
            @ApiParam(value = "商户 ID", required = true)
            @RequestParam Long merchantId,
            @ApiParam(value = "页码", defaultValue = "1")
            @RequestParam(defaultValue = "1") int page,
            @ApiParam(value = "每页数量", defaultValue = "20")
            @RequestParam(defaultValue = "20") int pageSize,
            @ApiParam(value = "名称关键词")
            @RequestParam(required = false) String keyword,
            @ApiParam(value = "分类")
            @RequestParam(required = false) String category) {

        Page<Product> result = productService.pageByMerchant(merchantId, page, pageSize, keyword, category);
        return Result.success(result);
    }

    @ApiOperation("根据条码查询商品")
    @GetMapping("/getByBarcode")
    public Result<Product> getByBarcode(
            @ApiParam(value = "商品条码", required = true, example = "6921168511280")
            @RequestParam String barcode) {
        Product p = productService.findByBarcode(barcode);
        return p != null ? Result.success(p) : Result.error(404, "商品不存在");
    }

    @ApiOperation("根据商户 + 条码查询商品")
    @GetMapping("/getByMerchantAndBarcode")
    public Result<Product> getByMerchantAndBarcode(
            @ApiParam(value = "商户 ID", required = true)
            @RequestParam Long merchantId,
            @ApiParam(value = "商品条码", required = true)
            @RequestParam String barcode) {
        Product p = productService.findByMerchantAndBarcode(merchantId, barcode);
        return p != null ? Result.success(p) : Result.error(404, "商品不存在");
    }

    @ApiOperation("关键词搜索商品")
    @GetMapping("/search")
    public Result<List<Product>> search(
            @ApiParam(value = "搜索关键词", required = true, example = "可乐")
            @RequestParam String keyword) {
        return Result.success(productService.search(keyword));
    }

    @ApiOperation("根据商户关键词搜索商品")
    @GetMapping("/searchByMerchant")
    public Result<List<Product>> searchByMerchant(
            @ApiParam(value = "商户 ID", required = true)
            @RequestParam Long merchantId,
            @ApiParam(value = "搜索关键词", required = true)
            @RequestParam String keyword) {
        return Result.success(productService.searchByMerchant(merchantId, keyword));
    }

    @ApiOperation("获取所有商品分类")
    @GetMapping("/categories")
    public Result<List<String>> categories() { return Result.success(productService.findAllCategories()); }

    @ApiOperation("根据商户获取商品分类")
    @GetMapping("/categoriesByMerchant")
    public Result<List<String>> categoriesByMerchant(
            @ApiParam(value = "商户 ID", required = true)
            @RequestParam Long merchantId) {
        return Result.success(productService.findCategoriesByMerchantId(merchantId));
    }

    @ApiOperation("根据 ID 查询商品")
    @GetMapping("/getById")
    public Result<Product> getById(
            @ApiParam(value = "商品 ID", required = true, example = "1")
            @RequestParam Long id) { return Result.success(productService.findById(id)); }

    @ApiOperation("新增或更新商品")
    @PostMapping("/save")
    public Result<Integer> save(
            @ApiParam(value = "商品信息", required = true)
            @RequestBody Product product) { return Result.success(productService.save(product)); }

    @ApiOperation("删除商品")
    @DeleteMapping("/delete")
    public Result<Integer> delete(
            @ApiParam(value = "商品 ID", required = true, example = "1")
            @RequestParam Long id) { return Result.success(productService.deleteById(id)); }
}
