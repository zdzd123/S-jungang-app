package com.jgzy.core.shopOrder.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jgzy.entity.po.ShopGoods;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zou
 * @since 2018-10-19
 */
@Repository
public interface ShopGoodsMapper extends BaseMapper<ShopGoods> {

    boolean updateStockById(@Param("count") Integer buyCount, @Param("id") Integer shopGoodsId);
}
