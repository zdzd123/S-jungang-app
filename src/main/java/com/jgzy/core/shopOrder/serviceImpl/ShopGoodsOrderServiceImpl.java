package com.jgzy.core.shopOrder.serviceImpl;

import com.baomidou.mybatisplus.plugins.Page;
import com.jgzy.constant.BaseConstant;
import com.jgzy.core.personalCenter.mapper.UserActivityCouponMapper;
import com.jgzy.core.shopOrder.mapper.ShopGoodsOrderMapper;
import com.jgzy.core.shopOrder.service.IShopGoodsOrderService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jgzy.core.shopOrder.vo.ShopGoodsOrderStatisticVo;
import com.jgzy.core.shopOrder.vo.ShopGoodsOrderVo;
import com.jgzy.core.shopOrder.vo.ShopgoodsOrderDetailVo;
import com.jgzy.entity.common.UserUuidThreadLocal;
import com.jgzy.entity.po.ShopGoodsOrder;
import com.jgzy.entity.po.UserActivityCoupon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zou
 * @since 2018-10-19
 */
@Service
public class ShopGoodsOrderServiceImpl extends ServiceImpl<ShopGoodsOrderMapper, ShopGoodsOrder> implements IShopGoodsOrderService {
    @Autowired
    private ShopGoodsOrderMapper shopGoodsOrderMapper;
    @Autowired
    private UserActivityCouponMapper userActivityCouponMapper;

    @Override
    public ShopGoodsOrderVo selectOneOrder(Integer id, String orderNo) {
//        ShopGoodsOrder shopGoodsOrder = new ShopGoodsOrder();
//        shopGoodsOrder.setId(id);
//        shopGoodsOrder.setOrderNo(orderNo);
//        // 查询单条
//        shopGoodsOrder = shopGoodsOrderMapper.selectOne(shopGoodsOrder);
//        ShopGoodsOrderVo shopGoodsOrderVo = shopGoodsOrderMapper.selectOneOrder(id, orderNo);
        Integer userId = UserUuidThreadLocal.get().getId();
        List<ShopGoodsOrderVo> shopGoodsOrderVoList = shopGoodsOrderMapper.selectOrderPage(userId, new Page<>(), null, null, id, orderNo);
        if (!CollectionUtils.isEmpty(shopGoodsOrderVoList)) {
            ShopGoodsOrderVo shopGoodsOrderVo = shopGoodsOrderVoList.get(0);
            // 优惠券
            getUserActivityCoupon(shopGoodsOrderVo);
            List<ShopgoodsOrderDetailVo> shopgoodsOrderDetailVoList = shopGoodsOrderMapper.selectDetailAndAttribute(shopGoodsOrderVo.getId());
            shopGoodsOrderVo.setShopgoodsOrderDetailVoList(shopgoodsOrderDetailVoList);
            return shopGoodsOrderVo;
        }
        return null;
    }

    /**
     * 获取商品优惠券
     *
     * @param shopGoodsOrderVo 商品信息
     */
    private void getUserActivityCoupon(ShopGoodsOrderVo shopGoodsOrderVo) {
        if (shopGoodsOrderVo.getShopActivityCouponId() != null) {
            UserActivityCoupon userActivityCoupon = userActivityCouponMapper.selectById(shopGoodsOrderVo.getShopActivityCouponId());
            if (userActivityCoupon != null) {
                // 优惠金额
                shopGoodsOrderVo.setAmount(userActivityCoupon.getAmount());
                // 满减要求
                shopGoodsOrderVo.setMeetAmount(userActivityCoupon.getMeetAmount());
            }
        }
    }

    @Override
    public Page<ShopGoodsOrderVo> selectMyOrderPage(Page<ShopGoodsOrderVo> page, Integer orderStatus, String orderSource) {
        String[] split = null;
        if (orderSource != null) {
            split = orderSource.split(",");
        }
        Integer id = UserUuidThreadLocal.get().getId();
        List<ShopGoodsOrderVo> shopGoodsOrderVoList = shopGoodsOrderMapper.selectOrderPage(id, page, orderStatus, split, null, null);
        for (ShopGoodsOrderVo shopGoodsOrderVo : shopGoodsOrderVoList) {
            // 优惠券
            getUserActivityCoupon(shopGoodsOrderVo);
            List<ShopgoodsOrderDetailVo> shopgoodsOrderDetailVoList = shopGoodsOrderMapper.selectDetailAndAttribute(shopGoodsOrderVo.getId());
            shopGoodsOrderVo.setShopgoodsOrderDetailVoList(shopgoodsOrderDetailVoList);
        }
        page.setRecords(shopGoodsOrderVoList);
        return page;
    }

    @Override
    public List<ShopGoodsOrderVo> selectMyOrder(Integer orderStatus, String orderSource) {
        String[] split = null;
        if (orderSource != null) {
            split = orderSource.split(",");
        }
        return shopGoodsOrderMapper.selectMyOrder(orderStatus, split);
    }

    @Override
    public List<ShopGoodsOrderStatisticVo> statistics() {
        return shopGoodsOrderMapper.statistics(UserUuidThreadLocal.get().getId());
    }
}
