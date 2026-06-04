package com.saoma.pos.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel(description = "商户新增/编辑请求")
public class MerchantSaveDTO implements Serializable {

    @ApiModelProperty(value = "商户ID（编辑时必填）", example = "1")
    private Long id;

    @ApiModelProperty(value = "商户名称", required = true, example = "测试便利店")
    @NotBlank(message = "商户名称不能为空")
    private String name;

    @ApiModelProperty(value = "联系人", example = "张三")
    private String contactName;

    @ApiModelProperty(value = "联系电话", example = "13800138000")
    private String contactPhone;

    @ApiModelProperty(value = "地址", example = "北京市朝阳区")
    private String address;

    @ApiModelProperty(value = "备注")
    private String remark;
}
