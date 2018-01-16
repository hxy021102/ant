package com.bx.ant.pageModel;

import java.util.Date;

@SuppressWarnings("serial")
public class DriverOrderShopBill implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private Long id;
	private Integer tenantId;
	private Date addtime;			
	private Date updatetime;			
	private Boolean isdeleted;
	private Integer driverAccountId;
	private Integer shopId;
	private String handleStatus;
	private String handleRemark;
	private String handleLoginId;
	private Integer amount;
	private Date startDate;			
	private Date endDate;			
	private String payWay;
	private String userName;
	private Double amountElement;
	private String createTimeName;
	

	public void setId(Long value) {
		this.id = value;
	}
	
	public Long getId() {
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
	public void setDriverAccountId(Integer driverAccountId) {
		this.driverAccountId = driverAccountId;
	}
	
	public Integer getDriverAccountId() {
		return this.driverAccountId;
	}
	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}
	
	public Integer getShopId() {
		return this.shopId;
	}
	public void setHandleStatus(String handleStatus) {
		this.handleStatus = handleStatus;
	}
	
	public String getHandleStatus() {
		return this.handleStatus;
	}
	public void setHandleRemark(String handleRemark) {
		this.handleRemark = handleRemark;
	}
	
	public String getHandleRemark() {
		return this.handleRemark;
	}
	public void setHandleLoginId(String handleLoginId) {
		this.handleLoginId = handleLoginId;
	}
	
	public String getHandleLoginId() {
		return this.handleLoginId;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
	public Integer getAmount() {
		return this.amount;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public Date getStartDate() {
		return this.startDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public Date getEndDate() {
		return this.endDate;
	}
	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}
	
	public String getPayWay() {
		return this.payWay;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Double getAmountElement() {
		return amountElement;
	}

	public void setAmountElement(Double amountElement) {
		this.amountElement = amountElement;
	}

	public String getCreateTimeName() {
		return createTimeName;
	}

	public void setCreateTimeName(String createTimeName) {
		this.createTimeName = createTimeName;
	}
}
