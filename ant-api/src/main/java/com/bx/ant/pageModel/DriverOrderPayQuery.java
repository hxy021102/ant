package com.bx.ant.pageModel;


@SuppressWarnings("serial")
public class DriverOrderPayQuery extends DriverOrderPay {

	private static final long serialVersionUID = 5454155825314635342L;

	private Long[] driverOrderShopIds;
	private String[]  statusArray;

	public Long[] getDriverOrderShopIds() {
		return driverOrderShopIds;
	}

	public void setDriverOrderShopIds(Long[] driverOrderShopIds) {
		this.driverOrderShopIds = driverOrderShopIds;
	}

	public String[] getStatusArray() {
		return statusArray;
	}

	public void setStatusArray(String[] statusArray) {
		this.statusArray = statusArray;
	}
}
