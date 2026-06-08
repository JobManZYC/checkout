package com.scan.pos.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("merchant")
@ApiModel(description = "商户信息")
public class Merchant extends BaseEntity {

    @ApiModelProperty(value = "商户名称", required = true, example = "测试便利店")
    private String name;

    @ApiModelProperty(value = "联系人", example = "张三")
    private String contactName;

    @ApiModelProperty(value = "联系电话", example = "13800138000")
    private String contactPhone;

    @ApiModelProperty(value = "地址", example = "北京市朝阳区")
    private String address;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "状态：0-禁用 1-启用", example = "1")
    private Integer status;
}
