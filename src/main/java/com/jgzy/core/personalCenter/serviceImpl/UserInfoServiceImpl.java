package com.jgzy.core.personalCenter.serviceImpl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jgzy.core.personalCenter.mapper.UserInfoMapper;
import com.jgzy.core.personalCenter.service.IUserInfoService;
import com.jgzy.core.personalCenter.vo.PersonalCenterVo;
import com.jgzy.entity.common.UserUuidThreadLocal;
import com.jgzy.entity.po.UserInfo;
import com.jgzy.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zou
 * @since 2018-10-15
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public PersonalCenterVo statistics() {
        Integer id = UserUuidThreadLocal.get().getId();
        String now = DateUtil.getNow();
        PersonalCenterVo personalCenterVo = userInfoMapper.statistics(id, now);
        return personalCenterVo;
    }
}
