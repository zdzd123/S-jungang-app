package com.jgzy.core.shopOrder.vo;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author zou
 * @since 2018-10-19
 */
@ApiModel("商品评价-vo")
public class ShopGoodsCommentVo{

    @ApiModelProperty(value = "id", example = "1")
    private Integer id;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 商品信息id
     */
    @ApiModelProperty(value = "商品信息id", example = "1")
    private Integer shopGoodsId;
    /**
     * 宝贝描述(1.0~5.0)
     */
    @ApiModelProperty(value = "宝贝描述(1.0~5.0)", example = "1.0")
    private Double descriptionScore;
    /**
     * 卖家服务(1.0~5.0)
     */
    @ApiModelProperty(value = "卖家服务(1.0~5.0)", example = "1.0")
    private Double serviceScore;
    /**
     * 物流描述(1.0~5.0)
     */
    @ApiModelProperty(value = "物流描述(1.0~5.0)", example = "1.0")
    private Double materialFlowScore;
    /**
     * 评价人(用户id)
     */
    @ApiModelProperty(value = "评价人(用户id)", example = "1")
    private Integer appraiser;
    /**
     * 评价内容
     */
    @ApiModelProperty(value = "评价内容", example = "很好")
    private String appraiseContent;
    /**
     * 评价图片(多图","分隔)
     */
    @ApiModelProperty(value = "评价图片(多图\",\"分隔)", example = "1.png")
    private String appraisePic;
    /**
     * 评价时间
     */
    @ApiModelProperty(value = "评价时间", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date appraiseTime;
    /**
     * 状态(待审核=1|已审核=2)
     */
    @ApiModelProperty(value = "状态(待审核=1|已审核=2)", example = "1")
    private Integer status;
    @ApiModelProperty(value = "评价数量", example = "100")
    private Integer commentCount;
    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称", example = "昵称")
    private String nickname;
    /**
     * 头像
     */
    @ApiModelProperty(value = "头像", example = "/1.png")
    private String headPortrait;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getShopGoodsId() {
        return shopGoodsId;
    }

    public void setShopGoodsId(Integer shopGoodsId) {
        this.shopGoodsId = shopGoodsId;
    }

    public Double getDescriptionScore() {
        return descriptionScore;
    }

    public void setDescriptionScore(Double descriptionScore) {
        this.descriptionScore = descriptionScore;
    }

    public Double getServiceScore() {
        return serviceScore;
    }

    public void setServiceScore(Double serviceScore) {
        this.serviceScore = serviceScore;
    }

    public Double getMaterialFlowScore() {
        return materialFlowScore;
    }

    public void setMaterialFlowScore(Double materialFlowScore) {
        this.materialFlowScore = materialFlowScore;
    }

    public Integer getAppraiser() {
        return appraiser;
    }

    public void setAppraiser(Integer appraiser) {
        this.appraiser = appraiser;
    }

    public String getAppraiseContent() {
        return appraiseContent;
    }

    public void setAppraiseContent(String appraiseContent) {
        this.appraiseContent = appraiseContent;
    }

    public String getAppraisePic() {
        return appraisePic;
    }

    public void setAppraisePic(String appraisePic) {
        this.appraisePic = appraisePic;
    }

    public Date getAppraiseTime() {
        return appraiseTime;
    }

    public void setAppraiseTime(Date appraiseTime) {
        this.appraiseTime = appraiseTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ShopGoodsComment{" +
                ", id=" + id +
                ", shopGoodsId=" + shopGoodsId +
                ", descriptionScore=" + descriptionScore +
                ", serviceScore=" + serviceScore +
                ", materialFlowScore=" + materialFlowScore +
                ", appraiser=" + appraiser +
                ", appraiseContent=" + appraiseContent +
                ", appraisePic=" + appraisePic +
                ", appraiseTime=" + appraiseTime +
                ", status=" + status +
                "}";
    }
}
