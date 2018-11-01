package com.jgzy.core.personalCenter.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jgzy.entity.po.UserGoodsCollect;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zou
 * @since 2018-10-17
 */
@Repository
public interface UserGoodsCollectMapper extends BaseMapper<UserGoodsCollect> {

    List<UserGoodsCollect> selectUserGoodsCollectByUserId(Page<UserGoodsCollect> page, Integer id);
}
