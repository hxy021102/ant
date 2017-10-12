package com.mobian.service;

import com.bx.ant.service.DeliverOrderAllocationServiceI;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by john on 17/10/12.
 */
@Service
public class DeliverOrderTaskService {
    @Resource
    private DeliverOrderAllocationServiceI deliverOrderAllocationService;

    public void updateOrderOwnerShopId(){
        deliverOrderAllocationService.updateOrderOwnerShopId();
    }
}
