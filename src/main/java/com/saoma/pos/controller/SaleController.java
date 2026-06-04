package com.saoma.pos.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.saoma.pos.common.Result;
import com.saoma.pos.pojo.entity.Sale;
import com.saoma.pos.pojo.entity.SaleItem;
import com.saoma.pos.service.SaleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Api(tags = "销售管理")
@RestController
@RequestMapping("/api/sale")
@CrossOrigin
public class SaleController {
    @Autowired
    private SaleService saleService;

    @ApiOperation("结账下单")
    @PostMapping("/checkout")
    public Result<Integer> checkout(
            @ApiParam(value = "订单信息（含明细）", required = true)
            @RequestBody Sale sale) {
        List<SaleItem> items = sale.getItems();
        sale.setItems(null);
        return Result.success(saleService.createSale(sale, items));
    }

    @ApiOperation("获取全部订单列表（超级管理员）")
    @GetMapping("/list")
    public Result<List<Sale>> list() { return Result.success(saleService.findAll()); }

    @ApiOperation("根据商户ID获取订单列表")
    @GetMapping("/merchant/{merchantId}")
    public Result<List<Sale>> listByMerchant(
            @ApiParam(value = "商户ID", required = true)
            @PathVariable Long merchantId) {
        return Result.success(saleService.findByMerchantId(merchantId));
    }

    @ApiOperation("分页获取订单列表")
    @GetMapping("/page")
    public Result<Page<Sale>> page(
            @ApiParam(value = "商户ID", required = true)
            @RequestParam Long merchantId,
            @ApiParam(value = "页码", defaultValue = "1")
            @RequestParam(defaultValue = "1") int page,
            @ApiParam(value = "每页数量", defaultValue = "20")
            @RequestParam(defaultValue = "20") int pageSize,
            @ApiParam(value = "订单号/收银员关键词")
            @RequestParam(required = false) String keyword,
            @ApiParam(value = "日期（yyyy-MM-dd）")
            @RequestParam(required = false) String date) {
        Page<Sale> result = saleService.pageByMerchant(merchantId, page, pageSize, keyword, date);
        return Result.success(result);
    }

    @ApiOperation("根据ID查询订单")
    @GetMapping("/{id}")
    public Result<Sale> getById(
            @ApiParam(value = "订单ID", required = true, example = "1")
            @PathVariable Long id) { return Result.success(saleService.findById(id)); }

    @ApiOperation("查询订单明细")
    @GetMapping("/{id}/items")
    public Result<List<SaleItem>> getItems(
            @ApiParam(value = "订单ID", required = true, example = "1")
            @PathVariable Long id) { return Result.success(saleService.getSaleItems(id)); }

    @ApiOperation("根据日期查询订单")
    @GetMapping("/date/{date}")
    public Result<List<Sale>> getByDate(
            @ApiParam(value = "日期（yyyy-MM-dd）", required = true, example = "2024-06-03")
            @PathVariable String date) { return Result.success(saleService.findByDate(date)); }
}
