package com.saoma.pos.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.saoma.pos.common.Result;
import com.saoma.pos.pojo.entity.User;
import com.saoma.pos.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "用户管理")
@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public Result<Map<String, Object>> login(
            @ApiParam(value = "登录参数（username, password）", required = true)
            @RequestBody Map<String, String> params) {
        String username = params.get("username");
        String password = params.get("password");
        User user = userService.login(username, password);
        if (user == null) return Result.error(401, "用户名或密码错误");
        Map<String, Object> data = new HashMap<>();
        data.put("id", user.getId());
        data.put("username", user.getUsername());
        data.put("realName", user.getRealName());
        data.put("role", user.getRole());
        data.put("merchantId", user.getMerchantId());
        return Result.success(data);
    }

    @ApiOperation("获取全部用户列表（超级管理员）")
    @GetMapping("/list")
    public Result<List<User>> list() { return Result.success(userService.findAll()); }

    @ApiOperation("根据商户ID获取用户列表")
    @GetMapping("/merchant/{merchantId}")
    public Result<List<User>> listByMerchant(
            @ApiParam(value = "商户ID", required = true)
            @PathVariable Long merchantId) {
        return Result.success(userService.findByMerchantId(merchantId));
    }

    @ApiOperation("分页获取用户列表")
    @GetMapping("/page")
    public Result<Page<User>> page(
            @ApiParam(value = "商户ID（超级管理员传null）")
            @RequestParam(required = false) Long merchantId,
            @ApiParam(value = "页码", defaultValue = "1")
            @RequestParam(defaultValue = "1") int page,
            @ApiParam(value = "每页数量", defaultValue = "20")
            @RequestParam(defaultValue = "20") int pageSize,
            @ApiParam(value = "搜索关键词（用户名/姓名/手机号）")
            @RequestParam(required = false) String keyword) {
        Page<User> result = userService.page(merchantId, page, pageSize, keyword);
        return Result.success(result);
    }

    @ApiOperation("新增或更新用户")
    @PostMapping("/save")
    public Result<Integer> save(
            @ApiParam(value = "用户信息", required = true)
            @RequestBody User user) { return Result.success(userService.save(user)); }

    @ApiOperation("删除用户")
    @DeleteMapping("/{id}")
    public Result<Integer> delete(
            @ApiParam(value = "用户ID", required = true, example = "1")
            @PathVariable Long id) { return Result.success(userService.deleteById(id)); }
}
