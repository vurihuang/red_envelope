package com.upeoe.redenvelope.utils;

import org.hibernate.annotations.SourceType;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author upeoe
 * @create 2019/4/10 13:01
 */
public class RedEnvelopeKitTests {

    @Test
    public void testGenNextRedEnvelope() throws Exception {
        int num = 1;
        double money = 100;
        double next = RedEnvelopeKit.genNextMoney(num, money);
        Assert.assertEquals(money, next, 0);

        int len = num = 3;
        double originMoney = money;
        double sum = 0;
        for (int i = 0; i < len; i++) {
            next = RedEnvelopeKit.genNextMoney(num, money);
            sum = MathKit.add(sum, next);
            money = MathKit.subtract(money, next);
            num--;
        }

        Assert.assertEquals(0, money, 0);
        Assert.assertEquals(originMoney, sum, 0);
    }

    @Test
    public void testGenRedEnvelopeList() throws Exception {
        int num = 10;
        double money = 100;

        List<Double> list = RedEnvelopeKit.genListMoney(num, money);
        double sum = 0;
        for (Double item : list) {
            sum = MathKit.add(sum, item);
        }

        Assert.assertEquals(money, sum, 0);

        num = 78;
        money = 1024.73;
        sum = 0;
        list = RedEnvelopeKit.genListMoney(num, money);
        for (Double item : list) {
            sum = MathKit.add(sum, item);
        }

        Assert.assertEquals(money, sum, 0);
    }

}
