package com.bx.ant.service;

import com.bx.ant.pageModel.DriverOrderShop;

/**
 * Created by w9777 on 2017/11/6.
 */
public interface DriverOrderShopState {
    ThreadLocal<DriverOrderShop> driverOrderShop = new ThreadLocal();
    String prefix = "DDOS";

    String getStateName();

    void handle(DriverOrderShop driverOrderShop);

    DriverOrderShopState next(DriverOrderShop driverOrderShop);
}
