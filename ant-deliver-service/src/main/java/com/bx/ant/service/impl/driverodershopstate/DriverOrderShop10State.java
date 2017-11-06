package com.bx.ant.service.impl.driverodershopstate;

import com.bx.ant.pageModel.DriverOrderShop;
import com.bx.ant.service.DriverOrderShopServiceI;
import com.bx.ant.service.DriverOrderShopState;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 派送/发货
 * Created by w9777 on 2017/11/6.
 */
@Service(value = "driverOrderShop10State")
public class DriverOrderShop10State implements DriverOrderShopState {

    @Resource(name = "driverOrderShop20State")
    private DriverOrderShopState driverOrderShopState20;

    @Resource
    private DriverOrderShopServiceI driverOrderShopSerivce;


    @Override
    public String getStateName() {
        return "10";
    }

    @Override
    public void handle(DriverOrderShop driverOrderShop) {
        DriverOrderShop orderShop = new DriverOrderShop();
        orderShop.setStatus(prefix + getStateName());
        driverOrderShopSerivce.edit(orderShop);
    }

    @Override
    public DriverOrderShopState next(DriverOrderShop driverOrderShop) {
        if ( (prefix  + "20").equals(driverOrderShop.getStatus())) {
            return driverOrderShopState20;
        }
        return null;
    }
}
