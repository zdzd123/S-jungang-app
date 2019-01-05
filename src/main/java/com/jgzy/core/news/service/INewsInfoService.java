package com.jgzy.core.news.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.jgzy.entity.po.NewsInfo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zou
 * @since 2018-11-06
 */
public interface INewsInfoService extends IService<NewsInfo> {
    /**
     * 查询资讯列表
     *
     * @param page
     * @param newsCategoryId
     * @param type
     * @return
     */
    Page<NewsInfo> selectMyPage(Page<NewsInfo> page, String newsCategoryId, String type);
}
