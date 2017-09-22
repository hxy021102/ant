package com.mobian.pageModel;

import com.mobian.util.ConvertNameUtil;

import java.math.BigDecimal;
import java.util.Date;

@SuppressWarnings("serial")
public class MbWarehouse implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private Integer id;
	private Integer tenantId;
	private Date addtime;			
	private Date updatetime;			
	private Boolean isdeleted;
	private String code;
	private String name;
	private Integer regionId;
	private String regionPath;
	private String address;
	private String warehouseType;
	private BigDecimal longitude;
	private BigDecimal latitude;

	public String getWarehouseTypeName() {
	    return ConvertNameUtil.getString(warehouseType);
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
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return this.code;
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
	public void setWarehouseType(String warehouseType) {
		this.warehouseType = warehouseType;
	}
	
	public String getWarehouseType() {
		return this.warehouseType;
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
}
