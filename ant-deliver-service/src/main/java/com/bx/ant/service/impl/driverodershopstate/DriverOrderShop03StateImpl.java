package com.bx.ant.service.impl.driverodershopstate;

import com.bx.ant.pageModel.DriverOrderShop;
import com.bx.ant.service.DriverOrderShopServiceI;
import com.bx.ant.service.DriverOrderShopState;
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

    @Resource(name = "driverOrderShop03StateImpl")
    private DriverOrderShopState driverOrderShopState03;

    @Resource
    private DriverOrderShopServiceI driverOrderShopSerivce;

    @Autowired
    private DriverOrderShopServiceI driverOrderShopService;

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

        //增加骑手订单数量
        driverOrderShopService.addAllocationOrderRedis(driverOrderShop.getDriverAccountId());
    }

    @Override
    public DriverOrderShopState next(DriverOrderShop driverOrderShop) {
        if ( (prefix  + "05").equals(driverOrderShop.getStatus())) {
            return driverOrderShopState05;
        }
        if ( (prefix  + "03").equals(driverOrderShop.getStatus())) {
            return driverOrderShopState03;
        }
        return null;
    }
}
