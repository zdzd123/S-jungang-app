package com.jgzy.core.news.serviceImpl;

import com.baomidou.mybatisplus.plugins.Page;
import com.jgzy.core.news.mapper.NewsInfoMapper;
import com.jgzy.core.news.service.INewsInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jgzy.entity.po.NewsInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zou
 * @since 2018-11-06
 */
@Service
public class NewsInfoServiceImpl extends ServiceImpl<NewsInfoMapper, NewsInfo> implements INewsInfoService {
    @Autowired
    private NewsInfoMapper newsInfoMapper;

    @Override
    public Page<NewsInfo> selectMyPage(Page<NewsInfo> page, String newsCategoryId, String type) {
        return page.setRecords(newsInfoMapper.selectMyPage(page,newsCategoryId,type));
    }
}
