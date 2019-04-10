package com.upeoe.redenvelope.utils;

import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author upeoe
 * @create 2019/4/10 14:27
 */
public class DateKitTests {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Test
    public void testForwardDay() throws Exception {
        Date today = formatter("2019-04-10 14:30:30");
        Date tomorrow = DateKit.forwardDay(today, DateKit.ONE);

        Assert.assertTrue(tomorrow.after(today));
        Assert.assertEquals(formatter("2019-04-11 14:30:30"), tomorrow);
    }

    private Date formatter(String pattern) throws ParseException {
        return sdf.parse(pattern);
    }

}
