package com.upeoe.redenvelope.utils;

import java.math.BigDecimal;

/**
 * @author upeoe
 * @create 2019/4/10 03:04
 */
public class MathKit {

    public static final int SCALE_2 = 2;

    public static double add(double val1, double val2) {
        BigDecimal b1 = new BigDecimal(Double.toString(val1));
        BigDecimal b2 = new BigDecimal(Double.toString(val2));
        return b1.add(b2).doubleValue();
    }

    public static double subtract(double val1, double val2) {
        BigDecimal b1 = new BigDecimal(Double.toString(val1));
        BigDecimal b2 = new BigDecimal(Double.toString(val2));
        return b1.subtract(b2).doubleValue();
    }

    public static double multiply(double val1, double val2) {
        BigDecimal b1 = new BigDecimal(Double.toString(val1));
        BigDecimal b2 = new BigDecimal(Double.toString(val2));
        return b1.multiply(b2).doubleValue();
    }

    public static double multiply(double val1, double val2, int scale, int roundType) {
        BigDecimal b1 = new BigDecimal(Double.toString(val1));
        BigDecimal b2 = new BigDecimal(Double.toString(val2));
        return b1.multiply(b2).setScale(scale, roundType).doubleValue();
    }

    public static double divide(double val1, double val2) {
        return divide(val1, val2, SCALE_2, BigDecimal.ROUND_HALF_UP);
    }

    public static double divide(double val1, double val2, int scale, int roundType) {
        BigDecimal b1 = new BigDecimal(Double.toString(val1));
        BigDecimal b2 = new BigDecimal(Double.toString(val2));
        return b1.divide(b2, scale, roundType).doubleValue();
    }
}
