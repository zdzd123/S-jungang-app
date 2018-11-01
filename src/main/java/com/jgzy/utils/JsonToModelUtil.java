package com.jgzy.utils;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

/**
 * @program: ahomet-app
 * @description: json对象转Model
 * @author: yaobing
 * @create: 2018-07-19 13:31
 */
public class JsonToModelUtil {
    public static <T> T parseJsonToModel(String jsonData, Class<T> type) {
        T result = JSON.parseObject(jsonData, type);
        return result;
    }

    public static <T> T parseJsonWithGson(String jsonData, Class<T> type) {
        Gson gson = new Gson();
        T result = gson.fromJson(jsonData, type);
        return result;

    }
}