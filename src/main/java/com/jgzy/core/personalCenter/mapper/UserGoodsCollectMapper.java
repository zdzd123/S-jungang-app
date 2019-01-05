package com.jgzy.core.personalCenter.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jgzy.core.personalCenter.vo.UserGoodsCollectionVo;
import com.jgzy.entity.po.UserGoodsCollect;
import org.apache.ibatis.annotations.Param;
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

    List<UserGoodsCollectionVo> selectUserGoodsCollectByUserId(Page<UserGoodsCollectionVo> page,
                                                               @Param("id") Integer id, @Param("type") String type);
}
