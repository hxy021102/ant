package com.mobian.service.impl.order;


import com.mobian.pageModel.MbOrder;

/**
 * Created by john on 16/10/30.
 */
public interface OrderState {

    ThreadLocal<MbOrder> order = new ThreadLocal();

    String prefix = "OD";
    /**
     * 获取状态
     * @return
     */
    String getStateName();

    /**
     * 某状态下处理操作
     * @param mbOrder
     */
    void handle(MbOrder mbOrder);

    /**
     * 下一个状态
     * @return
     */
    OrderState next(MbOrder mbOrder);
}
