package com.bx.ant.pageModel;

import com.mobian.util.ConvertNameUtil;

/**
 * Created by w9777 on 2017/11/3.
 */
public class DriverAccountView extends DriverAccount {
    private String handleStatusName;
    private String handleLoginName;
    private String sexName;
    private String typeName;


    public String getHandleStatusName() {
        return ConvertNameUtil.getString(super.getHandleStatus());
    }

    public void setHandleStatusName(String handleStatusName) {
        this.handleStatusName = handleStatusName;
    }

    public String getHandleLoginName() {
        return handleLoginName;
    }

    public void setHandleLoginName(String handleLoginName) {
        this.handleLoginName = handleLoginName;
    }

    public String getSexName() {
        return ConvertNameUtil.getString(super.getSex());
    }

    public void setSexName(String sexName) {
        this.sexName = sexName;
    }

    public String getTypeName() {
        return ConvertNameUtil.getString(super.getType());
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
