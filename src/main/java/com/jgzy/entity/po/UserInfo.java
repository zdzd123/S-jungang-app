package com.jgzy.entity.po;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author zou
 * @since 2018-10-15
 */

@ApiModel("用户模型-po")
@TableName("user_info")
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id", example = "1")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 账号
     */
    @ApiModelProperty(value = "账号", example = "111")
    private String accountNumber;
    /**
     * 密码
     */
    @ApiModelProperty(value = "密码", example = "111")
    private String passWord;
    /**
     * token
     */
    @ApiModelProperty(value = "token", example = "1111")
    private String token;
    /**
     * 验证码
     */
    @ApiModelProperty(value = "验证码", example = "111")
    private String code;
    /**
     * 状态(有效=1|锁定=2)
     */
    @ApiModelProperty(value = "状态(有效=1|锁定=2)", example = "1")
    private Integer validStatus;
    /**
     * 会员等级id
     */
    @ApiModelProperty(value = "会员等级id", example = "1")
    private Integer userLevelId;
    /**
     * 会员标签(names)
     */
    @ApiModelProperty(value = "会员标签(names)", example = "黄金")
    private String userTagIds;
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
    /**
     * 积分
     */
    @ApiModelProperty(value = "积分", example = "11")
    private BigDecimal point;
    /**
     * 余额1(可提现)
     */
    @ApiModelProperty(value = "余额1(可提现)", example = "11")
    private BigDecimal balance1;
    /**
     * 我麾下——合伙人——消费
     */
    @ApiModelProperty(value = "我麾下——合伙人——消费", example = "11")
    private BigDecimal balance2;
    /**
     * 我麾下——消费者——消费
     */
    @ApiModelProperty(value = "我麾下——消费者——消费", example = "11")
    private BigDecimal balance3;
    /**
     * 冻结金额
     */
    @ApiModelProperty(value = "冻结金额", example = "11")
    private BigDecimal balance4;
    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号", example = "18311111111")
    private String phone;
    /**
     * 邮件
     */
    @ApiModelProperty(value = "邮件", example = "111111111@qq.com")
    private String email;
    /**
     * 注册时间
     */
    @ApiModelProperty(value = "注册时间", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date registerTime;
    /**
     * 验证码发送时间
     */
    @ApiModelProperty(value = "验证码发送时间", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date effectiveTime;
    /**
     * 性别（1=男|2=女
     */
    @ApiModelProperty(value = "性别（1=男|2=女", example = "1")
    private Integer gender;
    /**
     * 生日
     */
    @ApiModelProperty(value = "生日", example = "2018-06-29 09:17:54")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date birthday;
    @ApiModelProperty(value = "occupation", example = "occupation")
    private String occupation;
    @ApiModelProperty(value = "earning", example = "earning")
    private String earning;
    @ApiModelProperty(value = "userName", example = "userName")
    private String userName;
    /**
     * 子账号(“，”分开）
     */
    @ApiModelProperty(value = "子账号(“，”分开）", example = "1,2")
    private String telExten;
    /**
     * 累计消费 +++2017-06-01
     */
    @ApiModelProperty(value = "累计消费", example = "11")
    private BigDecimal accumulatedConsumption;
    /**
     * 累计积分 +++2017-06-01
     */
    @ApiModelProperty(value = "累计积分", example = "11")
    private Integer accumulatedPoint;
    @ApiModelProperty(value = "commission", example = "11")
    private BigDecimal commission;
    /**
     * 用户来源  1=IOS|2=Android|3=微信|4=后台
     */
    @ApiModelProperty(value = "用户来源  1=IOS|2=Android|3=微信|4=后台", example = "1")
    private Integer fromSource;
    @ApiModelProperty(value = "isRobot", example = "1")
    private Integer isRobot;
    @ApiModelProperty(value = "isGetCoupon", example = "1")
    private Integer isGetCoupon;
    /**
     * 是否不在提醒 0=提醒，1=不在提醒
     */
    @ApiModelProperty(value = "是否不在提醒 0=提醒，1=不在提醒", example = "1")
    private Integer isRemind;
    /**
     * 慈善度
     */
    @ApiModelProperty(value = "慈善度", example = "1")
    private BigDecimal charitable;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(Integer validStatus) {
        this.validStatus = validStatus;
    }

    public Integer getUserLevelId() {
        return userLevelId;
    }

    public void setUserLevelId(Integer userLevelId) {
        this.userLevelId = userLevelId;
    }

    public String getUserTagIds() {
        return userTagIds;
    }

    public void setUserTagIds(String userTagIds) {
        this.userTagIds = userTagIds;
    }

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

    public BigDecimal getPoint() {
        return point;
    }

    public void setPoint(BigDecimal point) {
        this.point = point;
    }

    public BigDecimal getBalance1() {
        return balance1;
    }

    public void setBalance1(BigDecimal balance1) {
        this.balance1 = balance1;
    }

    public BigDecimal getBalance2() {
        return balance2;
    }

    public void setBalance2(BigDecimal balance2) {
        this.balance2 = balance2;
    }

    public BigDecimal getBalance3() {
        return balance3;
    }

    public void setBalance3(BigDecimal balance3) {
        this.balance3 = balance3;
    }

    public BigDecimal getBalance4() {
        return balance4;
    }

    public void setBalance4(BigDecimal balance4) {
        this.balance4 = balance4;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public Date getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(Date effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getEarning() {
        return earning;
    }

    public void setEarning(String earning) {
        this.earning = earning;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTelExten() {
        return telExten;
    }

    public void setTelExten(String telExten) {
        this.telExten = telExten;
    }

    public BigDecimal getAccumulatedConsumption() {
        return accumulatedConsumption;
    }

    public void setAccumulatedConsumption(BigDecimal accumulatedConsumption) {
        this.accumulatedConsumption = accumulatedConsumption;
    }

    public Integer getAccumulatedPoint() {
        return accumulatedPoint;
    }

    public void setAccumulatedPoint(Integer accumulatedPoint) {
        this.accumulatedPoint = accumulatedPoint;
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    public Integer getFromSource() {
        return fromSource;
    }

    public void setFromSource(Integer fromSource) {
        this.fromSource = fromSource;
    }

    public Integer getIsRobot() {
        return isRobot;
    }

    public void setIsRobot(Integer isRobot) {
        this.isRobot = isRobot;
    }

    public Integer getIsGetCoupon() {
        return isGetCoupon;
    }

    public void setIsGetCoupon(Integer isGetCoupon) {
        this.isGetCoupon = isGetCoupon;
    }

    public Integer getIsRemind() {
        return isRemind;
    }

    public void setIsRemind(Integer isRemind) {
        this.isRemind = isRemind;
    }

    public BigDecimal getCharitable() {
        return charitable;
    }

    public void setCharitable(BigDecimal charitable) {
        this.charitable = charitable;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                ", id=" + id +
                ", accountNumber=" + accountNumber +
                ", passWord=" + passWord +
                ", token=" + token +
                ", code=" + code +
                ", validStatus=" + validStatus +
                ", userLevelId=" + userLevelId +
                ", userTagIds=" + userTagIds +
                ", nickname=" + nickname +
                ", headPortrait=" + headPortrait +
                ", point=" + point +
                ", balance1=" + balance1 +
                ", balance2=" + balance2 +
                ", balance3=" + balance3 +
                ", balance4=" + balance4 +
                ", phone=" + phone +
                ", email=" + email +
                ", registerTime=" + registerTime +
                ", effectiveTime=" + effectiveTime +
                ", gender=" + gender +
                ", birthday=" + birthday +
                ", occupation=" + occupation +
                ", earning=" + earning +
                ", userName=" + userName +
                ", telExten=" + telExten +
                ", accumulatedConsumption=" + accumulatedConsumption +
                ", accumulatedPoint=" + accumulatedPoint +
                ", commission=" + commission +
                ", fromSource=" + fromSource +
                ", isRobot=" + isRobot +
                ", isGetCoupon=" + isGetCoupon +
                ", isRemind=" + isRemind +
                ", charitable=" + charitable +
                "}";
    }
}
