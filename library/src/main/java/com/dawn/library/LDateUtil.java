package com.dawn.library;

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
    private static final String PATTERN_DATETIME = "yyyy-MM-dd HH:mm:ss";
    private static final String PATTERN_DATE = "yyyy-MM-dd";
    private static final String PATTERN_TIME = "HH:mm:ss";

    private static SimpleDateFormat getDateTimeFormat() {
        return new SimpleDateFormat(PATTERN_DATETIME, Locale.getDefault());
    }

    private static SimpleDateFormat getDateFormat() {
        return new SimpleDateFormat(PATTERN_DATE, Locale.getDefault());
    }

    private static SimpleDateFormat getTimeFormat() {
        return new SimpleDateFormat(PATTERN_TIME, Locale.getDefault());
    }

    /**
     * 时间戳转换成日期时间
     * @param timestamp 日期时间long类型
     *
     * @return 日期时间字符串
     */
    public static String longToDateTime(long timestamp) {
        return getDateTimeFormat().format(new Date(timestamp));
    }

    /**
     * 时间戳转换成日期
     * @param timestamp 日期long类型
     *
     * @return 日期字符串
     */
    public static String longToDate(long timestamp) {
        return getDateFormat().format(new Date(timestamp));
    }

    /**
     * 时间戳转换成时间
     * @param timestamp 时间long类型
     *
     * @return 时间字符串
     */
    public static String longToTime(long timestamp) {
        return getTimeFormat().format(new Date(timestamp));
    }

    /**
     * 获取当前时间
     *
     * @return 当前时间
     */
    public static String getTime() {
        return getTimeFormat().format(new Date());
    }

    /**
     * 获取当前日期
     *
     * @return 当前日期
     */
    public static String getDate() {
        return getDateFormat().format(new Date());
    }

    /**
     * 获取当前日期时间
     *
     * @return 当前日期时间
     */
    public static String getDateTime(){
        return getDateTimeFormat().format(new Date());
    }

    /**
     * 根据自定义格式获取系统日期时间
     * @param format 格式的字符串
     *
     * @return 系统日期时间
     */
    public static String getDateTime(String format){
        return new SimpleDateFormat(format, Locale.getDefault()).format(new Date());
    }

    /**
     * 日期字符串转时间戳
     * @param dateTime 日期时间字符串 格式：yyyy-MM-dd HH:mm:ss
     * @return 时间戳
     */
    public static long dateTimeToLong(String dateTime) {
        try {
            Date date = getDateTimeFormat().parse(dateTime);
            return date != null ? date.getTime() : 0;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 日期字符串转时间戳
     * @param date 日期字符串 格式：yyyy-MM-dd
     * @return 时间戳
     */
    public static long dateToLong(String date) {
        try {
            Date d = getDateFormat().parse(date);
            return d != null ? d.getTime() : 0;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取当前时间戳
     * @return 当前时间戳（毫秒）
     */
    public static long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

    /**
     * 获取两个日期之间的天数差
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 天数差
     */
    public static int getDaysBetween(long startDate, long endDate) {
        return (int) ((endDate - startDate) / (24 * 60 * 60 * 1000));
    }

    /**
     * 判断是否为今天
     * @param timestamp 时间戳
     * @return 是否为今天
     */
    public static boolean isToday(long timestamp) {
        String today = longToDate(System.currentTimeMillis());
        String target = longToDate(timestamp);
        return today.equals(target);
    }

    /**
     * 获取星期几
     * @param timestamp 时间戳
     * @return 星期几 (1-7，1表示星期一)
     */
    public static int getDayOfWeek(long timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek == 1 ? 7 : dayOfWeek - 1; // 调整为1-7，1表示星期一
    }

    /**
     * 获取星期几的中文名称
     * @param timestamp 时间戳
     * @return 星期几的中文名称
     */
    public static String getDayOfWeekName(long timestamp) {
        String[] names = {"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};
        return names[getDayOfWeek(timestamp) - 1];
    }

    /**
     * 在时间戳基础上增加天数
     * @param timestamp 时间戳
     * @param days 增加的天数（可以为负数）
     * @return 新的时间戳
     */
    public static long addDays(long timestamp, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        calendar.add(Calendar.DAY_OF_YEAR, days);
        return calendar.getTimeInMillis();
    }

    /**
     * 在时间戳基础上增加小时
     * @param timestamp 时间戳
     * @param hours 增加的小时数（可以为负数）
     * @return 新的时间戳
     */
    public static long addHours(long timestamp, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTimeInMillis();
    }

    /**
     * 在时间戳基础上增加分钟
     * @param timestamp 时间戳
     * @param minutes 增加的分钟数（可以为负数）
     * @return 新的时间戳
     */
    public static long addMinutes(long timestamp, int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        calendar.add(Calendar.MINUTE, minutes);
        return calendar.getTimeInMillis();
    }

    /**
     * 格式化时间间隔为可读字符串
     * @param millis 毫秒数
     * @return 可读的时间字符串（如 "2天3小时5分钟"）
     */
    public static String formatDuration(long millis) {
        if (millis < 0) millis = -millis;
        long seconds = millis / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        if (days > 0) {
            return days + "天" + (hours % 24) + "小时" + (minutes % 60) + "分钟";
        } else if (hours > 0) {
            return hours + "小时" + (minutes % 60) + "分钟";
        } else if (minutes > 0) {
            return minutes + "分钟" + (seconds % 60) + "秒";
        } else {
            return seconds + "秒";
        }
    }

    /**
     * 判断是否为闰年
     * @param year 年份
     * @return 是否为闰年
     */
    public static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    /**
     * 根据生日时间戳计算年龄
     * @param birthdayTimestamp 生日时间戳
     * @return 年龄
     */
    public static int getAge(long birthdayTimestamp) {
        Calendar birthday = Calendar.getInstance();
        birthday.setTimeInMillis(birthdayTimestamp);
        Calendar now = Calendar.getInstance();

        int age = now.get(Calendar.YEAR) - birthday.get(Calendar.YEAR);
        if (now.get(Calendar.MONTH) < birthday.get(Calendar.MONTH) ||
                (now.get(Calendar.MONTH) == birthday.get(Calendar.MONTH)
                        && now.get(Calendar.DAY_OF_MONTH) < birthday.get(Calendar.DAY_OF_MONTH))) {
            age--;
        }
        return Math.max(age, 0);
    }

    /**
     * 获取某个月的天数
     * @param year 年份
     * @param month 月份（1-12）
     * @return 该月的天数
     */
    public static int getDaysInMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取友好的时间描述（如"刚刚"、"5分钟前"、"昨天"等）
     * @param timestamp 时间戳
     * @return 友好的时间描述
     */
    public static String getFriendlyTime(long timestamp) {
        long now = System.currentTimeMillis();
        long diff = now - timestamp;

        if (diff < 0) {
            return longToDateTime(timestamp);
        }

        long seconds = diff / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        if (seconds < 60) {
            return "刚刚";
        } else if (minutes < 60) {
            return minutes + "分钟前";
        } else if (hours < 24) {
            return hours + "小时前";
        } else if (days < 2) {
            return "昨天";
        } else if (days < 3) {
            return "前天";
        } else if (days < 30) {
            return days + "天前";
        } else if (days < 365) {
            return (days / 30) + "个月前";
        } else {
            return (days / 365) + "年前";
        }
    }

    /**
     * 获取今天的开始时间戳（00:00:00）
     * @return 今天开始的时间戳
     */
    public static long getStartOfDay() {
        return getStartOfDay(System.currentTimeMillis());
    }

    /**
     * 获取指定时间当天的开始时间戳（00:00:00）
     * @param timestamp 时间戳
     * @return 当天开始的时间戳
     */
    public static long getStartOfDay(long timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取指定时间当天的结束时间戳（23:59:59）
     * @param timestamp 时间戳
     * @return 当天结束的时间戳
     */
    public static long getEndOfDay(long timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTimeInMillis();
    }

}
