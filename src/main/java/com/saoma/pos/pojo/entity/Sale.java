package com.saoma.pos.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@TableName("sale")
@ApiModel(description = "销售订单")
public class Sale {
    @ApiModelProperty(value = "订单ID", example = "1")
    private Long id;

    @ApiModelProperty(value = "商户ID", example = "1")
    private Long merchantId;

    @ApiModelProperty(value = "订单号", example = "202406030001")
    private String orderNo;

    @ApiModelProperty(value = "总金额", example = "100.00")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "优惠金额", example = "5.00")
    private BigDecimal discountAmount;

    @ApiModelProperty(value = "实收金额", example = "95.00")
    private BigDecimal payAmount;

    @ApiModelProperty(value = "支付方式：1-现金 2-微信 3-支付宝 4-刷卡", example = "1")
    private Integer payType;

    @ApiModelProperty(value = "找零金额", example = "0.00")
    private BigDecimal changeAmount;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "状态：0-已取消 1-已完成", example = "1")
    private Integer status;

    @ApiModelProperty(value = "收银员ID", example = "1")
    private Long cashierId;

    @ApiModelProperty(value = "收银员姓名", example = "张三")
    private String cashierName;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "订单明细")
    @TableField(exist = false)
    private List<SaleItem> items;
}
