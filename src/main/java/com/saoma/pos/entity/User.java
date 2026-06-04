package com.saoma.pos.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;

@Data
@TableName("user")
@ApiModel(description = "系统用户")
public class User {
    @ApiModelProperty(value = "用户ID", example = "1")
    private Long id;

    @ApiModelProperty(value = "商户ID：0=超级管理员", example = "1")
    private Long merchantId;

    @ApiModelProperty(value = "用户名", required = true, example = "admin")
    private String username;

    @ApiModelProperty(value = "密码", required = true, example = "123456")
    private String password;

    @ApiModelProperty(value = "真实姓名", example = "管理员")
    private String realName;

    @ApiModelProperty(value = "手机号", example = "13800138000")
    private String phone;

    @ApiModelProperty(value = "角色：0-超级管理员 1-商户管理员 2-普通销售", example = "1")
    private Integer role;

    @ApiModelProperty(value = "状态：0-禁用 1-启用", example = "1")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
}
