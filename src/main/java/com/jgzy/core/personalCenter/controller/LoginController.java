package com.jgzy.core.personalCenter.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jgzy.config.WeiXinPayConfig;
import com.jgzy.constant.BaseConstant;
import com.jgzy.constant.ErrorCodeEnum;
import com.jgzy.constant.RedisConstant;
import com.jgzy.core.personalCenter.service.ILoginService;
import com.jgzy.core.personalCenter.service.IUserInfoService;
import com.jgzy.core.personalCenter.service.IUserOauthService;
import com.jgzy.core.personalCenter.vo.LoginVo;
import com.jgzy.entity.common.ResultWrapper;
import com.jgzy.entity.common.oauth_configModel;
import com.jgzy.entity.po.UserInfo;
import com.jgzy.entity.po.UserOauth;
import com.jgzy.utils.CommonUtil;
import com.jgzy.utils.EmojiFilterUtil;
import com.jgzy.utils.HttpRequest;
import com.jgzy.utils.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;

@RefreshScope
@RestController
@Api(value = "用户登录接口", description = "用户登录接口")
@RequestMapping(value = "/api/login")
public class LoginController {
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IUserOauthService userOauthService;

    /**
     * 登录验证，成功后修改token,并返回token,oauth_openid
     *
     * @param code
     * @return
     */
    @GetMapping(value = "/detail")
    @ApiOperation(value = "登录", notes = "登录")
    @Transactional
    public ResultWrapper<LoginVo> detail(@ApiParam(value = "code", required = true) @RequestParam String code){
        ResultWrapper<LoginVo> resultWrapper = new ResultWrapper<>();
        System.out.println("app_id--------------------------------------------------------" + WeiXinPayConfig.getApp_id());
        System.out.println("app_key--------------------------------------------------------" + WeiXinPayConfig.getApp_secret());
        System.out.println("code--------------------------------------------------------" + code);

        String string = HttpRequest.sendGet("https://api.weixin.qq.com/sns/oauth2/access_token?appid=" +
                WeiXinPayConfig.getApp_id() + "&secret=" + WeiXinPayConfig.getApp_secret() + "&code=" + code + "&grant_type=authorization_code");
        JSONObject jsonObject = new JSONObject(string);
        System.out.println("jsonObject---------------------------------------------/api/v1/weixinLogin" + jsonObject);
        String access_token = jsonObject.getString("access_token");
        String openid = jsonObject.getString("openid");
        String userInfo = HttpRequest.sendGet("https://api.weixin.qq.com/sns/userinfo?access_token=" +
                access_token + "&openid=" + openid + "&lang=zh_CN ");
        JSONObject userInfoJsonObject = new JSONObject(userInfo);

//            String openid= null;
//            String json = "{\n" +
//                    "  \"country\": \"中国\",\n" +
//                    "  \"province\": \"江苏\",\n" +
//                    "  \"city\": \"无锡\",\n" +
//                    "  \"openid\": \"oRwM80xcOq2KkGwq-lU6ZWlik0ZA\",\n" +
//                    "  \"sex\": 1,\n" +
//                    "  \"nickname\": \"Rainbow_Chen\",\n" +
//                    "  \"headimgurl\": \"http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83epoucTYIjUiasjIkHfUsXuGHA9uicTdFMRFQmlIrgtpkAVhl8E5IokW8d1icF04bVEEfzmL7SD3eHdYg/132\",\n" +
//                    "  \"language\": \"zh_CN\",\n" +
//                    "  \"privilege\": []\n" +
//                    "}";
//            JSONObject userInfoJsonObject = new JSONObject(json);

        System.out.println("userInfoJsonObject---------------------------------------------/api/v1/weixinLogin" + userInfoJsonObject);
        openid = userInfoJsonObject.getString("openid");
        String nickname = EmojiFilterUtil.filterEmoji2(userInfoJsonObject.getString("nickname"));
        int sex = userInfoJsonObject.getInt("sex");
        String headimgurl = userInfoJsonObject.getString("headimgurl");

        String oauth_name = "weixinh5";
        String oauth_openid = openid;
        String head_portrait = headimgurl;

        int platform = 4;// 微信

        if (StringUtils.isEmpty(oauth_openid) || StringUtils.isEmpty(head_portrait) ||
                StringUtils.isEmpty(nickname)) {
            resultWrapper.setErrorMsg("openid、头像或昵称为空");
            resultWrapper.setErrorCode(ErrorCodeEnum.ERROR_ARGS_MISS.getKey());
            return resultWrapper;
        }

        UserOauth user_oauth_h5 = userOauthService.selectOne(new EntityWrapper<UserOauth>()
                .eq("oauth_openid", oauth_openid));

        if (user_oauth_h5 != null) {
            UserInfo user = userInfoService.selectById(user_oauth_h5.getUserId());
            System.out.println("用户认证 user_oauth_h5不为空！");
            if (user == null) {
                System.out.println("用户 为空！");
                // 创建新用户
                UserInfo model = new UserInfo();
                model.setId(user_oauth_h5.getUserId());
                model.setNickname(nickname);
                model.setGender(sex);
                model.setHeadPortrait(head_portrait);
                model.setRegisterTime(new Date());
                model.setFromSource(platform);
                model.setValidStatus(1);
                model.setToken(CommonUtil.getUUID());
                model.setUserLevelId(1);
                boolean insert = userInfoService.insert(model);
                if (!insert) {
                    setLoginFail(resultWrapper);
                    return resultWrapper;
                }
                user_oauth_h5.setUserId(model.getId());
                userOauthService.updateById(user_oauth_h5);
                resultWrapper.setErrorMsg("登录成功");
                resultWrapper.setErrorCode(ErrorCodeEnum.SUCCESS.getKey());
                LoginVo loginVo = initLoginVo(model.getToken(), oauth_openid);
                resultWrapper.setResult(loginVo);
                RedisUtil.hset(RedisConstant.REDIS_USER_KEY, model.getToken(), model, RedisConstant.REDIS_LOGIN_TIME_OUT);
                return resultWrapper;
            } else {
                System.out.println("用户 不为空！");
                user.setToken(CommonUtil.getUUID());
                boolean updateById = userInfoService.updateById(user);
                if (!updateById) {
                    setLoginFail(resultWrapper);
                    return resultWrapper;
                }
                LoginVo loginVo = initLoginVo(user.getToken(), oauth_openid);
                resultWrapper.setResult(loginVo);
                RedisUtil.hset(RedisConstant.REDIS_USER_KEY, user.getToken(), user, RedisConstant.REDIS_LOGIN_TIME_OUT);
                return resultWrapper;
            }
        }else {
            System.out.println("用户认证 为空！ user_oauth is null; user_oauth_h5 is null");
            // 用户信息
            UserInfo model = new UserInfo();
            model.setGender(sex);
            model.setNickname(nickname);
            model.setHeadPortrait(head_portrait);
            model.setUserLevelId(1);
            model.setRegisterTime(new Date());
            model.setFromSource(platform);
            model.setToken(CommonUtil.getUUID());
            boolean insert = userInfoService.insert(model);
            if (!insert) {
                setLoginFail(resultWrapper);
                return resultWrapper;
            }
            // 用户认证
            UserOauth newuoModel = new UserOauth();
            newuoModel.setOauthName(oauth_name);
            newuoModel.setOauthOpenid(oauth_openid);
            newuoModel.setAddTime(new Date());
            newuoModel.setUserName(nickname);
            newuoModel.setUserId(model.getId());
            boolean insertOauth = userOauthService.insert(newuoModel);
            if (!insertOauth) {
                setLoginFail(resultWrapper);
                return resultWrapper;
            }
            LoginVo loginVo = initLoginVo(model.getToken(), oauth_openid);
            resultWrapper.setResult(loginVo);
            resultWrapper.setResult(loginVo);
            RedisUtil.hset(RedisConstant.REDIS_USER_KEY, model.getToken(), model, RedisConstant.REDIS_LOGIN_TIME_OUT);
            return resultWrapper;
        }
    }

    private void setLoginFail(ResultWrapper<LoginVo> resultWrapper) {
        resultWrapper.setErrorMsg("登录失败");
        resultWrapper.setErrorCode(ErrorCodeEnum.ERROR_LOGIN_FAIL.getKey());
    }

    /**
     * 初始化返回登录信息
     *
     * @param token
     * @param oauth_openid
     * @return
     */
    private LoginVo initLoginVo(String token, String oauth_openid) {
        LoginVo loginVo = new LoginVo();
        loginVo.setToken(token);
        loginVo.setOauthOpenid(oauth_openid);
        return loginVo;
    }

}
