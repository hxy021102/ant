package com.mobian.pageModel;

@SuppressWarnings("serial")
public class MbItemStockLogExport extends MbItemStockLog {

	private static final long serialVersionUID = 5454155825314635342L;

	private String refTypeName;

	private String refId;

	private String summary;

	private Integer initPrice;

	private Integer initQuantity;

	private Integer initAmount;

	private Integer inQuantity;

	private Integer inAmount;

	private Integer outPrice;

	private Integer outQuantity;

	private Integer outAmount;

	private String itemName;

	private String itemCode;

	public String getRefTypeName() {
		return refTypeName;
	}

	public void setRefTypeName(String refTypeName) {
		this.refTypeName = refTypeName;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Integer getInitPrice() {
		return initPrice;
	}

	public void setInitPrice(Integer initPrice) {
		this.initPrice = initPrice;
	}

	public Integer getInitQuantity() {
		return initQuantity;
	}

	public void setInitQuantity(Integer initQuantity) {
		this.initQuantity = initQuantity;
	}

	public Integer getInQuantity() {
		return inQuantity;
	}

	public void setInQuantity(Integer inQuantity) {
		this.inQuantity = inQuantity;
	}

	public Integer getOutPrice() {
		return outPrice;
	}

	public void setOutPrice(Integer outPrice) {
		this.outPrice = outPrice;
	}

	public Integer getOutQuantity() {
		return outQuantity;
	}

	public void setOutQuantity(Integer outQuantity) {
		this.outQuantity = outQuantity;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	@Override
	public String getItemName() {
		return itemName;
	}

	@Override
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public Integer getInitAmount() {
		return initAmount;
	}

	public void setInitAmount(Integer initAmount) {
		this.initAmount = initAmount;
	}

	public Integer getInAmount() {
		return inAmount;
	}

	public void setInAmount(Integer inAmount) {
		this.inAmount = inAmount;
	}

	public Integer getOutAmount() {
		return outAmount;
	}

	public void setOutAmount(Integer outAmount) {
		this.outAmount = outAmount;
	}
}
