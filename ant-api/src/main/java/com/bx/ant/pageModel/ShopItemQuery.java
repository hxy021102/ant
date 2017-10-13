package com.bx.ant.pageModel;

import com.bx.ant.pageModel.ShopItem;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;

@SuppressWarnings("serial")
public class ShopItemQuery extends ShopItem {
	private String name;
	private String quantityUnitName;
	private String url;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getQuantityUnitName() {
		return quantityUnitName;
	}

	public void setQuantityUnitName(String quantityUnitName) {
		this.quantityUnitName = quantityUnitName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
