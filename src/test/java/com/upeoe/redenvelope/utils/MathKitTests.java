package com.upeoe.redenvelope.utils;

import jdk.nashorn.internal.runtime.ECMAException;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * @author upeoe
 * @create 2019/4/10 13:18
 */
public class MathKitTests {

    @Test
    public void testAdd() throws Exception {
        double d1 = 10.24;
        double d2 = 655.35;
        double result = MathKit.add(d1, d2);
        Assert.assertEquals(665.59, result, 0);
    }

    @Test
    public void testSub() throws Exception {
        double d1 = 10.24;
        double d2 = 655.35;
        double result = MathKit.subtract(d2, d1);
        Assert.assertEquals(645.11, result, 0);
    }

    @Test
    public void testMulti() throws Exception {
        double d1 = 10.24;
        double d2 = 655.35;
        double result = MathKit.multiply(d1, d2);
        Assert.assertEquals(6710.784, result, 0);

        result = MathKit.multiply(d1, d2, MathKit.SCALE_2, BigDecimal.ROUND_HALF_UP);
        Assert.assertEquals(6710.78, result, 0);
    }

    @Test
    public void testDivide() throws Exception {
        double d1 = 10.24;
        double d2 = 655.35;
        double result = MathKit.divide(d2, d1);
        Assert.assertEquals(64, result, 0);

        result = MathKit.divide(d2, d1, MathKit.SCALE_2, BigDecimal.ROUND_HALF_UP);
        Assert.assertEquals(64, result, 0);
    }

}
