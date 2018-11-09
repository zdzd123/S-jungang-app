package com.jgzy.entity.po;

import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author zou
 * @since 2018-11-06
 */
@ApiModel("资讯信息-po")
@TableName("news_info")
public class NewsInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "id", example = "1")
    private Integer id;
    /**
     * 资讯分类id
     */
    @ApiModelProperty(value = "资讯分类id", example = "1")
    private Integer newsCategoryId;
    /**
     * 标题
     */
    @ApiModelProperty(value = "标题", example = "1")
    private String title;
    /**
     * 发布人(系统用户id)
     */
    @ApiModelProperty(value = "发布人(系统用户id)", example = "1")
    private Integer releaseUserId;
    /**
     * 摘要
     */
    @ApiModelProperty(value = "摘要", example = "1")
    private String summary;
    /**
     * 资讯正文
     */
    @ApiModelProperty(value = "资讯正文", example = "1")
    private String newsInfoPaper;
    /**
     * 图片
     */
    @ApiModelProperty(value = "图片", example = "1")
    private String pic;
    /**
     * 状态(未发布=1|已发布=2)
     */
    @ApiModelProperty(value = "状态(未发布=1|已发布=2)", example = "1")
    private Integer status;
    /**
     * 发布时间
     */
    @ApiModelProperty(value = "发布时间", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date releaseTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNewsCategoryId() {
        return newsCategoryId;
    }

    public void setNewsCategoryId(Integer newsCategoryId) {
        this.newsCategoryId = newsCategoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getReleaseUserId() {
        return releaseUserId;
    }

    public void setReleaseUserId(Integer releaseUserId) {
        this.releaseUserId = releaseUserId;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getNewsInfoPaper() {
        return newsInfoPaper;
    }

    public void setNewsInfoPaper(String newsInfoPaper) {
        this.newsInfoPaper = newsInfoPaper;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Date releaseTime) {
        this.releaseTime = releaseTime;
    }

    @Override
    public String toString() {
        return "NewsInfo{" +
                ", id=" + id +
                ", newsCategoryId=" + newsCategoryId +
                ", title=" + title +
                ", releaseUserId=" + releaseUserId +
                ", summary=" + summary +
                ", newsInfoPaper=" + newsInfoPaper +
                ", pic=" + pic +
                ", status=" + status +
                ", releaseTime=" + releaseTime +
                "}";
    }
}
