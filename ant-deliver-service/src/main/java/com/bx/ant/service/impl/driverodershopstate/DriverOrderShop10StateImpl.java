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

    @Resource(name = "driverOrderShop20StateImpl")
    private DriverOrderShopState driverOrderShopState20;

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

        //将门店运单状态更改为已发货
        DeliverOrder deliverOrder = new DeliverOrder();
        DeliverOrderShop deliverOrderShop = deliverOrderShopSerivce.get(driverOrderShop.getDeliverOrderShopId());

        deliverOrder.setId(deliverOrderShop.getDeliverOrderId());
        deliverOrder.setStatus(DeliverOrderServiceI.STATUS_DELIVERING);
        deliverOrder.setShopId(deliverOrderShop.getShopId());
        deliverOrderService.transform(deliverOrder);
    }

    @Override
    public DriverOrderShopState next(DriverOrderShop driverOrderShop) {
        if ( (prefix  + "20").equals(driverOrderShop.getStatus())) {
            return driverOrderShopState20;
        }
        return null;
    }
}
