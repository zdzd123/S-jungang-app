package com.jgzy.core.personalCenter.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.jgzy.core.personalCenter.vo.UserGoodsCollectionVo;
import com.jgzy.entity.po.UserGoodsCollect;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zou
 * @since 2018-10-17
 */
public interface IUserGoodsCollectService extends IService<UserGoodsCollect> {

    /**
     * 查询个人收藏
     *
     * @return
     * @param page
     * @param type
     */
    Page<UserGoodsCollectionVo> getUserGoodsCollectByUserId(Page<UserGoodsCollectionVo> page, String type);
}
