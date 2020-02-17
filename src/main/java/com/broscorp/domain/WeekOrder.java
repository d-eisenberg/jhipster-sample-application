package com.broscorp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A WeekOrder.
 */
@Entity
@Table(name = "week_order")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class WeekOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "weeknum")
    private Integer weeknum;

    @Column(name = "daynum")
    private Integer daynum;

    @Column(name = "user_id")
    private Integer userId;

    @Lob
    @Column(name = "user_order")
    private byte[] userOrder;

    @Column(name = "user_order_content_type")
    private String userOrderContentType;

    @ManyToOne
    @JsonIgnoreProperties("weekOrders")
    private Users userId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getWeeknum() {
        return weeknum;
    }

    public WeekOrder weeknum(Integer weeknum) {
        this.weeknum = weeknum;
        return this;
    }

    public void setWeeknum(Integer weeknum) {
        this.weeknum = weeknum;
    }

    public Integer getDaynum() {
        return daynum;
    }

    public WeekOrder daynum(Integer daynum) {
        this.daynum = daynum;
        return this;
    }

    public void setDaynum(Integer daynum) {
        this.daynum = daynum;
    }

    public Integer getUserId() {
        return userId;
    }

    public WeekOrder userId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public byte[] getUserOrder() {
        return userOrder;
    }

    public WeekOrder userOrder(byte[] userOrder) {
        this.userOrder = userOrder;
        return this;
    }

    public void setUserOrder(byte[] userOrder) {
        this.userOrder = userOrder;
    }

    public String getUserOrderContentType() {
        return userOrderContentType;
    }

    public WeekOrder userOrderContentType(String userOrderContentType) {
        this.userOrderContentType = userOrderContentType;
        return this;
    }

    public void setUserOrderContentType(String userOrderContentType) {
        this.userOrderContentType = userOrderContentType;
    }

    public Users getUserId() {
        return userId;
    }

    public WeekOrder userId(Users users) {
        this.userId = users;
        return this;
    }

    public void setUserId(Users users) {
        this.userId = users;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WeekOrder)) {
            return false;
        }
        return id != null && id.equals(((WeekOrder) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "WeekOrder{" +
            "id=" + getId() +
            ", weeknum=" + getWeeknum() +
            ", daynum=" + getDaynum() +
            ", userId=" + getUserId() +
            ", userOrder='" + getUserOrder() + "'" +
            ", userOrderContentType='" + getUserOrderContentType() + "'" +
            "}";
    }
}
