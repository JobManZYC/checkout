package com.scan.pos.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "登录响应")
public class LoginVO implements Serializable {

    @ApiModelProperty(value = "JWT Token（后续请求放入 Authorization: Bearer <token>）")
    private String token;

    @ApiModelProperty(value = "用户ID", example = "1")
    private Long id;

    @ApiModelProperty(value = "用户名", example = "admin")
    private String username;

    @ApiModelProperty(value = "真实姓名", example = "管理员")
    private String realName;

    @ApiModelProperty(value = "角色：0-超级管理员 1-商户管理员 2-普通销售", example = "1")
    private Integer role;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "商户ID", example = "1")
    private Long merchantId;
}
