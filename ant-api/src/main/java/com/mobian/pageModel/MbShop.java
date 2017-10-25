package com.mobian.pageModel;

import com.mobian.util.ConvertNameUtil;

import java.math.BigDecimal;
import java.util.Date;

@SuppressWarnings("serial")
public class MbShop implements java.io.Serializable {

    private static final long serialVersionUID = 5454155825314635342L;

    private Integer id;
    private Integer tenantId;
    private Date addtime;
    private Date updatetime;
    private Boolean isdeleted;
    private String name;
    private Integer regionId;
    private String regionPath;
    private String address;
    private Integer warehouseId;
    private String contactPhone;
    private String contactPeople;
    private String auditStatus;
    private String auditRemark;
    private Integer userId;
    private String auditLoginId;
    private Date auditDate;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private String shopType;
    private Integer salesLoginId;//销售id;

    private Integer parentId;
    private String parentName;

    private boolean onlyBranch; // 是否只查询分店
    private boolean onlyMain; // true:只查询主店

    private  Integer[] ids;

    public Integer[] getIds() {
        return ids;
    }

    public void setIds(Integer[] ids) {
        this.ids = ids;
    }


    public String getRegionPath() {
        return regionPath;
    }

    public void setRegionPath(String regionPath) {
        this.regionPath = regionPath;
    }

    public void setId(Integer value) {
        this.id = value;
    }

    public Integer getId() {
        return this.id;
    }


    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

    public Integer getTenantId() {
        return this.tenantId;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public Date getAddtime() {
        return this.addtime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Date getUpdatetime() {
        return this.updatetime;
    }

    public void setIsdeleted(Boolean isdeleted) {
        this.isdeleted = isdeleted;
    }

    public Boolean getIsdeleted() {
        return this.isdeleted;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public Integer getRegionId() {
        return this.regionId;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return this.address;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Integer getWarehouseId() {
        return this.warehouseId;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactPhone() {
        return this.contactPhone;
    }

    public void setContactPeople(String contactPeople) {
        this.contactPeople = contactPeople;
    }

    public String getContactPeople() {
        return this.contactPeople;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public String getAuditStatusName() {
        return ConvertNameUtil.getString(auditStatus);
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getAuditRemark() {
        return auditRemark;
    }

    public void setAuditRemark(String auditRemark) {
        this.auditRemark = auditRemark;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAuditLoginId() {
        return auditLoginId;
    }

    public void setAuditLoginId(String auditLoginId) {
        this.auditLoginId = auditLoginId;
    }

    public Date getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(Date auditDate) {
        this.auditDate = auditDate;
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

    public String getShopTypeName() {
        return ConvertNameUtil.getString(shopType);
    }

    public void setShopType(String shopType) {
        this.shopType = shopType;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public boolean isOnlyBranch() {
        return onlyBranch;
    }

    public void setOnlyBranch(boolean onlyBranch) {
        this.onlyBranch = onlyBranch;
    }

    public boolean isOnlyMain() {
        return onlyMain;
    }

    public void setOnlyMain(boolean onlyMain) {
        this.onlyMain = onlyMain;
    }

    public Integer getSalesLoginId() {
        return salesLoginId;
    }

    public void setSalesLoginId(Integer salesLoginId) {
        this.salesLoginId = salesLoginId;
    }
}
