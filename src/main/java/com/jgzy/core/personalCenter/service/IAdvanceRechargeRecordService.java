package com.jgzy.core.personalCenter.service;

import com.baomidou.mybatisplus.service.IService;
import com.jgzy.entity.po.AdvanceRechargeRecord;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zou
 * @since 2018-10-24
 */
public interface IAdvanceRechargeRecordService extends IService<AdvanceRechargeRecord> {

    /**
     * 更新用户权额
     *
     * @param advanceRechargeRecord 修改权额
     * @return flag
     */
    boolean updateMyAdvanceRecharge(AdvanceRechargeRecord advanceRechargeRecord);
}
