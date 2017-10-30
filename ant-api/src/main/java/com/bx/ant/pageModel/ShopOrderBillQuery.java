package com.bx.ant.pageModel;

import com.mobian.util.ConvertNameUtil;

import java.util.List;

@SuppressWarnings("serial")
public class ShopOrderBillQuery extends ShopOrderBill {
	private String shopName;
	private String statusName;
	private String reviewerName;
	private List<DeliverOrder> deliverOrderList;
	private Long[] deliverOrderIds;
	private String payWayName;
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

	public List<DeliverOrder> getDeliverOrderList() {
		return deliverOrderList;
	}

	public void setDeliverOrderList(List<DeliverOrder> deliverOrderList) {
		this.deliverOrderList = deliverOrderList;
	}

	public Long[] getDeliverOrderIds() {
		return deliverOrderIds;
	}

	public void setDeliverOrderIds(Long[] deliverOrderIds) {
		this.deliverOrderIds = deliverOrderIds;
	}

	public String getPayWayName() {
		return ConvertNameUtil.getString(this.payWayName);
	}

	public void setPayWayName(String payWayName) {
		this.payWayName = payWayName;
	}
}
