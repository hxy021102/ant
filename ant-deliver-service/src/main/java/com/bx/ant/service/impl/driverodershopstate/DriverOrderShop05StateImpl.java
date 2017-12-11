package com.bx.ant.service.impl.driverodershopstate;

import com.bx.ant.pageModel.DeliverOrder;
import com.bx.ant.pageModel.DeliverOrderShop;
import com.bx.ant.pageModel.DriverAccount;
import com.bx.ant.pageModel.DriverOrderShop;
import com.bx.ant.service.*;
import com.bx.ant.service.impl.DeliverOrderServiceImpl;
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

    @Resource(name = "driverOrderShop08StateImpl")
    private DriverOrderShopState driverOrderShopState08;

    @Resource
    private DriverOrderShopServiceI driverOrderShopSerivce;

    @Autowired
    private DriverOrderShopAllocationServiceI driverOrderShopAllocationService;

    @Autowired
    private DeliverOrderShopServiceI deliverOrderShopSerivce;

    @Autowired
    private DeliverOrderServiceImpl deliverOrderService;
    @Autowired
    private DriverAccountServiceI driverAccountService;


    @Override
    public String getStateName() {
        return "05";
    }

    @Override
    public void handle(DriverOrderShop driverOrderShop) {
        //1. 删除redis中所有订单记录

        driverOrderShopAllocationService.editClearOrderAllocation(driverOrderShop.getId());

        //2. 绑定骑手并编辑状态
        if (F.empty(driverOrderShop.getDriverAccountId())) {
            throw new ServiceException("DriverOrderShopState05状态缺少必要数据:driverOrderShop.driverAccountId");
        }
        DriverOrderShop orderShop = new DriverOrderShop();
        orderShop.setId(driverOrderShop.getId());
        orderShop.setStatus(prefix + getStateName());
        orderShop.setDriverAccountId(driverOrderShop.getDriverAccountId());
        driverOrderShopSerivce.edit(orderShop);


        //修改门店派单状态为已被骑手接单
        //修改为DOS21
        DeliverOrder deliverOrder = new DeliverOrder();
        DeliverOrderShop deliverOrderShop = deliverOrderShopSerivce.get(orderShop.getDeliverOrderShopId());
        deliverOrder.setId(deliverOrderShop.getDeliverOrderId());
        deliverOrder.setStatus(DeliverOrderServiceI.STATUS_CHECK_DRIVER_TAKE);
        deliverOrder.setShopId(deliverOrderShop.getShopId());
        DriverAccount driverAccount = driverAccountService.get(driverOrderShop.getDriverAccountId());
        deliverOrder.setDriverAccount(driverAccount);
        deliverOrderService.transform(deliverOrder);

    }

    @Override
    public DriverOrderShopState next(DriverOrderShop driverOrderShop) {
        if ( (prefix  + "08").equals(driverOrderShop.getStatus())) {
            return driverOrderShopState08;
        }
        return null;
    }
}
