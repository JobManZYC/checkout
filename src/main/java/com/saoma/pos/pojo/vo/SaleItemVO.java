package com.saoma.pos.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(description = "订单明细项响应")
public class SaleItemVO implements Serializable {

    @ApiModelProperty(value = "明细ID", example = "1")
    private Long id;

    @ApiModelProperty(value = "订单ID", example = "1")
    private Long saleId;

    @ApiModelProperty(value = "商户ID", example = "1")
    private Long merchantId;

    @ApiModelProperty(value = "商品ID", example = "1")
    private Long productId;

    @ApiModelProperty(value = "商品名称", example = "可口可乐")
    private String productName;

    @ApiModelProperty(value = "商品条码", example = "6921168511280")
    private String barcode;

    @ApiModelProperty(value = "单价", example = "3.50")
    private BigDecimal price;

    @ApiModelProperty(value = "数量", example = "2")
    private Integer quantity;

    @ApiModelProperty(value = "小计", example = "7.00")
    private BigDecimal subtotal;
}
