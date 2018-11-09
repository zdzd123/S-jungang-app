package com.jgzy.utils;


import com.jgzy.config.WeiXinPayConfig;
import com.jgzy.entity.common.Template;
import com.jgzy.entity.common.TemplateParam;
import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 模板消息
 *
 * @author xw
 */
public class TemplateMessageUtil {
    // 支付成功
    private static final String TEMPLATE_PAY_SUCCESS = "labWUz72DvnQkLlnC93D4Rk4xBQ-LtLHBcSl5PuLgYI";
    // 充值成功
    private static final String TEMPLATE_RECHARGE_SUCCESS = "lbzHMpCkqe7IfFSaMPwoVK7HUB1eEZpYMPpbVclIRYM";

    /**
     * 初始化消息模版
     *
     * @param templateId 模版id
     * @return 消息模版
     */
    private static Template initTemplate(String openId, String templateId) {
        Template template = new Template();
        template.setTemplateId(templateId);
        template.setToUser(openId);
        template.setTopColor("#00DD00");
        return template;
    }

    /**
     * 初始化充值成功消息模版
     *
     * @param time   充值时间
     * @param amount 充值金额
     * @return 消息模版
     */
    public static boolean initRechargeTemplate(String openId, String time, String amount) {
        Template template = initTemplate(openId, TEMPLATE_RECHARGE_SUCCESS);
        List<TemplateParam> paras = new ArrayList<>();
        paras.add(new TemplateParam("first", "尊金的顾客：您已成功充值权额", "#565656"));
        paras.add(new TemplateParam("keynote1", time, "#565656"));
        paras.add(new TemplateParam("keynote2", amount, "#565656"));
        paras.add(new TemplateParam("remark", "详情请查看我的订单中心", "#565656"));
        template.setTemplateParamList(paras);
        return sendTemplateMsg(template);
    }

    /**
     * 初始化支付成功消息模版
     *
     * @param tradeNo          订单号
     * @param orderAmountTotal 订单总额
     * @param couponAmount     优惠金额
     * @param totalRealPayMent 支付金额
     * @return 消息模版
     */
    public static boolean initPaySuccessTemplate(String openId, String tradeNo, String orderAmountTotal, String couponAmount, String totalRealPayMent) {
        Template template = initTemplate(openId, TEMPLATE_PAY_SUCCESS);
        List<TemplateParam> paras = new ArrayList<>();
        paras.add(new TemplateParam("first", "尊金的顾客：您已支付成功", "#565656"));
        paras.add(new TemplateParam("keyword2", tradeNo, "#565656"));
        paras.add(new TemplateParam("keyword3", orderAmountTotal, "#565656"));
        paras.add(new TemplateParam("keyword4", couponAmount, "#565656"));
        paras.add(new TemplateParam("keyword5", totalRealPayMent, "#565656"));
        paras.add(new TemplateParam("remark", "详情请查看我的订单中心", "#565656"));
        template.setTemplateParamList(paras);
        return sendTemplateMsg(template);
    }

    /**
     * 品牌费消息模版
     *
     * @param tradeNo          订单号
     * @param orderAmountTotal 金额
     * @return 消息模版
     */
    public static boolean initOriPaySuccessTemplate(String openId, String tradeNo, String orderAmountTotal) {
        Template template = initTemplate(openId, TEMPLATE_PAY_SUCCESS);
        List<TemplateParam> paras = new ArrayList<>();
        paras.add(new TemplateParam("first", "尊金的顾客：您已支付品牌费成功", "#565656"));
        paras.add(new TemplateParam("keyword2", tradeNo, "#565656"));
        paras.add(new TemplateParam("keyword3", orderAmountTotal, "#565656"));
        paras.add(new TemplateParam("remark", "支付品牌费成功", "#565656"));
        template.setTemplateParamList(paras);
        return sendTemplateMsg(template);
    }

    /**
     * 消息模版发送
     *
     * @param template 消息模版
     * @param <T>      参数
     * @return flag
     * @throws JSONException 错误
     */
    public static <T extends Template> boolean sendTemplateMsg(T template) throws JSONException {
        boolean flag = false;

        String getAccessToken = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + WeiXinPayConfig.getApp_id() + "&secret=" + WeiXinPayConfig.getApp_secret() + "";
        String result = HttpGet(getAccessToken);
        // 获取access_token
        if (result.contains("error")) {
            System.out.println("error");
        } else {
            System.out.println(result);
        }

        String access_token = "";

        try {
            JSONObject jsonObject = new JSONObject(result);
            access_token = jsonObject.get("access_token").toString();
        } catch (Exception e) {
            System.out.println("access_token exception");
        }
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + access_token;
        String StringResult = sendPost(requestUrl, template.toJSON());
        if (StringUtils.isNotEmpty(StringResult)) {
            JSONObject jsonObject = new JSONObject(StringResult);
            int errorCode = Integer.parseInt(jsonObject.get("errcode").toString());
            String errorMessage = jsonObject.get("errmsg").toString();
            ;
            if (errorCode == 0) {
                flag = true;
            } else {
                System.out.println("模板消息发送失败:" + errorCode + "," + errorMessage);
                flag = false;
            }
        }
        return flag;
    }

    /**
     * http get
     *
     * @param url url
     * @return response
     */
    private static String HttpGet(String url) {
        return HttpGet(url, "UTF-8");
    }

    /**
     * http get
     *
     * @param url         url
     * @param charsetName 字符
     * @return response
     */
    private static String HttpGet(String url, String charsetName) {
        String result = "";
        BufferedReader in = null;

        try {
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            conn.connect();
            Map<String, List<String>> map = conn.getHeaderFields();
            System.out.println("------------响应头字段[header-start]-------------");
            Iterator var8 = map.keySet().iterator();

            String line;
            while (var8.hasNext()) {
                line = (String) var8.next();
                System.out.println(line + "--->" + map.get(line));
            }
            System.out.println("------------响应头字段[header-end]-------------");
            for (in = new BufferedReader(new InputStreamReader(conn.getInputStream(), charsetName)); (line = in.readLine()) != null; result = result + line + System.getProperty("line.separator")) {
                ;
            }
        } catch (Exception var17) {
            System.out.println("发送GET请求出现异常！" + var17);
            var17.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException var16) {
                var16.printStackTrace();
            }

        }

        return result;
    }

    /**
     * http post
     *
     * @param url   url
     * @param param 参数
     * @return response
     */
    private static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接  
            URLConnection conn = realUrl.openConnection();
            //设置通用的请求属性  
            conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:21.0) Gecko/20100101 Firefox/21.0)");
            // 发送POST请求必须设置如下两行  
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流  
            OutputStreamWriter outWriter = new OutputStreamWriter(conn.getOutputStream(), "utf-8");
            out = new PrintWriter(outWriter);
            // 发送请求参数  
            out.print(param);
            // flush输出流的缓冲  
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应  
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流  
        finally {
            try {
                if (out != null) out.close();
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
}
