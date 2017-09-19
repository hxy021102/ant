package com.mobian.pageModel;

import com.mobian.util.ConvertNameUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class MbShopCoupons implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private Integer id;
	private Integer tenantId;
	private Date addtime;			
	private Date updatetime;			
	private Boolean isdeleted;
	private Integer couponsId;
	private Integer shopId;
	private Integer quantityTotal;
	private Integer quantityUsed;
	private String status;
	private Date timeStart;
	private Date timeEnd;			
	private String remark;
	private String payType;
	private String loginId;
	private String payTypeName;
	private String statusName;

	private List<MbShopCoupons> shopCouponsList = new ArrayList<MbShopCoupons>();

	

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
	public void setCouponsId(Integer couponsId) {
		this.couponsId = couponsId;
	}
	
	public Integer getCouponsId() {
		return this.couponsId;
	}
	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}
	
	public Integer getShopId() {
		return this.shopId;
	}
	public void setQuantityTotal(Integer quantityTotal) {
		this.quantityTotal = quantityTotal;
	}
	
	public Integer getQuantityTotal() {
		return this.quantityTotal;
	}
	public void setQuantityUsed(Integer quantityUsed) {
		this.quantityUsed = quantityUsed;
	}
	
	public Integer getQuantityUsed() {
		return this.quantityUsed;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return this.status;
	}
	public void setTimeStart(Date timeStart) {
		this.timeStart = timeStart;
	}
	
	public Date getTimeStart() {
		return this.timeStart;
	}
	public void setTimeEnd(Date timeEnd) {
		this.timeEnd = timeEnd;
	}
	
	public Date getTimeEnd() {
		return this.timeEnd;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getRemark() {
		return this.remark;
	}


	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public List<MbShopCoupons> getShopCouponsList() {
		return shopCouponsList;
	}

	public void setShopCouponsList(List<MbShopCoupons> shopCouponsList) {
		this.shopCouponsList = shopCouponsList;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getPayTypeName() {
		return ConvertNameUtil.getString(this.getPayType());
	}

	public void setPayTypeName(String payTypeName) {
		this.payTypeName = payTypeName;
	}

	public String getStatusName() {
		return ConvertNameUtil.getString(this.status);
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
}
