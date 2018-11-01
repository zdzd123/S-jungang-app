package com.jgzy.core.shopOrder.service;

import com.baomidou.mybatisplus.service.IService;
import com.jgzy.entity.po.UserFund;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zou
 * @since 2018-10-22
 */
public interface IUserFundService extends IService<UserFund> {

    void InsertUserFund(UserFund userFund);
}
