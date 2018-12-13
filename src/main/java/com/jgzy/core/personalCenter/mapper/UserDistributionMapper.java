package com.jgzy.core.personalCenter.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jgzy.core.personalCenter.vo.UserDistributionVo;
import com.jgzy.entity.po.UserDistribution;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zou
 * @since 2018-10-26
 */
@Repository
public interface UserDistributionMapper extends BaseMapper<UserDistribution> {

    /**
     * 查询合伙人团队列表
     *
     * @param page 分页
     * @param ids  id
     * @return 合伙人团队列表
     */
    List<UserDistributionVo> selectMyUserDistributionList(Page<UserDistributionVo> page, @Param("list") String[] ids);

    /**
     * 获取合伙人团队的ids
     *
     * @param id 合伙人
     * @return 合伙人团队
     */
    String selectMyUserDistributionIdList(@Param("id") Integer id);

    /**
     * 普通消费者ids
     *
     * @param id 用户id
     * @return 普通消费者ids
     */
    List<String> selectMyConsumerList(@Param("id") Integer id);

    /**
     * 合伙人ids
     *
     * @param id 用户id
     * @return 合伙人ids
     */
    List<String> selectMyPartner(@Param("id") Integer id);
}
