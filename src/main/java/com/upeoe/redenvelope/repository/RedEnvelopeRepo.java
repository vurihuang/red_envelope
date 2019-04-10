package com.upeoe.redenvelope.repository;

import com.upeoe.redenvelope.entity.RedEnvelope;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author upeoe
 * @create 2019/4/10 14:14
 */
public interface RedEnvelopeRepo extends JpaRepository<RedEnvelope, Integer> {

    RedEnvelope findByUserId(String userId);

    RedEnvelope findBySign(String sign);

    List<RedEnvelope> findAllByUserId(String userId);

}
