package com.jgzy.core.shopOrder.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.jgzy.core.shopOrder.vo.ShopGoodsCommentVo;
import com.jgzy.entity.po.ShopGoodsComment;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zou
 * @since 2018-10-19
 */
public interface IShopGoodsCommentService extends IService<ShopGoodsComment> {
    /**
     * 查询商品评价和用户信息
     * @param page
     * @param shopGoodsId
     * @return
     */
    Page<ShopGoodsCommentVo> selectMyGoodsCommentPage(Page<ShopGoodsCommentVo> page, Integer shopGoodsId);
}
