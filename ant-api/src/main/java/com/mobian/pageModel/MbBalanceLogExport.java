package com.mobian.pageModel;

@SuppressWarnings("serial")
public class MbBalanceLogExport extends MbBalanceLog {
	private Integer shopId;
	private String shopName;
	private String refTypeName;

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
}
