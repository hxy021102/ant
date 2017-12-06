package com.bx.ant.service.impl.state;

import com.bx.ant.pageModel.DeliverOrder;
import com.bx.ant.service.DeliverOrderLogServiceI;
import com.bx.ant.service.DeliverOrderServiceI;
import com.bx.ant.service.DeliverOrderState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 标记为骑手已配送完成状态
 * Created by wanxp on 17-9-26.
 */
@Service("deliverOrder50StateImpl")
public class DeliverOrder50StateImpl implements DeliverOrderState {

    @Resource(name = "deliverOrder30StateImpl")
    private DeliverOrderState deliverOrderState30;

    @Autowired
    private DeliverOrderServiceI deliverOrderService;


    @Override
    public String getStateName() {
        return "50";
    }


    @Override
    public void handle(DeliverOrder deliverOrder) {

        // 修改运单状态
        DeliverOrder orderNew = new DeliverOrder();
        orderNew.setId(deliverOrder.getId());
        orderNew.setStatus(prefix + getStateName());
        deliverOrderService.editAndAddLog(orderNew, DeliverOrderLogServiceI.TYPE_DRIVER_DELIVERED_DELIVER_ORDER, "运单已被骑手派送至目的地，等待确认");

    }

    @Override
    public DeliverOrderState next(DeliverOrder deliverOrder) {
        if ((prefix + "30").equals(deliverOrder.getStatus())) {
            return deliverOrderState30;
        }
        return null;
    }
}
