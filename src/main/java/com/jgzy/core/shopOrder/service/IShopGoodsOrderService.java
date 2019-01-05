package com.jgzy.core.shopOrder.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.jgzy.core.shopOrder.vo.ShopGoodsOrderStatisticVo;
import com.jgzy.core.shopOrder.vo.ShopGoodsOrderVo;
import com.jgzy.entity.po.ShopGoodsOrder;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zou
 * @since 2018-10-19
 */
public interface IShopGoodsOrderService extends IService<ShopGoodsOrder> {

    /**
     * 查询商品订单包含详情和属性
     *
     * @param page        分页
     * @param orderStatus 订单状态
     * @param orderSource 订单来源
     * @return 订单
     */
    Page<ShopGoodsOrderVo> selectMyOrderPage(Page<ShopGoodsOrderVo> page, Integer orderStatus, String orderSource);

    /**
     * 查询商品订单包含详情和属性
     *
     * @param orderId 订单id
     * @return 订单
     */
    ShopGoodsOrderVo selectMyOrder(Integer orderId);

    /**
     * 查询单个商品订单包含详情和属性
     *
     * @param id      订单id
     * @param orderNo 订单编号
     * @param role
     * @return 商品订单
     */
    ShopGoodsOrderVo selectOneOrder(Integer id, String orderNo, String role);

    /**
     * 统计订单数量
     *
     * @return 统计
     */
    List<ShopGoodsOrderStatisticVo> statistics();

    /**
     * 给管理员发送订单消息
     *
     * @param shopGoodsOrder 订单消息
     */
    void sendOrderTemplateToManager(ShopGoodsOrder shopGoodsOrder);
}
