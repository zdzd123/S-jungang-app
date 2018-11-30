package com.jgzy.core.shopOrder.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("订单统计-vo")
public class ShopGoodsOrderStatisticVo {
    @ApiModelProperty(value = "订单数量",example = "1")
    private String count;
    @ApiModelProperty(value = "订单状态(待审核=0待付款=1|已付款=2|待成团=61|团失败=62|" +
            "待发货=3|待收货=4|待评价=5|已评价=6|待退款=7|已退款=8|待退货=9|已退货=10|交易关闭=11|已逾期=12)\"",example = "1")
    private String orderStatus;
    @ApiModelProperty(value = "订单来源 1-合伙人 2-消费者",example = "1")
    private String orderSource;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
    }
}
