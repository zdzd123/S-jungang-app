package com.jgzy.core.shopOrder.service;

import com.baomidou.mybatisplus.service.IService;
import com.jgzy.core.shopOrder.vo.AdvertInfoVo;
import com.jgzy.entity.po.AdvertInfo;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zou
 * @since 2018-10-18
 */
public interface IAdvertInfoService extends IService<AdvertInfo> {

    /**
     * 查询图片信息
     *
     * @param id
     * @param site
     * @return
     */
    List<AdvertInfoVo> getAdvertInfo(Integer id, Integer site);
}
