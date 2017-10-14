package com.bx.ant.pageModel;

import com.mobian.pageModel.MbShop;
import com.mobian.pageModel.ShopDeliverApply;

import java.util.Date;

@SuppressWarnings("serial")
public class ShopDeliverApplyQuery extends ShopDeliverApply {
     private String ShopName;

	public String getShopName() {
		return ShopName;
	}

	public void setShopName(String shopName) {
		ShopName = shopName;
	}
}
