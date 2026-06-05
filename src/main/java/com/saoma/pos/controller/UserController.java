package com.saoma.pos.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.saoma.pos.common.Result;
import com.saoma.pos.pojo.dto.UserLoginDTO;
import com.saoma.pos.pojo.dto.UserSaveDTO;
import com.saoma.pos.pojo.vo.LoginVO;
import com.saoma.pos.pojo.vo.UserVO;
import com.saoma.pos.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "用户管理")
@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public Result<LoginVO> login(
            @ApiParam(value = "登录参数", required = true)
            @Valid @RequestBody UserLoginDTO dto) {
        LoginVO vo = userService.login(dto);
        if (vo == null) {
            return Result.error(401, "用户名或密码错误");
        }
        return Result.success(vo);
    }

    @ApiOperation("获取全部用户列表（超级管理员）")
    @GetMapping("/list")
    public Result<List<UserVO>> list() { return Result.success(userService.findAll()); }

    @ApiOperation("根据商户ID获取用户列表")
    @GetMapping("/listByMerchant")
    public Result<List<UserVO>> listByMerchant(
            @ApiParam(value = "商户ID", required = true)
            @RequestParam Long merchantId) {
        return Result.success(userService.findByMerchantId(merchantId));
    }

    @ApiOperation("分页获取用户列表")
    @GetMapping("/page")
    public Result<Page<UserVO>> page(
            @ApiParam(value = "商户ID（超级管理员传null）")
            @RequestParam(required = false) Long merchantId,
            @ApiParam(value = "页码", defaultValue = "1")
            @RequestParam(defaultValue = "1") int page,
            @ApiParam(value = "每页数量", defaultValue = "20")
            @RequestParam(defaultValue = "20") int pageSize,
            @ApiParam(value = "搜索关键词（用户名/姓名/手机号）")
            @RequestParam(required = false) String keyword) {
        Page<UserVO> result = userService.page(merchantId, page, pageSize, keyword);
        return Result.success(result);
    }

    @ApiOperation("新增或更新用户")
    @PostMapping("/save")
    public Result<Integer> save(
            @ApiParam(value = "用户信息", required = true)
            @Valid @RequestBody UserSaveDTO dto) { return Result.success(userService.save(dto)); }

    @ApiOperation("删除用户")
    @DeleteMapping("/delete")
    public Result<Integer> delete(
            @ApiParam(value = "用户ID", required = true, example = "1")
            @RequestParam Long id) { return Result.success(userService.deleteById(id)); }
}
