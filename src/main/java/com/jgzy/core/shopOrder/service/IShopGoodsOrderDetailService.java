package com.jgzy.core.shopOrder.service;

import com.baomidou.mybatisplus.service.IService;
import com.jgzy.core.shopOrder.vo.ShopgoodsOrderDetailVo;
import com.jgzy.entity.po.ShopGoodsOrderDetail;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zou
 * @since 2018-10-25
 */
public interface IShopGoodsOrderDetailService extends IService<ShopGoodsOrderDetail> {

    /**
     * 查询商品详情
     *
     * @param orderId 订单id
     * @return 商品订单详情
     */
    List<ShopgoodsOrderDetailVo> selectMyShopGoodsDetailList(Integer orderId);
}
