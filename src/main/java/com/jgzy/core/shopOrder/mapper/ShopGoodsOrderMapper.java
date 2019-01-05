package com.jgzy.core.shopOrder.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jgzy.core.shopOrder.vo.ShopGoodsOrderStatisticVo;
import com.jgzy.core.shopOrder.vo.ShopGoodsOrderVo;
import com.jgzy.core.shopOrder.vo.ShopgoodsOrderDetailVo;
import com.jgzy.entity.po.ShopGoodsOrder;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zou
 * @since 2018-10-19
 */
@Repository
public interface ShopGoodsOrderMapper extends BaseMapper<ShopGoodsOrder> {
    /**
     * 查询商品订单，包含详情和属性
     *
     * @param orderId 订单id
     * @return 订单
     */
    ShopGoodsOrderVo selectMyOrder(@Param("orderId") Integer orderId);

    /**
     * 查询商品订单信息
     *
     * @param page        分页
     * @param orderStatus 订单状态
     * @param orderSource 订单来源
     * @param id
     * @param orderNo
     * @return 商品订单
     */
    List<ShopGoodsOrderVo> selectOrderPage(@Param("userId") Integer userId, Page<ShopGoodsOrderVo> page, @Param("orderStatus") Integer orderStatus,
                                           @Param("list") String[] orderSource, @Param("id") Integer id, @Param("orderNo") String orderNo);

    /**
     * 查询商品详情和属性
     *
     * @param id 订单id
     * @return 商品详情和属性
     */
    List<ShopgoodsOrderDetailVo> selectDetailAndAttribute(Integer id);

    /**
     * 查询单条的商品订单
     *
     * @param id      id
     * @param orderNo 订单编号
     * @return 商品信息
     */
    ShopGoodsOrderVo selectOneOrder(@Param("id") Integer id, @Param("orderNo") String orderNo);

    /**
     * 统计订单个数
     *
     * @param id 用户ID
     * @return 个数
     */
    List<ShopGoodsOrderStatisticVo> statistics(@Param("id") Integer id);
}
