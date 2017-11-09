package com.bx.ant.service.impl.driverodershopstate;

import com.bx.ant.pageModel.DriverOrderShop;
import com.bx.ant.service.DriverOrderShopAllocationServiceI;
import com.bx.ant.service.DriverOrderShopServiceI;
import com.bx.ant.service.DriverOrderShopState;
import com.mobian.absx.F;
import com.mobian.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 已分配
 * Created by w9777 on 2017/11/6.
 */
@Service(value = "driverOrderShop03StateImpl")
public class DriverOrderShop03StateImpl implements DriverOrderShopState {

    @Resource(name = "driverOrderShop05StateImpl")
    private DriverOrderShopState driverOrderShopState05;

    @Resource
    private DriverOrderShopServiceI driverOrderShopSerivce;

    @Autowired
    private DriverOrderShopAllocationServiceI driverOrderShopAllocationService;


    @Override
    public String getStateName() {
        return "03";
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
        if ( (prefix  + "05").equals(driverOrderShop.getStatus())) {
            return driverOrderShopState05;
        }
        return null;
    }
}
