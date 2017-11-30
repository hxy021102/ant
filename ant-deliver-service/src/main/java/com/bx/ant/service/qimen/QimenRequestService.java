package com.bx.ant.service.qimen;

/**
 * Created by john on 17/11/30.
 */
public interface QimenRequestService {
    String QIM_02 = "QIM02";
    String QIM_01 = "QIM_01";
    String QIM_03 = "QIM_03";
    String QIM_04 = "QIM_04";
    String QIM_05 = "QIM_05";
    /**
     * 发货单确认接口
     */
    void updateDeliveryOrderConfirm();
}
