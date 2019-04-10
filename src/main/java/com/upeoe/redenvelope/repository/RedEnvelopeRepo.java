package com.upeoe.redenvelope.repository;

import com.upeoe.redenvelope.entity.RedEnvelope;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author upeoe
 * @create 2019/4/10 14:14
 */
public interface RedEnvelopeRepo extends JpaRepository<RedEnvelope, Integer> {

    RedEnvelope findByUserId(String userId);

    RedEnvelope findBySign(String sign);

}
