package com.bx.ant.pageModel;

import com.mobian.util.ConvertNameUtil;

import java.util.Date;


@SuppressWarnings("serial")
public class DeliverOrderShopQuery extends DeliverOrderShop {
	private String shopName;
	private String statusName;
	private String[] statusList;
	private String shopPayStatusName;
	private Date endDate;

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

	public String getShopPayStatusName() {
		return ConvertNameUtil.getString(shopPayStatusName);
	}

	public void setShopPayStatusName(String shopPayStatusName) {
		this.shopPayStatusName = shopPayStatusName;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
