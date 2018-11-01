package com.jgzy.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class WeiXinPayConfig {

    private static String app_id; // appid是微信公众账号或开放平台APP的唯一标识

    private static String app_secret;// AppSecret是APPID对应的接口密码

    private static String mch_id;// 微信支付商户号

    private static String key;// 交易过程生成签名的密钥

    private static String notify_url; // 服务器通知的页面文件路径 要用

    //@Value("${weixinpay.appid}")
    //public static String sslcert_path; // 证书路径
    //@Value("${weixinpay.appid}")
    //public static String sslcert_password; // 证书密码
    public static String getApp_id() {
        return app_id;
    }
    @Value("${weixinpay.appid}")
    public void setApp_id(String app_id) {
        WeiXinPayConfig.app_id = app_id;
    }

    public static String getApp_secret() {
        return app_secret;
    }
    @Value("${weixinpay.app_secret}")
    public void setApp_secret(String app_secret) {
        WeiXinPayConfig.app_secret = app_secret;
    }

    public static String getMch_id() {
        return mch_id;
    }
    @Value("${weixinpay.mch_id}")
    public void setMch_id(String mch_id) {
        WeiXinPayConfig.mch_id = mch_id;
    }

    public static String getKey() {
        return key;
    }
    @Value("${weixinpay.key}")
    public  void setKey(String key) {
        WeiXinPayConfig.key = key;
    }

    public static String getNotify_url() {
        return notify_url;
    }
    @Value("${weixinpay.notify_url}")
    public  void setNotify_url(String notify_url) {
        WeiXinPayConfig.notify_url = notify_url;
    }
}
