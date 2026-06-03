package com.saoma.pos.controller;

import com.saoma.pos.common.Result;
import com.saoma.pos.entity.User;
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
        return Result.success(data);
    }

    @ApiOperation("获取全部用户列表")
    @GetMapping("/list")
    public Result<List<User>> list() { return Result.success(userService.findAll()); }

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
