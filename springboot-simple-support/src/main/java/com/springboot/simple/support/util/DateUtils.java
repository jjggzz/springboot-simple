package com.springboot.simple.support.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 * @author jgz
 * @version 1.0
 * @date 2020/7/9 9:15
 **/
public class DateUtils {

    private static final int[] WEEK_DAY = {7, 1, 2, 3, 4, 5, 6};

    /**
     * 获取当前毫秒值
     * @return {@link long}
     * @author jgz
     * @date 9:17 2020/7/9
     */
    public static long currentTimeInMillis(){
        return Calendar.getInstance().getTimeInMillis();
    }

    /**
     * 获取该日期是星期几
     * @param time 毫秒值
     * @return {@link int}
     * @author jgz
     * @date 9:22 2020/7/9
     */
    public static int getWeek(long time) {
        return getWeek(new Date(time));
    }

    /**
     * 获取该日期是星期几
     * @param date 时间
     * @return {@link int}
     * @author jgz
     * @date 9:23 2020/7/9
     */
    public static int getWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return WEEK_DAY[w];
    }

    /**
     * 获取该时间在当前小时的第几分钟
     * @param time 毫秒值
     * @return {@link int}
     * @author jgz
     * @date 9:54 2020/7/9
     */
    public static int getMinuteOfHour(long time){
        return getMinuteOfHour(new Date(time));
    }

    /**
     * 获取该时间在当前小时的第几分钟
     * @param date 时间
     * @return {@link int}
     * @author jgz
     * @date 9:55 2020/7/9
     */
    public static int getMinuteOfHour(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return  calendar.get(Calendar.MINUTE);
    }

    /**
     * 获取该时间在当天的第几个小时
     * @param time 毫秒值
     * @return {@link int}
     * @author jgz
     * @date 9:59 2020/7/9
     */
    public static int getHourOfDay(long time){
        return getHourOfDay(new Date(time));
    }

    /**
     * 获取该时间在当天的第几个小时
     * @param date 时间
     * @return {@link int}
     * @author jgz
     * @date 10:00 2020/7/9
     */
    public static int getHourOfDay(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取该时间在当月的第几天
     * @param time 毫秒值
     * @return {@link int}
     * @author jgz
     * @date 10:00 2020/7/9
     */
    public static int getDayOfMonth(long time){
        return getDayOfMonth(new Date(time));
    }

    /**
     * 获取该时间在当月的第几天
     * @param date 时间
     * @return {@link int}
     * @author jgz
     * @date 10:00 2020/7/9
     */
    public static int getDayOfMonth(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取简单的年月日 格式: yyyy-MM-dd
     * @param time 毫秒值
     * @return {@link java.lang.String}
     * @author jgz
     * @date 11:01 2020/7/9
     */
    public static String simpleDateFormat(long time){
        return simpleDateFormat(new Date(time));
    }

    /**
     * 获取简单的年月日 格式: yyyy-MM-dd
     * @param date 日期
     * @return {@link java.lang.String}
     * @author jgz
     * @date 11:01 2020/7/9
     */
    public static String simpleDateFormat(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }

    /**
     * 获取详细的年月日时分秒 格式: yyyy-MM-dd HH:mm:ss
     * @param time 毫秒值
     * @return {@link java.lang.String}
     * @author jgz
     * @date 11:04 2020/7/9
     */
    public static String detailDateFormat(long time){
        return detailDateFormat(new Date(time));
    }

    /**
     * 获取详细的年月日时分秒 格式: yyyy-MM-dd HH:mm:ss
     * @param date 时间
     * @return {@link java.lang.String}
     * @author jgz
     * @date 11:05 2020/7/9
     */
    public static String detailDateFormat(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }

    /**
     * 获取简单的年月日 格式: yyyy年MM月dd日
     * @param time 毫秒值
     * @return {@link java.lang.String}
     * @author jgz
     * @date 11:08 2020/7/9
     */
    public static String simpleDateFormatZhCn(long time){
        return simpleDateFormatZhCn(new Date(time));
    }

    /**
     * 获取简单的年月日 格式: yyyy年MM月dd日
     * @param date 时间
     * @return {@link java.lang.String}
     * @author jgz
     * @date 11:08 2020/7/9
     */
    public static String simpleDateFormatZhCn(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        return simpleDateFormat.format(date);
    }

    /**
     * 获取详细的年月日时分秒 格式: yyyy年MM月dd日 HH时mm分ss秒
     * @param time 毫秒值
     * @return {@link java.lang.String}
     * @author jgz
     * @date 11:04 2020/7/9
     */
    public static String detailDateFormatZhCn(long time){
        return detailDateFormatZhCn(new Date(time));
    }

    /**
     * 获取详细的年月日时分秒 格式: yyyy年MM月dd日 HH时mm分ss秒
     * @param date 时间
     * @return {@link java.lang.String}
     * @author jgz
     * @date 11:05 2020/7/9
     */
    public static String detailDateFormatZhCn(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        return simpleDateFormat.format(date);
    }

    /**
     * 获取当前时间戳
     * @return {@link String}
     * @author jgz
     * @date 11:26 2020/7/9
     */
    public static String getCurrentTimestamp(){
        return getTimestamp(System.currentTimeMillis());
    }

    /**
     * 获取时间戳
     * @param time 毫秒值
     * @return {@link java.lang.String}
     * @author jgz
     * @date 11:26 2020/7/9
     */
    public static String getTimestamp(long time){
        return getTimestamp(new Date(time));
    }

    /**
     * 获取时间戳
     * @param date 时间
     * @return {@link java.lang.String}
     * @author jgz
     * @date 11:26 2020/7/9
     */
    public static String getTimestamp(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return simpleDateFormat.format(date);
    }

    /**
     * 将简单日期字符串解析为Date,失败返回空
     * @param str 格式化字串
     * @return {@link java.util.Date}
     * @author jgz
     * @date 11:51 2020/7/9
     */
    public static Date simpleDateParseToDate(String str) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = simpleDateFormat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 将详细日期字符串解析为Date,失败返回空
     * @param str 格式化字串
     * @return {@link java.util.Date}
     * @author jgz
     * @date 13:32 2020/7/9
     */
    public static Date detailDateParseToDate(String str) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 将简单日期字符串解析为Date,失败返回空
     * @param str 格式化字串
     * @return {@link java.util.Date}
     * @author jgz
     * @date 11:51 2020/7/9
     */
    public static Date simpleDateParseToDateZhCn(String str) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = null;
        try {
            date = simpleDateFormat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 将详细日期字符串解析为Date,失败返回空
     * @param str 格式化字串
     * @return {@link java.util.Date}
     * @author jgz
     * @date 13:32 2020/7/9
     */
    public static Date detailDateParseToDateZhCn(String str) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        Date date = null;
        try {
            date = simpleDateFormat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 获取两个日期之间相差的天数,返回[date1-date2]的结果
     * @param date1 日期1
     * @param date2 日期2
     * @return {@link int}
     * @author jgz
     * @date 13:44 2020/7/9
     */
    public static int getDifferDay(Date date1,Date date2){
        return getDifferDay(date1.getTime(),date2.getTime());
    }

    /**
     * 获取两个毫秒值之间相差的天数,返回[time1-time2]的结果
     * @param time1
     * @param time2
     * @return {@link int}
     * @author jgz
     * @date 13:45 2020/7/9
     */
    public static int getDifferDay(long time1,long time2){
        return (int)((time1 - time2) / (1000 * 3600 * 24));
    }

    /**
     * 获取某个日期与当前时间相差的天数,返回[当前时间-date]的结果
     * @param date 时间
     * @return {@link int}
     * @author jgz
     * @date 13:48 2020/7/9
     */
    public static int getDifferDayByToday(Date date){
        return getDifferDay(new Date(),date);
    }

    /**
     * 获取某个日期与当前时间相差的天数,返回[当前毫秒值-time]的结果
     * @param time 毫秒值
     * @return {@link int}
     * @author jgz
     * @date 13:49 2020/7/9
     */
    public static int getDifferDayByToday(long time){
        return getDifferDay(System.currentTimeMillis(),time);
    }

    /**
     * 将时间修改为?天后的时间,days为正数往后,days为负数往前
     * @param date 时间
     * @param days 天数
     * @return {@link java.util.Date}
     * @author jgz
     * @date 13:55 2020/7/9
     */
    public static Date changeDateByDay(Date date,int days){
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.add(Calendar.DAY_OF_MONTH,days);
        return instance.getTime();
    }

    /**
     * 将毫秒值修改为?天后的毫秒值,days为正数往后,days为负数往前
     * @param time 毫秒值
     * @param days 天数
     * @return {@link long}
     * @author jgz
     * @date 13:58 2020/7/9
     */
    public static long changeDateByDay(long time,int days){
        return changeDateByDay(new Date(time),days).getTime();
    }

    /**
     * 将时间修改为?月后的时间,months为正数往后,months为负数往前
     * @param date 时间
     * @param months 月数
     * @return {@link java.util.Date}
     * @author jgz
     * @date 13:55 2020/7/9
     */
    public static Date changeDateByMonth(Date date,int months){
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.add(Calendar.MONTH,months);
        return instance.getTime();
    }

    /**
     * 将毫秒值修改为?天后的毫秒值,months为正数往后,months为负数往前
     * @param time 毫秒值
     * @param months 月数
     * @return {@link long}
     * @author jgz
     * @date 13:58 2020/7/9
     */
    public static long changeDateByMonth(long time,int months){
        return changeDateByMonth(new Date(time),months).getTime();
    }

    /**
     * 获取日期在当天的开始时间
     * @param date 时间
     * @return {@link Date}
     * @author jgz
     * @date 15:56 2020/7/9
     */
    public static Date getDateDayStart(Date date){
        SimpleDateFormat dateSdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        Date res = null;
        try {
            res = dateSdf.parse(dateSdf.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return res;
    }

}
