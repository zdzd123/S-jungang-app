package com.jgzy.core.shopOrder.serviceImpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Iterables;
import com.jgzy.core.shopOrder.mapper.ShopGoodsAttributeMapper;
import com.jgzy.core.shopOrder.mapper.ShopGoodsMapper;
import com.jgzy.core.shopOrder.service.IShopGoodsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jgzy.core.shopOrder.vo.ShopGoodsVo;
import com.jgzy.entity.po.ShopGoods;
import com.jgzy.entity.po.ShopGoodsAttribute;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zou
 * @since 2018-10-19
 */
@Service
public class ShopGoodsServiceImpl extends ServiceImpl<ShopGoodsMapper, ShopGoods> implements IShopGoodsService {
    @Autowired
    private ShopGoodsMapper shopGoodsMapper;
    @Autowired
    private ShopGoodsAttributeMapper shopGoodsAttributeMapper;

    @Override
    public Page<ShopGoodsVo> selectPageVo(Page<ShopGoodsVo> page, EntityWrapper<ShopGoods> entityWrapper) {
        List<ShopGoods> shopGoodslist = shopGoodsMapper.selectPage(page, entityWrapper);
        List<ShopGoodsVo> shopGoodsVoList = new ArrayList<>();
        for (ShopGoods shopGoods:shopGoodslist){
            ShopGoodsVo shopGoodsVo = new ShopGoodsVo();
            List<ShopGoodsAttribute> shopGoodsAttributeList = shopGoodsAttributeMapper.selectList(
                    new EntityWrapper<ShopGoodsAttribute>()
                            .eq("shop_goods_id", shopGoods.getId()));
            BeanUtils.copyProperties(shopGoods, shopGoodsVo);
            shopGoodsVo.setShopGoodsAttributeList(shopGoodsAttributeList);
            shopGoodsAttributeList.removeIf(next -> StringUtils.isEmpty(next.getAttributeValueName()) || next.getAttributeValueName().equals("下拉选择"));
            shopGoodsVoList.add(shopGoodsVo);
        }
        page.setRecords(shopGoodsVoList);
        return page;
    }

    @Override
    public boolean updateStockById(Integer buyCount, Integer shopGoodsId) {
        return shopGoodsMapper.updateStockById(buyCount, shopGoodsId);
    }
}
