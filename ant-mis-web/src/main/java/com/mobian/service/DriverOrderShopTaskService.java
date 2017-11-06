package com.mobian.service;

import com.bx.ant.service.DriverOrderShopAllocationServiceI;
import com.bx.ant.service.DriverOrderShopServiceI;

import javax.annotation.Resource;

/**
 * Created by w9777 on 2017/11/6.
 */
public class DriverOrderShopTaskService {
    @Resource
     private DriverOrderShopAllocationServiceI driverOrderShopAllocationService;

    public void orderAllocation(){
        driverOrderShopAllocationService.orderAllocation();
    }
}
