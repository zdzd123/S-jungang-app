package com.jgzy.core.personalCenter.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jgzy.entity.po.UserStockFund;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 用户库存流水 Mapper 接口
 * </p>
 *
 * @author zou
 * @since 2019-01-11
 */
@Repository
public interface UserStockFundMapper extends BaseMapper<UserStockFund> {
    /**
     * 插入库存流水
     *
     * @param userStockFund 库存流水
     */
    void insertMyUserStockFund(UserStockFund userStockFund);
}
