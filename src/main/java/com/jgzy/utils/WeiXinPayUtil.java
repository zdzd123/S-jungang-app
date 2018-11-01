package com.jgzy.utils;

import com.alibaba.fastjson.JSONException;
import com.jgzy.config.WeiXinPayConfig;
import com.jgzy.entity.common.WeiXinData;
import com.jgzy.entity.common.WeiXinTradeType;
import org.json.JSONArray;
import org.json.JSONML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeiXinPayUtil {

    private static final Logger logger = LoggerFactory.getLogger(WeiXinPayUtil.class);

    /**
     * 微信统一下单支付
     *
     * @param weiXinTradeType
     * @param openid
     * @param product_id
     * @param out_trade_no
     * @param body
     * @param total_fee
     * @param ip
     * @return
     * @throws JSONException
     * @throws IOException
     */
    public static WeiXinData makePreOrder(WeiXinTradeType weiXinTradeType, String openid, String product_id,
                                          String out_trade_no, String body, double total_fee, String ip, String notify_url) throws JSONException, IOException {
        switch (weiXinTradeType) {
            case JSAPI:
                if (openid == null || "".equals(openid))
                    throw new IOException("统一支付接口中，缺少必填参数openid！trade_type为JSAPI时，openid为必填参数！");
                break;
            case NATIVE:
                openid = "";
                if (product_id == null || "".equals(product_id))
                    throw new IOException("统一支付接口中，缺少必填参数product_id！trade_type为NATIVE时，product_id为必填参数！");
                break;
            case APP:
                openid = "";
                break;

        }

        System.out.println(product_id + "==========创建支付订单=============" + out_trade_no);

        String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
        StringBuilder xml = new StringBuilder();
        String result_code = "", return_code = "", return_msg = "", err_code = "", err_code_des = "", code_url = "";
        String prepay_id = "", charset = "UTF-8", nonce_str = "";
        String weixinMoney = new java.text.DecimalFormat("#").format(total_fee * 100);// 微信是以分为单位的所以要乘以100
        nonce_str = CommonUtil.getRandomString(32);

        WeiXinData wxData = new WeiXinData();
        wxData.put("appid", WeiXinPayConfig.getApp_id());
        wxData.put("openid", openid);
        wxData.put("mch_id", WeiXinPayConfig.getMch_id());
        wxData.put("product_id", product_id);
        wxData.put("body", new String(body.getBytes(charset), charset));
        wxData.put("nonce_str", nonce_str);
        wxData.put("notify_url", notify_url);
        wxData.put("out_trade_no", out_trade_no);
        wxData.put("spbill_create_ip", ip);
        wxData.put("total_fee", weixinMoney);
        wxData.put("trade_type", weiXinTradeType.name().toUpperCase());
        wxData.put("key", WeiXinPayConfig.getKey());
        wxData.put("sign", wxData.makeSign());

        logger.info("WeiXinPayCore[makePreOrder][0] 输出----------------------------------------------------------：");
        logger.info("WeiXinPayCore[makePreOrder][0] url.toString()-=======>" + wxData.toURL());
        logger.info("WeiXinPayCore[makePreOrder][0] xml.toString()-=======>" + wxData.toXML());

        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "text/xml");
        conn.setRequestProperty("Charset", charset);
        OutputStream os = conn.getOutputStream();
        os.write(wxData.toXML().getBytes(charset));
        xml.delete(0, xml.length());
        os.close();

        int responseCode = conn.getResponseCode();
        InputStreamReader in = null;
        BufferedReader br = null;
        if (responseCode == 200) {
            in = new InputStreamReader(conn.getInputStream(), charset);
            br = new BufferedReader(in);
            String retData = null;
            while ((retData = br.readLine()) != null)
                xml.append(retData);

            logger.info("WeiXinPayCore[makePreOrder][0] response : " + xml.toString());

            JSONArray childNodes = JSONML.toJSONObject(xml.toString()).getJSONArray("childNodes");
            int len = childNodes.length() - 1;
            for (int i = len; i > -1; i--) {
                org.json.JSONObject js = childNodes.getJSONObject(i);
                Object tagName = js.get("tagName");
                if (tagName.equals("prepay_id")) {
                    prepay_id = js.getJSONArray("childNodes").getString(0);
                } else if (tagName.equals("code_url")) {
                    code_url = js.getJSONArray("childNodes").getString(0);
                } else if (tagName.equals("result_code")) {
                    result_code = js.getJSONArray("childNodes").getString(0);
                } else if (tagName.equals("return_code")) {
                    return_code = js.getJSONArray("childNodes").getString(0);
                } else if (tagName.equals("return_msg")) {
                    return_msg = js.getJSONArray("childNodes").getString(0);
                } else if (tagName.equals("err_code")) {
                    err_code = js.getJSONArray("childNodes").getString(0);
                } else if (tagName.equals("err_code_des")) {
                    err_code_des = js.getJSONArray("childNodes").getString(0);
                }
            }
        }
        if (in != null)
            in.close();
        if (br != null)
            br.close();
        conn.disconnect();

        // ----------------------------------给h5返回的数据
        String timeStamp = System.currentTimeMillis() + "";
        timeStamp = timeStamp.substring(0, 10);// 微信只要精确到秒

        // 主体--------------------------------------------
        wxData = new WeiXinData();
        switch (weiXinTradeType) {
            case JSAPI:
                //https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=7_7&index=6
                wxData.put("appId", WeiXinPayConfig.getApp_id()); //公众账号ID
                wxData.put("nonceStr", CommonUtil.getRandomString(32)); //随机字符串
                wxData.put("package", "prepay_id=" + prepay_id);
                wxData.put("signType", "MD5");
                wxData.put("timeStamp", timeStamp);
                wxData.put("key", WeiXinPayConfig.getKey());
                wxData.put("sign", wxData.makeSignH5()); //签名

                logger.info("WeiXinPayCore[makePreOrder][1]  输出----------------------------------------------------------：");
                logger.info("WeiXinPayCore[makePreOrder][1]  url.toString()-=======>" + wxData.toURL());
                logger.info("WeiXinPayCore[makePreOrder][1]  xml.toString()-=======>" + wxData.toXML());
                break;
            case NATIVE:
                wxData.put("code_url", code_url); //二维码链接
                break;
            case APP:
                wxData.put("appid", WeiXinPayConfig.getApp_id()); //公众账号ID
                wxData.put("partnerid", WeiXinPayConfig.getMch_id()); //商户号
                wxData.put("noncestr", CommonUtil.getRandomString(32)); //随机字符串
                wxData.put("timestamp", timeStamp);
                wxData.put("prepayid", prepay_id); //预付款id
                wxData.put("package", "Sign=WXPay");
                wxData.put("key", WeiXinPayConfig.getKey());
                wxData.put("sign", wxData.makeSign()); //签名

                logger.info("WeiXinPayCore[makePreOrder][1]  输出----------------------------------------------------------：");
                logger.info("WeiXinPayCore[makePreOrder][1]  url.toString()-=======>" + wxData.toURL());
                logger.info("WeiXinPayCore[makePreOrder][1]  xml.toString()-=======>" + wxData.toXML());
                wxData.put("sign_type", "MD5"); //签名类型MD5
                break;
        }

        wxData.put("return_code", return_code); //返回状态码
        wxData.put("return_msg", return_msg); //返回信息
        wxData.put("result_code", result_code); //业务结果
        wxData.put("err_code", err_code); //错误代码
        wxData.put("err_code_des", err_code_des); //错误代码描述

        //--------------------------------------------
        logger.info("WeiXinPayCore[makePreOrder][2]  输出----------------------------------------------------------：");
        logger.info("WeiXinPayCore[makePreOrder][2]  url.toString()-=======>" + wxData.toURL());
        logger.info("WeiXinPayCore[makePreOrder][2]  xml.toString()-=======>" + wxData.toXML());
        return wxData;
    }

    /**
     * 处理微信回调
     *
     * @param request
     * @return
     */
    public static WeiXinNotify notify_url(HttpServletRequest request) {
        logger.info("微信支付回调数据开始");
        String inputLine;
        StringBuilder notityXml = new StringBuilder();
        BufferedReader bufferedReader = null;
        WeiXinNotify notify = new WeiXinNotify();
        try {
            bufferedReader = request.getReader();
            while ((inputLine = bufferedReader.readLine()) != null)
                notityXml.append(inputLine);
            if (bufferedReader != null)
                bufferedReader.close();
            if (notityXml.length() < 10) {
                return notify.setResult("FAIL", "notityXml.length()<10");
            }
            JSONArray result = JSONML.toJSONObject(notityXml.toString()).getJSONArray("childNodes");
            int len = result.length();
            for (int i = 0; i < len; i++) {

                org.json.JSONObject js = result.getJSONObject(i);
                Object tagName = js.get("tagName");
                if (tagName.equals("appid")) {
                    notify.appid = js.getJSONArray("childNodes").getString(0);
                } else if (tagName.equals("bank_type")) {
                    notify.bank_type = js.getJSONArray("childNodes").getString(0);
                } else if (tagName.equals("cash_fee")) {
                    notify.cash_fee = js.getJSONArray("childNodes").getString(0);
                } else if (tagName.equals("fee_type")) {
                    notify.fee_type = js.getJSONArray("childNodes").getString(0);
                } else if (tagName.equals("is_subscribe")) {
                    notify.is_subscribe = js.getJSONArray("childNodes").getString(0);
                } else if (tagName.equals("mch_id")) {
                    notify.mch_id = js.getJSONArray("childNodes").getString(0);
                } else if (tagName.equals("nonce_str")) {
                    notify.nonce_str = js.getJSONArray("childNodes").getString(0);
                } else if (tagName.equals("openid")) {
                    notify.openid = js.getJSONArray("childNodes").getString(0);
                } else if (tagName.equals("out_trade_no")) {
                    notify.out_trade_no = js.getJSONArray("childNodes").getString(0);
                } else if (tagName.equals("result_code")) {
                    notify.result_code = js.getJSONArray("childNodes").getString(0);
                } else if (tagName.equals("return_code")) {
                    notify.return_code = js.getJSONArray("childNodes").getString(0);
                } else if (tagName.equals("return_msg")) {
                    notify.return_msg = js.getJSONArray("childNodes").getString(0);
                } else if (tagName.equals("time_end")) {
                    notify.time_end = js.getJSONArray("childNodes").getString(0);
                } else if (tagName.equals("total_fee")) {
                    notify.total_fee = js.getJSONArray("childNodes").getInt(0) + "";
                } else if (tagName.equals("trade_type")) {
                    notify.trade_type = js.getJSONArray("childNodes").getString(0);
                } else if (tagName.equals("transaction_id")) {
                    notify.transaction_id = js.getJSONArray("childNodes").getString(0);
                }
                // 优惠券 ------------------17-12-19
                else if (tagName.equals("coupon_count")) {
                    notify.coupon_count = js.getJSONArray("childNodes").getString(0);
                } else if (tagName.equals("coupon_fee")) {
                    notify.coupon_fee = js.getJSONArray("childNodes").getString(0);
                }
                // coupon_fee_0
                else if (tagName.equals("coupon_fee_0")) {
                    notify.coupon_fee_0 = js.getJSONArray("childNodes").getString(0);
                } else if (tagName.equals("coupon_id_0")) {
                    notify.coupon_id_0 = js.getJSONArray("childNodes").getString(0);
                }
                // coupon_fee_1
                else if (tagName.equals("coupon_fee_1")) {
                    notify.coupon_fee_1 = js.getJSONArray("childNodes").getString(0);
                } else if (tagName.equals("coupon_id_1")) {
                    notify.coupon_id_1 = js.getJSONArray("childNodes").getString(0);
                }
                // coupon_fee_2
                else if (tagName.equals("coupon_fee_2")) {
                    notify.coupon_fee_2 = js.getJSONArray("childNodes").getString(0);
                } else if (tagName.equals("coupon_id_2")) {
                    notify.coupon_id_2 = js.getJSONArray("childNodes").getString(0);
                }
                // 签名
                else if (tagName.equals("sign")) {
                    notify.sign = js.getJSONArray("childNodes").getString(0);
                }

            }

            WeiXinData wxData = new WeiXinData();
            wxData.put("appid", notify.appid);
            wxData.put("bank_type", notify.bank_type);
            wxData.put("cash_fee", notify.cash_fee);
            wxData.put("fee_type", notify.fee_type);
            wxData.put("is_subscribe", notify.is_subscribe);
            wxData.put("mch_id", notify.mch_id);
            wxData.put("nonce_str", notify.nonce_str);
            wxData.put("openid", notify.openid);
            wxData.put("out_trade_no", notify.out_trade_no);
            wxData.put("result_code", notify.result_code);
            wxData.put("return_code", notify.return_code);
            wxData.put("time_end", notify.time_end);
            wxData.put("total_fee", notify.total_fee);
            wxData.put("trade_type", notify.trade_type);
            wxData.put("transaction_id", notify.transaction_id);

            // 优惠券 ------------------17-12-19
            wxData.put("coupon_count", notify.coupon_count);
            wxData.put("coupon_fee", notify.coupon_fee);
            // coupon_fee_0
            wxData.put("coupon_fee_0", notify.coupon_fee_0);
            wxData.put("coupon_id_0", notify.coupon_id_0);
            // coupon_fee_1
            wxData.put("coupon_fee_1", notify.coupon_fee_1);
            wxData.put("coupon_id_1", notify.coupon_id_1);
            // coupon_fee_2
            wxData.put("coupon_fee_2", notify.coupon_fee_2);
            wxData.put("coupon_id_2", notify.coupon_id_2);

            wxData.put("key", WeiXinPayConfig.getKey());

            String url = wxData.toURL();

            // 移除编号 T01-17-12-19 空位符

            if (!CommonUtil.MD5Purity(url).equals(notify.sign)) {// 验证签名
                logger.info("微信服务号支付回调签名异常sign=" + notify.sign);
                logger.info(url);
                return notify.setResult("FAIL", "微信支付回调签名异常");
            }
            return notify.setResult("SUCCESS", "OK");
        } catch (JSONException e) {
            e.printStackTrace();
            notify.setReturn_msg(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            notify.setReturn_msg(e.getMessage());
        }
        System.out.println("微信支付回调数据结束");
        return notify.setResult("FAIL");
    }
}
