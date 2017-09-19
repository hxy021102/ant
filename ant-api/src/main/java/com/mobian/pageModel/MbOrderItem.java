package com.mobian.pageModel;

import java.util.Date;

@SuppressWarnings("serial")
public class MbOrderItem implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private Integer id;
	private Integer tenantId;
	private Date addtime;
	private Date updatetime;
	private Boolean isdeleted;
	private Integer itemId;
	private Integer quantity;
	private Integer marketPrice;
	private Integer buyPrice;
	private Integer orderId;
	private Date updatetimeBegin;
	private Date updatetimeEnd;
	private Integer voucherQuantityTotal;
	private Integer voucherQuantityUsed;


	private String usableQuantity;
	private MbItem item;

	public String getUsableQuantity() {
		return usableQuantity;
	}

	public void setUsableQuantity(String usableQuantity) {
		this.usableQuantity = usableQuantity;
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
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getItemId() {
		return this.itemId;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getQuantity() {
		return this.quantity;
	}
	public void setMarketPrice(Integer marketPrice) {
		this.marketPrice = marketPrice;
	}

	public Integer getMarketPrice() {
		return this.marketPrice;
	}
	public void setBuyPrice(Integer buyPrice) {
		this.buyPrice = buyPrice;
	}

	public Integer getBuyPrice() {
		return this.buyPrice;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getOrderId() {
		return this.orderId;
	}

	public MbItem getItem() {
		return item;
	}

	public void setItem(MbItem item) {
		this.item = item;
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

	public Integer getVoucherQuantityTotal() {
		return voucherQuantityTotal;
	}

	public void setVoucherQuantityTotal(Integer voucherQuantityTotal) {
		this.voucherQuantityTotal = voucherQuantityTotal;
	}

	public Integer getVoucherQuantityUsed() {
		return voucherQuantityUsed;
	}

	public void setVoucherQuantityUsed(Integer voucherQuantityUsed) {
		this.voucherQuantityUsed = voucherQuantityUsed;
	}
}
