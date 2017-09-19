package com.mobian.pageModel;

import java.util.Date;

@SuppressWarnings("serial")
public class MbUserAddress implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private Integer id;
	private Integer tenantId;
	private Date addtime;
	private Date updatetime;
	private Boolean isdeleted;
	private Integer userId;
	private Integer regionId;
	private String provinceName;
	private String cityName;
	private String countyName;
	private String detailInfo;
	private String postalCode;
	private String userName;
	private String telNumber;
	private Boolean defaultAddress;

	public String address;

	public String getAddress() {
		return provinceName + "-" + cityName + "-" + countyName + " " + detailInfo;
	}

	public void setAddress(String address) {
		this.address = address;
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
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getUserId() {
		return this.userId;
	}
	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

	public Integer getRegionId() {
		return this.regionId;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getProvinceName() {
		return this.provinceName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityName() {
		return this.cityName;
	}
	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public String getCountyName() {
		return this.countyName;
	}
	public void setDetailInfo(String detailInfo) {
		this.detailInfo = detailInfo;
	}

	public String getDetailInfo() {
		return this.detailInfo;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getPostalCode() {
		return this.postalCode;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return this.userName;
	}
	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}

	public String getTelNumber() {
		return this.telNumber;
	}
	public void setDefaultAddress(Boolean defaultAddress) {
		this.defaultAddress = defaultAddress;
	}

	public Boolean getDefaultAddress() {
		return this.defaultAddress;
	}

}
