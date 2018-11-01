package com.jgzy.core.personalCenter.service;

import com.baomidou.mybatisplus.service.IService;
import com.jgzy.core.personalCenter.vo.PersonalCenterVo;
import com.jgzy.entity.po.UserInfo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zou
 * @since 2018-10-15
 */
public interface IUserInfoService extends IService<UserInfo> {
    /**
     * 个人中心统计
     *
     * @return
     */
    PersonalCenterVo statistics();
}
