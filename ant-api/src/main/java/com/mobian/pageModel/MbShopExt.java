package com.mobian.pageModel;



@SuppressWarnings("serial")
public class MbShopExt extends MbShop {

	private static final long serialVersionUID = 5454155825314635342L;

	private Integer balanceAmount;

	private Integer balanceId;

	private Integer cashBalanceId;

	private Integer cashBalanceAmount;
	//门店欠款
	private Integer debt;
	private Integer totalDebt;
	//派单
	private Integer deliver;
	private String salesLoginName;

	private Double balanceAmountD;
	private Double debtD;
	private Double totalDebtD;
	private Double cashBalanceAmountD;

	public Integer getTotalDebt() {
		return totalDebt;
	}

	public void setTotalDebt(Integer totalDebt) {
		this.totalDebt = totalDebt;
	}

	public Integer getDebt() {
		return debt;
	}

	public void setDebt(Integer debt) {
		this.debt = debt;
	}

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

	public String getSalesLoginName() {
		return salesLoginName;
	}

	public void setSalesLoginName(String salesLoginName) {
		this.salesLoginName = salesLoginName;
	}

	public Integer getDeliver() {
		return deliver;
	}

	public void setDeliver(Integer deliver) {
		this.deliver = deliver;
	}

	public Double getBalanceAmountD() {
		return balanceAmountD;
	}

	public void setBalanceAmountD(Double balanceAmountD) {
		this.balanceAmountD = balanceAmountD;
	}

	public Double getDebtD() {
		return debtD;
	}

	public void setDebtD(Double debtD) {
		this.debtD = debtD;
	}

	public Double getTotalDebtD() {
		return totalDebtD;
	}

	public void setTotalDebtD(Double totalDebtD) {
		this.totalDebtD = totalDebtD;
	}

	public Double getCashBalanceAmountD() {
		return cashBalanceAmountD;
	}

	public void setCashBalanceAmountD(Double cashBalanceAmountD) {
		this.cashBalanceAmountD = cashBalanceAmountD;
	}
}
