package com.jgzy.constant;

import java.math.BigDecimal;

public class BaseConstant {
    public static final String SESSION_KEY = "userInfo";
    public static final String SESSION_KEY_TRACE = "userOperationRecord";
    public final static String CHARSET = "UTF-8";

    // 逻辑用错误flag
    public static final String ERROR_KEY = "error";

    // 不是特定商品
    public static final int NOT_SPECIAL = 0;
    // 特定商品
    public static final int IS_SPECIAL = 1;

    // 合伙人审核状态(0-正常 1-锁定 2-待审核 3-未通过
    public static final int ORIGINATOR_INFO_STATUS_0 = 0;
    // 商品评价状态(待审核=1|已审核=2)
    public static final int COMMENT_STATUS_2 = 2;
    // 优惠券状态(未使用=1|已使用=2)
    public static final int COUPON_STATE_1 = 1;
    public static final int COUPON_STATE_2 = 2;
    // 支付方式(积分=9|支付宝=1|微信支付=2|权额支付=3|余额支付=4|权额余额混合支付=5|品牌费=6)
    public static final int PAY_TYPE_1 = 1;
    public static final int PAY_TYPE_2 = 2;
    public static final int PAY_TYPE_3 = 3;
    public static final int PAY_TYPE_4 = 4;
    public static final int PAY_TYPE_5 = 5;
    public static final int PAY_TYPE_6 = 6;
    public static final int PAY_TYPE_9 = 9;
    // 运费(卖家承担运费=1|买家承担运费=2)
    public static final int FREIGHT_2 = 2;
    // 订单状态(待审核=0待付款=1|已付款=2|待成团=61|团失败=62|
    // 待发货=3|待收货=4|待评价=5|已评价=6|待退款=7|已退款=8|待退货=9|已退货=10|交易关闭=11|已逾期=12)"
    public static final int ORDER_STATUS_0 = 0;
    public static final int ORDER_STATUS_1 = 1;
    public static final int ORDER_STATUS_3 = 3;
    public static final int ORDER_STATUS_4 = 4;
    public static final int ORDER_STATUS_6 = 6;
    public static final int ORDER_STATUS_11 = 11;
    public static final int ORDER_STATUS_12 = 12;
    // 流水交易方式(收入=1|支出=2)
    public static final int TRADE_TYPE_1 = 1;
    public static final int TRADE_TYPE_2 = 2;
    // 1-消费，2-转入，3-转出，4-收益，5-退款，\n" +
    // 6-已提现，61-提现退回，62-提现冻结，\n" +
    // 7-合伙人佣金，71-合伙人佣金冻结，72-合伙人佣金冻结到期转出，73-合伙人佣金退回\n" +
    // 8-消费者佣金，81-消费者佣金冻结，82-消费者佣金冻结到期转出，83-消费者佣金退回\n" +
    // 9-自己消费佣金，91-自己消费佣金冻结，92-自己消费佣金冻结到期转出，93-自己消费佣金退回
    public static final int BUSSINESS_TYPE_1 = 1;
    public static final int BUSSINESS_TYPE_2 = 2;
    public static final int BUSSINESS_TYPE_4 = 4;
    public static final int BUSSINESS_TYPE_5 = 5;
    public static final int BUSSINESS_TYPE_7 = 7;
    public static final int BUSSINESS_TYPE_8 = 8;
    public static final int BUSSINESS_TYPE_62 = 62;
    // 1-积分，2-余额，3-冻结，4-微信，5-支付宝，6-提现，7-荣誉值，8-权额， 9-品牌费
    public static final int ACCOUNT_TYPE_1 = 1;
    public static final int ACCOUNT_TYPE_2 = 2;
    public static final int ACCOUNT_TYPE_3 = 3;
    public static final int ACCOUNT_TYPE_4 = 4;
    public static final int ACCOUNT_TYPE_8 = 8;
    public static final int ACCOUNT_TYPE_9 = 9;
    // 状态(0-正常 1-锁定 2-待审核 3-未通过)
    public static final int ORIGINATOR_STATUS_2 = 2;
    // 跳转类型 0-无 1-商品分类 2-商品详情 3-活动详情 4-礼包详情 5-单页
    public static final int PIC_VALUE_TYPE_1 = 1;
    public static final int PIC_VALUE_TYPE_2 = 2;
    public static final int PIC_VALUE_TYPE_3 = 3;
    public static final int PIC_VALUE_TYPE_4 = 4;
    public static final int PIC_VALUE_TYPE_5 = 5;
    // 广告位置(首页轮播图=6 非活动轮播图=7 活动分类展示图=8 无=1)
    public static final int ADVERT_SITE_6 = 6;
    public static final int ADVERT_SITE_7 = 7;
    public static final int ADVERT_SITE_8 = 8;
    // 是否预售(否=1，是=2)
    public static final int PRE_SELL_2 = 2;
    public static final int PRE_SELL_1 = 1;
    // 品牌费返还比例
    public static final BigDecimal ORIGINATOR_RATE = new BigDecimal("0.02");
    // 合伙人订单前缀
    public static final String PRE_ORDER = "HHR";
    // 库存订单前缀
    public static final String PRE_ORDER_STOCK = "KC";
    // 运费标识 1-到付 2-等待计算 3-包邮
    public static final int CARRIAGE_TYPE_1 = 1;
    public static final int CARRIAGE_TYPE_2 = 2;
    public static final int CARRIAGE_TYPE_3 = 3;
    // 是否放入库存 1=不存入 2=存入 3=自提
    public static final int IS_STOCK_1 = 1;
    public static final int IS_STOCK_2 = 2;
    public static final int IS_STOCK_3 = 3;
    // 订单来源 合伙人=1|库存=2|普通消费者=3
    public static final int ORDER_SOURCE_1 = 1;
    public static final int ORDER_SOURCE_2 = 2;
    public static final int ORDER_SOURCE_3 = 3;
}
