package com.upeoe.redenvelope.utils;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author upeoe
 * @create 2019/4/10 15:17
 */
public class SignKitTests {

    @Test
    public void testGenerate() throws Exception {
        String sign = SignKit.generate();
        Assert.assertEquals(sign.length(), SignKit.DEFAULT_SIGN_LEN);
        Assert.assertTrue(sign.length() > 0);

        sign = SignKit.generate(8);
        Assert.assertEquals(sign.length(), 8);
        Assert.assertTrue(sign.length() > 0);
    }

}
