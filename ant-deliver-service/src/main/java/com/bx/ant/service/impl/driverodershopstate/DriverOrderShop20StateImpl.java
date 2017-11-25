package com.bx.ant.service.impl.driverodershopstate;

import com.bx.ant.pageModel.DeliverOrder;
import com.bx.ant.pageModel.DeliverOrderShop;
import com.bx.ant.pageModel.DriverOrderShop;
import com.bx.ant.service.DeliverOrderServiceI;
import com.bx.ant.service.DeliverOrderShopServiceI;
import com.bx.ant.service.DriverOrderShopServiceI;
import com.bx.ant.service.DriverOrderShopState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 已送达
 * Created by w9777 on 2017/11/6.
 */
@Service(value = "driverOrderShop20StateImpl")
public class DriverOrderShop20StateImpl implements DriverOrderShopState {

    @Resource(name = "driverOrderShop30StateImpl")
    private DriverOrderShopState driverOrderShopState30;

    @Resource
    private DriverOrderShopServiceI driverOrderShopSerivce;

    @Autowired
    private DeliverOrderServiceI deliverOrderService;

    @Autowired
    private DeliverOrderShopServiceI deliverOrderShopSerivce;


    @Override
    public String getStateName() {
        return "20";
    }

    @Override
    public void handle(DriverOrderShop driverOrderShop) {
        DriverOrderShop orderShop = new DriverOrderShop();
        orderShop.setId(driverOrderShop.getId());
        orderShop.setStatus(prefix + getStateName());
        orderShop.setCompleteImages(driverOrderShop.getCompleteImages());
        orderShop.setCompleteRemark(driverOrderShop.getCompleteRemark());


        driverOrderShopSerivce.edit(orderShop);

        // 将门店运单状态更改为配送完成
        DeliverOrder deliverOrder = new DeliverOrder();
        DeliverOrderShop deliverOrderShop = deliverOrderShopSerivce.get(orderShop.getDeliverOrderShopId());
        deliverOrder.setId(deliverOrderShop.getDeliverOrderId());
        deliverOrder.setStatus(DeliverOrderServiceI.STATUS_DRIVER_DELIVERING);
        deliverOrder.setShopId(deliverOrderShop.getShopId());
        deliverOrderService.transform(deliverOrder);

    }

    @Override
    public DriverOrderShopState next(DriverOrderShop driverOrderShop) {
        if ( (prefix  + "30").equals(driverOrderShop.getStatus())) {
            return driverOrderShopState30;
        }
        return null;
    }
}
