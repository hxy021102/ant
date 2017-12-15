package com.bx.ant.service.impl.driverodershopstate;

import com.bx.ant.pageModel.DriverOrderShop;
import com.bx.ant.service.DriverOrderShopServiceI;
import com.bx.ant.service.DriverOrderShopState;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 骑手已经取货
 * Created by w9777 on 2017/11/6.
 */
@Service(value = "driverOrderShop08StateImpl")
public class DriverOrderShop08StateImpl implements DriverOrderShopState {
    @Resource(name = "driverOrderShop50StateImpl")
    private DriverOrderShopState driverOrderShopState50;

    @Resource(name = "driverOrderShop10StateImpl")
    private DriverOrderShopState driverOrderShopState10;

    @Resource
    private DriverOrderShopServiceI driverOrderShopSerivce;

    @Override
    public String getStateName() {
        return "08";
    }

    @Override
    public void handle(DriverOrderShop driverOrderShop) {
        DriverOrderShop orderShop = new DriverOrderShop();
        orderShop.setId(driverOrderShop.getId());
        orderShop.setStatus(prefix + getStateName());
        driverOrderShopSerivce.edit(orderShop);
    }

    @Override
    public DriverOrderShopState next(DriverOrderShop driverOrderShop) {
        if ( (prefix  + "10").equals(driverOrderShop.getStatus())) {
            return driverOrderShopState10;
        }
        if ((prefix + "50").equals(driverOrderShop.getStatus())) {
            return driverOrderShopState50;
        }
        return null;
    }
}
