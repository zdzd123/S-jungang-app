package com.jgzy.core.news.serviceImpl;

import com.jgzy.core.news.mapper.NewsCategoryMapper;
import com.jgzy.core.news.service.INewsCategoryService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jgzy.entity.po.NewsCategory;
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
public class NewsCategoryServiceImpl extends ServiceImpl<NewsCategoryMapper, NewsCategory> implements INewsCategoryService {

}
