package com.jgzy.core.shopOrder.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jgzy.entity.po.UserFund;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zou
 * @since 2018-10-22
 */
@Repository
public interface UserFundMapper extends BaseMapper<UserFund> {

    void insertUserFund(UserFund userFund);
}
