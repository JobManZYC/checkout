package com.saoma.pos.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel(description = "用户新增/编辑请求")
public class UserSaveDTO implements Serializable {

    @ApiModelProperty(value = "用户ID（编辑时必填）", example = "1")
    private Long id;

    @ApiModelProperty(value = "商户ID：0=超级管理员", required = true, example = "1")
    @NotNull(message = "商户ID不能为空")
    private Long merchantId;

    @ApiModelProperty(value = "用户名", required = true, example = "admin")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @ApiModelProperty(value = "密码（新增时必填）", example = "123456")
    private String password;

    @ApiModelProperty(value = "真实姓名", example = "管理员")
    private String realName;

    @ApiModelProperty(value = "手机号", example = "13800138000")
    private String phone;

    @ApiModelProperty(value = "角色：0-超级管理员 1-商户管理员 2-普通销售", example = "1")
    private Integer role;
}
