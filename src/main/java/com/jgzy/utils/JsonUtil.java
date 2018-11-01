package com.jgzy.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.List;
import java.util.Map;

/**
 * @program: haojihui-app
 * @description: json 类型转换
 * @author: yongli.liu
 * @create: 2018-07-12 10:13
 */
public class JsonUtil {

    /**
     * json 转换为Map
     * @param json
     * @return
     */
    public static Map<String,Object> toMap(String json) {
        Map<String,Object> map = JSON.parseObject(json,new TypeReference<Map<String,Object>>(){});
        return map;
    }
    /**
     * 转换为Map转换为json
     * @param map
     * @return
     */
    static String toString(Map<String,Object> map) {
        String str = JSON.toJSONString(map);
        return str;
    }

    /**
     * 转换为Map转换为json
     * @param map
     * @return
     */
    public static String toString2(Map<String, String> map) {
        String str = JSON.toJSONString(map);
        return str;
    }
    /**
     * 转化Json到Map
     * @param json
     * @return
     */
    public static List<Map> toList(String json) {
        List<Map> list = JSON.parseArray(json,Map.class);
        return list;
    }
}