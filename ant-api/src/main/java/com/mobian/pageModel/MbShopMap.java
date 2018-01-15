package com.mobian.pageModel;

import com.bx.ant.pageModel.DistributeRangeMap;

import java.math.BigDecimal;
import java.util.List;

@SuppressWarnings("serial")
public class MbShopMap implements java.io.Serializable {

    private static final long serialVersionUID = 5454155825314635342L;

    private String name;
    private String address;
    private String contactPhone;
    private String contactPeople;
    private Integer userId;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private String shopType;
    private List<DistributeRangeMap> distributeRangeMapList;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public String getShopType() {
        return shopType;
    }

    public void setShopType(String shopType) {
        this.shopType = shopType;
    }

    public List<DistributeRangeMap> getDistributeRangeMapList() {
        return distributeRangeMapList;
    }

    public void setDistributeRangeMapList(List<DistributeRangeMap> distributeRangeMapList) {
        this.distributeRangeMapList = distributeRangeMapList;
    }
}