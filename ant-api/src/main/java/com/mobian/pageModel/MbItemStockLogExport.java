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

	private Integer costAmount;
    //导出显示元设置属性
	private double initPriceElement;
	private double initAmountElement;
	private double inPriceElement;
	private double inAmountElement;
	private double outPriceElement;
	private double outAmountElement;
	private double costPriceElement;
	private double costAmountElement;

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

	public Integer getCostAmount() {
		return costAmount;
	}

	public void setCostAmount(Integer costAmount) {
		this.costAmount = costAmount;
	}

	public double getInitPriceElement() {
		return initPriceElement;
	}

	public void setInitPriceElement(double initPriceElement) {
		this.initPriceElement = initPriceElement;
	}

	public double getInitAmountElement() {
		return initAmountElement;
	}

	public void setInitAmountElement(double initAmountElement) {
		this.initAmountElement = initAmountElement;
	}

	public double getInPriceElement() {
		return inPriceElement;
	}

	public void setInPriceElement(double inPriceElement) {
		this.inPriceElement = inPriceElement;
	}

	public double getInAmountElement() {
		return inAmountElement;
	}

	public void setInAmountElement(double inAmountElement) {
		this.inAmountElement = inAmountElement;
	}

	public double getOutPriceElement() {
		return outPriceElement;
	}

	public void setOutPriceElement(double outPriceElement) {
		this.outPriceElement = outPriceElement;
	}

	public double getOutAmountElement() {
		return outAmountElement;
	}

	public void setOutAmountElement(double outAmountElement) {
		this.outAmountElement = outAmountElement;
	}

	public double getCostPriceElement() {
		return costPriceElement;
	}

	public void setCostPriceElement(double costPriceElement) {
		this.costPriceElement = costPriceElement;
	}

	public double getCostAmountElement() {
		return costAmountElement;
	}

	public void setCostAmountElement(double costAmountElement) {
		this.costAmountElement = costAmountElement;
	}
}
