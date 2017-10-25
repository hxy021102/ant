/*
 * @author John
 * @date - 2017-04-18
 */

package com.mobian.model;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@SuppressWarnings("serial")
@Entity
@Table(name = "mb_shop")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TmbShop implements java.io.Serializable, IEntity {
    private static final long serialVersionUID = 5454155825314635342L;

    //alias
    public static final String TABLE_ALIAS = "MbShop";
    public static final String ALIAS_ID = "主键";
    public static final String ALIAS_TENANT_ID = "租户ID";
    public static final String ALIAS_ADDTIME = "添加时间";
    public static final String ALIAS_UPDATETIME = "修改时间";
    public static final String ALIAS_ISDELETED = "是否删除,1删除，0未删除";
    public static final String ALIAS_NAME = "名称";
    public static final String ALIAS_REGION_ID = "行政区划";
    public static final String ALIAS_ADDRESS = "具体地址";
    public static final String ALIAS_WAREHOUSE_ID = "仓库ID";
    public static final String ALIAS_CONTACT_PHONE = "联系电话";
    public static final String ALIAS_CONTACT_PEOPLE = "联系人";
    public static final String ALIAS_SHOP_TYPE = "类型";
    public static final String ALIAS_INVOICE_DEFAULT = "默认开票模板";

    //date formats
    public static final String FORMAT_ADDTIME = com.mobian.util.Constants.DATE_FORMAT_FOR_ENTITY;
    public static final String FORMAT_UPDATETIME = com.mobian.util.Constants.DATE_FORMAT_FOR_ENTITY;


    //可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
    //columns START
    //
    private Integer id;
    //
    private Integer tenantId;
    //@NotNull
    private Date addtime;
    //@NotNull
    private Date updatetime;
    //@NotNull
    private Boolean isdeleted;
    //@NotBlank @Length(max=128)
    private String name;
    //
    private Integer regionId;
    //@Length(max=512)
    private String address;
    //
    private Integer warehouseId;
    //@Length(max=32)
    private String contactPhone;
    //@Length(max=32)
    private String contactPeople;

    private String auditStatus;
    private String auditRemark;
    private Integer userId;
    private String auditLoginId;
    private Date auditDate;
    private String shopType;
    private Integer parentId;

    private BigDecimal longitude;
    private BigDecimal latitude;
    //columns END


    public TmbShop() {
    }

    public TmbShop(Integer id) {
        this.id = id;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, length = 10)
    public Integer getId() {
        return this.id;
    }

    @Column(name = "tenant_id", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
    public Integer getTenantId() {
        return this.tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }


    @Column(name = "addtime", unique = false, nullable = false, insertable = true, updatable = true, length = 19)
    public Date getAddtime() {
        return this.addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }


    @Column(name = "updatetime", unique = false, nullable = false, insertable = true, updatable = true, length = 19)
    public Date getUpdatetime() {
        return this.updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    @Column(name = "isdeleted", unique = false, nullable = false, insertable = true, updatable = true, length = 0)
    public Boolean getIsdeleted() {
        return this.isdeleted;
    }

    public void setIsdeleted(Boolean isdeleted) {
        this.isdeleted = isdeleted;
    }

    @Column(name = "name", unique = false, nullable = false, insertable = true, updatable = true, length = 128)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "region_id", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
    public Integer getRegionId() {
        return this.regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    @Column(name = "address", unique = false, nullable = true, insertable = true, updatable = true, length = 512)
    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "warehouse_id", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
    public Integer getWarehouseId() {
        return this.warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    @Column(name = "contact_phone", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
    public String getContactPhone() {
        return this.contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    @Column(name = "contact_people", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
    public String getContactPeople() {
        return this.contactPeople;
    }

    public void setContactPeople(String contactPeople) {
        this.contactPeople = contactPeople;
    }

    @Column(name = "audit_status", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    @Column(name = "audit_remark", unique = false, nullable = true, insertable = true, updatable = true, length = 500)
    public String getAuditRemark() {
        return auditRemark;
    }

    @Column(name = "shop_type", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
    public String getShopType() {
        return shopType;
    }

    public void setShopType(String shopType) {
        this.shopType = shopType;
    }

    public void setAuditRemark(String auditRemark) {
        this.auditRemark = auditRemark;
    }

    @Column(name = "user_id", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Column(name = "audit_login_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
    public String getAuditLoginId() {
        return auditLoginId;
    }

    public void setAuditLoginId(String auditLoginId) {
        this.auditLoginId = auditLoginId;
    }

    @Column(name = "audit_date", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
    public Date getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(Date auditDate) {
        this.auditDate = auditDate;
    }

    @Column(name = "longitude", unique = false, nullable = true, insertable = true, updatable = true, scale = 6)
    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    @Column(name = "latitude", unique = false, nullable = true, insertable = true, updatable = true, scale = 6)
    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    @Column(name = "parent_id", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
}

