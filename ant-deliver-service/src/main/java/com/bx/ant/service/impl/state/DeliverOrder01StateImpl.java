package com.bx.ant.service.impl.state;

import com.bx.ant.service.DeliverOrderServiceI;
import com.bx.ant.service.DeliverOrderState;
import com.mobian.pageModel.DeliverOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 订单已分配,带接单状态
 * Created by wanxp on 17-9-26.
 */
@Service("deliverOrder01StateImpl")
public class DeliverOrder01StateImpl implements DeliverOrderState {

    @Resource(name = "deliverOrder20StateImpl")
    private DeliverOrderState deliverOrderState20;

    @Autowired
    private DeliverOrderServiceI deliverOrderService;

    @Override
    public String getStateName() {
        return "01";
    }

    @Override
    public void handle(DeliverOrder deliverOrder) {
        deliverOrder.setStatus(prefix + getStateName());
        deliverOrderService.add(deliverOrder);
    }

    @Override
    public DeliverOrderState next(DeliverOrder deliverOrder) {
        if ((prefix + "20").equals(deliverOrder.getStatus())) {
            return deliverOrderState20;
        }
        return null;
    }
}
