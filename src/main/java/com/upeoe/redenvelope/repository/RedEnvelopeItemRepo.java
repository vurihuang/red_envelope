package com.upeoe.redenvelope.repository;

import com.upeoe.redenvelope.entity.RedEnvelopeItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

/**
 * @author upeoe
 * @create 2019/4/10 18:00
 */
public interface RedEnvelopeItemRepo extends JpaRepository<RedEnvelopeItem, Integer>, CrudRepository<RedEnvelopeItem, Integer> {

    List<RedEnvelopeItem> findAllByRedEnvelopeId(Integer redEnvelopeId);

    @Query(nativeQuery = true,
            value = "select * from red_envelope_item where red_envelope_id = ?1 and get_time = ?2")
    List<RedEnvelopeItem> findAllUnExpiredByRedEnvelopeId(Integer redEnvelopeId, Date getTime);

    Long countByRedEnvelopeIdAndBelongTo(Integer redEnvelopeId, String belongTo);

    List<RedEnvelopeItem> findByBelongTo(String belongTo);

}
