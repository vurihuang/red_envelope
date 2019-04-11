package com.upeoe.redenvelope.service;

import com.alibaba.fastjson.JSONObject;
import com.upeoe.redenvelope.Constants;
import com.upeoe.redenvelope.entity.RedEnvelope;
import com.upeoe.redenvelope.entity.RedEnvelopeItem;
import com.upeoe.redenvelope.entity.User;
import com.upeoe.redenvelope.exception.BusinessException;
import com.upeoe.redenvelope.repository.RedEnvelopeItemRepo;
import com.upeoe.redenvelope.repository.RedEnvelopeRepo;
import com.upeoe.redenvelope.repository.UserRepo;
import com.upeoe.redenvelope.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author upeoe
 * @create 2019/4/11 00:29
 * Red Envelope Service.
 */
@Service
public class RedEnvelopeService {

    private static Logger LOG = LoggerFactory.getLogger(RedEnvelopeService.class);

    @Autowired
    RedEnvelopeRepo redEnvelopeRepo;

    @Autowired
    RedEnvelopeItemRepo redEnvelopeItemRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    RedisKit redisKit;

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public String sendRedEnvelope(RedEnvelope params) throws BusinessException {
        if (null == params) {
            throw new BusinessException("Invalid request params.");
        }
        if (Constants.MIN_RED_ENVELOPE_NUM >= params.getNumber() ||
                Constants.MAX_RED_ENVELOPE_NUM <= params.getNumber()) {
            throw new BusinessException("Incorrect red envelope number, range["
                    + Constants.MIN_RED_ENVELOPE_NUM + ", "
                    + Constants.MAX_RED_ENVELOPE_NUM + "].");
        }
        if (BigDecimal.ZERO.compareTo(params.getMoney()) != -1) {
            throw new BusinessException("Incorrect red envelope money, should more than 0.");
        }

        synchronized (RedEnvelopeService.class) {
            Date now = new Date();
            String sign = SignKit.generate();
            params.setSign(sign);
            params.setCreatedAt(now);
            params.setExpiredAt(DateKit.forwardDay(now, DateKit.ONE));
            params = redEnvelopeRepo.saveAndFlush(params);

            redisKit.set(Constants.REDIS_KEY.RED_ENVELOPE_SEND.val + ":" + sign, sign, Constants.ONE_DAY_EXPIRED);

            List<Double> redEvlps = RedEnvelopeKit.genListMoney(params.getNumber(), params.getMoney().doubleValue());
            for (double item : redEvlps) {
                RedEnvelopeItem record = redEnvelopeItemRepo.saveAndFlush(new RedEnvelopeItem(
                        params.getId(), "", now, new BigDecimal(item)));
                redisKit.lPush(Constants.REDIS_KEY.RED_ENVELOPE_ITEM_SEND.val + ":" + sign, record.getId(), Constants.ONE_DAY_EXPIRED);
            }

            return sign;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public BigDecimal fetchRedEnvelope(String userId, String sign) throws BusinessException {
        if (StringUtils.isBlank(userId)) {
            throw new BusinessException("Incorrect user.");
        }
        if (StringUtils.isBlank(sign)) {
            throw new BusinessException("Incorrect red envelope sign.");
        }

        synchronized (RedEnvelopeService.class) {
            String key = (String) redisKit.get(Constants.REDIS_KEY.RED_ENVELOPE_SEND.val + ":" + sign);
            if (null == key) {
                throw new BusinessException("Invalid red envelope sign.");
            }
            String fetchKey = Constants.REDIS_KEY.RED_ENVELOPE_ITEM_FETCH.val + ":" + sign;
            boolean isExists = redisKit.sIsMem(fetchKey, userId);
            if (isExists) {
                throw new BusinessException("Sorry, you have already fetch this red envelope.");
            }
            String reiSendKey = Constants.REDIS_KEY.RED_ENVELOPE_ITEM_SEND.val + ":" + sign;
            Long len = redisKit.lLen(reiSendKey);
            if (null == len || len == 0) {
                throw new BusinessException("No red envelope can be fetch.");
            }

            String lockKey = Constants.LOCK_KEY.RED_ENVELOPE_AT_LOCK.name() + ":" + sign;
            try {
                boolean isLock = redisKit.setLock(lockKey, JSONObject.toJSONString(Constants.LOCK_KEY.RED_ENVELOPE_AT_LOCK.name()),
                        Constants.REDIS_LOCK_EXPIRED);
                if (!isLock) {
                    throw new BusinessException("Service is busy, try late please.");
                }

                Integer itemId = (Integer) redisKit.lPop(reiSendKey);
                Optional<RedEnvelopeItem> opt = redEnvelopeItemRepo.findById(itemId);
                if (!opt.isPresent()) {
                    throw new BusinessException("Fetch red envelope failed, please retry.");
                }
                RedEnvelopeItem item = opt.get();
                item.setBelongTo(userId);
                item.setGetTime(new Date());
                redEnvelopeItemRepo.save(item);

                User user = userRepo.findByUsername(userId);
                if (null != user) {
                    user.setAmount(new BigDecimal(MathKit.add(
                            user.getAmount().doubleValue(), item.getAmount().doubleValue())));
                } else {
                    // if not exists current user, save it.
                    user = new User(userId, item.getAmount());
                }
                userRepo.save(user);

                long expired = redisKit.getExpire(fetchKey);
                if (expired == -2) {
                    redisKit.sadd(fetchKey, userId, Constants.ONE_DAY_EXPIRED);
                } else {
                    redisKit.sadd(fetchKey, userId);
                }

                return item.getAmount();
            } catch (Exception e) {
                throw new BusinessException(e.getMessage());
            } finally {
                redisKit.releaseLock(lockKey);
            }
        }
    }

}
