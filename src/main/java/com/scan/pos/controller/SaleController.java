package com.scan.pos.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scan.pos.common.Result;
import com.scan.pos.pojo.dto.SaleCheckoutDTO;
import com.scan.pos.pojo.vo.SaleItemVO;
import com.scan.pos.pojo.vo.SaleVO;
import com.scan.pos.service.SaleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
            @Valid @RequestBody SaleCheckoutDTO dto) {
        return Result.success(saleService.createSale(dto));
    }

    @ApiOperation("获取全部订单列表（超级管理员）")
    @GetMapping("/list")
    public Result<List<SaleVO>> list() { return Result.success(saleService.findAll()); }

    @ApiOperation("根据商户ID获取订单列表")
    @GetMapping("/listByMerchant")
    public Result<List<SaleVO>> listByMerchant(
            @ApiParam(value = "商户ID", required = true)
            @RequestParam Long merchantId) {
        return Result.success(saleService.findByMerchantId(merchantId));
    }

    @ApiOperation("分页获取订单列表")
    @GetMapping("/page")
    public Result<Page<SaleVO>> page(
            @ApiParam(value = "商户ID", required = true)
            @RequestParam Long merchantId,
            @ApiParam(value = "页码", defaultValue = "1")
            @RequestParam(defaultValue = "1") int page,
            @ApiParam(value = "每页数量", defaultValue = "20")
            @RequestParam(defaultValue = "20") int pageSize,
            @ApiParam(value = "订单号/收银员关键词")
            @RequestParam(required = false) String keyword,
            @ApiParam(value = "开始日期（yyyy-MM-dd）")
            @RequestParam(required = false) String startDate,
            @ApiParam(value = "结束日期（yyyy-MM-dd）")
            @RequestParam(required = false) String endDate) {
        Page<SaleVO> result = saleService.pageByMerchant(merchantId, page, pageSize, keyword, startDate, endDate);
        return Result.success(result);
    }

    @ApiOperation("根据ID查询订单（含明细）")
    @GetMapping("/getById")
    public Result<SaleVO> getById(
            @ApiParam(value = "订单ID", required = true, example = "1")
            @RequestParam Long id) { return Result.success(saleService.findById(id)); }

    @ApiOperation("查询订单明细")
    @GetMapping("/getItems")
    public Result<List<SaleItemVO>> getItems(
            @ApiParam(value = "订单ID", required = true, example = "1")
            @RequestParam Long id) { return Result.success(saleService.getSaleItems(id)); }

    @ApiOperation("根据日期查询订单")
    @GetMapping("/getByDate")
    public Result<List<SaleVO>> getByDate(
            @ApiParam(value = "日期（yyyy-MM-dd）", required = true, example = "2024-06-03")
            @RequestParam String date) { return Result.success(saleService.findByDate(date)); }

    @ApiOperation("根据商户+日期查询订单")
    @GetMapping("/getByMerchantAndDate")
    public Result<List<SaleVO>> getByMerchantAndDate(
            @ApiParam(value = "商户ID", required = true)
            @RequestParam Long merchantId,
            @ApiParam(value = "日期（yyyy-MM-dd）", required = true)
            @RequestParam String date) { return Result.success(saleService.findByMerchantAndDate(merchantId, date)); }
}
