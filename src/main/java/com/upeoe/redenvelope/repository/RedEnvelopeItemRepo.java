package com.upeoe.redenvelope.repository;

import com.upeoe.redenvelope.entity.RedEnvelopeItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author upeoe
 * @create 2019/4/10 18:00
 */
public interface RedEnvelopeItemRepo extends JpaRepository<RedEnvelopeItem, Integer> {

    List<RedEnvelopeItem> findAllByRedEnvelopeId(Integer redEnvelopeId);

}
