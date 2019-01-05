package com.jgzy.entity.common;


import com.jgzy.entity.po.UserInfo;

/**
 * 从拦截器中获取userInfo对象
 *
 * @author zou
 */
public class UserUuidThreadLocal {
    private static final ThreadLocal<UserInfo> LOCAL = new ThreadLocal<>();

    public static void set(UserInfo userInfo) {
        LOCAL.set(userInfo);
    }

    public static UserInfo get() {
        return LOCAL.get();
    }

    public static void remove() {
        LOCAL.remove();
    }
}
