package com.bx.ant.pageModel;

import com.mobian.pageModel.MbShop;
import com.mobian.util.ConvertNameUtil;

import java.math.BigDecimal;
import java.util.Date;

@SuppressWarnings("serial")
public class ShopDeliverApply implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private Integer id;
	private Integer tenantId;
	private Date addtime;			
	private Date updatetime;			
	private Boolean isdeleted;
	private Integer shopId;
	private String deliveryWay;
	private Boolean online;
	private Boolean frozen;
	private String result;
	private String status;
	private Integer accountId;
	private String statusName;

	private MbShop mbShop;

	private BigDecimal maxDeliveryDistance;
	private Boolean smsRemind;//短信通知
	private Boolean uploadRequired; //上传回单
	private String  deliveryType; //派单类型
	private Integer freight; // 运费统一配置：分/件

	private BigDecimal distance; // 运单和门店之间的距离
	private Boolean transferAuth;
	private Integer[] shopIds;

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
	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}
	
	public Integer getShopId() {
		return this.shopId;
	}
	public void setDeliveryWay(String deliveryWay) {
		this.deliveryWay = deliveryWay;
	}
	
	public String getDeliveryWay() {
		return this.deliveryWay;
	}
	public void setOnline(Boolean online) {
		this.online = online;
	}
	
	public Boolean getOnline() {
		return this.online;
	}

	public Boolean getFrozen() {
		return frozen;
	}

	public void setFrozen(Boolean frozen) {
		this.frozen = frozen;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	public String getResult() {
		return this.result;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return this.status;
	}
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	
	public Integer getAccountId() {
		return this.accountId;
	}

	public MbShop getMbShop() {
		return mbShop;
	}

	public void setMbShop(MbShop mbShop) {
		this.mbShop = mbShop;
	}

	public String getStatusName() {
		return ConvertNameUtil.getString(this.status);
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public BigDecimal getMaxDeliveryDistance() {
		return maxDeliveryDistance;
	}

	public void setMaxDeliveryDistance(BigDecimal maxDeliveryDistance) {
		this.maxDeliveryDistance = maxDeliveryDistance;
	}

	public Boolean getSmsRemind() {
		return smsRemind;
	}

	public void setSmsRemind(Boolean smsRemind) {
		this.smsRemind = smsRemind;
	}

	public Boolean getUploadRequired() {
		return uploadRequired;
	}

	public void setUploadRequired(Boolean uploadRequired) {
		this.uploadRequired = uploadRequired;
	}

	public String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	public BigDecimal getDistance() {
		return distance;
	}

	public void setDistance(BigDecimal distance) {
		this.distance = distance;
	}

	public Integer getFreight() {
		return freight;
	}

	public void setFreight(Integer freight) {
		this.freight = freight;
	}

	public Boolean getTransferAuth() {
		return transferAuth;
	}

	public void setTransferAuth(Boolean transferAuth) {
		this.transferAuth = transferAuth;
	}

	public Integer[] getShopIds() {
		return shopIds;
	}

	public void setShopIds(Integer[] shopIds) {
		this.shopIds = shopIds;
	}
}
