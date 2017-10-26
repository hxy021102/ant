package com.mobian.pageModel;

@SuppressWarnings("serial")
public class MbBalanceLogExport extends MbBalanceLog {
	private Integer shopId;
	private String shopName;
	private String refTypeName;
	private double amountElement;

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	@Override
	public String getRefTypeName() {
		return refTypeName;
	}

	public void setRefTypeName(String refTypeName) {
		this.refTypeName = refTypeName;
	}

	public double getAmountElement() {
		return amountElement;
	}

	public void setAmountElement(double amountElement) {
		this.amountElement = amountElement;
	}
}
