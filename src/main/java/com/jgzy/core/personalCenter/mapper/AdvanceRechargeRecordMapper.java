package com.jgzy.core.personalCenter.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jgzy.entity.po.AdvanceRechargeRecord;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zou
 * @since 2018-10-24
 */
@Repository
public interface AdvanceRechargeRecordMapper extends BaseMapper<AdvanceRechargeRecord> {
    /**
     * 更新我的权额信息
     *
     * @param advanceRechargeRecord 权额信息
     * @return flag
     */
    boolean updateMyAdvanceRecharge(AdvanceRechargeRecord advanceRechargeRecord);
}
