package com.bx.ant.service.impl.driverodershopstate;

import com.bx.ant.pageModel.DriverOrderShop;
import com.bx.ant.service.DriverOrderShopServiceI;
import com.bx.ant.service.DriverOrderShopState;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 结算
 * Created by w9777 on 2017/11/6.
 */
@Service(value = "driverOrderShop30StateImpl")
public class DriverOrderShop30StateImpl implements DriverOrderShopState {



    @Resource
    private DriverOrderShopServiceI driverOrderShopSerivce;


    @Override
    public String getStateName() {
        return "30";
    }

    @Override
    public void handle(DriverOrderShop driverOrderShop) {
        DriverOrderShop orderShop = new DriverOrderShop();
        orderShop.setStatus(prefix + getStateName());
        driverOrderShopSerivce.edit(orderShop);
     }

    @Override
    public DriverOrderShopState next(DriverOrderShop driverOrderShop) {
        return null;
    }
}
