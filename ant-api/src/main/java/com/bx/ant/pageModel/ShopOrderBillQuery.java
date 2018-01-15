package com.bx.ant.pageModel;

import com.mobian.util.ConvertNameUtil;

import java.util.List;

@SuppressWarnings("serial")
public class ShopOrderBillQuery extends ShopOrderBill {
	private String statusName;
	private String reviewerName;
	private List<DeliverOrderShop> deliverOrderShopList;
	private Long[] deliverOrderIds;
	private String payWayName;

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

	public List<DeliverOrderShop> getDeliverOrderShopList() {
		return deliverOrderShopList;
	}

	public void setDeliverOrderShopList(List<DeliverOrderShop> deliverOrderShopList) {
		this.deliverOrderShopList = deliverOrderShopList;
	}

}
