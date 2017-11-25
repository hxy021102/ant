package com.mobian.pageModel;

import com.mobian.util.ConvertNameUtil;

import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class MbOrder implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private Integer id;
	private Integer tenantId;
	private Date addtime;
	private Long days;
	private Date updatetime;
	private Boolean isdeleted;
	private Integer userId;
	private Integer totalPrice;
	private Integer totalRefundAmount;
	private String status;
	private String deliveryWay;
	private String deliveryStatus;
	private Date deliveryRequireTime;			
	private String deliveryAddress;
	private Integer deliveryRegion;
	private String payStatus;
	private String payWay;
	private Date payTime;			
	private String invoiceWay;
	private String contactPhone;
	private String contactPeople;
	private String userRemark;

	private Integer shopId;
	private String shopName;
	private Integer orderPrice;
	private Integer deliveryPrice;
	private String userNickName;
	private String invoiceStatus;

	private String invoiceStatusName;
	private String orderStatusName;
	private String deliveryStatusName;
	private String deliveryWayName;
	private String payStatusName;
	private String deliveryDriver;
	private String deliveryDriverName;
	private Integer deliveryCost;

	private Date deliveryTime;

	private String loginId;
	private Integer paymentId;
	private String remitter;
	private String remitterTime;
	private String remark;
	private String userDeliveryRemark;
	private String refId;
	private Integer deliveryWarehouseId;
	private String bankCode;
	private String payCode;
	//司机配送完成后备注属性,该数据不会影响TmbOrder表,只会将数据记录在TmbOrderLog内
	private String completeDeliveryRemark;
	//回桶,坏桶登记后确认备注
	private String confirmCallbackRemark;
	private String addLoginId;
	private Boolean usedByCoupons;

	private List<MbOrderItem> mbOrderItemList; //订单商品信息
	private MbOrderInvoice mbOrderInvoice; //订单发票信息
	private Date orderTimeBegin;//订单开始时间
	private Date orderTimeEnd;
	private Integer[] shopIds;//主店和其包含的分店ID


	private Date deliveryTimeBegin;//订单发货时间
	private Date deliveryTimeEnd;
    private String deliverOrderShopIds;
	public Date getDeliveryTimeBegin() {
		return deliveryTimeBegin;
	}

	public void setDeliveryTimeBegin(Date deliveryTimeBegin) {
		this.deliveryTimeBegin = deliveryTimeBegin;
	}

	public Date getDeliveryTimeEnd() {
		return deliveryTimeEnd;
	}

	public void setDeliveryTimeEnd(Date deliveryTimeEnd) {
		this.deliveryTimeEnd = deliveryTimeEnd;
	}

	public Date getOrderTimeBegin() {
		return orderTimeBegin;
	}

	public void setOrderTimeBegin(Date orderTimeBegin) {
		this.orderTimeBegin = orderTimeBegin;
	}

	public Date getOrderTimeEnd() {
		return orderTimeEnd;
	}

	public void setOrderTimeEnd(Date orderTimeEnd) {
		this.orderTimeEnd = orderTimeEnd;
	}

	public Long getDays() {
		return days;
	}

	public void setDays(Long days) {
		this.days = days;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getDeliveryStatusName() {
		return ConvertNameUtil.getString(deliveryStatus);
	}

	public void setDeliveryStatusName(String deliveryStatusName) {
		this.deliveryStatusName = deliveryStatusName;
	}

	public String getOrderStatusName() {
		return ConvertNameUtil.getString(status);
	}

	public void setOrderStatusName(String orderStatusName) {
		this.orderStatusName = orderStatusName;
	}

	public String getPayStatusName() {
		return ConvertNameUtil.getString(payStatus);
	}

	public void setPayStatusName(String payStatusName) {
		this.payStatusName = payStatusName;
	}

	public String getDeliveryWayName() {
		return ConvertNameUtil.getString(deliveryWay);
	}

	public void setDeliveryWayName(String deliveryWayName) {
		this.deliveryWayName = deliveryWayName;
	}

	public String getInvoiceStatusName() {
		return ConvertNameUtil.getString(invoiceStatus);
	}

	public void setInvoiceStatusName(String invoiceStatusName) {
		this.invoiceStatusName = invoiceStatusName;
	}

	public String getInvoiceWayName() {
		return ConvertNameUtil.getString(invoiceWay);
	}

    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Integer getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Integer orderPrice) {
        this.orderPrice = orderPrice;
    }

    public Integer getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(Integer deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
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
	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	public Integer getTotalPrice() {
		return this.totalPrice;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return this.status;
	}
	public void setDeliveryWay(String deliveryWay) {
		this.deliveryWay = deliveryWay;
	}
	
	public String getDeliveryWay() {
		return this.deliveryWay;
	}
	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}
	
	public String getDeliveryStatus() {
		return this.deliveryStatus;
	}
	public void setDeliveryRequireTime(Date deliveryRequireTime) {
		this.deliveryRequireTime = deliveryRequireTime;
	}
	
	public Date getDeliveryRequireTime() {
		return this.deliveryRequireTime;
	}
	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	
	public String getDeliveryAddress() {
		return this.deliveryAddress;
	}
	public void setDeliveryRegion(Integer deliveryRegion) {
		this.deliveryRegion = deliveryRegion;
	}
	
	public Integer getDeliveryRegion() {
		return this.deliveryRegion;
	}
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	
	public String getPayStatus() {
		return this.payStatus;
	}
	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}
	
	public String getPayWay() {
		return this.payWay;
	}
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	
	public Date getPayTime() {
		return this.payTime;
	}
	public void setInvoiceWay(String invoiceWay) {
		this.invoiceWay = invoiceWay;
	}
	
	public String getInvoiceWay() {
		return this.invoiceWay;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	
	public String getContactPhone() {
		return this.contactPhone;
	}
	public void setContactPeople(String contactPeople) {
		this.contactPeople = contactPeople;
	}
	
	public String getContactPeople() {
		return this.contactPeople;
	}
	public void setUserRemark(String userRemark) {
		this.userRemark = userRemark;
	}
	
	public String getUserRemark() {
		return this.userRemark;
	}

	public MbOrderInvoice getMbOrderInvoice() {
		return mbOrderInvoice;
	}

	public void setMbOrderInvoice(MbOrderInvoice mbOrderInvoice) {
		this.mbOrderInvoice = mbOrderInvoice;
	}

	public List<MbOrderItem> getMbOrderItemList() {
		return mbOrderItemList;
	}

	public void setMbOrderItemList(List<MbOrderItem> mbOrderItemList) {
		this.mbOrderItemList = mbOrderItemList;
	}

	public Integer getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
	}

	public String getRemitter() {
		return remitter;
	}

	public void setRemitter(String remitter) {
		this.remitter = remitter;
	}

	public String getRemitterTime() {
		return remitterTime;
	}

	public void setRemitterTime(String remitterTime) {
		this.remitterTime = remitterTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public Integer getDeliveryWarehouseId() {
		return deliveryWarehouseId;
	}

	public void setDeliveryWarehouseId(Integer deliveryWarehouseId) {
		this.deliveryWarehouseId = deliveryWarehouseId;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getPayCode() {
		return payCode;
	}

	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}

	public String getUserDeliveryRemark() {
		return userDeliveryRemark;
	}

	public void setUserDeliveryRemark(String userDeliveryRemark) {
		this.userDeliveryRemark = userDeliveryRemark;
	}

	public String getCompleteDeliveryRemark() {
		return completeDeliveryRemark;
	}

	public void setCompleteDeliveryRemark(String completeDeliveryRemark) {
		this.completeDeliveryRemark = completeDeliveryRemark;
	}

	public String getConfirmCallbackRemark() {
		return confirmCallbackRemark;
	}

	public void setConfirmCallbackRemark(String confirmCallbackRemark) {
		this.confirmCallbackRemark = confirmCallbackRemark;
	}

	public String getDeliveryDriver() {
		return deliveryDriver;
	}

	public void setDeliveryDriver(String deliveryDriver) {
		this.deliveryDriver = deliveryDriver;
	}

	public Date getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public String getDeliveryDriverName() {
		return deliveryDriverName;
	}

	public void setDeliveryDriverName(String deliveryDriverName) {
		this.deliveryDriverName = deliveryDriverName;
	}

	public Integer getTotalRefundAmount() {
		return totalRefundAmount;
	}

	public void setTotalRefundAmount(Integer totalRefundAmount) {
		this.totalRefundAmount = totalRefundAmount;
	}

	public String getAddLoginId() {
		return addLoginId;
	}

	public void setAddLoginId(String addLoginId) {
		this.addLoginId = addLoginId;
	}

	public Boolean getUsedByCoupons() {
		return usedByCoupons;
	}

	public void setUsedByCoupons(Boolean usedByCoupons) {
		this.usedByCoupons = usedByCoupons;
	}
	public Integer[] getShopIds() {
		return shopIds;
	}

	public void setShopIds(Integer[] shopIds) {
		this.shopIds = shopIds;
	}

	public Integer getDeliveryCost() {
		return deliveryCost;
	}

	public void setDeliveryCost(Integer deliveryCost) {
		this.deliveryCost = deliveryCost;
	}

	public String getDeliverOrderShopIds() {
		return deliverOrderShopIds;
	}

	public void setDeliverOrderShopIds(String deliverOrderShopIds) {
		this.deliverOrderShopIds = deliverOrderShopIds;
	}
}
