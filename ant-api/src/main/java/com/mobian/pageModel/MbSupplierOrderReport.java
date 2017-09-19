package com.mobian.pageModel;

import java.util.Date;

public class MbSupplierOrderReport implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private Integer itemId;
	private String itemCode;
	private String itemName;
	private Integer quantity;
	// 入库数量
	private Integer stockInQuantity;
	// 入库总价
	private Integer totalPrice;

	private Date startDate;
	private Date endDate;
	private String itemIds;

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getStockInQuantity() {
		return stockInQuantity;
	}

	public void setStockInQuantity(Integer stockInQuantity) {
		this.stockInQuantity = stockInQuantity;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getItemIds() {
		return itemIds;
	}

	public void setItemIds(String itemIds) {
		this.itemIds = itemIds;
	}
}
