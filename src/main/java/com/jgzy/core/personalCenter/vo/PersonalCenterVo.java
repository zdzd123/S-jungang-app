package com.jgzy.core.personalCenter.vo;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel("个人中心展示模型-VO")
public class PersonalCenterVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "收藏数", example = "100")
    private Integer collectionCount;
    @ApiModelProperty(value = "足迹数", example = "100")
    private Integer footprintCount;
    @ApiModelProperty(value = "优惠券数", example = "100")
    private Integer userActivityCoupon;

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
