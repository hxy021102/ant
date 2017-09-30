package com.bx.ant.service.impl.state;

import com.bx.ant.service.DeliverOrderServiceI;
import com.bx.ant.service.DeliverOrderState;
import com.mobian.pageModel.DeliverOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 已配送完成,等待用户确认状态
 * Created by wanxp on 17-9-26.
 */
@Service("deliverOrder30StateImpl")
public class DeliverOrder30StateImpl implements DeliverOrderState {

    @Resource(name = "deliverOrder40StateImpl")
    private DeliverOrderState deliverOrderState40;

    @Autowired
    private DeliverOrderServiceI deliverOrderService;

    @Override
    public String getStateName() {
        return "30";
    }

    @Override
    public void handle(DeliverOrder deliverOrder) {

        //修改运单状态
        DeliverOrder order = new DeliverOrder();
        order.setId(deliverOrder.getId());
        order.setStatus(prefix + getStateName());
        order.setDeliveryStatus(deliverOrderService.DELIVER_STATUS_DELIVERED);
        deliverOrderService.edit(order);

        //
    }

    @Override
    public DeliverOrderState next(DeliverOrder deliverOrder) {
        if ((prefix + "40").equals(deliverOrder.getStatus())) {
            return deliverOrderState40;
        }
        return null;
    }
}
