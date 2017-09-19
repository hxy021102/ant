package com.mobian.pageModel;


/**
 * EasyUI tree模型
 * 
 * @author John
 * 
 */
@SuppressWarnings("serial")
public class ItemTree extends Tree {
	private String code;
	private Integer marketPrice;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(Integer marketPrice) {
		this.marketPrice = marketPrice;
	}

}
