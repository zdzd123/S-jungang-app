package com.jgzy.core.news.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jgzy.entity.po.ShopVideoInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zou
 * @since 2018-11-06
 */
@Repository
public interface ShopVideoInfoMapper extends BaseMapper<ShopVideoInfo> {

    Boolean updatePlayTimes(@Param("id") Integer id);
}