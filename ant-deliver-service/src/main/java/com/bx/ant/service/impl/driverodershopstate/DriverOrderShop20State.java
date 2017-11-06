package com.bx.ant.service.impl.driverodershopstate;

import com.bx.ant.pageModel.DriverOrderShop;
import com.bx.ant.service.DriverOrderShopServiceI;
import com.bx.ant.service.DriverOrderShopState;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 已送达
 * Created by w9777 on 2017/11/6.
 */
@Service(value = "driverOrderShop20State")
public class DriverOrderShop20State implements DriverOrderShopState {

    @Resource(name = "driverOrderShop30State")
    private DriverOrderShopState driverOrderShopState30;

    @Resource
    private DriverOrderShopServiceI driverOrderShopSerivce;


    @Override
    public String getStateName() {
        return "20";
    }

    @Override
    public void handle(DriverOrderShop driverOrderShop) {
        DriverOrderShop orderShop = new DriverOrderShop();
        orderShop.setStatus(prefix + getStateName());
        driverOrderShopSerivce.edit(orderShop);

    }

    @Override
    public DriverOrderShopState next(DriverOrderShop driverOrderShop) {
        if ( (prefix  + "30").equals(driverOrderShop.getStatus())) {
            return driverOrderShopState30;
        }
        return null;
    }
}
