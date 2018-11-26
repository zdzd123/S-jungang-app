package com.jgzy.core.personalCenter.controller;

import com.jgzy.config.WeiXinPayConfig;
import com.jgzy.constant.RedisConstant;
import com.jgzy.entity.common.ResultWrapper;
import com.jgzy.utils.RedisUtil;
import com.jgzy.utils.TemplateMessageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONObject;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RefreshScope
@RestController
@RequestMapping("/api/weixin")
@Api(value = "微信信息接口", description = "微信信息接口")
public class WeiXinInfoController {

    @GetMapping(value = "/constant/getTicket")
    @ApiOperation(value = "微信分享获取ticket", notes = "微信分享获取ticket")
    public ResultWrapper<Map<String, Object>> detail(@ApiParam(value = "url") @RequestParam String url) {

        ResultWrapper<Map<String, Object>> resultWrapper = new ResultWrapper<>();
        Map<String, Object> resultMap = new HashMap<>();
        // 获取缓存accessToken
        Object accessTokenObj = RedisUtil.get(RedisConstant.REDIS_ACCESS_TOKEN);
        String accessToken;
        if (accessTokenObj == null) {
            // accessToken
            String getAccessToken = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" +
                    WeiXinPayConfig.getApp_id() + "&secret=" + WeiXinPayConfig.getApp_secret() + "";
            String result = TemplateMessageUtil.HttpGet(getAccessToken);
            if (result.contains("error")) {
                resultMap.put("result", "access_token exception");
                resultWrapper.setResult(resultMap);
                return resultWrapper;
            }
            JSONObject accessTokenObject = new JSONObject(result);
            accessToken = accessTokenObject.get("access_token").toString();
            System.out.println("accessToken --------------------------------" + accessToken);
            // 缓存accessToken
            RedisUtil.set(RedisConstant.REDIS_ACCESS_TOKEN, accessToken, RedisConstant.REDIS_ACCESSTOKEN_TIME_OUT);
        } else {
            accessToken = accessTokenObj.toString();
        }
        // 获取ticket
        String getJsApiTicket = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" +
                accessToken + "&type=jsapi";
        String ticketResult = TemplateMessageUtil.HttpGet(getJsApiTicket);
        if (!ticketResult.contains("ticket")) {
            System.out.println("accessToken --------------------------------" + accessToken);
            System.out.println("ticket --------------------------------" + ticketResult);
            resultMap.put("result", "ticket exception");
            resultWrapper.setResult(resultMap);
            return resultWrapper;
        }
        JSONObject ticketObject = new JSONObject(ticketResult);
        String ticket = ticketObject.get("ticket").toString();
        // 封装参数
        StringBuilder xml = new StringBuilder();
        String nonce_str = GetRandomCode(32, 3);
        long timeMills = new Date().getTime();
        xml.append("jsapi_ticket=")
                .append(ticket).append("&noncestr=")
                .append(nonce_str).append("&timestamp=")
                .append(timeMills).append("&url=")
                .append(url);// 处理中文
        String sign = DigestUtils.sha1Hex(xml.toString());
        // 返回参数
        resultMap.put("appId", WeiXinPayConfig.getApp_id());
        resultMap.put("timestamp", timeMills);
        resultMap.put("nonceStr", nonce_str);
        resultMap.put("signature", sign);
        resultMap.put("jsapi_ticket", ticket);
        resultWrapper.setResult(resultMap);
        return resultWrapper;
    }

    /**
     * 获取随机字符
     *
     * @param codeCount       字符长度
     * @param all0_char1_Num2 字符类型
     * @return 随机字符
     */
    public static String GetRandomCode(int codeCount, int all0_char1_Num2) {
        String val = "";
        Random random = new Random();

        for (int i = 0; i < codeCount; ++i) {
            String charOrNumStr = "";
            switch (all0_char1_Num2) {
                case 0:
                default:
                    charOrNumStr = random.nextInt(2) % 2 == 0 ? "char" : "num";
                    break;
                case 1:
                    charOrNumStr = "char";
                    break;
                case 2:
                    charOrNumStr = "num";
            }

            if ("char".equalsIgnoreCase(charOrNumStr)) {
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val = val + (char) (random.nextInt(26) + temp);
            } else {
                val = val + String.valueOf(random.nextInt(10));
            }
        }

        return val.toUpperCase();
    }
}
