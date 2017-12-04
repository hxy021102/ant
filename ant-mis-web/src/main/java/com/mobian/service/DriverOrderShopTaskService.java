package com.mobian.service;

import com.bx.ant.service.DriverOrderShopAllocationServiceI;
import com.bx.ant.service.DriverOrderShopBillServiceI;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by w9777 on 2017/11/6.
 */
@Service
public class DriverOrderShopTaskService {
    @Resource
     private DriverOrderShopAllocationServiceI driverOrderShopAllocationService;

    @Resource
    private DriverOrderShopBillServiceI driverOrderShopBillService;

    public void orderAllocation(){
        driverOrderShopAllocationService.orderAllocation();
    }

    public void addPayOperation() {
        driverOrderShopBillService.addPayOperation();
    }
}
