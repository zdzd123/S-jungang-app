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
 *
 * </p>
 *
 * @author zou
 * @since 2018-10-24
 */
@ApiModel("微信认证信息-po")
@TableName("user_oauth")
public class UserOauth implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id", example = "1")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty(value = "用户id", example = "1")
    private Integer userId;
    @ApiModelProperty(value = "用户名称", example = "1")
    private String userName;
    @ApiModelProperty(value = "oauthName", example = "1")
    private String oauthName;
    @ApiModelProperty(value = "oauthAccessToken", example = "1")
    private String oauthAccessToken;
    @ApiModelProperty(value = "oauthOpenid", example = "1")
    private String oauthOpenid;
    @ApiModelProperty(value = "addTime", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date addTime;
    @ApiModelProperty(value = "oauthUnionid", example = "1")
    private String oauthUnionid;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOauthName() {
        return oauthName;
    }

    public void setOauthName(String oauthName) {
        this.oauthName = oauthName;
    }

    public String getOauthAccessToken() {
        return oauthAccessToken;
    }

    public void setOauthAccessToken(String oauthAccessToken) {
        this.oauthAccessToken = oauthAccessToken;
    }

    public String getOauthOpenid() {
        return oauthOpenid;
    }

    public void setOauthOpenid(String oauthOpenid) {
        this.oauthOpenid = oauthOpenid;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getOauthUnionid() {
        return oauthUnionid;
    }

    public void setOauthUnionid(String oauthUnionid) {
        this.oauthUnionid = oauthUnionid;
    }

    @Override
    public String toString() {
        return "UserOauth{" +
                ", id=" + id +
                ", userId=" + userId +
                ", userName=" + userName +
                ", oauthName=" + oauthName +
                ", oauthAccessToken=" + oauthAccessToken +
                ", oauthOpenid=" + oauthOpenid +
                ", addTime=" + addTime +
                ", oauthUnionid=" + oauthUnionid +
                "}";
    }
}
