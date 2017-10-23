package com.bx.ant.pageModel;

import com.mobian.util.ConvertNameUtil;

import java.util.List;


@SuppressWarnings("serial")
public class DeliverOrderShopQuery extends DeliverOrderShop {
	private String shopName;
	private String statusName;
	private String[] statusList;

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getStatusName() {
		return ConvertNameUtil.getString(this.statusName);
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String[] getStatusList() {
		return statusList;
	}

	public void setStatusList(String[] statusList) {
		this.statusList = statusList;
	}
}
