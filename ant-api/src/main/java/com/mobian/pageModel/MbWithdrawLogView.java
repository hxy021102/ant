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
    private String shopName;
    private Integer shopId;
    private String applyLoginName;

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

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getApplyLoginName() {
        return applyLoginName;
    }

    public void setApplyLoginName(String applyLoginName) {
        this.applyLoginName = applyLoginName;
    }
}
