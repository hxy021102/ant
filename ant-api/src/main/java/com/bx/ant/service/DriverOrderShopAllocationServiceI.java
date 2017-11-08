package com.bx.ant.service;

/**
 * Created by w9777 on 2017/11/6.
 */
public interface DriverOrderShopAllocationServiceI {

    /**
     * 分单函数
     */
    void orderAllocation();

    /**
     *  清除订单
     * @param driverOrderShopId
     * @return 删除次数
     */
    Integer editClearOrderAllocation(Long driverOrderShopId);
}
