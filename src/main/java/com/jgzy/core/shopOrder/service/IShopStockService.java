package com.jgzy.core.shopOrder.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.jgzy.core.shopOrder.vo.ShopStockVo;
import com.jgzy.entity.po.ShopStock;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zou
 * @since 2018-10-31
 */
public interface IShopStockService extends IService<ShopStock> {

    Page<ShopStockVo> selectMyStock(Page<ShopStockVo> page, Integer platformGoodsCategoryId, String shopName);

    ShopStockVo selectMyStockById(Integer id);

    /**
     * 获取库存统计
     *
     * @return 库存统计
     */
    ShopStockVo getCountStock();

    /**
     * 更新商品库存
     *
     * @param buyCount    购买数量
     * @param shopGoodsId 商品id
     * @param userId
     * @return flag
     */
    boolean updateMyShopStockByGoodsId(Integer buyCount, Integer shopGoodsId, Integer userId);

    /**
     * 通过id或者商品id查询某个人的库存
     *
     * @param id          库存id
     * @param shopGoodsId 商品id
     * @return 库存
     */
    ShopStockVo selectMyStockByIdOrShopGoodsId(Integer id, Integer shopGoodsId);
}
