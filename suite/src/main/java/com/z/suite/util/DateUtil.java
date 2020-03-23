/**
 * CopyRight (c) 2016 北京好数科技有限公司 保留所有权利
 */

package com.z.suite.util;

import com.z.suite.exception.ExceptionCode;
import com.z.suite.exception.SystemException;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateUtil extends DateUtils {

    private static Logger logger = LogManager.getLogger(DateUtil.class);

    private static Map<Integer, String> CN_WEEK_DAYS = new HashMap<Integer, String>();

    static {
        CN_WEEK_DAYS.put(1, "周一");
        CN_WEEK_DAYS.put(2, "周二");
        CN_WEEK_DAYS.put(3, "周三");
        CN_WEEK_DAYS.put(4, "周四");
        CN_WEEK_DAYS.put(5, "周五");
        CN_WEEK_DAYS.put(6, "周六");
        CN_WEEK_DAYS.put(7, "周日");
    }

    /**
     * 获取当前日期时间
     *
     * @return
     */
    public static long getCurrentDateTime() {
        Date dt = new Date();
        return getDateTime(dt);
    }

    /**
     * 获取当前日期时间
     *
     * @return
     */
    public static String getCurrentDateTimeCN() {
        return DateFormatUtils.format(new Date(), "yyyy年MM月dd日 HH:mm:ss");
    }

    /**
     * 获取当前日期时间
     *
     * @return
     */
    public static String getCurrentDateTimeEN() {
        return DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 获取日期时间
     *
     * @param dt
     * @return
     */
    public static long getDateTime(final Date dt) {
        if (dt == null) {
            return 0;
        }
        String dtString = DateFormatUtils.format(dt, "yyyyMMddHHmmss");
        return Long.parseLong(dtString);
    }

    /**
     * 获取日期
     *
     * @param dt
     * @return
     */
    public static int getDate(final Date dt) {
        if (dt == null) {
            return 0;
        }
        String dtString = DateFormatUtils.format(dt, "yyyyMMdd");
        return Integer.parseInt(dtString);
    }

    /**
     * 获取月日期
     *
     * @param dt
     * @return
     */
    public static int getMonthDate(final Date dt) {
        if (dt == null) {
            return 0;
        }
        String dtString = DateFormatUtils.format(dt, "yyyyMM");
        return Integer.parseInt(dtString);
    }

    /**
     * 根据整数获取日期类型
     *
     * @param dt
     * @return
     * @throws ParseException
     */
    public static Date getDate(final long dt) throws SystemException {
        String dtStr = String.valueOf(dt);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        try {
            if (dtStr.length() == 8) {
                return sdf.parse(dtStr);
            } else {
                return sdf.parse(dtStr.substring(0, 8));
            }

        } catch (ParseException e) {
            logger.error("日期解析错误", e);
            throw new SystemException(510101);
        }
    }

    public static Date getDateTime(final long dt) throws SystemException {
        String dtStr = String.valueOf(dt);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
            return sdf.parse(dtStr);
        } catch (ParseException e) {
            logger.error("日期解析错误", e);
            throw new SystemException(510101);
        }
    }

    /**
     * 根据时间长整形获取时间字符串
     *
     * @param dt
     * @return
     * @throws SystemException
     */
    public static String getDateStr(final long dt) {
        String dts = String.valueOf(dt);
        String result = "未知";
        if (dts.length() == 8) {
            result = dts.substring(0, 4) + "-" + dts.substring(4, 6) + "-" + dts.substring(6, 8);
        } else if (dts.length() == 14) {
            result = dts.substring(0, 4) + "-" + dts.substring(4, 6) + "-" + dts.substring(6, 8) + " "
                    + dts.substring(8, 10) + ":" + dts.substring(10, 12) + ":" + dts.substring(12, 14);
        }
        return result;
    }

    /**
     * 获取日期字符串
     *
     * @param dt
     * @return
     */
    public static String getDateStr(final Date dt) {
        return DateFormatUtils.format(dt, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 获取当前日期
     *
     * @return
     */
    public static int getCurrentDate() {
        Date dt = new Date();
        return getDate(dt);
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static int getCurrentTime() {
        Date dt = new Date();

        return getTime(dt);
    }

    /**
     * 获取时间
     *
     * @param dt
     * @return
     */
    public static int getTime(final Date dt) {
        String dtString = DateFormatUtils.format(dt, "HHmmss");
        return Integer.parseInt(dtString);
    }

    /**
     * 获取时间
     *
     * @param dt
     * @return
     * @throws SystemException
     */
    public static int getTime(final Long dt) throws SystemException {
        Date dateTime = getDateTime(dt);
        return getTime(dateTime);
    }

    /**
     * 获取天数差
     *
     * @param st
     *        起始日期
     * @param et
     *        终止日期
     * @return
     * @throws ParseException
     */
    public static int getDifferDays(final long st, final long et) throws SystemException {
        // 解析到天
        Date sdate = getDate(st);
        Date edate = getDate(et);
        return getDiffDays(sdate, edate);
    }

    /**
     * 获取天数差
     *
     * @param st
     *        起始日期
     * @param et
     *        终止日期
     * @return
     */
    public static int getDiffDays(final Date st, final Date et) {
        long intervalMilli = et.getTime() - st.getTime();
        return (int) (intervalMilli / (24 * 60 * 60 * 1000));
    }

    /**
     * 通过天数获取日期
     *
     * @param st
     *        起始日期
     * @param days
     *        相差天数
     * @return
     * @throws ParseException
     */
    public static int getDateByDays(final int st, final int days) throws SystemException {
        Date startDate = getDate(st);
        return getDateByDays(startDate, days);
    }

    /**
     * 通过天数获取日期
     *
     * @param st
     *        起始日期
     * @param days
     *        相差天数
     * @return
     */
    public static int getDateByDays(final Date st, final int days) {
        Date et = addDays(st, days);
        return getDate(et);
    }

    /**
     * 通过月获取日期
     *
     * @param st
     * @param months
     * @return
     * @throws SystemException
     */
    public static int getDateByMonths(final int st, final int months) throws SystemException {
        Date startDate = getDate(st);
        return getDateByMonths(startDate, months);
    }

    /**
     * 通过月数获取日期
     *
     * @param st
     *        起始日期
     * @param months
     *        相差月数
     * @return
     */
    public static int getDateByMonths(final Date st, final int months) {
        Date et = addMonths(st, months);

        return getDate(et);
    }

    /**
     * 通过天数计算日期
     *
     * @param days
     *        相差天数
     * @return
     */
    public static int getDateFromToday(final int days) {
        Date st = new Date();
        return getDateByDays(st, days);
    }

    /**
     * 获取前几天
     *
     * @param days
     * @return
     */
    public static int getPreDate(final int days) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(new Date());
        rightNow.add(Calendar.DATE, -days);// 你要加减的日期
        Date preDate = rightNow.getTime();

        return getDate(preDate);
    }

    /**
     * 获取给定日期的前几天
     *
     * @param dt
     * @param days
     * @return
     * @throws SystemException
     */
    public static int getPreDate(final long dt, final int days) throws SystemException {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(getDateTime(dt));
        rightNow.add(Calendar.DATE, -days);// 你要加减的日期
        Date preDate = rightNow.getTime();

        return getDate(preDate);
    }

    /**
     * 获取给定日期的前几天
     *
     * @param dt
     * @param days
     * @return
     * @throws SystemException
     */
    public static int getPreDate(final int dt, final int days) throws SystemException {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(getDate(dt));
        rightNow.add(Calendar.DATE, -days);// 你要加减的日期
        Date preDate = rightNow.getTime();

        return getDate(preDate);
    }

    /**
     * 获取给定时间的前几天时间
     *
     * @param dt
     * @param days
     * @return
     * @throws SystemException
     */
    public static long getPreDateTime(final long dt, final int days) throws SystemException {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(getDateTime(dt));
        rightNow.add(Calendar.DATE, -days);// 你要加减的日期
        Date preDateTime = rightNow.getTime();

        return getDateTime(preDateTime);
    }

    /**
     * 获得周一的日期
     *
     * @param dt
     * @return
     */
    public static int getMondayDate(final Date dt) {
        Calendar dtCal = toCalendar(dt);
        // Java 中星期 日、一、二、三、四、五、六,分别对应是 1 - 7. 减一，则周日是 0
        int weekDay = dtCal.get(Calendar.DAY_OF_WEEK) - 1;
        int diffDays = 0;
        if (weekDay == 1) {
            // 如果已经是周一则返回当天
            return getDate(dt);
        } else if (weekDay == 0) {
            // 如果是周日，差距天数为 6
            diffDays = -6;
        } else {
            diffDays = -(weekDay - 1);
        }
        // 返回周一的天数
        return getDateByDays(dt, diffDays);

    }

    /**
     * 获取上周一的日期
     *
     * @param dt
     * @return
     */
    public static int getLastMondayDate(final Date dt) {
        Calendar dtCal = toCalendar(dt);
        // Java 中星期 日、一、二、三、四、五、六,分别对应是 1 - 7. 减一，则周日是 0
        int weekDay = dtCal.get(Calendar.DAY_OF_WEEK) - 1;
        int diffDays = 0;
        if (weekDay == 0) {
            // 如果是周日，差距天数为 6
            diffDays = -6;
        } else {
            diffDays = -(weekDay - 1);
        }
        // 返回周一的天数
        return getDateByDays(dt, diffDays - 7);
    }

    /**
     * 获得周一的日期
     *
     * @param dt
     * @return
     * @throws SystemException
     */
    public static int getMondayDate(final long dt) throws SystemException {
        Date st = getDate(dt);

        return getMondayDate(st);
    }

    /**
     * 获取两个日期之间的秒数
     *
     * @param st
     * @param et
     * @return
     */
    public static int getDiffSeconds(final Date st, final Date et) {
        long a = et.getTime();
        long b = st.getTime();
        int c = (int) ((a - b) / 1000);
        return Math.abs(c);
    }

    /**
     * 获取两个日期之间的秒数
     *
     * @param st
     * @param et
     * @return
     */
    public static int getDiffSeconds(final long st, final long et) throws SystemException {
        return getDiffSeconds(getDateTime(st), getDateTime(et));
    }

    /**
     * 获取年差
     *
     * @param st
     *        起始日期
     * @param et
     *        终止日期
     * @return
     */
    public static int getDiffYears(final Date st, final Date et) {
        long intervalMilli = et.getTime() - st.getTime();
        return (int) (intervalMilli / 1000 / 60 / 60 / 24 / 365);
    }

    public static int getMonthStartDate(long date) throws SystemException {
        Date dt = getDate(date);
        return getMonthStartDate(dt);
    }

    /**
     * 月初相差天数
     *
     * @param dt
     * @return
     */
    public static int getMonthStartDiffDays(Date dt) {
        Calendar dtCal = toCalendar(dt);
        int monthDay = dtCal.get(Calendar.DAY_OF_MONTH);
        int diffDays = 0;
        if (monthDay > 1) {
            diffDays = -(monthDay - 1);
        }
        return diffDays;
    }

    public static int getMonthStartDate(Date dt) {
        int diffDays = getMonthStartDiffDays(dt);
        // 返回月初的日期
        return getDateByDays(dt, diffDays);
    }

    public static int getMonthEndDate(long date) throws SystemException {
        Date dt = getDate(date);
        return getMonthEndDate(dt);
    }

    /**
     * 获取月最后一天
     *
     * @param date
     * @return
     */
    public static Date getMonthEndDay(Date date) {
        Calendar dtCal = toCalendar(date);
        int monthDay = dtCal.get(Calendar.DAY_OF_MONTH);
        int last = dtCal.getActualMaximum(Calendar.DAY_OF_MONTH); // 获取本月最大天数
        int dif = last - monthDay;
        return addDays(date, dif);
    }

    public static int getMonthEndDate(Date date) {
        Calendar dtCal = toCalendar(date);
        int monthDay = dtCal.get(Calendar.DAY_OF_MONTH);
        int last = dtCal.getActualMaximum(Calendar.DAY_OF_MONTH); // 获取本月最大天数
        int dif = last - monthDay;
        return getDateByDays(date, dif);
    }

    /**
     * 获取时:分:秒
     *
     * @param date
     * @return
     * @throws SystemException
     */
    public static String getTimeStr(long date) throws SystemException {
        return getDateStr(date).substring(11, 16);
    }

    /**
     * 判断是否一个有效日期
     *
     * @param str
     * @param dateFormat
     * @return
     */
    public static boolean isValidDate(String str, String dateFormat) {
        boolean convertSuccess = true;
        // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
        // 如果想判断格式为yyyy-MM-dd，需要写成-分隔符的形式
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        try {

            format.setLenient(false);
            format.parse(str);
        } catch (Exception e) {
            convertSuccess = false;
        }
        return convertSuccess;
    }

    /**
     * 获取年的差数
     *
     * @param dt
     * @return
     * @throws SystemException
     */
    public static int getDiffYears(final int dt) throws SystemException {

        Date date = getDate(dt);

        return getDiffYears(date, new Date());

    }

    /**
     * 获取一周的周几
     *
     * @param dt
     * @return
     * @throws SystemException
     */
    public static int getWeekDay(final Date dt) {
        Calendar dtCal = toCalendar(dt);
        // Java 中星期 日、一、二、三、四、五、六,分别对应是 1 - 7. 减一，则周日是 0
        int weekDay = dtCal.get(Calendar.DAY_OF_WEEK) - 1;
        if (weekDay == 0) {
            weekDay = 7;
        }
        return weekDay;
    }

    /**
     * 获取一周的周几
     *
     * @param dt
     * @return
     * @throws SystemException
     */
    public static int getWeekDay(final int dt) throws SystemException {
        Date date = getDate(dt);

        return getWeekDay(date);
    }

    /**
     * 获取中文的周几
     *
     * @param weekDay
     * @return
     */
    public static String getWeekDayCN(final int weekDay) {
        return CN_WEEK_DAYS.get(weekDay);
    }

    /**
     * 两个时间相差的月份
     *
     * @param start
     * @param end
     * @return
     * @throws SystemException
     */
    public static int getDifferMonths(int start, int end) throws SystemException {
        Calendar bef = Calendar.getInstance();
        Calendar aft = Calendar.getInstance();
        bef.setTime(DateUtil.getDate(start));
        aft.setTime(DateUtil.getDate(end));
        int result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);
        int month = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12;
        return Math.abs(month + result);
    }

    public static int getDifferMonths(Date start, Date end) throws ParseException, SystemException {
        Calendar bef = Calendar.getInstance();
        Calendar aft = Calendar.getInstance();
        bef.setTime(start);
        aft.setTime(end);
        int result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);
        int month = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12;
        return Math.abs(month + result);
    }

    /**
     * 获取每个月的1号
     *
     * @param date
     * @return
     * @throws SystemException
     */
    public static int getFirstByMonth(int date) throws SystemException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtil.getDate(date));
        calendar.set(Calendar.DATE, 1);
        return DateUtil.getDate(calendar.getTime());
    }

    /**
     * 获取下月的1号
     *
     * @return
     * @throws SystemException
     */
    public static int getNextFirstByMonth() throws SystemException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DATE, 1);
        return DateUtil.getDate(calendar.getTime());
    }

    /**
     * 时间加几个月
     *
     * @param date
     * @param months
     * @return
     * @throws SystemException
     */
    public static int getAddMonths(int date, int months) throws SystemException {
        Calendar calendar = Calendar.getInstance();
        if (months == 0) {
            return date;
        } else {
            calendar.setTime(DateUtil.getDate(date));
            calendar.add(Calendar.MONTH, months);
            return DateUtil.getDate(calendar.getTime());
        }
    }

    /**
     * 两个时间相差的月份
     *
     * @param start
     * @param end
     * @return
     * @throws SystemException
     */
    public static int getDifferMonthsNoAbs(int start, int end) throws SystemException {
        Calendar bef = Calendar.getInstance();
        Calendar aft = Calendar.getInstance();
        bef.setTime(DateUtil.getDate(start));
        aft.setTime(DateUtil.getDate(end));
        int result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);
        int month = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12;
        return month + result;
    }

    /**
     * 时间加几个月的最后一天
     *
     * @param date
     * @param months
     * @return
     * @throws SystemException
     */
    public static int getAddMonthsLastDay(int date, int months) throws SystemException {

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            Date addMonthsDate = formatter.parse(String.valueOf(getAddMonths(date, months)));
            final Calendar cal = Calendar.getInstance();
            cal.setTime(addMonthsDate);
            final int last = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            cal.set(Calendar.DAY_OF_MONTH, last);
            return Integer.valueOf(formatter.format(cal.getTime()));
        } catch (ParseException e) {
            logger.error("计算日期加几个月的最后一天出错", e);
            throw new SystemException(ExceptionCode.PARSE_ERROR, "计算日期加几个月的最后一天出错" + e.getMessage());
        }
    }

    /**
     * 两个时间差 分
     *
     * @param st
     * @param et
     * @return
     * @throws SystemException
     */
    public static long getDiffMinute(long st, long et) throws SystemException {
        long senconds = DateUtil.getDiffSeconds(st, et);
        return (Math.abs(senconds) / 60);
    }

    /**
     * 通过时间戳转换为日期
     *
     * @param timestamp
     * @return
     */
    public static String getDateByTime(long timestamp) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(timestamp);
        return simpleDateFormat.format(date);
    }

    /**
     * 获取格式化日期字符串,参数为Date
     *
     * @param date
     * @param format
     * @return
     */
    public static String getFormatDate(Date date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    /**
     * 获取格式化日期字符串,参数为Date,默认yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String getFormatDate(int date) {
        if (date < 10000000) {
            return "";
        }
        String dateStr = String.valueOf(date);
        return dateStr.substring(0, 4) + "-" + dateStr.substring(4, 6) + "-" + dateStr.substring(6, 8);
    }

    /**
     * 获取格式化日期字符串,参数为Date,默认yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String getFormatDate(Date date) {
        if (date == null) {
            return "";
        }
        return getFormatDate(date, "yyyy-MM-dd");
    }

    /**
     * 获取格式化日期字符串,单数Date, 默认yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String getFormatDateTime(Date date) {
        if (date == null) {
            return "";
        }
        return getFormatDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 通过时间戳转换为日期时间
     *
     * @param timestamp
     * @return
     */
    public static String getDateTimeByTime(long timestamp) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(timestamp);
        return simpleDateFormat.format(date);
    }

    /**
     * 获取钱几分钟时间
     *
     * @param minute
     * @param format
     *        "yyyy-MM-dd HH:mm:ss"
     * @return
     */
    public static String getPreMinuteDateTime(int minute, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Calendar beforeTime = Calendar.getInstance();
        beforeTime.add(Calendar.MINUTE, -minute);
        Date beforeD = beforeTime.getTime();
        String time = sdf.format(beforeD);
        return time;
    }

    /**
     * 加天数后日期
     *
     * @param date
     * @param days
     * @return
     * @throws SystemException
     */
    public static int addDays(int date, int days) throws SystemException {
        return getDate(addDays(getDate(date), days));
    }

    /**
     * 获取当前月份
     *
     * @return
     */
    public static int getCurrentMonth() {
        Date dt = new Date();
        return getDateMonth(dt);
    }

    /**
     * 获取月份
     *
     * @param dt
     * @return
     */
    public static int getDateMonth(Date dt) {
        String dtString = DateFormatUtils.format(dt, "yyyyMM");
        return Integer.parseInt(dtString);
    }

    public static void main(String[] args) {
        // System.out.println(getMonthEndDate(addMonths(new Date(), 1)));
        try {
            Date currentDate = new Date();
            int currentDay = DateUtil.getDate(currentDate);
            System.out.println(getAddMonths(currentDay, 1));
            System.out.println(DateUtil.addMonths(currentDate, 1));
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SystemException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
