package com.upeoe.redenvelope.repository;

import com.upeoe.redenvelope.entity.RedEnvelope;
import com.upeoe.redenvelope.entity.RedEnvelopeItem;
import com.upeoe.redenvelope.utils.DateKit;
import com.upeoe.redenvelope.utils.MathKit;
import com.upeoe.redenvelope.utils.RedEnvelopeKit;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author upeoe
 * @create 2019/4/10 18:01
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RedEnvelopeItemRepoTests {

    @Autowired
    RedEnvelopeRepo redEnvelopeRepo;
    @Autowired
    RedEnvelopeItemRepo redEnvelopeItemRepo;

    private Date now = new Date();
    private int num = random();
    private double money = random() * 10;
    private String sign = "abcXYZ";

    @Test
    public void testSaveAndDelete() throws Exception {
        save();
        delete();
    }

    @Test
    public void testQuery() throws Exception {
        save();
        List<RedEnvelopeItem> records = findRedEnvelopeItems();
        Assert.assertEquals(num, records.size());
        delete();
    }

    @Test
    public void testUpdate() throws Exception {
        save();
        List<RedEnvelopeItem> records = findRedEnvelopeItems();

        RedEnvelopeItem record = records.get(0);
        record.setBelongTo("user2");
        record.setGetTime(new Date());
        redEnvelopeItemRepo.save(record);
        delete();
    }

    private static int random() {
        int num = MathKit.randomInt(10);
        while (num <= 0) {
            num = MathKit.randomInt(10);
        }
        return num;
    }

    private List<RedEnvelopeItem> findRedEnvelopeItems() {
        return redEnvelopeItemRepo.findAllByRedEnvelopeId(redEnvelopeRepo.findBySign(sign).getId());
    }

    public void save() throws Exception {
        String user = "user1";
        RedEnvelope redEnvelope = new RedEnvelope(user, sign, num, new BigDecimal(money),
                now, DateKit.forwardDay(now, DateKit.ONE));
        redEnvelopeRepo.saveAndFlush(redEnvelope);

        Assert.assertEquals(user, redEnvelope.getUserId());
        Assert.assertTrue(null != redEnvelope.getId());

        List<Double> list = RedEnvelopeKit.genListMoney(num, money);
        for (double item : list) {
            redEnvelopeItemRepo.save(new RedEnvelopeItem(redEnvelope.getId(), "", now, new BigDecimal(item)));
        }
    }

    public void delete() throws Exception {
        Integer id = redEnvelopeRepo.findBySign(sign).getId();
        redEnvelopeItemRepo.deleteAll(findRedEnvelopeItems());
        redEnvelopeRepo.deleteById(id);
    }

}
