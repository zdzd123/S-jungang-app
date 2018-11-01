package com.jgzy.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

/**
 * 支付宝配置
 * 
 * @author feel
 * 
 */
@Component
public class AlipayConfig {
    public final static String URL = "https://openapi.alipay.com/gateway.do"; // 请求网关地址
	public final static String CHARSET = "UTF-8"; // 编码
	public final static String FORMAT = "json"; // 返回格式
    // 超时时间
    public final static String TIMEOUT_EXPRESS = "2m";
    // 销售产品码
    public final static String PRODUCT_CODE = "QUICK_WAP_PAY";

    @Value("${alipay.appid}")
	private String APPID; // 商户appid
	@Value("${alipay.rsa_private_key}")
    private String RSA_PRIVATE_KEY; // 私钥 pkcs8格式的
	@Value("${alipay.rsa_public_key}")
    private String RSA_PUBLIC_KEY; // 退款公钥 pkcs8格式的
	@Value("${alipay.notify_url}")
    private String NOTIFY_URL; //服务器异步通知页面路径
	@Value("${alipay.return_url}")
    private String RETURN_URL; // 商户可以自定义同步跳转地址
	// 支付宝公钥
	@Value("${alipay.alipay_public_key}")
    private String ALIPAY_PUBLIC_KEY;
	@Value("${alipay.type}")
    private String TYPE;
    /** RSA2 */
    @Value("${alipay.sign_type}")
    private String SIGN_TYPE;

    public String getAPPID() {
        return APPID;
    }

    public String getRSA_PRIVATE_KEY() {
        return RSA_PRIVATE_KEY;
    }

    public String getRSA_PUBLIC_KEY() {
        return RSA_PUBLIC_KEY;
    }

    public String getNOTIFY_URL() {
        return NOTIFY_URL;
    }

    public String getRETURN_URL() {
        return RETURN_URL;
    }

    public String getALIPAY_PUBLIC_KEY() {
        return ALIPAY_PUBLIC_KEY;
    }

    public String getTYPE() {
        return TYPE;
    }

    public String getSIGN_TYPE() {
        return SIGN_TYPE;
    }
}
