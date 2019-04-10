package com.upeoe.redenvelope.service;

import com.upeoe.redenvelope.Constants;
import com.upeoe.redenvelope.entity.RedEnvelope;
import com.upeoe.redenvelope.entity.RedEnvelopeItem;
import com.upeoe.redenvelope.exception.BusinessException;
import com.upeoe.redenvelope.repository.RedEnvelopeItemRepo;
import com.upeoe.redenvelope.repository.RedEnvelopeRepo;
import com.upeoe.redenvelope.utils.DateKit;
import com.upeoe.redenvelope.utils.RedEnvelopeKit;
import com.upeoe.redenvelope.utils.SignKit;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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

        Date now = new Date();
        String sign = SignKit.generate();
        params.setSign(sign);
        params.setCreatedAt(now);
        params.setExpiredAt(DateKit.forwardDay(now, DateKit.ONE));
        params = redEnvelopeRepo.saveAndFlush(params);

        List<Double> redEvlps = RedEnvelopeKit.genListMoney(params.getNumber(), params.getMoney().doubleValue());
        for (double item : redEvlps) {
            redEnvelopeItemRepo.save(new RedEnvelopeItem(params.getId(), "", now, new BigDecimal(item)));
        }

        return sign;
    }

    public BigDecimal fetchRedEnvelope(String userId, String sign) throws BusinessException {
        if (StringUtils.isBlank(userId)) {
            throw new BusinessException("Incorrect user.");
        }
        if (StringUtils.isBlank(sign)) {
            throw new BusinessException("Incorrect red envelope sign.");
        }

        RedEnvelope redEnvelope = redEnvelopeRepo.findBySign(sign);
        if (null == redEnvelope) {
            throw new BusinessException("Invalid red envelope sign.");
        }
        Long count = redEnvelopeItemRepo.countByRedEnvelopeIdAndBelongTo(redEnvelope.getId(), userId);
        if (count != 0) {
            throw new BusinessException("Sorry, you have already fetch this red envelope.");
        }

        List<RedEnvelopeItem> items = redEnvelopeItemRepo.findAllUnExpiredByRedEnvelopeId(
                redEnvelope.getId(), redEnvelope.getCreatedAt());
        if (items.size() == 0) {
            throw new BusinessException("No red envelope can be fetch.");
        }
        RedEnvelopeItem item = items.get(0);
        item.setBelongTo(userId);
        item.setGetTime(new Date());
        redEnvelopeItemRepo.save(item);

        return item.getAmount();
    }

}
