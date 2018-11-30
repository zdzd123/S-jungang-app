package com.jgzy.core.shopOrder.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jgzy.core.shopOrder.vo.ShopGoodsCommentVo;
import com.jgzy.entity.po.ShopGoodsComment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zou
 * @since 2018-10-19
 */
@Repository
public interface ShopGoodsCommentMapper extends BaseMapper<ShopGoodsComment> {

    List<ShopGoodsCommentVo> selectMyGoodsCommentPage(Page<ShopGoodsCommentVo> page, @Param("shopGoodsId") Integer shopGoodsId);
}
