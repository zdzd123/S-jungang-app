package com.jgzy.core.personalCenter.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jgzy.entity.po.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zou
 * @since 2018-10-10
 */
@Repository
public interface LoginMapper extends BaseMapper<UserInfo> {
    List<UserInfo> selectUserCountByPwd(@Param("phoneNum") String phoneNum, @Param("password") String password);

    int updateTokenByPwd(@Param("phoneNum") String phoneNum, @Param("password") String password, @Param("token") String token);
}
