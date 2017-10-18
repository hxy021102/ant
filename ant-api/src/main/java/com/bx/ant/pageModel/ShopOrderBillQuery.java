package com.bx.ant.pageModel;

import com.mobian.util.ConvertNameUtil;

@SuppressWarnings("serial")
public class ShopOrderBillQuery extends ShopOrderBill {
	private String shopName;
	private String statusName;
	private String reviewerName;

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

	public String getReviewerName() {
		return reviewerName;
	}

	public void setReviewerName(String reviewerName) {
		this.reviewerName = reviewerName;
	}
}
