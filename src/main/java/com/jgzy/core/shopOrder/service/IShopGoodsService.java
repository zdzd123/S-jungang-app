package com.jgzy.core.shopOrder.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.jgzy.core.shopOrder.vo.ShopGoodsVo;
import com.jgzy.entity.po.ShopGoods;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zou
 * @since 2018-10-19
 */
public interface IShopGoodsService extends IService<ShopGoods> {

    Page<ShopGoodsVo> selectPageVo(Page<ShopGoodsVo> page, EntityWrapper<ShopGoods> entityWrapper);
}
