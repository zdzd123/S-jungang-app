package com.jgzy.core.personalCenter.serviceImpl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jgzy.constant.BaseConstant;
import com.jgzy.constant.RedisConstant;
import com.jgzy.core.personalCenter.mapper.LoginMapper;
import com.jgzy.core.personalCenter.service.ILoginService;
import com.jgzy.entity.po.UserInfo;
import com.jgzy.utils.CommonUtil;
import com.jgzy.utils.MD5Gen;
import com.jgzy.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author beyond
 * @since 2018-10-10
 */
@Service
public class LoginServiceImpl extends ServiceImpl<LoginMapper, UserInfo> implements ILoginService {

    @Autowired
    private LoginMapper loginMapper;

    @Transactional()
    @Override
    public String selectUserByPwd(String phoneNum, String password) {
        // 登录验证
        List<UserInfo> userInfoList = loginMapper.selectUserCountByPwd(phoneNum, password);
        if (userInfoList.size() == 1) {
            // redis delete token
            RedisUtil.hdel(RedisConstant.REDIS_USER_KEY, userInfoList.get(0).getToken());
            String token = CommonUtil.getUUID();
            // 修改token
            int updateCount = loginMapper.updateTokenByPwd(phoneNum, password, token);
            if (updateCount != 1) {
                throw new OptimisticLockingFailureException(BaseConstant.ERROR_KEY);
            }
            userInfoList.get(0).setToken(token);
            // TODO redis add token 测试用不加过期时间
            //RedisUtil.hset(RedisConstant.REDIS_USER_KEY, token, userInfoList.get(0), RedisConstant.REDIS_TIME_OUT);
            RedisUtil.hset(RedisConstant.REDIS_USER_KEY, token, userInfoList.get(0));
            return token;
        }
        return BaseConstant.ERROR_KEY;
    }

}
