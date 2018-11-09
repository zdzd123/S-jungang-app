package com.jgzy.core.personalCenter.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jgzy.core.personalCenter.vo.PersonalCenterVo;
import com.jgzy.entity.po.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zou
 * @since 2018-10-15
 */
@Repository
public interface UserInfoMapper extends BaseMapper<UserInfo> {
    PersonalCenterVo statistics(@Param("id") Integer id, @Param("now") String now);

    /**
     * 更新股权基金
     *
     * @param id
     * @param stockRight
     * @return
     */
    boolean updateStockRight(@Param("id") Integer id, @Param("stockRight") BigDecimal stockRight);

    /**
     * 更新佣金
     *
     * @param id
     * @param commission
     * @return
     */
    boolean updateCommissionDiscount(@Param("id") Integer id, @Param("commission") BigDecimal commission);
}
