package com.bx.ant.pageModel;


@SuppressWarnings("serial")
public class SupplierView extends Supplier{
     private Integer balance;  //余额
     private Integer bond;     //保证金
     private Integer credit;   //授信

	public Integer getBalance() {
		return balance;
	}

	public void setBalance(Integer balance) {
		this.balance = balance;
	}

	public Integer getBond() {
		return bond;
	}

	public void setBond(Integer bond) {
		this.bond = bond;
	}

	public Integer getCredit() {
		return credit;
	}

	public void setCredit(Integer credit) {
		this.credit = credit;
	}
}
