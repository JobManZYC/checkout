package com.scan.pos.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@ApiModel(description = "结账下单请求")
public class SaleCheckoutDTO implements Serializable {

    @ApiModelProperty(value = "商户ID", required = true, example = "1")
    @NotNull(message = "商户ID不能为空")
    private Long merchantId;

    @ApiModelProperty(value = "总金额", example = "100.00")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "优惠金额", example = "5.00")
    private BigDecimal discountAmount;

    @ApiModelProperty(value = "实收金额", required = true, example = "95.00")
    @NotNull(message = "实收金额不能为空")
    private BigDecimal payAmount;

    @ApiModelProperty(value = "支付方式：1-现金 2-微信 3-支付宝 4-刷卡", required = true, example = "1")
    @NotNull(message = "支付方式不能为空")
    private Integer payType;

    @ApiModelProperty(value = "找零金额", example = "0.00")
    private BigDecimal changeAmount;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "收银员ID", example = "1")
    private Long cashierId;

    @ApiModelProperty(value = "收银员姓名", example = "张三")
    private String cashierName;

    @ApiModelProperty(value = "订单明细", required = true)
    @Valid
    @NotEmpty(message = "订单明细不能为空")
    private List<SaleItemDTO> items;
}
