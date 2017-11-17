package com.bx.ant.pageModel;

import com.mobian.util.ConvertNameUtil;

@SuppressWarnings("serial")
public class DriverOrderPayView extends DriverOrderPay {

	private static final long serialVersionUID = 5454155825314635342L;

	private String userName;
    private String statusName;
    private String payWayName;
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getStatusName() {
		return ConvertNameUtil.getString(this.statusName);
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getPayWayName() {
		return ConvertNameUtil.getString(payWayName);
	}

	public void setPayWayName(String payWayName) {
		this.payWayName = payWayName;
	}
}
