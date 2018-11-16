package com.jgzy.core.personalCenter.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.jgzy.core.personalCenter.vo.UserGoodsFootprintVo;
import com.jgzy.entity.po.UserGoodsFootprint;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zou
 * @since 2018-10-17
 */
public interface IUserGoodsFootprintService extends IService<UserGoodsFootprint> {

    /**
     * 获取个人足迹
     *
     * @return
     */
    Page<UserGoodsFootprintVo> getUserGoodsFootprint(Page<UserGoodsFootprintVo> page);

    /**
     * 新增用户足迹
     *
     * @param po 足迹
     * @return flag
     */
    boolean insertMyFoot(UserGoodsFootprint po);
}
