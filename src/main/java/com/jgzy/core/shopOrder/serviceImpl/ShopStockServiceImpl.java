package com.jgzy.core.shopOrder.serviceImpl;

import com.jgzy.core.shopOrder.mapper.ShopStockMapper;
import com.jgzy.core.shopOrder.service.IShopStockService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jgzy.core.shopOrder.vo.ShopStockVo;
import com.jgzy.entity.po.ShopStock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zou
 * @since 2018-10-31
 */
@Service
public class ShopStockServiceImpl extends ServiceImpl<ShopStockMapper, ShopStock> implements IShopStockService {
    @Autowired
    private ShopStockMapper shopStockMapper;

    @Override
    public ShopStockVo selectMyStock(Integer platformGoodsCategoryId, String shopName) {
         return  shopStockMapper.selectMyStock(platformGoodsCategoryId, shopName);
    }

    @Override
    public ShopStockVo selectMyStockById(Integer id) {
        return shopStockMapper.selectMyStockById(id);
    }
}
