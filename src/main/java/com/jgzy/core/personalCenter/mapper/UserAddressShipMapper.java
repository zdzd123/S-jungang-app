package com.jgzy.core.personalCenter.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jgzy.core.personalCenter.vo.UserAddressVo;
import com.jgzy.entity.po.UserAddressShip;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zou
 * @since 2018-10-17
 */
@Repository
public interface UserAddressShipMapper extends BaseMapper<UserAddressShip> {
    List<UserAddressVo> selectUserAddressShip(Page<UserAddressVo> page, @Param("id") Integer id);

    UserAddressVo selectDetailById(@Param("shipAddressId") Integer shipAddressId);

    List<UserAddressVo> selectMyList(@Param("id") Integer id, @Param("search") String search);
}
