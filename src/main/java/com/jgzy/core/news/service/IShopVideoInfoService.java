package com.jgzy.core.news.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.jgzy.core.news.vo.ShopVideoInfoVo;
import com.jgzy.entity.po.ShopVideoInfo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zou
 * @since 2018-11-06
 */
public interface IShopVideoInfoService extends IService<ShopVideoInfo> {

    Boolean updatePlayTimes(Integer id);

    /**
     * 查询视频商品
     *
     * @param id 视频id
     * @return 视屏商品
     */
    ShopVideoInfoVo selectMyShopVideo(Integer id);

    /**
     * 查询视频列表
     *
     * @param page
     * @param platformVideoCategoryId
     * @param type
     * @return
     */
    Page<ShopVideoInfo> selectMyPage(Page<ShopVideoInfo> page, String platformVideoCategoryId, String type);
}
