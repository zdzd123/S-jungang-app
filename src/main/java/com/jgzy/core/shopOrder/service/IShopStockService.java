package com.jgzy.core.shopOrder.service;

import com.baomidou.mybatisplus.service.IService;
import com.jgzy.core.shopOrder.vo.ShopStockVo;
import com.jgzy.entity.po.ShopStock;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zou
 * @since 2018-10-31
 */
public interface IShopStockService extends IService<ShopStock> {

    ShopStockVo selectMyStock(Integer platformGoodsCategoryId, String shopName);
}
