package com.jgzy.core.shopOrder.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jgzy.core.shopOrder.vo.ShopStockVo;
import com.jgzy.entity.po.ShopStock;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zou
 * @since 2018-10-31
 */
@Repository
public interface ShopStockMapper extends BaseMapper<ShopStock> {

    ShopStockVo selectMyStock(@Param("platformGoodsCategoryId") Integer platformGoodsCategoryId, @Param("shopName")String shopName);

    ShopStockVo selectMyStockById(@Param("id") Integer id);
}
