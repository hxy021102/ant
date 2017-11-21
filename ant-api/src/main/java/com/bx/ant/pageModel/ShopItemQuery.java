package com.bx.ant.pageModel;


import com.mobian.util.ConvertNameUtil;



@SuppressWarnings("serial")
public class ShopItemQuery extends ShopItem {
	private String name;
	private String quantityUnitName;
	private String url;
    private String statusName;
    private String shopName;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getQuantityUnitName() {
		return quantityUnitName;
	}

	public void setQuantityUnitName(String quantityUnitName) {
		this.quantityUnitName = quantityUnitName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getStatusName() {
		return ConvertNameUtil.getString(this.statusName);
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
}
