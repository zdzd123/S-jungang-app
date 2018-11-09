package com.jgzy.core.personalCenter.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jgzy.entity.po.UserDistributionConstant;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zou
 * @since 2018-11-09
 */
@Repository
public interface UserDistributionConstantMapper extends BaseMapper<UserDistributionConstant> {

    UserDistributionConstant selectMyDistributionById(@Param("id") Integer id);

    UserDistributionConstant selectParentDistributionById(@Param("id")Integer id);
}
