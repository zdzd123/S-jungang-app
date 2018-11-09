package com.jgzy.utils;

import java.math.BigDecimal;

/**
 * 微信[消息体]
 *
 * @author feel
 */
public class WeiXinNotify {

    String appid = "", total_fee = "", bank_type = "", cash_fee = "", fee_type = "", is_subscribe = "", mch_id = "", nonce_str = "";
    String out_trade_no = "", transaction_id = "", openid = "", sign = "", result_code = "", return_code = "", time_end = "", trade_type = "";
    String return_msg = "";

    //优惠券
    String coupon_count = "", coupon_fee = "", coupon_fee_0 = "", coupon_id_0 = "", coupon_fee_1 = "", coupon_id_1 = "", coupon_fee_2 = "", coupon_id_2 = "";

    public Double getTotal_fee() {
        return new BigDecimal(total_fee).doubleValue();
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public WeiXinNotify setResult(String result_code) {
        this.result_code = result_code;
        return this;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }

    public WeiXinNotify setResult(String result_code, String return_msg) {
        this.result_code = result_code;
        this.return_msg = return_msg;
        return this;
    }

    public WeiXinNotify setResultSuccess() {
        this.result_code = "SUCCESS";
        this.return_msg = "OK";
        return this;
    }

    public WeiXinNotify setResultFail(String return_msg) {
        this.result_code = "FAIL";
        this.return_msg = return_msg;
        return this;
    }

    public boolean checkSuccess() {
        if ("SUCCESS".equals(this.result_code)) {
            return true;
        } else if ("FAIL".equals(this.result_code)) {
            return false;
        }
        return false;
    }

    public String getBodyXML() {
        StringBuilder xml = new StringBuilder();
        xml.append("<xml>");
        xml.append("  <return_code><![CDATA[" + this.result_code + "]]></return_code>");
        xml.append("  <return_msg><![CDATA[" + this.return_msg + "]]></return_msg>");
        xml.append("</xml>");
        return xml.toString();
    }

    @Override
    public String toString() {
        return "WeiXinNotify{" +
                "appid='" + appid + '\'' +
                ", total_fee='" + total_fee + '\'' +
                ", bank_type='" + bank_type + '\'' +
                ", cash_fee='" + cash_fee + '\'' +
                ", fee_type='" + fee_type + '\'' +
                ", is_subscribe='" + is_subscribe + '\'' +
                ", mch_id='" + mch_id + '\'' +
                ", nonce_str='" + nonce_str + '\'' +
                ", out_trade_no='" + out_trade_no + '\'' +
                ", transaction_id='" + transaction_id + '\'' +
                ", openid='" + openid + '\'' +
                ", sign='" + sign + '\'' +
                ", result_code='" + result_code + '\'' +
                ", return_code='" + return_code + '\'' +
                ", time_end='" + time_end + '\'' +
                ", trade_type='" + trade_type + '\'' +
                ", return_msg='" + return_msg + '\'' +
                ", coupon_count='" + coupon_count + '\'' +
                ", coupon_fee='" + coupon_fee + '\'' +
                ", coupon_fee_0='" + coupon_fee_0 + '\'' +
                ", coupon_id_0='" + coupon_id_0 + '\'' +
                ", coupon_fee_1='" + coupon_fee_1 + '\'' +
                ", coupon_id_1='" + coupon_id_1 + '\'' +
                ", coupon_fee_2='" + coupon_fee_2 + '\'' +
                ", coupon_id_2='" + coupon_id_2 + '\'' +
                '}';
    }
}
