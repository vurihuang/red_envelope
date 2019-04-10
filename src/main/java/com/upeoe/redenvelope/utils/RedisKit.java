package com.upeoe.redenvelope.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


/**
 * @author upeoe
 * @create 2019/4/10 03:46
 * Redis Database Kit.
 */
@Component
public class RedisKit {

    private static Logger LOG = LoggerFactory.getLogger(RedisKit.class);

    private ThreadLocal<String> lockFlag = new ThreadLocal<>();

    private static final String NX = "NX";
    private static final String PX = "PX";

    @Autowired
    private RedisTemplate<String, Object> redisTpl;

    public boolean hasKey(String key) {
        try {
            return redisTpl.hasKey(key);
        } catch (Exception e) {
            return false;
        }
    }

    public void del(String... keys) {
        if (keys != null && keys.length > 0) {
            if (keys.length == 1) {
                redisTpl.delete(keys[0]);
            } else {
                redisTpl.delete(CollectionUtils.arrayToList(keys));
            }
        }
    }


    /**
     * Get key.
     *
     * @param key
     * @return
     */
    public Object get(String key) {
        return key != null ? redisTpl.opsForValue().get(key) : null;
    }

    /**
     * Set key.
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(String key, Object value) {
        try {
            redisTpl.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            LOG.error("Error on set key, ex: {}", e.getMessage(), e);
            return false;
        }
    }

    public boolean set(String key, Object value, long expired) {
        try {
            if (expired > 0) {
                redisTpl.opsForValue().set(key, value, expired, TimeUnit.MILLISECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            LOG.error("Error on set key with expired time, ex: {}", e.getMessage(), e);
            return false;
        }
    }

    public boolean lPush(String key, Object item, long expired) {
        try {
            redisTpl.opsForList().leftPush(key, item);
            if (expired > 0) {
                expire(key, expired);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean rPush(String key, Object item, long expired) {
        try {
            redisTpl.opsForList().rightPush(key, item);
            if (expired > 0) {
                expire(key, expired);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Object lPop(String key) {
        return redisTpl.opsForList().leftPop(key);
    }

    public Object rPop(String key) {
        return redisTpl.opsForList().rightPop(key);
    }

    public Long lLen(String key) {
        return redisTpl.opsForList().size(key);
    }

    public void lPushAll(String key, Collection items) {
        redisTpl.opsForList().leftPushAll(key, items);
    }

    public void rPushAll(String key, Collection items) {
        redisTpl.opsForList().rightPushAll(key, items);
    }

    public void sadd(String key, Object value) {
        redisTpl.opsForSet().add(key, value);
    }

    public void sadd(String key, Object value, long expired) {
        redisTpl.opsForSet().add(key, value);
        if (expired > 0) {
            expire(key, expired);
        }
    }

    public Long sLen(String key) {
        return redisTpl.opsForSet().size(key);
    }

    public Long sRem(String key, String value) {
        return redisTpl.opsForSet().remove(key, value);
    }

    public Boolean sIsMem(String key, Object value) {
        return redisTpl.opsForSet().isMember(key, value);
    }

    public boolean expire(String key, long expired) {
        try {
            if (expired > 0) {
                redisTpl.expire(key, expired, TimeUnit.MILLISECONDS);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public long getExpire(String key) {
        return redisTpl.getExpire(key, TimeUnit.MILLISECONDS);
    }

    public boolean setLock(String key, String lockVal, long expired) {
        try {
            String result = redisTpl.execute(new RedisCallback<String>() {
                @Override
                public String doInRedis(RedisConnection redisConnection) throws DataAccessException {
                    String lockName = lockVal + "_" + UUID.randomUUID().toString();
                    lockFlag.set(lockName);
                    return ((Jedis) redisConnection.getNativeConnection()).set(key, lockName, NX, PX, expired);
                }
            });
            return !StringUtils.isEmpty(result);
        } catch (Exception e) {
            return false;
        }
    }

    public void releaseLock(String key) {
        Object val = get(key);
        if (null != val) {
            if (Objects.equals(String.valueOf(val), lockFlag.get())) {
                redisTpl.delete(key);
            }
        }
    }

}
