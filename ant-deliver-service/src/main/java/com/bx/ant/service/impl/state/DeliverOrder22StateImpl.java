package com.bx.ant.service.impl.state;

import com.bx.ant.pageModel.DeliverOrder;
import com.bx.ant.pageModel.DeliverOrderShop;
import com.bx.ant.pageModel.DriverOrderShop;
import com.bx.ant.service.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 已接单,确认骑手已取货
 * Created by wanxp on 17-9-26.
 */
@Service("deliverOrder22StateImpl")
public class DeliverOrder22StateImpl implements DeliverOrderState {

    @Resource(name = "deliverOrder25StateImpl")
    private DeliverOrderState deliverOrderState25;

    @Autowired
    private DeliverOrderServiceI deliverOrderService;

    @Autowired
    private DriverOrderShopServiceI driverOrderShopService;

    @Autowired
    private DeliverOrderShopServiceI deliverOrderShopService;

    @Override
    public String getStateName() {
        return "22";
    }

    @Override
    public void handle(DeliverOrder deliverOrder) {
        //修改运单状态
        DeliverOrder orderNew = new DeliverOrder();
        orderNew.setId(deliverOrder.getId());
        orderNew.setStatus(prefix + getStateName());
        orderNew.setDeliveryStatus(DeliverOrderServiceI.DELIVER_STATUS_DELIVERING);
        deliverOrderService.editAndAddLog(orderNew, DeliverOrderLogServiceI.TYPE_DRIVER_TAKE_ITEM, "骑手已取货");

        //骑手订单状态为骑手已经取货
        DeliverOrder order = deliverOrderService.get(deliverOrder.getId());
        if (ShopDeliverApplyServiceI.DELIVER_WAY_DRIVER.equals(order.getDeliveryWay())) {

            DeliverOrderShop deliverOrderShop = new DeliverOrderShop();
            deliverOrderShop.setStatus(DeliverOrderShopServiceI.STATUS_ACCEPTED);
            deliverOrderShop.setDeliverOrderId(deliverOrder.getId());
            List<DeliverOrderShop> deliverOrderShops = deliverOrderShopService.query(deliverOrderShop);
            if (CollectionUtils.isNotEmpty(deliverOrderShops)) {
                deliverOrderShop = deliverOrderShops.get(0);
                DriverOrderShop driverOrderShop = driverOrderShopService.getByDeliverOrderShopId(deliverOrderShop.getId());
                if (driverOrderShop != null) {
                    driverOrderShop.setStatus(DriverOrderShopServiceI.STATUS_ITEM_TAKEN);
                    driverOrderShopService.transform(driverOrderShop);
                }
            }
        }
    }

    @Override
    public DeliverOrderState next(DeliverOrder deliverOrder) {
        if ((prefix + "25").equals(deliverOrder.getStatus())) {
            return deliverOrderState25;
        }
        return null;
    }
}
