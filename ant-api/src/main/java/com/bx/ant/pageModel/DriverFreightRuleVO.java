package com.bx.ant.pageModel;

import com.mobian.util.ConvertNameUtil;

/**
 * Created by w9777 on 2017/11/10.
 */
public class DriverFreightRuleVO extends DriverFreightRule {
    private String regionPath;
    private String loginName;
    private String typeName;


    public String getRegionPath() {
        return regionPath;
    }

    public void setRegionPath(String regionPath) {
        this.regionPath = regionPath;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getTypeName() {
        return ConvertNameUtil.getString(super.getType());
    }
}
