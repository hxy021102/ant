package com.bx.ant.service;

import com.bx.ant.pageModel.DeliverOrder;

/**
 * Created by wanxp on 17-9-25.
 */
public interface DeliverOrderState {

    ThreadLocal<DeliverOrder> deliverOrder = new ThreadLocal();

    String ACTION = "action";

    String REJECT = "REJECT";


    String prefix = "DOS";
    /**
     * 获取状态
     * @return
     */
    String getStateName();

    /**
     * 某状态下处理操作
     * @param deliverOrder
     */
    void handle(DeliverOrder deliverOrder);

    /**
     * 下一个状态
     * @return
     */
    DeliverOrderState next(DeliverOrder DeliverOrder);
}
