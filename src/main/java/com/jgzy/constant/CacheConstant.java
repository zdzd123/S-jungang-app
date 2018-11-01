package com.jgzy.constant;

/**
 * 缓存key，统一管理 <br>
 * <font color="red">
 * <li> 每个key，标明涉及的表，便于更新表，删除对应的缓存</li>
 * <li> 目前缓存只针对Service层做缓存</li>
 * 注：如果Redis未启动，则带有Redis缓存的查询更新报错，但实际更新成功</li>
 * </font>
 */
public class CacheConstant {

    /**
     * 涉及的表：t_user_base
     */
    public static final String USERBASESERVICEIMPL_SELECTBYUSERNAME = "userBaseServiceImpl.selectByUserName";

}
