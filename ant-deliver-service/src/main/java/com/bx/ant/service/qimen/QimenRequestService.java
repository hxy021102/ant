package com.bx.ant.service.qimen;

/**
 * Created by john on 17/11/30.
 */
public interface QimenRequestService {
    String QIM_02 = "QIM02";
    String QIM_01 = "QIM01";
    String QIM_03 = "QIM03";
    String QIM_04 = "QIM04";
    String QIM_05 = "QIM05";
    String QIM_06 = "QIM06";
    String QIM_07 = "QIM07";
    String QIM_08 = "QIM08";
    String QIM_09 = "QIM09";
    /**
     * 发货单确认接口
     */
    void updateDeliveryOrderConfirm();


    void updateOrderProcessReportRequest();
}
