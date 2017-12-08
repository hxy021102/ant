package com.bx.ant.service.impl.driverodershopstate;

import com.bx.ant.pageModel.DeliverOrder;
import com.bx.ant.pageModel.DeliverOrderShop;
import com.bx.ant.pageModel.DriverOrderShop;
import com.bx.ant.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 派送/发货
 * Created by w9777 on 2017/11/6.
 */
@Service(value = "driverOrderShop10StateImpl")
public class DriverOrderShop10StateImpl implements DriverOrderShopState {
    @Resource(name = "driverOrderShop50StateImpl")
    private DriverOrderShopState driverOrderShopState50;

    @Resource(name = "driverOrderShop15StateImpl")
    private DriverOrderShopState driverOrderShopState15;

    @Resource
    private DriverOrderShopServiceI driverOrderShopSerivce;

    @Autowired
    private DeliverOrderShopServiceI deliverOrderShopSerivce;

    @Autowired
    private DeliverOrderServiceI deliverOrderService;


    @Override
    public String getStateName() {
        return "10";
    }

    @Override
    public void handle(DriverOrderShop driverOrderShop) {
        DriverOrderShop orderShop = new DriverOrderShop();
        orderShop.setId(driverOrderShop.getId());
        orderShop.setStatus(prefix + getStateName());
        driverOrderShopSerivce.edit(orderShop);
        orderShop = driverOrderShopSerivce.get(orderShop.getId());

        //将门店运单状态更改为已发货
        DeliverOrder deliverOrder = new DeliverOrder();
        DeliverOrderShop deliverOrderShop = deliverOrderShopSerivce.get(orderShop.getDeliverOrderShopId());
        deliverOrder.setId(deliverOrderShop.getDeliverOrderId());
        deliverOrder.setStatus(DeliverOrderServiceI.STATUS_DELIVERING);
        deliverOrder.setShopId(deliverOrderShop.getShopId());
        deliverOrderService.transform(deliverOrder);
    }

    @Override
    public DriverOrderShopState next(DriverOrderShop driverOrderShop) {
        if ( (prefix  + "15").equals(driverOrderShop.getStatus())) {
            return driverOrderShopState15;
        }
        if ((prefix + "50").equals(driverOrderShop.getStatus())) {
            return driverOrderShopState50;
        }
        return null;
    }
}
