package com.mobian.pageModel;

@SuppressWarnings("serial")
public class MbSupplierStockInItemExport extends MbSupplierStockInItem {

	private static final long serialVersionUID = 5454155825314635342L;


	private String itemCode;

	private String itemName;

	private Integer rate;

	private String orderId;

	private String contractCode;

	private String supplierName;


	private String payStatusName;

	private String invoiceStatusName;

	private String warehouseName;

	private String driverLoginId;

	private String driverName;

	private String invoiceNo;

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Integer getRate() {
		return rate;
	}

	public void setRate(Integer rate) {
		this.rate = rate;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getPayStatusName() {
		return payStatusName;
	}

	public void setPayStatusName(String payStatusName) {
		this.payStatusName = payStatusName;
	}

	public String getInvoiceStatusName() {
		return invoiceStatusName;
	}

	public void setInvoiceStatusName(String invoiceStatusName) {
		this.invoiceStatusName = invoiceStatusName;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public String getDriverLoginId() {
		return driverLoginId;
	}

	public void setDriverLoginId(String driverLoginId) {
		this.driverLoginId = driverLoginId;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
}
