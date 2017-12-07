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
@Service(value = "driverOrderShop20StateImpl")
public class DriverOrderShop20StateImpl implements DriverOrderShopState {
    @Resource(name = "driverOrderShop50StateImpl")
    private DriverOrderShopState driverOrderShopState50;

    @Resource(name = "driverOrderShop30StateImpl")
    private DriverOrderShopState driverOrderShopState30;

    @Resource
    private DriverOrderShopServiceI driverOrderShopService;


    @Override
    public String getStateName() {
        return "20";
    }

    @Override
    public void handle(DriverOrderShop driverOrderShop) {
        DriverOrderShop orderShop = new DriverOrderShop();
        orderShop.setId(driverOrderShop.getId());
        orderShop.setStatus(prefix + getStateName());

        driverOrderShopService.edit(orderShop);
    }

    @Override
    public DriverOrderShopState next(DriverOrderShop driverOrderShop) {
        if ( (prefix  + "30").equals(driverOrderShop.getStatus())) {
            return driverOrderShopState30;
        }
        if ((prefix + "50").equals(driverOrderShop.getStatus())) {
            return driverOrderShopState50;
        }
        return null;
    }
}
