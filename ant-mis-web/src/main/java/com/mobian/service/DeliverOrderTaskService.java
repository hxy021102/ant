package com.mobian.service;

import com.bx.ant.service.DeliverOrderAllocationServiceI;
import com.bx.ant.service.DeliverOrderServiceI;
import com.bx.ant.service.DeliverOrderShopServiceI;
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
    private DeliverOrderShopServiceI deliverOrderShopService;


    public void orderAllocation(){
        deliverOrderAllocationService.orderAllocation();
    }

    public void settleShopPay(){
        deliverOrderService.settleShopPay();
    }

    public void checkTimeOutOrder(){
        deliverOrderShopService.checkTimeOutOrder();
    }
}
