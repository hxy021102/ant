package com.mobian.pageModel;

import com.bx.ant.pageModel.DeliverOrder;

/**
 * Created by 黄晓渝 on 2017/10/28.
 */
public class MbAssignShop {
    private Integer id;
    private String name;
    private String contactPhone;
    private double distance;
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

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
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
}
