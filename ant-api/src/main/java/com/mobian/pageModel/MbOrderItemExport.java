package com.mobian.pageModel;


import java.util.Date;

@SuppressWarnings("serial")
public class MbOrderItemExport extends MbOrderItem {

	private static final long serialVersionUID = 5454155825314635342L;
	/**
	 * 门店ID
	 */
	private Integer shopId;
	/**
	 * 门店名称
	 */
	private String shopName;

	/**
	 * 门店类型
	 */
	private String shopTypeName;
	/**
	 * 订单金额
	 */
	private Integer orderPrice;
	/**
	 * 退款总金额
	 */
	private Integer totalRefundAmount;
	/**
	 * 配送司机ID
	 */
	private String deliveryDriver;
	/**
	 * 配送司机名称
	 */
	private String deliveryDriverName;
	/**
	 * 订单状态
	 */
	private String status;
	/**
	 * 退回数量
	 */
	private Integer refundQuantity;
	/**
	 * 退回类型
	 */
	private String refundType;

	/**
	 * 商品名称
	 */
	private String itemName;
	/**
	 * 商品编码
	 */
	private String itemCode;

	/**
	 * 付款状态
	 */
	private String payStatus;

	/**
	 * 配送地址
	 */
	private String deliveryAddress;

	/**
	 * 渠道
	 */
	private String channel;

	/**
	 * 节点时间
	 */
	private Date nodeTime;

	/**
	 *发货时间
	 */
	private Date deliveryTime;

	public Date getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
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

	public Integer getTotalRefundAmount() {
		return totalRefundAmount;
	}

	public void setTotalRefundAmount(Integer totalRefundAmount) {
		this.totalRefundAmount = totalRefundAmount;
	}

	public String getDeliveryDriver() {
		return deliveryDriver;
	}

	public void setDeliveryDriver(String deliveryDriver) {
		this.deliveryDriver = deliveryDriver;
	}

	public String getDeliveryDriverName() {
		return deliveryDriverName;
	}

	public void setDeliveryDriverName(String deliveryDriverName) {
		this.deliveryDriverName = deliveryDriverName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getRefundQuantity() {
		return refundQuantity;
	}

	public void setRefundQuantity(Integer refundQuantity) {
		this.refundQuantity = refundQuantity;
	}

	public String getRefundType() {
		return refundType;
	}

	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Date getNodeTime() {
		return nodeTime;
	}

	public void setNodeTime(Date nodeTime) {
		this.nodeTime = nodeTime;
	}

	public String getShopTypeName() {
		return shopTypeName;
	}

	public void setShopTypeName(String shopTypeName) {
		this.shopTypeName = shopTypeName;
	}
}
