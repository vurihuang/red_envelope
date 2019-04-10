package com.upeoe.redenvelope.utils;

import com.sun.javafx.scene.control.skin.VirtualFlow;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author upeoe
 * @create 2019/4/10 02:41
 * Red Envelope Common Kit.
 */
public class RedEnvelopeKit {
    private static final double MIN_MONEY = 0.01;

    /**
     * 获取下一次红包随机金额
     *
     * @param remainNum   剩余红包个数
     * @param remainMoney 剩余红包金额
     * @return 红包随机金额
     */
    public static double genNextMoney(int remainNum, double remainMoney) {
        if (remainNum == 1) {
            return MathKit.divide(MathKit.multiply(remainMoney, 100), 100);
        }

        Random r = new Random();
        double max_money = MathKit.multiply(MathKit.divide(remainMoney, remainNum), 2);
        double money = MathKit.multiply(r.nextDouble(), max_money);
        money = money <= MIN_MONEY ? MIN_MONEY : money;
        return MathKit.divide(MathKit.multiply(money, 100, MathKit.SCALE_2, BigDecimal.ROUND_FLOOR), 100);
    }

    /**
     * 获取一组随机金额红包
     *
     * @param num   红包个数
     * @param money 红包金额
     * @return 一组随机金额红包
     */
    public static List<Double> genListMoney(int num, double money) {
        List<Double> envelopes = new ArrayList<>();

        if (num == 1) {
            envelopes.add(money);
        } else {
            while (num >= 1) {
                double next = genNextMoney(num, money);
                envelopes.add(next);
                money = MathKit.subtract(money, next);
                num--;
            }
        }

        return envelopes;
    }

}
