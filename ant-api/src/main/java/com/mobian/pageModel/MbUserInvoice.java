package com.mobian.pageModel;

import com.mobian.util.ConvertNameUtil;

import java.util.Date;

@SuppressWarnings("serial")
public class MbUserInvoice implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private Integer id;
	private Integer tenantId;
	private Date addtime;			
	private Date updatetime;			
	private Boolean isdeleted;
	private Integer userId;
	private String companyName;
	private String companyTfn;
	private String registerAddress;
	private String registerPhone;
	private String bankName;
	private String bankNumber;
	private String invoiceUse;

	private String invoiceUseName;

	public String getInvoiceUseName() {
		return ConvertNameUtil.getString(invoiceUse);
	}

	public void setInvoiceUseName(String invoiceUseName) {
		this.invoiceUseName = invoiceUseName;
	}

	public void setId(Integer value) {
		this.id = value;
	}
	
	public Integer getId() {
		return this.id;
	}

	
	public void setTenantId(Integer tenantId) {
		this.tenantId = tenantId;
	}
	
	public Integer getTenantId() {
		return this.tenantId;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	
	public Date getAddtime() {
		return this.addtime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	
	public Date getUpdatetime() {
		return this.updatetime;
	}
	public void setIsdeleted(Boolean isdeleted) {
		this.isdeleted = isdeleted;
	}
	
	public Boolean getIsdeleted() {
		return this.isdeleted;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public Integer getUserId() {
		return this.userId;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public String getCompanyName() {
		return this.companyName;
	}
	public void setCompanyTfn(String companyTfn) {
		this.companyTfn = companyTfn;
	}
	
	public String getCompanyTfn() {
		return this.companyTfn;
	}
	public void setRegisterAddress(String registerAddress) {
		this.registerAddress = registerAddress;
	}
	
	public String getRegisterAddress() {
		return this.registerAddress;
	}
	public void setRegisterPhone(String registerPhone) {
		this.registerPhone = registerPhone;
	}
	
	public String getRegisterPhone() {
		return this.registerPhone;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	public String getBankName() {
		return this.bankName;
	}
	public void setBankNumber(String bankNumber) {
		this.bankNumber = bankNumber;
	}
	
	public String getBankNumber() {
		return this.bankNumber;
	}
	public void setInvoiceUse(String invoiceUse) {
		this.invoiceUse = invoiceUse;
	}
	
	public String getInvoiceUse() {
		return this.invoiceUse;
	}

}
