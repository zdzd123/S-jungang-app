package com.jgzy.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

public class CommonUtil {
    /**
     * 要求外部订单号必须唯一。
     *
     * @return
     */
    public static String getTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }

    /**
     * 获取uuid
     *
     * @return uuid
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 生成一个随机字符串
     *
     * @param length 表示生成字符串的长度
     * @return
     */
    public static String getRandomString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
    /**
     * MD5
     *
     * @param plainText
     * @return
     */
    public static String MD5Purity(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes("utf-8"));
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            plainText = buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return plainText.toUpperCase();
    }

    /**
     * 计算耗材费
     *
     * @param count         个数
     * @param materialCosts 金额0
     * @return 耗材费
     */
    public static BigDecimal calcMaterialCosts(int count, BigDecimal materialCosts) {
        if (count > 24) {
            return materialCosts;
        }
        switch (count) {
            case 1:
                materialCosts = BigDecimalUtil.add(materialCosts, 3);
                break;
            case 2:
                materialCosts = materialCosts.add(BigDecimalUtil.mul(2.4, count));
                break;
            case 3:
                materialCosts = materialCosts.add(BigDecimalUtil.mul(2, count));
                break;
            case 4:
                materialCosts = materialCosts.add(BigDecimalUtil.mul(1.8, count));
                break;
            case 5:
                materialCosts = materialCosts.add(BigDecimalUtil.mul(1.5, count));
                break;
            case 6:
                materialCosts = materialCosts.add(BigDecimalUtil.mul(1.4, count));
                break;
            default:
                int remainder = count % 6;
                materialCosts = BigDecimalUtil.mul(1.4, count - remainder);
                if (remainder != 0){
                    materialCosts = calcMaterialCosts(remainder, materialCosts);
                }
                break;
        }
        return materialCosts;
    }
}
