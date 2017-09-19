package com.mobian.service;

/**
 * Created by john on 16/10/16.
 */
public interface TaskServiceI {
    /**
     * 发送消息
     */
    void sendMessage();
    /**
     * 发送消息  提醒司机回单
     */
    void remindDrivers();
    /**
     * 设置合同价
     */
    void setContractPrice();

    /**
     * 设置订单量
     */
    void setOrderQuantity();
    /**
     * 删除未支付的订单
     */
    void deleteUnPayOrder();

}
