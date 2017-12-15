package com.bx.ant.service.impl.driverodershopstate;

import com.bx.ant.pageModel.DriverOrderShop;
import com.bx.ant.service.DeliverOrderShopServiceI;
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
    private DriverOrderShopServiceI driverOrderShopService;


    @Override
    public String getStateName() {
        return "30";
    }

    @Override
    public void handle(DriverOrderShop driverOrderShop) {
        //修改运单状态
        driverOrderShopService.editStatusByHql(driverOrderShop.getId(), "DHS02");

     }

    @Override
    public DriverOrderShopState next(DriverOrderShop driverOrderShop) {
        return null;
    }
}
