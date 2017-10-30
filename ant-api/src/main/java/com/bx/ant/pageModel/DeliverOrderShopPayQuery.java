package com.bx.ant.pageModel;

import com.mobian.util.ConvertNameUtil;

@SuppressWarnings("serial")
public class DeliverOrderShopPayQuery extends DeliverOrderShopPay{
	private String shopName;
	private String statusName;
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
