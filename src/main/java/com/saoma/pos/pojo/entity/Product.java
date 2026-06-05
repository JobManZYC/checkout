package com.saoma.pos.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("product")
@ApiModel(description = "商品信息")
public class Product extends BaseEntity {

    @ApiModelProperty(value = "商品ID", example = "1")
    private Long id;

    @ApiModelProperty(value = "商户ID", example = "1")
    private Long merchantId;

    @ApiModelProperty(value = "商品条码", required = true, example = "6921168511280")
    private String barcode;

    @ApiModelProperty(value = "商品名称", required = true, example = "可口可乐")
    private String name;

    @ApiModelProperty(value = "商品分类", example = "饮料")
    private String category;

    @ApiModelProperty(value = "售价", required = true, example = "3.50")
    private BigDecimal price;

    @ApiModelProperty(value = "成本价", example = "2.80")
    private BigDecimal cost;

    @ApiModelProperty(value = "库存数量", example = "100")
    private Integer stock;

    @ApiModelProperty(value = "单位", example = "瓶")
    private String unit;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "状态：0-下架 1-上架", example = "1")
    private Integer status;
}
