package com.due.basic.tookit.utils;

import com.due.basic.toolkit.enums.YesOrNoEnum;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DateUtil extends DateUtils {

    public static final String DAY_PATTERN = "yyyy-MM-dd";
    public static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DATETIME_MS_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String DATETIME_PATTERN_YMD = "yyyyMMdd";
    public static final String DATETIME_PATTERN_YMDS = "yyyyMMddHHmmss";
    public static final String DATETIME_PATTERN_MDS = "MMddHHHmmss";
    public static final String DATETIME_PATTERN_YM = "yyyy-MM";
    public static final String DATETIME_PATTERN_YEAR = "yyyy";
    public static final String DATETIME_PATTERN_DAY = "MMdd";
    public static final String DATETIME_PATTERN_MD = "MM.dd";
    public static final String DATETIME_PATTERN_HMS = "HH:mm:ss";
    public static final String DATETIME_PATTERN_HM = "HH:mm";
    public static final String DATETIME_PATTERN_M = "yyMM";
    public static final String DATETIME_PATTERN_Y = "yy";

    public static final String DATETIME_PATTERN_YMDE = "yyyy-MM-dd EEEE";

    public static final String[] DATETIME_PATTERNS = new String[]{"yyyy-MM-dd", "yyyy/MM/dd", "yyyy.MM.dd", "yyyyMMdd", "yyyy年MM月dd日", "yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss", "yyyy年MM月dd日HH时mm分ss秒", "yyyy-MM-dd'T'HH:mm:ss'Z'", "yyyy-MM-dd HH:mm:ss.S", "yyyy-MM-dd'T'HH:mm:ss.S'Z'"};

    /**
     * 获取当前时间  日期格式
     *
     * @return
     */
    public static Date getDate() {
        return new Date();
    }

    /**
     * 获取昨天的的时间
     *
     * @return
     */
    public static Date getYesterday() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
        return calendar.getTime();
    }

    /**
     * 获取明天的的时间
     *
     * @return
     */
    public static Date getTomorrow() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);
        return calendar.getTime();
    }

    /**
     * 获取当前时间的一周开始时间 (00:00:00)
     *
     * @return
     */
    public static Date getWeekStartData() {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取当前时间的开始时间
     *
     * @return
     */
    public static Date getNowStartData() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取当前时间的结束时间
     *
     * @return
     */
    public static Date getNowEndData() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    /**
     * 获取当前时间的所在周的结束时间 （23:59:999）
     *
     * @return
     */
    public static Date getWeekEndData() {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }


    /**
     * 获取当前日期所在的月的第一天（00:00:00）
     *
     * @return
     */
    public static Date getMonthStartData() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取当前日期所在的月的最后一天（11:59:99.999）
     *
     * @return
     */
    public static Date getMonthEndData() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    /**
     * 获取当前日期所在的年的第一天（00:00:00）
     *
     * @return
     */
    public static Date getYearStartData() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 0);
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取当前日期所在的年的最后一天（11:59:99.999）
     *
     * @return
     */
    public static Date getYearEndData() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 1);
        calendar.set(Calendar.DAY_OF_YEAR, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    /**
     * 格式化时间转为字符串 'yyyy-MM-dd'
     *
     * @param date
     * @return
     */
    public static String formatDateYMD(Date date) {
        return DateFormatUtils.format(date, DAY_PATTERN);
    }

    /**
     * 格式化时间转为字符串 'yyyy-MM-dd HH:mm:ss'
     *
     * @param date
     * @return
     */
    public static String formatDateYMDHMS(Date date) {
        return DateFormatUtils.format(date, DATETIME_PATTERN);
    }

    /**
     * 格式化时间转为字符串 'yyyyMMdd'
     *
     * @param date
     * @return
     */
    public static String formatYMD(Date date) {
        return DateFormatUtils.format(date, DATETIME_PATTERN_YMD);
    }

    /**
     * 格式化时间转为字符串 'yyyyMMddHHmmss'
     *
     * @param date
     * @return
     */
    public static String formatYMDHMS(Date date) {
        return DateFormatUtils.format(date, DATETIME_PATTERN_YMDS);
    }

    /**
     * 格式化时间转为字符串
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String formatDate(Date date, String pattern) {
        return DateFormatUtils.format(date, pattern);
    }

    public static String formatDateUTC(Date date, String pattern, Locale locale) {
        return DateFormatUtils.formatUTC(date, pattern, locale);
    }

    public static String formatDateString(String date, String pattern1, String pattern2) {
        return formatDate(parseDate(date, pattern1), pattern2);
    }

    /**
     * 格式化指定时间  00:00:00
     *
     * @param date
     * @return
     */
    public static Date formatStartDate(Date date) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 格式化指定时间  23:59:59
     *
     * @param date
     * @return
     */
    public static Date formatEndDate(Date date) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }

    /**
     * 获取当前时间多少秒之后的时间
     *
     * @return
     */
    public static Date addSeconds(int seconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.SECOND, seconds);
        return calendar.getTime();
    }

    /**
     * 获取当前时间多少分钟之后的时间
     *
     * @param minute
     * @return
     */
    public static Date addMinutes(int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, minute);
        return calendar.getTime();
    }

    /**
     * 获取指定时间多少分钟之后的时间
     *
     * @param minute
     * @return
     */
    public static Date addMinutes(Date date, int minute) {
        if (date == null) {
            date = new Date();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minute);
        return calendar.getTime();
    }

    /**
     * 获取当前时间多少天之后的时间
     *
     * @param day
     * @return
     */
    public static Date addDay(int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }

    /**
     * 字符串转换时间格式
     *
     * @param str
     * @param pattern
     * @return
     */
    public static Date parseDate(String str, String pattern) {
        try {
            return parseDate(str, new String[]{pattern});
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 判断指定时间是否在当前时间之后
     *
     * @param date
     * @return
     */
    public static YesOrNoEnum checkDateAfter(Date date) {
        if (date == null) {
            return null;
        }
        if (date.after(new Date())) {
            return YesOrNoEnum.YES;
        } else {
            return YesOrNoEnum.NO;
        }
    }

    /**
     * 获取两个日期之间的所有日期(字符串格式, 按天计算)
     */
    public static List<String> getBetweenDays(Date start, Date end) {
        List<String> result = new ArrayList<>();

        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(start);

        tempStart.add(Calendar.DAY_OF_YEAR, 1);
        SimpleDateFormat sdf = new SimpleDateFormat(DAY_PATTERN);
        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(end);
//        result.add(sdf.format(start));
        while (!tempStart.after(tempEnd)) {
            result.add(sdf.format(tempStart.getTime()));
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
        }
        return result;
    }

    /**
     * 获取两个日期之间的所有月(字符串格式, 按月计算)
     */
    public static List<String> getBetweenMonths(Date start, Date end) {
        List<String> result = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(start);
        tempStart.add(Calendar.MONTH, 1);
        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(end);
        result.add(sdf.format(start));
        while (tempStart.before(tempEnd)) {
            result.add(sdf.format(tempStart.getTime()));
            tempStart.add(Calendar.MONTH, 1);
        }
        return result;
    }

    public static String checkTimeAfter(String time) {
        String hms = " 17:00:00";
        if (StringUtils.isBlank(time)) {
            return StringUtils.join(formatDate(getDate(), DAY_PATTERN), hms);
        }
        String T = DATETIME_PATTERN_HMS;
        if (time.length() == 5) {
            T = DATETIME_PATTERN_HM;
        } else if (time.length() == 8) {
            T = DATETIME_PATTERN_HMS;
        }
        Date checkDate = parseDate(time, T);

        SimpleDateFormat sdf = new SimpleDateFormat(T);
        Calendar calendar = Calendar.getInstance();

        Date newDate = parseDate(sdf.format(calendar.getTime()), T);

        if (!newDate.after(checkDate)) {
            return StringUtils.join(formatDate(getDate(), DAY_PATTERN), hms);
        }

        return StringUtils.join(formatDate(getTomorrow(), DAY_PATTERN), hms);
    }

    public static int getYearDiff(Date start, Date end) {
        Calendar startCal = Calendar.getInstance();
        Calendar endCal = Calendar.getInstance();
        startCal.setTime(start);
        endCal.setTime(end);
        return endCal.get(Calendar.YEAR) - startCal.get(Calendar.YEAR);
    }

    public static Date getDateYMD(Date date) {
        return parseDate(formatDateYMD(date), DAY_PATTERN);
    }

    public static String getYMDString(Date date) {
        return DateFormatUtils.format(date, DATETIME_PATTERN_YMD);
    }

    public static String getYearString(Date date) {
        return DateFormatUtils.format(date, DATETIME_PATTERN_YEAR);
    }

    public static String getDayString(Date date) {
        return DateFormatUtils.format(date, DATETIME_PATTERN_DAY);
    }

    public static String getYMString(Date date) {
        return DateFormatUtils.formatUTC(date, DATETIME_PATTERN_M);
    }

    public static String getYString(Date date) {
        return DateFormatUtils.formatUTC(date, DATETIME_PATTERN_Y);
    }

    public static Date lastDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, 5);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    public static Date lastDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    public static Date lastDayOfYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 1);
        calendar.set(Calendar.DAY_OF_YEAR, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }
//
//    public static void main(String[] args) {
//        Date date = lastDayOfMonth();
//        String s = formatDateYMD(date);
//        System.out.println(s);
////        System.out.println("="+getYMString(lastDayOfYear()));
////        System.out.println(">>"+StringUtils.substring("35464457568678", "35464457568678".length()-2, "35464457568678".length()));
//	}
}
