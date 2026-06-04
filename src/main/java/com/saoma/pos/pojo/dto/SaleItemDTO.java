package com.saoma.pos.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(description = "订单明细项请求")
public class SaleItemDTO implements Serializable {

    @ApiModelProperty(value = "商品ID", required = true, example = "1")
    @NotNull(message = "商品ID不能为空")
    private Long productId;

    @ApiModelProperty(value = "商品名称", example = "可口可乐")
    private String productName;

    @ApiModelProperty(value = "商品条码", example = "6921168511280")
    private String barcode;

    @ApiModelProperty(value = "单价", required = true, example = "3.50")
    @NotNull(message = "单价不能为空")
    private BigDecimal price;

    @ApiModelProperty(value = "数量", required = true, example = "2")
    @NotNull(message = "数量不能为空")
    private Integer quantity;

    @ApiModelProperty(value = "小计", example = "7.00")
    private BigDecimal subtotal;
}
