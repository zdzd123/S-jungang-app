package com.jgzy.core.personalCenter.vo;

import com.jgzy.core.shopOrder.vo.ShopGoodsOrderStatisticVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel("个人中心展示模型-VO")
public class PersonalCenterVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "收藏数", example = "100")
    private Integer collectionCount;
    @ApiModelProperty(value = "足迹数", example = "100")
    private Integer footprintCount;
    @ApiModelProperty(value = "优惠券数", example = "100")
    private Integer userActivityCoupon;
    @ApiModelProperty(value = "用户姓名", example = "100")
    private String nickName;
    @ApiModelProperty(value = "用户头像", example = "100")
    private String headPortrait;
    @ApiModelProperty(value = "用户等级", example = "100")
    private String levelId;
    @ApiModelProperty(value = "订单数量", example = "100")
    private List<ShopGoodsOrderStatisticVo> shopGoodsOrderStatisticVolist;

    public List<ShopGoodsOrderStatisticVo> getShopGoodsOrderStatisticVolist() {
        return shopGoodsOrderStatisticVolist;
    }

    public void setShopGoodsOrderStatisticVolist(List<ShopGoodsOrderStatisticVo> shopGoodsOrderStatisticVolist) {
        this.shopGoodsOrderStatisticVolist = shopGoodsOrderStatisticVolist;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getLevelId() {
        return levelId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    public Integer getCollectionCount() {
        return collectionCount;
    }

    public void setCollectionCount(Integer collectionCount) {
        this.collectionCount = collectionCount;
    }

    public Integer getFootprintCount() {
        return footprintCount;
    }

    public void setFootprintCount(Integer footprintCount) {
        this.footprintCount = footprintCount;
    }

    public Integer getUserActivityCoupon() {
        return userActivityCoupon;
    }

    public void setUserActivityCoupon(Integer userActivityCoupon) {
        this.userActivityCoupon = userActivityCoupon;
    }
}
