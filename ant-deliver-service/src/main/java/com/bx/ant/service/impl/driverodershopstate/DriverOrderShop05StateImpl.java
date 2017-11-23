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
 * 接单
 * Created by w9777 on 2017/11/6.
 */
@Service(value = "driverOrderShop05StateImpl")
public class DriverOrderShop05StateImpl implements DriverOrderShopState {

    @Resource(name = "driverOrderShop10StateImpl")
    private DriverOrderShopState driverOrderShopState10;

    @Resource
    private DriverOrderShopServiceI driverOrderShopSerivce;

    @Autowired
    private DriverOrderShopAllocationServiceI driverOrderShopAllocationService;


    @Override
    public String getStateName() {
        return "05";
    }

    @Override
    public void handle(DriverOrderShop driverOrderShop) {
        //1. 绑定骑手并编辑状态
        if (F.empty(driverOrderShop.getDriverAccountId())) {
            throw new ServiceException("DriverOrderShopState05状态缺少必要数据:driverOrderShop.driverAccountId");
        }
        DriverOrderShop orderShop = new DriverOrderShop();
        orderShop.setId(driverOrderShop.getId());
        orderShop.setStatus(prefix + getStateName());
        orderShop.setDriverAccountId(driverOrderShop.getDriverAccountId());
        driverOrderShopSerivce.edit(orderShop);

        //2. 删除redis中所有订单记录

       driverOrderShopAllocationService.editClearOrderAllocation(driverOrderShop.getId());




    }

    @Override
    public DriverOrderShopState next(DriverOrderShop driverOrderShop) {
        if ( (prefix  + "10").equals(driverOrderShop.getStatus())) {
            return driverOrderShopState10;
        }
        return null;
    }
}
