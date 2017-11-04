package com.bx.ant.pageModel;

import com.mobian.util.ConvertNameUtil;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

/**
 * Created by w9777 on 2017/11/3.
 */
public class DriverOrderShopBillView extends DriverOrderShopBill {
    private String handleStatusName;
    private String handleLoginName;
    private String payWayName;

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

    public String getPayWayName() {
        return ConvertNameUtil.getString(super.getPayWay());
    }

    public void setPayWayName(String payWayName) {
        this.payWayName = payWayName;
    }
}
