package com.dawn.library.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 日期工具类
 */
@SuppressWarnings("unused")
public class LDateUtil {
    private static final SimpleDateFormat DATE_FORMAT_DATETIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
    private static final SimpleDateFormat DATE_FORMAT_DATE = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    private static final SimpleDateFormat DATE_FORMAT_TIME = new SimpleDateFormat("HH:mm:ss", Locale.US);

    /**
     * 时间戳转换成日期时间
     * @param timestamp 日期时间long类型
     *
     * @return 日期时间字符串
     */
    public static String longToDateTime(long timestamp) {
        return DATE_FORMAT_DATETIME.format(new Date(timestamp));
    }

    /**
     * 时间戳转换成日期
     * @param timestamp 日期long类型
     *
     * @return 日期字符串
     */
    public static String longToDate(long timestamp) {
        return DATE_FORMAT_DATE.format(new Date(timestamp));
    }

    /**
     * 时间戳转换成时间
     * @param timestamp 时间long类型
     *
     * @return 时间字符串
     */
    public static String longToTime(long timestamp) {
        return DATE_FORMAT_TIME.format(new Date(timestamp));
    }

    /**
     * 获取当前时间
     *
     * @return 当前时间
     */
    public static String getTime() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        return cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND);
    }

    /**
     * 获取当前日期
     *
     * @return 当前日期
     */
    public static String getDate() {
        return new SimpleDateFormat("yyyyMMdd", Locale.US).format(System.currentTimeMillis());
    }

    /**
     * 获取当前日期时间
     *
     * @return 当前日期时间
     */
    public static String getDateTime(){
        return DATE_FORMAT_DATETIME.format(System.currentTimeMillis());
    }

    /**
     * 根据自定义格式获取系统日期时间
     * @param format 格式的字符串
     *
     * @return 系统日期时间
     */
    public static String getDateTime(String format){
        return new SimpleDateFormat(format, Locale.US).format(System.currentTimeMillis());
    }

}
