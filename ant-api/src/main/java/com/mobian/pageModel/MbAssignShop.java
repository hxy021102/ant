package com.mobian.pageModel;

import com.bx.ant.pageModel.DeliverOrder;

import java.math.BigDecimal;

/**
 * Created by 黄晓渝 on 2017/10/28.
 */
public class MbAssignShop implements Comparable<MbAssignShop>,java.io.Serializable{
    private Integer id;
    private String name;
    private String contactPhone;
    private BigDecimal distance;
    private Long deliverOrderId;
    private DeliverOrder deliverOrder;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public BigDecimal getDistance() {
        return distance;
    }

    public void setDistance(BigDecimal distance) {
        this.distance = distance;
    }

    public DeliverOrder getDeliverOrder() {
        return deliverOrder;
    }

    public void setDeliverOrder(DeliverOrder deliverOrder) {
        this.deliverOrder = deliverOrder;
    }

    public Long getDeliverOrderId() {
        return deliverOrderId;
    }

    public void setDeliverOrderId(Long deliverOrderId) {
        this.deliverOrderId = deliverOrderId;
    }

    @Override
    public int compareTo(MbAssignShop o) {
        if (this.distance.doubleValue() > o.getDistance().doubleValue()) {
            return 1;
        } else if (this.distance.doubleValue() < o.getDistance().doubleValue()) {
            return -1;
        } else {
            return 0;
        }
    }
}
