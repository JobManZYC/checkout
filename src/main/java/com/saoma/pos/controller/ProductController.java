package com.saoma.pos.controller;

import com.saoma.pos.common.Result;
import com.saoma.pos.entity.Product;
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

    @ApiOperation("获取全部商品列表")
    @GetMapping("/list")
    public Result<List<Product>> list() { return Result.success(productService.findAll()); }

    @ApiOperation("根据条码查询商品")
    @GetMapping("/barcode/{barcode}")
    public Result<Product> getByBarcode(
            @ApiParam(value = "商品条码", required = true, example = "6921168511280")
            @PathVariable String barcode) {
        Product p = productService.findByBarcode(barcode);
        return p != null ? Result.success(p) : Result.error(404, "商品不存在");
    }

    @ApiOperation("关键词搜索商品")
    @GetMapping("/search")
    public Result<List<Product>> search(
            @ApiParam(value = "搜索关键词", required = true, example = "可乐")
            @RequestParam String keyword) {
        return Result.success(productService.search(keyword));
    }

    @ApiOperation("获取所有商品分类")
    @GetMapping("/categories")
    public Result<List<String>> categories() { return Result.success(productService.findAllCategories()); }

    @ApiOperation("根据ID查询商品")
    @GetMapping("/{id}")
    public Result<Product> getById(
            @ApiParam(value = "商品ID", required = true, example = "1")
            @PathVariable Long id) { return Result.success(productService.findById(id)); }

    @ApiOperation("新增或更新商品")
    @PostMapping("/save")
    public Result<Integer> save(
            @ApiParam(value = "商品信息", required = true)
            @RequestBody Product product) { return Result.success(productService.save(product)); }

    @ApiOperation("删除商品")
    @DeleteMapping("/{id}")
    public Result<Integer> delete(
            @ApiParam(value = "商品ID", required = true, example = "1")
            @PathVariable Long id) { return Result.success(productService.deleteById(id)); }
}
