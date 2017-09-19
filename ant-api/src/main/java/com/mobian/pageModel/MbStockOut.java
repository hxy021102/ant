package com.mobian.pageModel;

import com.mobian.util.ConvertNameUtil;

import java.util.Date;

@SuppressWarnings("serial")
public class MbStockOut implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private Integer id;
	private Integer tenantId;
	private Date addtime;			
	private Date updatetime;			
	private Boolean isdeleted;
	private String stockOutPeopleId;
	private String stockOutPeopleName;
	private String loginId;
	private String loginName;
	private Date stockOutTime;
	private Date stockOutTimeBegin;
	private Date stockOutTimeEnd;
	private String remark;
	private Integer warehouseId;
	private String warehouseName;
	private String stockOutType;
	private String stockOutTypeName;

	public String getStockOutTypeName() {
		return ConvertNameUtil.getString(this.stockOutType);
	}

	public void setStockOutTypeName(String stockOutTypeName) {
		this.stockOutTypeName = stockOutTypeName;
	}

	public String getStockOutType() {
		return stockOutType;
	}

	public void setStockOutType(String stockOutType) {
		this.stockOutType = stockOutType;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Integer getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Integer warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public Date getStockOutTimeBegin() {
		return stockOutTimeBegin;
	}

	public void setStockOutTimeBegin(Date stockOutTimeBegin) {
		this.stockOutTimeBegin = stockOutTimeBegin;
	}

	public Date getStockOutTimeEnd() {
		return stockOutTimeEnd;
	}

	public void setStockOutTimeEnd(Date stockOutTimeEnd) {
		this.stockOutTimeEnd = stockOutTimeEnd;
	}

	public String getStockOutPeopleName() {
		return stockOutPeopleName;
	}

	public void setStockOutPeopleName(String stockOutPeopleName) {
		this.stockOutPeopleName = stockOutPeopleName;
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
	public void setStockOutPeopleId(String stockOutPeopleId) {
		this.stockOutPeopleId = stockOutPeopleId;
	}
	
	public String getStockOutPeopleId() {
		return this.stockOutPeopleId;
	}
	public void setStockOutTime(Date stockOutTime) {
		this.stockOutTime = stockOutTime;
	}
	
	public Date getStockOutTime() {
		return this.stockOutTime;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getRemark() {
		return this.remark;
	}

}
