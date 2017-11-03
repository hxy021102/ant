package com.mobian.pageModel;

import com.mobian.util.ConvertNameUtil;

import java.util.Date;

/**
 * Created by w9777 on 2017/11/1.
 */
public class MbWithdrawLogView extends MbWithdrawLog {
    private String refTypeName;
    private String handleStatusName;
    private String handleLoginName;
    private Date addtimeBegin;
    private Date addtimeEnd;

    public String getRefTypeName() {
        return ConvertNameUtil.getString(super.getRefType());
    }

    public void setRefTypeName(String refTypeName) {
        this.refTypeName = refTypeName;
    }

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

    public Date getAddtimeBegin() {
        return addtimeBegin;
    }

    public void setAddtimeBegin(Date addtimeBegin) {
        this.addtimeBegin = addtimeBegin;
    }

    public Date getAddtimeEnd() {
        return addtimeEnd;
    }

    public void setAddtimeEnd(Date addtimeEnd) {
        this.addtimeEnd = addtimeEnd;
    }
}
