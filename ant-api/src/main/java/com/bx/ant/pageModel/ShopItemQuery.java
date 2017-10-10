package com.bx.ant.pageModel;

import com.mobian.pageModel.ShopItem;

import java.util.Date;

@SuppressWarnings("serial")
public class ShopItemQuery extends ShopItem {
	private String itemName;

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
}
