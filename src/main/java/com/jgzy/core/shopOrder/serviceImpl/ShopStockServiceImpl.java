package com.jgzy.core.shopOrder.serviceImpl;

import com.baomidou.mybatisplus.plugins.Page;
import com.jgzy.core.shopOrder.mapper.ShopStockMapper;
import com.jgzy.core.shopOrder.service.IShopStockService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jgzy.core.shopOrder.vo.ShopStockVo;
import com.jgzy.entity.common.UserUuidThreadLocal;
import com.jgzy.entity.po.ShopStock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public Page<ShopStockVo> selectMyStock(Page<ShopStockVo> page, Integer platformGoodsCategoryId, String shopName) {
        Integer id = UserUuidThreadLocal.get().getId();
        List<ShopStockVo> shopStockVoList = shopStockMapper.selectMyStock(page, platformGoodsCategoryId, shopName, id);
        page.setRecords(shopStockVoList);
        return page;
    }

    @Override
    public ShopStockVo selectMyStockById(Integer id) {
        return shopStockMapper.selectMyStockById(id);
    }

    @Override
    public ShopStockVo getCountStock() {
        Integer id = UserUuidThreadLocal.get().getId();
        return shopStockMapper.selectCountStock(id);
    }

    @Override
    public boolean updateMyShopStockByGoodsId(Integer buyCount, Integer shopGoodsId, Integer userId) {
        return shopStockMapper.updateMyShopStockByGoodsId(buyCount, shopGoodsId, userId);
    }

    @Override
    public ShopStockVo selectMyStockByIdOrShopGoodsId(Integer id, Integer shopGoodsId) {
        if (id == null && shopGoodsId == null){
            return null;
        }else {
            return shopStockMapper.selectMyStockByIdOrShopGoodsId(id, shopGoodsId, UserUuidThreadLocal.get().getId());
        }
    }
}
