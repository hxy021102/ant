package com.mobian.pageModel;

@SuppressWarnings("serial")
public class MbShopCouponsView extends MbShopCoupons {

	private static final long serialVersionUID = 5454155825314635342L;

	private String couponsName;
	private String shopName;
	private String loginName;

	public String getCouponsName() {
		return couponsName;
	}

	public void setCouponsName(String couponsName) {
		this.couponsName = couponsName;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}


	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

}
