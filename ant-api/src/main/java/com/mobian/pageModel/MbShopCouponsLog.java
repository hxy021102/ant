package com.mobian.pageModel;

import com.mobian.util.ConvertNameUtil;

import java.util.Date;

@SuppressWarnings("serial")
public class MbShopCouponsLog implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private Integer id;
	private Integer tenantId;
	private Date addtime;
	private Date updatetime;
	private Boolean isdeleted;
	private Integer shopCouponsId;
	private Integer quantityUsed;
	private Integer quantityTotal;
	private String shopCouponsStatus;
	private String loginId;
	private String refId;
	private String refType;
	private String reason;
	private String remark;
	private String shopCouponsStatusName;
	private String refTypeName;



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
	public void setShopCouponsId(Integer shopCouponsId) {
		this.shopCouponsId = shopCouponsId;
	}

	public Integer getShopCouponsId() {
		return this.shopCouponsId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getLoginId() {
		return this.loginId;
	}
	public void setRefId(String refId) {
		this.refId = refId;
	}

	public String getRefId() {
		return this.refId;
	}
	public void setRefType(String refType) {
		this.refType = refType;
	}

	public String getRefType() {
		return this.refType;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getReason() {
		return this.reason;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemark() {
		return this.remark;
	}

	public Integer getQuantityUsed() {
		return quantityUsed;
	}

	public Integer getQuantityTotal() {
		return quantityTotal;
	}

	public void setQuantityTotal(Integer quantityTotal) {
		this.quantityTotal = quantityTotal;
	}

	public void setQuantityUsed(Integer quantityUsed) {
		this.quantityUsed = quantityUsed;
	}

	public String getShopCouponsStatus() {
		return shopCouponsStatus;
	}

	public void setShopCouponsStatus(String shopCouponsStatus) {
		this.shopCouponsStatus = shopCouponsStatus;
	}

	public String getShopCouponsStatusName() {
		return ConvertNameUtil.getString(this.shopCouponsStatus);
	}

	public void setShopCouponsStatusName(String shopCouponsStatusName) {
		this.shopCouponsStatusName = shopCouponsStatusName;
	}

	public String getRefTypeName() {
		return ConvertNameUtil.getString(this.refType);
	}

	public void setRefTypeName(String refTypeName) {
		this.refTypeName = refTypeName;
	}
}
