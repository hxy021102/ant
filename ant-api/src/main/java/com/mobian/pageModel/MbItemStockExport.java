package com.mobian.pageModel;

@SuppressWarnings("serial")
public class MbItemStockExport extends MbItemStock {

	private static final long serialVersionUID = 5454155825314635342L;

	private String averagePriceFormat;
	private String totalPriceFormat;

	public String getAveragePriceFormat() {
		return averagePriceFormat;
	}

	public void setAveragePriceFormat(String averagePriceFormat) {
		this.averagePriceFormat = averagePriceFormat;
	}

	public String getTotalPriceFormat() {
		return totalPriceFormat;
	}

	public void setTotalPriceFormat(String totalPriceFormat) {
		this.totalPriceFormat = totalPriceFormat;
	}
}
