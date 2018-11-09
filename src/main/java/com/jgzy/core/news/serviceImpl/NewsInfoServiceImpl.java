package com.jgzy.core.news.serviceImpl;

import com.jgzy.core.news.mapper.NewsInfoMapper;
import com.jgzy.core.news.service.INewsInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jgzy.entity.po.NewsInfo;
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

}
