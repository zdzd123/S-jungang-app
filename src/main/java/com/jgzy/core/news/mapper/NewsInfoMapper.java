package com.jgzy.core.news.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jgzy.entity.po.NewsInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zou
 * @since 2018-11-06
 */
@Repository
public interface NewsInfoMapper extends BaseMapper<NewsInfo> {
    /**
     * 查询列表
     * @param page
     * @param newsCategoryId
     * @param type
     * @return
     */
    List<NewsInfo> selectMyPage(Page<NewsInfo> page, @Param("newsCategoryId") String newsCategoryId, @Param("type") String type);
}
