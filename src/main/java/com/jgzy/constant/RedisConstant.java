package com.jgzy.constant;

import java.math.BigDecimal;

public class RedisConstant {
    // redis user
    public static final String REDIS_USER_KEY = "jgUser";
    // redis 登录过期时间 24小时
    public static final Long REDIS_LOGIN_TIME_OUT = 24*60*60l;
    // redis accessToken过期时间 24小时
    public static final Long REDIS_ACCESSTOKEN_TIME_OUT = new BigDecimal(1.5*60*60).longValue();
    // 微信 accessToken
    public static final String REDIS_ACCESS_TOKEN = "jgAccessToken";
}
