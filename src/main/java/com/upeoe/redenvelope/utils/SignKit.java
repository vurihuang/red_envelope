package com.upeoe.redenvelope.utils;

import java.util.Random;

/**
 * @author upeoe
 * @create 2019/4/10 15:09
 */
public class SignKit {

    public static final int DEFAULT_SIGN_LEN = 6;

    private static final char[] CHARS = {
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
            'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
            'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K',
            'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            '2', '3', '4', '5', '6', '7', '8', '9'
    };

    public static String generate() {
        return generate(DEFAULT_SIGN_LEN);
    }

    public static String generate(int len) {
        if (len <= 0) {
            return "";
        }
        StringBuilder str = new StringBuilder();
        Random r = new Random();
        int cnt = 0;
        int i;
        int max = CHARS.length;
        while (cnt < len) {
            i = Math.abs(r.nextInt(max));
            if (i >= 0 && i < max) {
                str.append(CHARS[i]);
                cnt++;
            }
        }
        return str.toString();
    }

}
