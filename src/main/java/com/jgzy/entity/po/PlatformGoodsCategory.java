package com.jgzy.entity.po;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 * 平台商品分类
 * </p>
 *
 * @author zou
 * @since 2018-10-18
 */
@ApiModel("平台商品分类-po")
@TableName("platform_goods_category")
public class PlatformGoodsCategory implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "id", example = "1")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 上级商品分类id
     */
    @ApiModelProperty(value = "上级商品分类id", example = "1")
    private Integer parentId;
    /**
     * 展示图
     */
    @ApiModelProperty(value = "展示图", example = "1.jpg")
    private String pic;
    /**
     * icon图
     */
    @ApiModelProperty(value = "icon图", example = "1.jpg")
    private String iconPic;
    /**
     * 商品分类名称
     */
    @ApiModelProperty(value = "商品分类名称", example = "葡萄酒")
    private String name;
    /**
     * 菜单ID列表(逗号分隔开)
     */
    @ApiModelProperty(value = "菜单ID列表(逗号分隔开)", example = ",2,")
    private String classList;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", example = "1")
    private String remarks;
    /**
     * 分类是否显示(否=1|是=2)
     */
    @ApiModelProperty(value = "分类是否显示(否=1|是=2)", example = "1")
    private Integer isShow;
    /**
     * 导航深度
     */
    @ApiModelProperty(value = "导航深度", example = "1")
    private Integer classLayer;
    /**
     * 排序
     */
    @ApiModelProperty(value = "排序", example = "1")
    private Integer sortId;
    /**
     * 添加时间
     */
    @ApiModelProperty(value = "添加时间", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date addTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getIconPic() {
        return iconPic;
    }

    public void setIconPic(String iconPic) {
        this.iconPic = iconPic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassList() {
        return classList;
    }

    public void setClassList(String classList) {
        this.classList = classList;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    public Integer getClassLayer() {
        return classLayer;
    }

    public void setClassLayer(Integer classLayer) {
        this.classLayer = classLayer;
    }

    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    @Override
    public String toString() {
        return "PlatformGoodsCategory{" +
                ", id=" + id +
                ", parentId=" + parentId +
                ", pic=" + pic +
                ", iconPic=" + iconPic +
                ", name=" + name +
                ", classList=" + classList +
                ", remarks=" + remarks +
                ", isShow=" + isShow +
                ", classLayer=" + classLayer +
                ", sortId=" + sortId +
                ", addTime=" + addTime +
                "}";
    }
}
