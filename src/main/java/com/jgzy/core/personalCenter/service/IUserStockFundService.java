package com.jgzy.core.personalCenter.service;

import com.baomidou.mybatisplus.service.IService;
import com.jgzy.entity.po.UserStockFund;

import java.util.List;

/**
 * <p>
 * 用户库存流水 服务类
 * </p>
 *
 * @author zou
 * @since 2019-01-11
 */
public interface IUserStockFundService extends IService<UserStockFund> {
    /**
     * 批量插入库存流水
     *
     * @param userStockFundList 库存流水
     */
    void insertMyUserStockBatch(List<UserStockFund> userStockFundList);

    /**
     * 插入库存流水
     *
     * @param userStockFund 库存流水
     */
    void insertMyUserStock(UserStockFund userStockFund);
}
