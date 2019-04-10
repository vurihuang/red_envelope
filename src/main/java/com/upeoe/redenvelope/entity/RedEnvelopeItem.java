package com.upeoe.redenvelope.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author upeoe
 * @create 2019/4/10 17:52
 */
@Entity
@Table(name = "red_envelope_item")
public class RedEnvelopeItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @JsonIgnore
    @Column(name = "red_envelope_id")
    private Integer redEnvelopeId;
    @Column(name = "belong_to")
    private String belongTo;
    @Column(name = "get_time")
    private Date getTime;
    @Column(name = "amount")
    private BigDecimal amount;

    public RedEnvelopeItem() {
    }

    public RedEnvelopeItem(Integer redEnvelopeId, Date getTime, BigDecimal amount) {
        this.redEnvelopeId = redEnvelopeId;
        this.getTime = getTime;
        this.amount = amount;
    }

    public RedEnvelopeItem(Integer redEnvelopeId, String belongTo, Date getTime, BigDecimal amount) {
        this.redEnvelopeId = redEnvelopeId;
        this.belongTo = belongTo;
        this.getTime = getTime;
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRedEnvelopeId() {
        return redEnvelopeId;
    }

    public void setRedEnvelopeId(Integer redEnvelopeId) {
        this.redEnvelopeId = redEnvelopeId;
    }

    public String getBelongTo() {
        return belongTo;
    }

    public void setBelongTo(String belongTo) {
        this.belongTo = belongTo;
    }

    public Date getGetTime() {
        return getTime;
    }

    public void setGetTime(Date getTime) {
        this.getTime = getTime;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "RedEnvelopeItem{" +
                "id=" + id +
                ", redEnvelopeId=" + redEnvelopeId +
                ", belongTo='" + belongTo + '\'' +
                ", getTime=" + getTime +
                ", amount=" + amount +
                '}';
    }
}
