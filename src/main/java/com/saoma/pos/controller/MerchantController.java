package com.saoma.pos.controller;

import com.saoma.pos.common.Result;
import com.saoma.pos.entity.Merchant;
import com.saoma.pos.service.MerchantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "商户管理（超级管理员）")
@RestController
@RequestMapping("/api/merchant")
@CrossOrigin
public class MerchantController {

    @Autowired
    private MerchantService merchantService;

    @ApiOperation("获取全部商户列表")
    @GetMapping("/list")
    public Result<List<Merchant>> list() {
        return Result.success(merchantService.findAll());
    }

    @ApiOperation("根据ID查询商户")
    @GetMapping("/{id}")
    public Result<Merchant> getById(
            @ApiParam(value = "商户ID", required = true, example = "1")
            @PathVariable Long id) {
        return Result.success(merchantService.findById(id));
    }

    @ApiOperation("新增或更新商户")
    @PostMapping("/save")
    public Result<Integer> save(
            @ApiParam(value = "商户信息", required = true)
            @RequestBody Merchant merchant) {
        return Result.success(merchantService.save(merchant));
    }

    @ApiOperation("删除商户")
    @DeleteMapping("/{id}")
    public Result<Integer> delete(
            @ApiParam(value = "商户ID", required = true, example = "1")
            @PathVariable Long id) {
        return Result.success(merchantService.deleteById(id));
    }
}
