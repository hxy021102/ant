package com.bx.ant.service.impl.state;

import com.bx.ant.pageModel.DeliverOrder;
import com.bx.ant.service.DeliverOrderLogServiceI;
import com.bx.ant.service.DeliverOrderServiceI;
import com.bx.ant.service.DeliverOrderState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 骑手已接单,等待骑手取货
 * Created by wanxp on 17-9-26.
 */
@Service("deliverOrder21StateImpl")
public class DeliverOrder21StateImpl implements DeliverOrderState {

    @Resource(name = "deliverOrder22StateImpl")
    private DeliverOrderState deliverOrderState22;

    @Autowired
    private DeliverOrderServiceI deliverOrderService;

    @Override
    public String getStateName() {
        return "21";
    }

    @Override
    public void handle(DeliverOrder deliverOrder) {
        //修改运单状态
        DeliverOrder orderNew = new DeliverOrder();
        orderNew.setId(deliverOrder.getId());
        orderNew.setStatus(prefix + getStateName());
        orderNew.setDeliveryStatus(DeliverOrderServiceI.DELIVER_STATUS_HANDLEING);
        deliverOrderService.editAndAddLog(orderNew, DeliverOrderLogServiceI.TYPE_DRIVER_TAKE_ORDER, "骑手已接单,等待骑手取货");
    }

    @Override
    public DeliverOrderState next(DeliverOrder deliverOrder) {
        if ((prefix + "22").equals(deliverOrder.getStatus())) {
            return deliverOrderState22;
        }
        return null;
    }
}
