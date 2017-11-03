package com.mobian.pageModel;

import com.mobian.util.ConvertNameUtil;

import java.util.Date;

@SuppressWarnings("serial")
public class MbSupplierContractClauseQuery extends MbSupplierContractClause{

	private String clauseName;

	public String getClauseName() {
		return ConvertNameUtil.getString(this.clauseName);
	}

	public void setClauseName(String clauseName) {
		this.clauseName = clauseName;
	}
}
