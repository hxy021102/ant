package com.bx.ant.service.impl.driverodershopstate;

import com.bx.ant.pageModel.DriverOrderShop;
import com.bx.ant.service.DriverOrderShopServiceI;
import com.bx.ant.service.DriverOrderShopState;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Map;

/**
 * error
 * Created by w9777 on 2017/11/6.
 */
@Service(value = "driverOrderShop50StateImpl")
public class DriverOrderShop50StateImpl implements DriverOrderShopState {
    @Resource
    private Map<String, DriverOrderShopState> driverOrderShopStateFactory ;

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
        return driverOrderShopStateFactory.get("driverOrderShop" + driverOrderShop.getStatus().substring(4) + "Impl");
    }
}
