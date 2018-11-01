package com.jgzy.core.personalCenter.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jgzy.core.personalCenter.vo.PersonalCenterVo;
import com.jgzy.entity.po.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zou
 * @since 2018-10-15
 */
@Repository
public interface UserInfoMapper extends BaseMapper<UserInfo> {
    PersonalCenterVo statistics(@Param("id") Integer id, @Param("now") String now);
}
