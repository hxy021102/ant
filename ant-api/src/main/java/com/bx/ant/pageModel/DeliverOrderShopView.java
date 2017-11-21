package com.bx.ant.pageModel;

import com.mobian.pageModel.MbShop;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by w9777 on 2017/10/23.
 */
public class DeliverOrderShopView extends DeliverOrderShop {

    private DeliverOrder delvierOrder;
    private List<DeliverOrderShopItem> deliverOrderShopItemList;
    private String contactPhone;
    private String contactPeople;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private String deliverAddress;
    private Date deliverRequireTime;
    private MbShop shop;


    public DeliverOrder getDelvierOrder() {
        return delvierOrder;
    }

    public void setDelvierOrder(DeliverOrder delvierOrder) {
        this.delvierOrder = delvierOrder;
    }

    public List<DeliverOrderShopItem> getDeliverOrderShopItemList() {
        return deliverOrderShopItemList;
    }

    public void setDeliverOrderShopItemList(List<DeliverOrderShopItem> deliverOrderShopItemList) {
        this.deliverOrderShopItemList = deliverOrderShopItemList;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactPeople() {
        return contactPeople;
    }

    public void setContactPeople(String contactPeople) {
        this.contactPeople = contactPeople;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public String getDeliverAddress() {
        return deliverAddress;
    }

    public void setDeliverAddress(String deliverAddress) {
        this.deliverAddress = deliverAddress;
    }

    public Date getDeliverRequireTime() {
        return deliverRequireTime;
    }

    public void setDeliverRequireTime(Date deliverRequireTime) {
        this.deliverRequireTime = deliverRequireTime;
    }

    public MbShop getShop() {
        return shop;
    }

    public void setShop(MbShop shop) {
        this.shop = shop;
    }
}
