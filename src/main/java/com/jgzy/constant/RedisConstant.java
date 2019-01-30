package com.jgzy.constant;

public class RedisConstant {
    // redis user
    public static final String REDIS_USER_KEY = "jgUser";
    // redis 登录过期时间 24小时
    public static final Long REDIS_LOGIN_TIME_OUT = 24 * 60 * 60l;
    // redis 缓存accessToken 过期时间 1个半小时
    public static final Long ACCESS_TOKEN_TIME_OUT = 7000l;
    // 微信 accessToken
    public static final String REDIS_ACCESS_TOKEN = "jgAccessToken";
}
