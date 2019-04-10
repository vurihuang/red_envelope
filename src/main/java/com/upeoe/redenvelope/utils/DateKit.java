package com.upeoe.redenvelope.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author upeoe
 * @create 2019/4/10 14:22
 */
public class DateKit {

    public static final int ONE = 1;

    public static Date forwardDay(Date d, int day) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(d);
        calendar.add(Calendar.DATE, day);
        return calendar.getTime();
    }

}
