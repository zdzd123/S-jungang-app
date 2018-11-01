package com.jgzy.entity.common;


import com.jgzy.entity.po.UserInfo;

/**
 * 从拦截器中获取userInfo对象
 *
 * @author zou
 */
public class UserUuidThreadLocal {
    // 把构造函数私有，外面不能new，只能通过下面两个方法操作
    private UserUuidThreadLocal() {

    }

    private static final ThreadLocal<UserInfo> LOCAL = new ThreadLocal<UserInfo>();

    public static void set(UserInfo userInfo) {
        LOCAL.set(userInfo);
    }

    public static UserInfo get() {
        return LOCAL.get();
    }
}
