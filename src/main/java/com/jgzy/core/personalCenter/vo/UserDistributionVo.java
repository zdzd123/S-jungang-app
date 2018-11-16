package com.jgzy.core.personalCenter.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("合伙人列表-vo")
public class UserDistributionVo {
    @ApiModelProperty(value = "id", example = "1")
    private String userId;
    @ApiModelProperty(value = "头像", example = "1")
    private String memberHeadPortrait;
    @ApiModelProperty(value = "名称", example = "1")
    private String memberName;
    @ApiModelProperty(value = "手机", example = "1")
    private String phone;
    @ApiModelProperty(value = "等级", example = "1")
    private String levelId;
    @ApiModelProperty(value = "添加时间", example = "1")
    private String addTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMemberHeadPortrait() {
        return memberHeadPortrait;
    }

    public void setMemberHeadPortrait(String memberHeadPortrait) {
        this.memberHeadPortrait = memberHeadPortrait;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLevelId() {
        return levelId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }
}
