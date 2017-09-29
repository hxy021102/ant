package com.bx.ant.service.impl.state;

import com.bx.ant.service.DeliverOrderServiceI;
import com.bx.ant.service.DeliverOrderShopServiceI;
import com.bx.ant.service.DeliverOrderState;
import com.mobian.pageModel.DeliverOrder;
import com.mobian.pageModel.DeliverOrderShop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 门店拒绝接单状态
 * Created by wanxp on 17-9-26.
 */
@Service("deliverOrder15StateImpl")
public class DeliverOrder15StateImpl implements DeliverOrderState {

    @Resource(name = "deliverOrder20StateImpl")
    private DeliverOrderState deliverOrderState20;

    @Autowired
    private DeliverOrderServiceI deliverOrderService;

    @Autowired
    private DeliverOrderShopServiceI deliverOrderShopService;

    @Override
    public String getStateName() {
        return "15";
    }

    @Override
    public void handle(DeliverOrder deliverOrder) {

        //修改运单状态
        DeliverOrder order = new DeliverOrder();
        order.setId(deliverOrder.getId());
        order.setStatus(prefix + getStateName());
        deliverOrderService.edit(order);

        //修改运单门店状态
        DeliverOrderShop deliverOrderShop = new DeliverOrderShop();
        deliverOrderShop.setStatus(deliverOrderShopService.STATUS_AUDITING);
        deliverOrderShop.setDeliverOrderId(order.getId());
        deliverOrderShopService.editStatus(deliverOrderShop,deliverOrderShopService.STATUS_REFUSED);

        //TODO 这里应该执行重新分配订单方法

    }

    @Override
    public DeliverOrderState next(DeliverOrder deliverOrder) {
        if ("DO20".equals(deliverOrder.getStatus())) {
            return deliverOrderState20;
        }
        return null;
    }
}
