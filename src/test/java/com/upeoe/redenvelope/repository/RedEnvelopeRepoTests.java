package com.upeoe.redenvelope.repository;

import com.upeoe.redenvelope.entity.RedEnvelope;
import com.upeoe.redenvelope.utils.DateKit;
import com.upeoe.redenvelope.utils.MathKit;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author upeoe
 * @create 2019/4/10 14:16
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RedEnvelopeRepoTests {

    @Autowired
    private RedEnvelopeRepo repo;
    private String sign = "qwerAB";
    private String user = "user1";

    @Test
    public void testASave() throws Exception {
        Date d = new Date();
        RedEnvelope r = new RedEnvelope(user, sign, random(), new BigDecimal(random() * 10), d, DateKit.forwardDay(d, DateKit.ONE));
        repo.save(r);
    }

    @Test
    public void testBQuery() throws Exception {
        RedEnvelope record = repo.findByUserId(user);
        Assert.assertEquals(record.getUserId(), user);

        record = repo.findBySign(sign);
        Assert.assertEquals(record.getSign(), sign);
    }

    @Test
    public void testCUpdate() throws Exception {
        RedEnvelope record = repo.findBySign(sign);
        record.setMoney(new BigDecimal(MathKit.subtract(record.getMoney().doubleValue(), 0.01)));
        repo.save(record);
    }

    @Test
    public void testDelete() throws Exception {
        RedEnvelope record = repo.findBySign(sign);
        repo.delete(record);
    }

    private int random() {
        int num = MathKit.randomInt(10);
        while (num <= 0) {
            num = MathKit.randomInt(10);
        }
        return num;
    }

}
