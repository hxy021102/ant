package com.bx.ant.service.impl.driverodershopstate;

import com.bx.ant.pageModel.DriverOrderShop;
import com.bx.ant.service.DriverOrderShopServiceI;
import com.bx.ant.service.DriverOrderShopState;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *  添加
 * Created by w9777 on 2017/11/6.
 */
@Service(value = "driverOrderShop01StateImpl")
public class DriverOrderShop01StateImpl implements DriverOrderShopState {

    @Resource(name = "driverOrderShop05StateImpl")
    private DriverOrderShopState driverOrderShopState05;

    @Resource
    private DriverOrderShopServiceI driverOrderShopSerivce;


    @Override
    public String getStateName() {
        return "01";
    }

    @Override
    public void handle(DriverOrderShop driverOrderShop) {
        driverOrderShop.setStatus(prefix + getStateName());
        driverOrderShop.setPayStatus(DriverOrderShopServiceI.PAY_STATUS_NOT_PAY);
        driverOrderShopSerivce.add(driverOrderShop);
    }

    @Override
    public DriverOrderShopState next(DriverOrderShop driverOrderShop) {
        if ( (prefix  + "05").equals(driverOrderShop.getStatus())) {
            return driverOrderShopState05;
        }
        return null;
    }
}
