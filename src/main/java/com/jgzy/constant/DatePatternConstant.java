package com.jgzy.constant;

/**
 * Created by Administrator on 2017/11/2 0002.
 */
public class DatePatternConstant {
    /**
     * 只有日期 年月日<span style="color:green"><code>{@value}</code></span>.
     * <p>
     * example:<span style="color:green">2012-01-22</span>
     * </p>
     */
    public static final String COMMON_DATE = "yyyy-MM-dd";

    /**
     * 中文日期格式,年月日<span style="color:green"><code>{@value}</code></span>.
     * <p>
     * example:<span style="color:green">2015年07月17日</span>
     * </p>
     *
     * @since 1.2.2
     */
    public static final String CHINESE_COMMON_DATE = "yyyy年MM月dd日";

    /**
     * 只有时间<span style="color:green"><code>{@value}</code></span>.
     * <p>
     * example:<span style="color:green">21:57:36</span>
     * </p>
     */
    public static final String COMMON_TIME = "HH:mm:ss";

    /**
     * 只有时间且不带秒 <span style="color:green"><code>{@value}</code></span>.
     * <p>
     * <p>
     * example:<span style="color:green">21:57</span>
     * </p>
     */
    public static final String COMMON_TIME_WITHOUT_SECOND = "HH:mm";

    /**
     * <span style="color:green"><code>{@value}</code></span>.
     * <p>
     * example:<span style="color:green">2013-12-27 22:13:55</span>
     * </p>
     */
    public static final String COMMON_DATE_AND_TIME = "yyyy-MM-dd HH:mm:ss";

    /**
     * 中文日期+时间格式 <span style="color:green"><span style="color:green"><code>{@value}</code></span>.</span>.
     * <p>
     * example: <span style="color:green">2015年07月17日 15:33:00</span>
     * </p>
     *
     * @since 1.2.2
     */
    public static final String CHINESE_COMMON_DATE_AND_TIME = "yyyy年MM月dd日 HH:mm:ss";

    /**
     * 带毫秒的时间格式 <span style="color:green"><code>{@value}</code></span>.
     * <p>
     * <p>
     * example: <span style="color:green">2013-12-27 22:13:55.453</span>
     * </p>
     */
    public static final String COMMON_DATE_AND_TIME_WITH_MILLISECOND = "yyyy-MM-dd HH:mm:ss.SSS";

    /**
     * 不带秒 <span style="color:green"><code>{@value}</code></span>.
     * <p>
     * <p>
     * example: <span style="color:green">2013-12-27 22:13</span>
     * </p>
     */
    public static final String COMMON_DATE_AND_TIME_WITHOUT_SECOND = "yyyy-MM-dd HH:mm";

    /**
     * 不带年 不带秒 <span style="color:green"><code>{@value}</code></span>.
     * <p>
     * <p>
     * example: <span style="color:green">12-27 22:13</span>
     * </p>
     */
    public static final String COMMON_DATE_AND_TIME_WITHOUT_YEAR_AND_SECOND = "MM-dd HH:mm";

    // *******************************************************************

    /**
     * 时间戳, 一般用于拼接文件名称<span style="color:green"><code>{@value}</code></span>.
     * <p>
     * <p>
     * example: <span style="color:green">20131227215816</span>
     * </p>
     */
    public static final String TIMESTAMP = "yyyyMMddHHmmss";

    /**
     * 带毫秒的时间戳,<span style="color:green"><code>{@value}</code></span>.
     * <p>
     * <p>
     * example: <span style="color:green">20131227215758437</span>
     * </p>
     */
    public static final String TIMESTAMP_WITH_MILLISECOND = "yyyyMMddHHmmssSSS";

    //*******************************************************************************

    /**
     * 年月 带水平线,一般用于分类日志,将众多日志按月分类 <span style="color:green"><code>{@value}</code></span>.
     * <p>
     * <p>
     * example: <span style="color:green">2012-01</span>
     * </p>
     */
    public static final String YEAR_AND_MONTH = "yyyy-MM";

    /**
     * 月日 <span style="color:green"><code>{@value}</code></span>.
     * <p>
     * <p>
     * example: <span style="color:green">01-22</span>
     * </p>
     */
    public static final String MONTH_AND_DAY = "MM-dd";

    /**
     * 月日带星期 <span style="color:green"><code>{@value}</code></span>.
     * <p>
     * <p>
     * example: <span style="color:green">01-22(星期四)</span>
     * </p>
     */
    public static final String MONTH_AND_DAY_WITH_WEEK = "MM-dd(E)";

    //**********************************************************************************

    /**
     * <span style="color:green"><code>{@value}</code></span>.
     * <p>
     * <p>
     * example: <span style="color:green">31/03/2014 14:53:39</span>
     * </p>
     */
    public static final String ddMMyyyyHHmmss = "dd/MM/yyyy HH:mm:ss";

    /**
     * <span style="color:green"><code>{@value}</code></span>.
     * <p>
     * <p>
     * example: <span style="color:green">13</span>
     * </p>
     */
    public static final String yy = "yy";

    /**
     * <span style="color:green"><code>{@value}</code></span>.
     * <p>
     * <p>
     * example: <span style="color:green">2013</span>
     * </p>
     */
    public static final String yyyy = "yyyy";

    /**
     * MM月份 <span style="color:green"><code>{@value}</code></span>.
     * <p>
     * <p>
     * example: <span style="color:green">12</span>
     * </p>
     */
    public static final String MM = "MM";

    /**
     * <span style="color:green"><code>{@value}</code></span>.
     * <p>
     * <p>
     * example: <span style="color:green">20131227</span>
     * </p>
     */
    public static final String yyyyMMdd = "yyyyMMdd";

    /**
     * <span style="color:green"><code>{@value}</code></span>.
     * <p>
     * <p>
     * example: <span style="color:green">2156</span>
     * </p>
     */
    public static final String mmss = "mmss";

    /**
     * <span style="color:green"><code>{@value}</code></span>.
     * <p>
     * <p>
     * example: <span style="color:green">21</span>
     * </p>
     */
    public static final String HH = "HH";

    /**
     * 系统 {@link Date#toString()} 使用的格式,并且 {@link java.util.Locale#US}. <span style="color:green"><code>{@value}</code></span>.
     * <p>
     * <p>
     * example: <span style="color:green">星期五 十二月 27 22:13:55 CST 2013</span>
     * </p>
     *
     * @see Date#toString()
     */
    public static final String forToString = "EEE MMM dd HH:mm:ss zzz yyyy";

    //***************************************************************************************************

    /**
     * Don't let anyone instantiate this class.
     */
    private DatePatternConstant() {
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }
}
