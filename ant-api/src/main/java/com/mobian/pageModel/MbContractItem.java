package com.mobian.pageModel;

import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class MbContractItem implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private Integer id;
	private Integer tenantId;
	private Date addtime;			
	private Date updatetime;			
	private Boolean isdeleted;
	private Integer contractId;
	private Integer itemId;
	private Integer price;

	private String itemName;
	private String shopName;
	private Integer newPrice;

    private Integer[] contractIds;
	private List<MbContractItem> mbContractItemList;
	public List<MbContractItem> getMbContractItemList() {
		return mbContractItemList;
	}
	public void setMbContractItemList(List<MbContractItem> mbContractItemList) {
		this.mbContractItemList = mbContractItemList;
	}
	public Integer getNewPrice() {
		return newPrice;
	}

	public void setNewPrice(Integer newPrice) {
		this.newPrice = newPrice;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public Date getExpiryDateStart() {
		return expiryDateStart;
	}

	public void setExpiryDateStart(Date expiryDateStart) {
		this.expiryDateStart = expiryDateStart;
	}

	public Date getExpiryDateEnd() {
		return expiryDateEnd;
	}

	public void setExpiryDateEnd(Date expiryDateEnd) {
		this.expiryDateEnd = expiryDateEnd;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	private Date expiryDateStart;
	private Date expiryDateEnd;
	private String code;

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
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
	public void setContractId(Integer contractId) {
		this.contractId = contractId;
	}
	
	public Integer getContractId() {
		return this.contractId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	
	public Integer getItemId() {
		return this.itemId;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	
	public Integer getPrice() {
		return this.price;
	}

	public Integer[] getContractIds() {
		return contractIds;
	}

	public void setContractIds(Integer[] contractIds) {
		this.contractIds = contractIds;
	}
}
