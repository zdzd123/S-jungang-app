package com.jgzy.core.shopOrder.serviceImpl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jgzy.core.shopOrder.mapper.ShopGoodsCommentMapper;
import com.jgzy.core.shopOrder.service.IShopGoodsCommentService;
import com.jgzy.core.shopOrder.vo.ShopGoodsCommentVo;
import com.jgzy.entity.common.UserUuidThreadLocal;
import com.jgzy.entity.po.ShopGoodsComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zou
 * @since 2018-10-19
 */
@Service
public class ShopGoodsCommentServiceImpl extends ServiceImpl<ShopGoodsCommentMapper, ShopGoodsComment> implements IShopGoodsCommentService {
    @Autowired
    private ShopGoodsCommentMapper shopGoodsCommentMapper;

    @Override
    public Page<ShopGoodsCommentVo> selectMyGoodsCommentPage(Page<ShopGoodsCommentVo> page, Integer shopGoodsId) {
        List<ShopGoodsCommentVo> shopGoodsCommentVoList = shopGoodsCommentMapper.selectMyGoodsCommentPage(page, shopGoodsId);
        page.setRecords(shopGoodsCommentVoList);
        return page;
    }
}
