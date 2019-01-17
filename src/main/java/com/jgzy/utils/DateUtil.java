package com.jgzy.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAmount;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static final String CHINA_DATE_FORMAT = "yyyy年MM月dd日";
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String TIME_FORMAT = "HH:mm:ss";

    /**
     * 获取系统时间 yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getNow() {
        Date date = new Date();
        return formatDate(date, DATETIME_FORMAT);
    }

    /**
     * 根据毫秒数 格式化成 时间格式
     *
     * @param time
     * @param fmt  fmt为空的时候，默认是yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String formatDate(Long time, String fmt) {
        return formatDate(getTime(time), fmt);
    }

    /**
     * 格式化日期
     *
     * @param date
     * @param fmt  ,fmt为空，默认是 yyyy-MM-dd
     * @return
     */
    public static String formatDate(Date date, String fmt) {
        if (date == null) {
            return "";
        }
        if (fmt == null) fmt = "yyyy-MM-dd";
        SimpleDateFormat myFormat = new SimpleDateFormat(fmt);
        return myFormat.format(date);
    }

    /**
     * 根据毫秒数 获取当前时间
     *
     * @param time
     * @return
     */
    public static Timestamp getTime(Long time) {
        return new Timestamp(time);
    }

    /**
     * 格式化毫秒时间戳，格式yyyy-MM-dd HH:mm:ss.SSS
     *
     * @param millis
     * @return
     */
    public static String formatTimeInMillis(Long millis) {
        return formatDate(millis, "yyyy-MM-dd HH:mm:ss.SSS");
    }

    /**
     * 获取今天 日期
     *
     * @return
     */
    public static Date getDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    /**
     * 当前日期的后几天
     *
     * @param date
     * @param n
     * @return
     */
    public static Date getAfterDay(Date date, int n) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, n);
        return c.getTime();
    }

    //---------获取时间天数----------------------------------------------

    /**
     * 获取当前月的最后一天
     *
     * @param date
     * @return
     */
    public static String getMonthEnd(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DATE, c.getActualMaximum(Calendar.DATE));
        c.set(Calendar.HOUR, c.getActualMaximum(Calendar.HOUR));
        c.set(Calendar.MINUTE, c.getActualMaximum(Calendar.MINUTE));
        c.set(Calendar.SECOND, c.getActualMaximum(Calendar.SECOND));
        return formatDate(c.getTime(), DATETIME_FORMAT);
    }

    /**
     * 获得当前日期的月份第一天
     *
     * @param date
     * @return
     */
    public static String getMonthBegin(Date date) {
        return formatDate(date, "yyyy-MM-01 00:00:00");
    }

    /**
     * 获取当天最大时间
     *
     * @param date
     * @return
     */
    public static String getDayEnd(Date date) {
        return formatDate(date, "yyyy-MM-dd 23:59:59");
    }

    /**
     * 获得当前日期的月份第一天
     *
     * @param date
     * @return
     */
    public static String getDayBegin(Date date) {
        return formatDate(date, "yyyy-MM-dd 00:00:00");
    }

    //-----------------格式化字符串为日期--------------------------------------

    /**
     * 格式化字符串为日期
     *
     * @param date
     * @param format
     * @return
     */
    public static Date parseDate(String date, String format) {
        try {
            return new SimpleDateFormat(format).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date parseDate(String date) {
        return parseDate(date, DATE_FORMAT);
    }

    public static Date parseChinaDate(String date) {
        return parseDate(date, CHINA_DATE_FORMAT);
    }

    public static Date parseDateTime(String date) {
        return parseDate(date, DATETIME_FORMAT);
    }

    public static Date parseTime(String date) {
        return parseDate(date, TIME_FORMAT);
    }

    /**
     * 获取几小时后的时间
     *
     * @param hour 小时
     * @return 时间
     */
    public static Date getHoursLater(int hour) {
        Date date = new Date();
        Calendar dar = Calendar.getInstance();
        dar.setTime(date);
        dar.add(java.util.Calendar.HOUR_OF_DAY, hour);
        return dar.getTime();
    }

    /**
     * 获取几天后的时间
     *
     * @param days 天
     * @return 时间
     */
    public static Date getDaysLater(int days) {
        Date date = new Date();
        Calendar dar = Calendar.getInstance();
        dar.setTime(date);
        dar.add(Calendar.DATE, days);
        return dar.getTime();
    }

    /**
     * 获取几分钟后的时间
     *
     * @param mins 天
     * @return 时间
     */
    public static Date getMINsLater(int mins) {
        Date date = new Date();
        Calendar dar = Calendar.getInstance();
        dar.setTime(date);
        dar.add(Calendar.MINUTE, mins);
        return dar.getTime();
    }
}
