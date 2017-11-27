package com.bx.ant.service.impl.state;

import com.bx.ant.pageModel.DeliverOrder;
import com.bx.ant.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 已接单,等待骑手取货
 * Created by wanxp on 17-9-26.
 */
@Service("deliverOrder22StateImpl")
public class DeliverOrder22StateImpl implements DeliverOrderState {

    @Resource(name = "deliverOrder25StateImpl")
    private DeliverOrderState deliverOrderState25;

    @Autowired
    private DeliverOrderServiceI deliverOrderService;

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
        orderNew.setDeliveryStatus(DeliverOrderServiceI.STATUS_DRIVER_TOKEN);
        deliverOrderService.editAndAddLog(orderNew, DeliverOrderLogServiceI.TYPE_DRIVER_TAKE_ITEM, "骑手接货");
    }

    @Override
    public DeliverOrderState next(DeliverOrder deliverOrder) {
        if ((prefix + "25").equals(deliverOrder.getStatus())) {
            return deliverOrderState25;
        }
        return null;
    }
}
