package com.mobian.pageModel;

import com.mobian.util.ConvertNameUtil;

import java.util.Date;

@SuppressWarnings("serial")
public class MbBalanceLog implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private Integer id;
	private Integer tenantId;
	private Date addtime;			
	private Date updatetime;			
	private Boolean isdeleted;
	private Integer balanceId;
	private Integer amount;
	private String refId;
	private String refType;
	private String reason;
	private String remark;
	private Boolean isShow;
	//加的字段
	private Integer amountIn;  //正金额
	private Integer amountOut; //负金额
	private String refTypes;

	private Date updatetimeBegin;
	private Date updatetimeEnd;
	private Integer shopId;
	private Integer[] balanceIds;
    private String shopName;


	public String getRefTypes() {
		return refTypes;
	}

	public void setRefTypes(String refTypes) {
		this.refTypes = refTypes;
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
	public void setBalanceId(Integer balanceId) {
		this.balanceId = balanceId;
	}
	
	public Integer getBalanceId() {
		return this.balanceId;
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
	public String getRefTypeName() {
		return ConvertNameUtil.getString(this.refType);
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

	public Boolean getIsShow() {
		return isShow;
	}

	public void setIsShow(Boolean isShow) {
		this.isShow = isShow;
	}

	public Date getUpdatetimeBegin() {
		return updatetimeBegin;
	}

	public void setUpdatetimeBegin(Date updatetimeBegin) {
		this.updatetimeBegin = updatetimeBegin;
	}

	public Date getUpdatetimeEnd() {
		return updatetimeEnd;
	}

	public void setUpdatetimeEnd(Date updatetimeEnd) {
		this.updatetimeEnd = updatetimeEnd;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public Integer[] getBalanceIds() {
		return balanceIds;
	}

	public void setBalanceIds(Integer[] balanceIds) {
		this.balanceIds = balanceIds;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getAmountOut() {
		return amountOut;
	}

	public void setAmountOut(Integer amountOut) {
		this.amountOut = amountOut;
	}

	public Integer getAmountIn() {
		return amountIn;
	}

	public void setAmountIn(Integer amountIn) {
		this.amountIn = amountIn;
	}
}
