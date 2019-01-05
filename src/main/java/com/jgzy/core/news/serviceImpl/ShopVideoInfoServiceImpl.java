package com.jgzy.core.news.serviceImpl;

import com.baomidou.mybatisplus.plugins.Page;
import com.jgzy.core.news.mapper.ShopVideoInfoMapper;
import com.jgzy.core.news.service.IShopVideoInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jgzy.core.news.vo.ShopVideoInfoVo;
import com.jgzy.entity.po.ShopVideoInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zou
 * @since 2018-11-06
 */
@Service
public class ShopVideoInfoServiceImpl extends ServiceImpl<ShopVideoInfoMapper, ShopVideoInfo> implements IShopVideoInfoService {
    @Autowired
    private ShopVideoInfoMapper shopVideoInfoMapper;

    @Override
    public Boolean updatePlayTimes(Integer id) {
        return shopVideoInfoMapper.updatePlayTimes(id);
    }

    @Override
    public ShopVideoInfoVo selectMyShopVideo(Integer id) {
        return shopVideoInfoMapper.selectMyShopVideo(id);
    }

    @Override
    public Page<ShopVideoInfo> selectMyPage(Page<ShopVideoInfo> page, String platformVideoCategoryId, String type) {
        return page.setRecords(shopVideoInfoMapper.selectMyPage(page, platformVideoCategoryId, type));
    }
}
