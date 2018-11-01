package com.ahomet.constant.db;

import java.util.Objects;

/**
 * @program: app
 * @description:
 * @author: yongli.liu
 * @create: 2018-07-09 14:26
 */
public class AdvertInfoConstant {

    public enum AdvertSiteEnum{
        /**
         *广告位置
         */
        ADVERT_SITE_ONE(1, "首页"),
        ADVERT_SITE_TWO(2, "酒店预定"),
        ADVERT_SITE_THREE(3, "中国旅游商城"),
        ADVERT_SITE_FOUR(4, "同城生活"),
        ADVERT_SITE_FIVE(5, "快乐分享"),
        ADVERT_SITE_SIX(6, "演播室"),
        ADVERT_SITE_SEVEN(7, "百台联播"),
        ADVERT_SITE_EIGHT(8, "新闻热点"),
        ADVERT_SITE_NINE(9, "项目投资"),
        ADVERT_SITE_TEN(10, "加盟合作"),
        ADVERT_SITE_ELEVEN(11, "优惠活动"),
        ADVERT_SITE_TWELVE(12, "商圈推荐"),
        ADVERT_SITE_THIRTEEN(13, "热门城市酒店"),
        ADVERT_SITE_FOURTEEN(14, "帮你推荐"),
        ADVERT_SITE_FIFTEEN(15, "民宿酒店"),
        ADVERT_SITE_SIXTEEN(16, "商务酒店"),
        ADVERT_SITE_SEVENTEEN(17, "短租酒店"),
        ADVERT_SITE_EIGHTEEN(18, "海外酒店"),
        ADVERT_SITE_NINETEEN(19, "酒店精品优选");


        public static String desc(Integer key) {
            for (AdvertInfoConstant.AdvertSiteEnum advertSiteEnum : AdvertInfoConstant.AdvertSiteEnum.values()) {
                Integer keyArg = advertSiteEnum.key;
                if (Objects.equals(key, keyArg)) {
                    return advertSiteEnum.value;
                }
            }
            return "";
        }

        AdvertSiteEnum(Integer key, String value) {
            this.key = key;
            this.value = value;
        }

        public Integer getKey() {
            return key;
        }

        public void setKey(Integer key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        Integer key;
        String value;
    }

    public enum SpecificSiteEnum{
        /**
         *具体位置
         */
        SPECIFIC_SITE_ONE(1, "第一模块"),
        SPECIFIC_SITE_TWO(2, "第二模块"),
        SPECIFIC_SITE_THREE(3, "第三模块"),
        SPECIFIC_SITE_FOUR(4, "第四模块"),
        SPECIFIC_SITE_FIVE(5, "第五模块"),
        SPECIFIC_SITE_SIX(6, "第六模块"),
        SPECIFIC_SITE_SEVEN(7, "第七模块"),
        SPECIFIC_SITE_EIGHT(8, "第八模块"),
        SPECIFIC_SITE_NINE(9, "第九模块"),
        SPECIFIC_SITE_TEN(10, "第十模块"),
        SPECIFIC_SITE_ELEVEN(11, "第十一模块");


        public static String desc(Integer key) {
            for (AdvertInfoConstant.SpecificSiteEnum specificSiteEnum : AdvertInfoConstant.SpecificSiteEnum.values()) {
                Integer keyArg = specificSiteEnum.key;
                if (Objects.equals(key, keyArg)) {
                    return specificSiteEnum.value;
                }
            }
            return "";
        }

        SpecificSiteEnum(Integer key, String value) {
            this.key = key;
            this.value = value;
        }

        public Integer getKey() {
            return key;
        }

        public void setKey(Integer key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        Integer key;
        String value;
    }

    public enum StateEnum{
        /**
         *开启状态
         */
        STATE_NO(1, "未开启"),
        STATE_YES(2, "已开启");


        public static String desc(Integer key) {
            for (AdvertInfoConstant.StateEnum stateEnum : AdvertInfoConstant.StateEnum.values()) {
                Integer keyArg = stateEnum.key;
                if (Objects.equals(key, keyArg)) {
                    return stateEnum.value;
                }
            }
            return "";
        }

        StateEnum(Integer key, String value) {
            this.key = key;
            this.value = value;
        }

        public Integer getKey() {
            return key;
        }

        public void setKey(Integer key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        Integer key;
        String value;
    }

}