package com.upeoe.redenvelope.utils;

import java.math.BigDecimal;
import java.util.Random;

/**
 * @author upeoe
 * @create 2019/4/10 03:04
 */
public class MathKit {

    // decimal scale
    public static final int SCALE_2 = 2;

    /**
     * Add two double number.
     *
     * @param val1
     * @param val2
     * @return
     */
    public static double add(double val1, double val2) {
        BigDecimal b1 = new BigDecimal(Double.toString(val1));
        BigDecimal b2 = new BigDecimal(Double.toString(val2));
        return b1.add(b2).doubleValue();
    }

    /**
     * Subtract two double number.
     *
     * @param val1
     * @param val2
     * @return
     */
    public static double subtract(double val1, double val2) {
        BigDecimal b1 = new BigDecimal(Double.toString(val1));
        BigDecimal b2 = new BigDecimal(Double.toString(val2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * Multiply two double number.
     *
     * @param val1
     * @param val2
     * @return
     */
    public static double multiply(double val1, double val2) {
        BigDecimal b1 = new BigDecimal(Double.toString(val1));
        BigDecimal b2 = new BigDecimal(Double.toString(val2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * Multiply two double number, specifies decimal scale and round type.
     *
     * @param val1
     * @param val2
     * @param scale
     * @param roundType
     * @return
     */
    public static double multiply(double val1, double val2, int scale, int roundType) {
        BigDecimal b1 = new BigDecimal(Double.toString(val1));
        BigDecimal b2 = new BigDecimal(Double.toString(val2));
        return b1.multiply(b2).setScale(scale, roundType).doubleValue();
    }

    /**
     * Divide two double number with default decimal scale and round type.
     *
     * @param val1
     * @param val2
     * @return
     */
    public static double divide(double val1, double val2) {
        return divide(val1, val2, SCALE_2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Divide two double number, specifies decimal scale and round type.
     *
     * @param val1
     * @param val2
     * @param scale
     * @param roundType
     * @return
     */
    public static double divide(double val1, double val2, int scale, int roundType) {
        BigDecimal b1 = new BigDecimal(Double.toString(val1));
        BigDecimal b2 = new BigDecimal(Double.toString(val2));
        return b1.divide(b2, scale, roundType).doubleValue();
    }

    /**
     * Generate a random number, specifies number bound.
     *
     * @param bound
     * @return
     */
    public static int randomInt(int bound) {
        return new Random().nextInt(bound);
    }

}
