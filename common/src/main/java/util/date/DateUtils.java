package util.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author sunwell
 * @date 2017/6/8
 */
public class DateUtils {

    public static Integer YEAR = 1;
    public static Integer QUARTER = 2;
    public static Integer MONTH = 3;
    public static Integer WEEK = 4;
    public static Integer DAY = 5;

    public static String DATE_FORMAT = "yyyy-MM-dd";
    public static String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static Calendar getCalendarFormYear(int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cal.set(Calendar.YEAR, year);
        return cal;
    }

    /**
     * @author kasia
     * @date: Created in
     * @modified By:
     * @description: 获取当前时间月份
     */
    public static int getMonth(Long time) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(time));
        int month = c.get(Calendar.MONTH);
        return month + 1;
    }

    /**
     * @author kasia
     * @date: Created in
     * @modified By:
     * @description:获取时间所在季度
     */
    public static int getSeason(Date date) {

        int season = 0;

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int month = c.get(Calendar.MONTH);
        switch (month) {
            case Calendar.JANUARY:
            case Calendar.FEBRUARY:
            case Calendar.MARCH:
                season = 1;
                break;
            case Calendar.APRIL:
            case Calendar.MAY:
            case Calendar.JUNE:
                season = 2;
                break;
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.SEPTEMBER:
                season = 3;
                break;
            case Calendar.OCTOBER:
            case Calendar.NOVEMBER:
            case Calendar.DECEMBER:
                season = 4;
                break;
            default:
                break;
        }
        return season;
    }

    /**
     * @author kasia
     * @date: Created in
     * @modified By:
     * @description: 获取某年某周的开始时间
     */
    public static String getStartDayOfWeekNo(int year, int weekNo) {
        Calendar cal = getCalendarFormYear(year);
        cal.set(Calendar.WEEK_OF_YEAR, weekNo);
        return cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" +
                cal.get(Calendar.DAY_OF_MONTH);

    }

    /**
     * @author kasia
     * @date: Created in
     * @modified By:
     * @description: 获取某年某周的结束时间
     */
    public static String getEndDayOfWeekNo(int year, int weekNo) {
        Calendar cal = getCalendarFormYear(year);
        cal.set(Calendar.WEEK_OF_YEAR, weekNo);
        cal.add(Calendar.DAY_OF_WEEK, 6);
        return cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" +
                cal.get(Calendar.DAY_OF_MONTH);
    }


    /**
     * @author kasia
     * @date: Created in
     * @modified By:
     * @description: 获取时间戳所在年
     */
    public static int getYear(Long time) {
        String data1 = new SimpleDateFormat("yyyy").format(time);
        return Integer.valueOf(data1);
    }

    /**
     * @author kasia
     * @date: Created in
     * @modified By:
     * @description:当前时间戳所在周是第几年的周,返回年
     */
    public static int getYearWithWeek(Long time) {
        Calendar cal = Calendar.getInstance();

        cal.setTime(new Date(time));

        int d = 0;
        if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
            d = -6;
        } else {
            d = 2 - cal.get(Calendar.DAY_OF_WEEK);
        }
        cal.add(Calendar.DAY_OF_WEEK, d);
        cal.add(Calendar.DAY_OF_WEEK, 6);
        // 所在周结束日期
        String data2 = new SimpleDateFormat("yyyy").format(cal.getTime());
        return Integer.valueOf(data2);
    }

    /**
     * @author kasia
     * @date: Created in
     * @modified By:
     * @description: 获取当前时间戳的所在周的开始结束日期
     */
    public static String getFirstAndLastOfWeek(Long time) throws ParseException {
        Calendar cal = Calendar.getInstance();

        cal.setTime(new Date(time));

        int d = 0;
        if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
            d = -6;
        } else {
            d = 2 - cal.get(Calendar.DAY_OF_WEEK);
        }
        cal.add(Calendar.DAY_OF_WEEK, d);
        // 所在周开始日期
        String data1 = new SimpleDateFormat("yyyy/MM/dd").format(cal.getTime());
        cal.add(Calendar.DAY_OF_WEEK, 6);
        // 所在周结束日期
        String data2 = new SimpleDateFormat("yyyy/MM/dd").format(cal.getTime());
        return data1 + "-" + data2;

    }

    /**
     * @author kasia
     * @date: Created in
     * @modified By:
     * @description: 获取当前时间戳所在年的第几周
     */
    public static int getWeek(Long time) {
        Date date = new Date(time);
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * @author kasia
     * @date: Created in
     * @modified By:
     * @description: 返回当前时间的零点时间
     */
    public static Long getTheDayZero(Long time) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        String str = simpleDateFormat.format(new Date(time));
        return simpleDateFormat.parse(str).getTime();
    }

    /**
     * 日期转字符串
     *
     * @param date
     * @param format
     * @return
     */
    public static String formatDate(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format); // "yyyy-MM-dd HH:mm:ss"
        return sdf.format(date);
    }

    /**
     * 字符串转日期
     *
     * @param strDate
     * @param format
     * @return
     * @throws ParseException
     */
    public static Date parseDate(String strDate, String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.parse(strDate);
    }


    /**
     * 是否是有效日期
     *
     * @param str
     * @return
     */
    public static boolean isValidDate(String str) {
        boolean convertSuccess = true;
        // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        try {
            // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            Date.parse(str);
            format.setLenient(false);
            format.format(new Date(str));
//            format.parse(str);
        } catch (Exception e) {
            // e.printStackTrace();
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            convertSuccess = false;
        }
        return convertSuccess;
    }


    /**
     * 将制定日期加减相应月份
     *
     * @param date
     * @param add_month
     * @return
     */
    public static Date addMonth(Date date, int add_month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, add_month);
        return calendar.getTime();
    }

    /**
     * 将制定日期加减相应天数
     *
     * @param date    原日期
     * @param add_day 天数，正数为加几天，负数为减几天
     * @return
     */
    public static Date addDay(Date date, int add_day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, add_day);
        return calendar.getTime();
    }

    public static Date initDateByDay() {
//        long current = System.currentTimeMillis();
//        return new Date(current / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset());
        long now = System.currentTimeMillis() / 1000L;
        long daySecond = 60 * 60 * 24;
        long dayTime = now - (now + 8 * 3600) % daySecond;
        return new Date(dayTime * 1000);
    }

    /**
     * 将制定日期加减相应小时
     *
     * @param date     原日期
     * @param add_hour 小时，正数为加几小时，负数为减几小时
     * @return
     */
    public static Date addHour(Date date, int add_hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, add_hour);
        return calendar.getTime();
    }

    /**
     * 将指定日期加上相应分钟
     *
     * @param date
     * @param add_minute
     * @return
     */
    public static Date addMinute(Date date, int add_minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, add_minute);
        return calendar.getTime();
    }

    /**
     * 将指定日期加上相应秒
     *
     * @param date
     * @param add_minute
     * @return
     */
    public static Date addSecond(Date date, int add_second) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, add_second);
        return calendar.getTime();
    }

    public static long getDistanceDays(String str1, String str2) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date one;
        Date two;
        long days = 0;
        try {
            one = df.parse(str1);
            two = df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff;
            if (time1 > time2) {
                diff = time1 - time2;
            } else {
                diff = 0;
            }
            days = diff / (1000 * 60 * 60 * 24);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }

    /**
     * 日期加减计算
     *
     * @param d
     * @param field
     * @param amount
     * @return
     */
    public static Date dateCalculate(Date d, int field, int amount) {
        if (d == null) {
            return null;
        }
        GregorianCalendar g = new GregorianCalendar();
        g.setGregorianChange(d);
        g.add(field, amount);
        return g.getTime();
    }

    public static Date dateCalculate2(Date d, int field, int amount) {
        if (d == null) {
            return null;
        }
        Calendar g = Calendar.getInstance();
        g.setTime(d);
        g.add(field, amount);
        return g.getTime();
    }


    public static Date setDateField2(Date d, int field, int amount) {
        if (d == null) {
            return null;
        }
        Calendar g = Calendar.getInstance();
        g.setTime(d);
        g.set(field, amount);
        return g.getTime();
    }


    /**
     * 时间戳转化为Date
     *
     * @param format
     * @param timestamp
     * @return
     * @throws ParseException
     */
    public static Date timestampToDate(String format, Long timestamp) throws ParseException {
        if (format == null || format.trim().isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.parse(sdf.format(timestamp));
    }

    /**
     * 设置时分秒都为0
     *
     * @param date
     * @return
     */
    public static Date setDateHourMinSecToZero(Date date) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date);
        // 将时分秒,毫秒域清零
        cal1.set(Calendar.HOUR_OF_DAY, 0);
        cal1.set(Calendar.MINUTE, 0);
        cal1.set(Calendar.SECOND, 0);
        cal1.set(Calendar.MILLISECOND, 0);
//        date = setDatefield(date, GregorianCalendar.HOUR, 0);
//        date = setDatefield(date, GregorianCalendar.MINUTE, 0);
//        date = setDatefield(date, GregorianCalendar.SECOND, 0);
        return cal1.getTime();
    }

    public static void main(String[] args) {
        System.out.println(initDateByDay());
    }
}
