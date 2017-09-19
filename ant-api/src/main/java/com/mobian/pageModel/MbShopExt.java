package com.mobian.pageModel;



@SuppressWarnings("serial")
public class MbShopExt extends MbShop {

	private static final long serialVersionUID = 5454155825314635342L;

	private Integer balanceAmount;

	private Integer balanceId;

	private Integer cashBalanceId;

	private Integer cashBalanceAmount;

	public Integer getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(Integer balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public Integer getBalanceId() {
		return balanceId;
	}

	public void setBalanceId(Integer balanceId) {
		this.balanceId = balanceId;
	}

	public Integer getCashBalanceId() {
		return cashBalanceId;
	}

	public void setCashBalanceId(Integer cashBalanceId) {
		this.cashBalanceId = cashBalanceId;
	}

	public Integer getCashBalanceAmount() {
		return cashBalanceAmount;
	}

	public void setCashBalanceAmount(Integer cashBalanceAmount) {
		this.cashBalanceAmount = cashBalanceAmount;
	}
}
