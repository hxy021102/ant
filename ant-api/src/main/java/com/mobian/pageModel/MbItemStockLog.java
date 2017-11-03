package com.mobian.pageModel;

import com.mobian.util.ConvertNameUtil;

import java.util.Date;

@SuppressWarnings("serial")
public class MbItemStockLog implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private Integer id;
	private Integer tenantId;
	private Date addtime;			
	private Date updatetime;			
	private Boolean isdeleted;
	private Integer itemStockId;
	private Integer quantity;
	private String loginId;
	private String logType;
	private String reason;
	private String remark;
	private Integer costPrice;
	private Integer endQuantity;
	private Integer inPrice;

	private String loginName;
	private String logTypeName;
	private String itemName;
	private Date stockLogStartTime;
	private Date stockLogEndTime;
	private Integer warehouseId;
	private Integer[] itemStockIds;
	private String warehouseName;
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLogTypeName() {
		return ConvertNameUtil.getString(logType);
	}

	public void setLogTypeName(String logTypeName) {
		this.logTypeName = logTypeName;
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
	public void setItemStockId(Integer itemStockId) {
		this.itemStockId = itemStockId;
	}
	
	public Integer getItemStockId() {
		return this.itemStockId;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	public Integer getQuantity() {
		return this.quantity;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	
	public String getLoginId() {
		return this.loginId;
	}
	public void setLogType(String logType) {
		this.logType = logType;
	}
	
	public String getLogType() {
		return this.logType;
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

	public Date getStockLogStartTime() {
		return stockLogStartTime;
	}

	public void setStockLogStartTime(Date stockLogStartTime) {
		this.stockLogStartTime = stockLogStartTime;
	}

	public Date getStockLogEndTime() {
		return stockLogEndTime;
	}

	public void setStockLogEndTime(Date stockLogEndTime) {
		this.stockLogEndTime = stockLogEndTime;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Integer[] getItemStockIds() {
		return itemStockIds;
	}

	public void setItemStockIds(Integer[] itemStockIds) {
		this.itemStockIds = itemStockIds;
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

	public Integer getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(Integer costPrice) {
		this.costPrice = costPrice;
	}

	public Integer getEndQuantity() {
		return endQuantity;
	}

	public void setEndQuantity(Integer endQuantity) {
		this.endQuantity = endQuantity;
	}

	public Integer getInPrice() {
		return inPrice;
	}

	public void setInPrice(Integer inPrice) {
		this.inPrice = inPrice;
	}
}
