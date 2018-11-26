package com.jgzy.core.shopOrder.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jgzy.core.shopOrder.vo.ShopgoodsOrderDetailVo;
import com.jgzy.entity.po.ShopGoodsOrderDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zou
 * @since 2018-10-25
 */
@Repository
public interface ShopGoodsOrderDetailMapper extends BaseMapper<ShopGoodsOrderDetail> {
    /**
     * 查询商品详情
     *
     * @param orderId 订单id
     * @return 商品详情
     */
    List<ShopgoodsOrderDetailVo> selectMyShopGoodsDetailList(@Param("orderId") Integer orderId);
}
