package com.upeoe.redenvelope.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author upeoe
 * @create 2019/4/10 13:40
 */
@Entity
@Table(name = "red_envelope")
public class RedEnvelope implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "sign")
    private String sign;
    @Column(name = "number")
    private int number;
    @Column(name = "money")
    private BigDecimal money;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "expired_at")
    private Date expiredAt;

    public RedEnvelope() {
    }

    public RedEnvelope(String userId, String sign, int number, BigDecimal money, Date createdAt, Date expiredAt) {
        this.userId = userId;
        this.sign = sign;
        this.number = number;
        this.money = money;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(Date expiredAt) {
        this.expiredAt = expiredAt;
    }

    @Override
    public String toString() {
        return "RedEnvelope{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", sign='" + sign + '\'' +
                ", number=" + number +
                ", money=" + money +
                ", createdAt=" + createdAt +
                ", expiredAt=" + expiredAt +
                '}';
    }
}
