package com.jgzy.core.personalCenter.service;

import com.baomidou.mybatisplus.service.IService;
import com.jgzy.entity.po.UserInfo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zou
 * @since 2018-10-10
 */
public interface ILoginService extends IService<UserInfo> {
    /**
     * 登录验证，成功后修改token并返回
     *
     * @param phoneNum
     * @param password
     * @return
     */
    String selectUserByPwd(String phoneNum, String password);
}
