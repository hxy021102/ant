package com.bx.ant.service;

/**
 * Created by john on 17/10/11.
 */
public interface DeliverOrderYouzanServiceI {

    String WAIT_SELLER_SEND_GOODS = "WAIT_SELLER_SEND_GOODS"; // 等待卖家发货，即：买家已付款
    String WAIT_BUYER_CONFIRM_GOODS = "WAIT_BUYER_CONFIRM_GOODS"; // 等待买家确认收货，即：卖家已发货
    String TRADE_BUYER_SIGNED = "TRADE_BUYER_SIGNED"; // 买家已签收

    /**
     * 有赞订单对接
     */
    void youzanOrders();

    /**
     * 卖家确认收货
     * @param tid
     */
    void youzanOrderConfirm(String tid);

    /**
     * 自动结算
     */
    void settleYouzanBill();

}
