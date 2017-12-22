package com.bx.ant.service.qimen;

import com.bx.ant.pageModel.DeliverOrder;

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
    String QIM_10 = "QIM10";
    String QIM_12 = "QIM12";


    String JYCK = "JYCK";//一般交易出库单
    /**
     * 发货单确认接口
     */
    void updateDeliveryOrderConfirm(DeliverOrder deliverOrder);

    /**
     * 修改订单处理流水
     * @param status
     * @param deliverOrder
     */
    void updateOrderProcessReportRequest(String status,DeliverOrder deliverOrder);
}
