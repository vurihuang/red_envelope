package com.upeoe.redenvelope;

/**
 * @author upeoe
 * @create 2019/4/9 15:20
 */
public class Constants {

    public static final int MIN_RED_ENVELOPE_NUM = 1;
    public static final int MAX_RED_ENVELOPE_NUM = 1000;

    public static final String JWT_SIGNKEY = "YWhveQ==";
    public static final long JWT_DEFAULT_EXPIRE_MILLS = 60000 * 60;
    public static final long REDIS_LOCK_EXPIRED = 15000;
    public static final long ONE_DAY_EXPIRED = 1000 * 3600 *24;

    public enum REDIS_KEY {
        RED_ENVELOPE_SEND("re:send"),
        RED_ENVELOPE_ITEM_SEND("rei:send"),
        RED_ENVELOPE_ITEM_FETCH("rei:fetch");
        public String val;

        REDIS_KEY(String val) {
            this.val = val;
        }
    }

    public enum LOCK_KEY {
        RED_ENVELOPE_AT_LOCK;
    }

}
