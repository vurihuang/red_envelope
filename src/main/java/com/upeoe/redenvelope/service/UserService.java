package com.upeoe.redenvelope.service;

import com.upeoe.redenvelope.entity.RedEnvelope;
import com.upeoe.redenvelope.entity.RedEnvelopeItem;
import com.upeoe.redenvelope.entity.User;
import com.upeoe.redenvelope.repository.RedEnvelopeItemRepo;
import com.upeoe.redenvelope.repository.RedEnvelopeRepo;
import com.upeoe.redenvelope.repository.UserRepo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author upeoe
 * @create 2019/4/11 00:30
 * User service.
 */
@Service
public class UserService {

    @Autowired
    RedEnvelopeRepo redEnvelopeRepo;

    @Autowired
    RedEnvelopeItemRepo redEnvelopeItemRepo;

    @Autowired
    UserRepo userRepo;

    public List<RedEnvelope> findSendRedEnvelopesByUserId(String userId) {
        List<RedEnvelope> result = new ArrayList<>();

        if (StringUtils.isBlank(userId)) {
            return result;
        }
        return redEnvelopeRepo.findAllByUserId(userId);
    }

    public List<RedEnvelopeItem> findFetchedRedEnvelopesByUserId(String userId) {
        List<RedEnvelopeItem> result = new ArrayList<>();

        if (StringUtils.isBlank(userId)) {
            return result;
        }
        return redEnvelopeItemRepo.findByBelongTo(userId);
    }

    public User findAmount(String userId) {
        return userRepo.findByUsername(userId);
    }

}
