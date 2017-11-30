package com.bx.ant.service.impl.driverodershopstate;

import com.bx.ant.pageModel.DriverOrderShop;
import com.bx.ant.service.DriverOrderShopServiceI;
import com.bx.ant.service.DriverOrderShopState;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * error
 * Created by w9777 on 2017/11/6.
 */
@Service(value = "driverOrderShop50StateImpl")
public class DriverOrderShop50StateImpl implements DriverOrderShopState {

    @Resource(name = "driverOrderShop01StateImpl")
    private DriverOrderShopState driverOrderShopState01;

    @Resource
    private DriverOrderShopServiceI driverOrderShopSerivce;


    @Override
    public String getStateName() {
        return "50";
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
        //
        if ( (prefix  + "01").equals(driverOrderShop.getStatus())) {
            return driverOrderShopState01;
        }

        return null;
    }
}
