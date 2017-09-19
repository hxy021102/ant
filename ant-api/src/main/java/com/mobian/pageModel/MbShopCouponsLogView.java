package com.mobian.pageModel;

@SuppressWarnings("serial")
public class MbShopCouponsLogView extends MbShopCouponsLog {

	private String loginName;
	private String couponsName;

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getCouponsName() {
		return couponsName;
	}

	public void setCouponsName(String couponsName) {
		this.couponsName = couponsName;
	}
}
