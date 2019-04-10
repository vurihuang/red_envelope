package com.upeoe.redenvelope;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author upeoe
 * @create 2019/4/10 03:57
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RedisTests {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testSetString() throws Exception {
        String key = "red_env:hello";
        String value = "world";
        stringRedisTemplate.opsForValue().set(key, value);
        Assert.assertEquals(value, stringRedisTemplate.opsForValue().get(key));
    }

}
