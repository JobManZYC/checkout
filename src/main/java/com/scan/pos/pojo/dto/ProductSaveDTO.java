package com.scan.pos.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(description = "商品新增/编辑请求")
public class ProductSaveDTO implements Serializable {

    @ApiModelProperty(value = "商品ID（编辑时必填）", example = "1")
    private Long id;

    @ApiModelProperty(value = "商户ID", required = true, example = "1")
    @NotNull(message = "商户ID不能为空")
    private Long merchantId;

    @ApiModelProperty(value = "商品条码", required = true, example = "6921168511280")
    @NotBlank(message = "商品条码不能为空")
    private String barcode;

    @ApiModelProperty(value = "商品名称", required = true, example = "可口可乐")
    @NotBlank(message = "商品名称不能为空")
    private String name;

    @ApiModelProperty(value = "商品分类", example = "饮料")
    private String category;

    @ApiModelProperty(value = "售价", required = true, example = "3.50")
    @NotNull(message = "售价不能为空")
    private BigDecimal price;

    @ApiModelProperty(value = "成本价", example = "2.80")
    private BigDecimal cost;

    @ApiModelProperty(value = "库存数量", example = "100")
    private Integer stock;

    @ApiModelProperty(value = "单位", example = "瓶")
    private String unit;

    @ApiModelProperty(value = "备注")
    private String remark;
}
