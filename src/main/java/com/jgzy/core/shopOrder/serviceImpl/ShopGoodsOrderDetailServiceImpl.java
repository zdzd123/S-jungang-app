package com.jgzy.core.shopOrder.serviceImpl;

import com.jgzy.core.shopOrder.mapper.ShopGoodsOrderDetailMapper;
import com.jgzy.core.shopOrder.service.IShopGoodsOrderDetailService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jgzy.core.shopOrder.vo.ShopgoodsOrderDetailVo;
import com.jgzy.entity.po.ShopGoodsOrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zou
 * @since 2018-10-25
 */
@Service
public class ShopGoodsOrderDetailServiceImpl extends ServiceImpl<ShopGoodsOrderDetailMapper, ShopGoodsOrderDetail> implements IShopGoodsOrderDetailService {
    @Autowired
    private ShopGoodsOrderDetailMapper shopGoodsOrderDetailMapper;

    @Override
    public List<ShopgoodsOrderDetailVo> selectMyShopGoodsDetailList(Integer orderId) {
        return shopGoodsOrderDetailMapper.selectMyShopGoodsDetailList(orderId);
    }
}
