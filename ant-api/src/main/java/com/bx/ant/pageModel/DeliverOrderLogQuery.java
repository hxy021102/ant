package com.bx.ant.pageModel;

import com.mobian.util.ConvertNameUtil;

@SuppressWarnings("serial")
public class DeliverOrderLogQuery extends DeliverOrderLog {
    private String logTypeName;
    private String loginName;

	public String getLogTypeName() {
		return ConvertNameUtil.getString(this.logTypeName);
	}

	public void setLogTypeName(String logTypeName) {
		this.logTypeName = logTypeName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
}
