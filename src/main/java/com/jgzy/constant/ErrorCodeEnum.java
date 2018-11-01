package com.jgzy.constant;

import java.util.Objects;

public enum ErrorCodeEnum {

    SUCCESS(0, "操作成功"),
    ERROR(9999, "系统忙，请稍后重试"),
    ERROR_INNER(9998, "内部错误"),
    ERROR_ARGS_FILL_IN(9001, "参数必填"),
    ERROR_ARGS_MISS(9002, "缺少必要参数"),
    ERROR_ARGS_TYPE(9003, "参数类型错误"),
    ERROR_ARGS(9004, "自定义错误"),

    ERROR_LOGIN_FAIL(8001, "登录失败,请检查账号密码"),
    ERROR_LOGIN_OUT(8002, "请重新登录"),

    SESSION_REMOVE_FAIL(9100, "session 移除失败"),

    // 91** 打头的错误生产不返回前端描述
    ERROR_JEDIS_CONNECTION_EXCEPTION(9100, "Redis 连接失败错误");


    public static String desc(Integer key) {
        for (ErrorCodeEnum errorCodeEnum : ErrorCodeEnum.values()) {
            Integer keyArg = errorCodeEnum.key;
            if (Objects.equals(key, keyArg)) {
                return errorCodeEnum.value;
            }
        }
        return "";
    }

    ErrorCodeEnum(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    Integer key;
    String value;


}
