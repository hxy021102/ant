package com.mobian.service;

import com.bx.ant.service.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by john on 17/10/12.
 */
@Service
public class DeliverOrderTaskService {
    @Resource
    private DeliverOrderAllocationServiceI deliverOrderAllocationService;

    @Resource
    private DeliverOrderServiceI deliverOrderService;

    @Resource
    private DeliverOrderYouzanServiceI deliverOrderYouzanService;

    @Resource
    private DeliverOrderShopServiceI deliverOrderShopService;

    @Resource
    private DriverOrderShopAllocationServiceI driverOrderShopAllocationService;


    public void orderAllocation(){
        deliverOrderAllocationService.orderAllocation();
    }

    public void settleShopPay(){
        deliverOrderShopService.settleShopPay();
    }

    public void checkTimeOutOrder(){
        deliverOrderShopService.checkTimeOutOrder();
    }

    public void youzanOrders(){
        deliverOrderYouzanService.youzanOrders();
    }

    public void driverOrderShopAllocation() {
        driverOrderShopAllocationService.orderAllocation();
    }


}
