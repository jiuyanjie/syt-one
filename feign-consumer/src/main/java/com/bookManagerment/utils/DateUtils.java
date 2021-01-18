package com.bookManagerment.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {

    /**
     * 求两个日期的差
     * @param date
     * @param date2
     * @return
     */
    public static int differenceDay(Date date,Date date2){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        Calendar calendar2 = new GregorianCalendar();
        calendar2.setTime(date2);
        return calendar.get(Calendar.DAY_OF_YEAR)-calendar2.get(Calendar.DAY_OF_YEAR);
    }


    /**
     * 将传递过来的date转换为指定格式
     *
     * @param format
     * @return
     */
    public static String dateFormat(String format, Date date) {
        return new SimpleDateFormat(format).format(date);
    }

    public static String dateFormat(String format, Timestamp date) {
        return new SimpleDateFormat(format).format(date);
    }


    public static Timestamp strToSqlDate(String strDate, String dateFormat) {
        SimpleDateFormat sf = new SimpleDateFormat(dateFormat);
        Date date = null;
        try {
            date = sf.parse(strDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Timestamp dateSQL = new Timestamp(date.getTime());
        return dateSQL;
    }

    public static java.sql.Date strToSqlDate2(String strDate, String dateFormat) {
        SimpleDateFormat sf = new SimpleDateFormat(dateFormat);
        Date date = null;
        try {
            date = sf.parse(strDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        java.sql.Date dateSQL = new java.sql.Date(date.getTime());
        return dateSQL;
    }

}
