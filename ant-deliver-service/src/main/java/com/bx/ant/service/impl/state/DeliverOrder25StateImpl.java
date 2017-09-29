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
 * 门店已发货状态
 * Created by wanxp on 17-9-26.
 */
@Service("deliverOrder25StateImpl")
public class DeliverOrder25StateImpl implements DeliverOrderState {

    @Resource(name = "deliverOrder30StateImpl")
    private DeliverOrderState deliverOrderState30;

    @Autowired
    private DeliverOrderServiceI deliverOrderService;


    @Override
    public String getStateName() {
        return "25";
    }

    @Override
    public void handle(DeliverOrder deliverOrder) {

        //修改运单状态
        DeliverOrder order = new DeliverOrder();
        order.setId(deliverOrder.getId());
        order.setStatus(prefix + getStateName());
        order.setDeliveryStatus(deliverOrderService.DELIVER_STATUS_DELIVERING);
        deliverOrderService.edit(order);

        //修改门店运单状态
//        DeliverOrderShop deliverOrderShop = new DeliverOrderShop();
//        deliverOrderShop.setStatus(deliverOrderShopService.STATUS_AUDITING);
//        deliverOrderShop.setDeliverOrderId(order.getId());
//        deliverOrderShopService.editStatus(deliverOrderShop,deliverOrderShopService.STATUS_REFUSED);


    }

    @Override
    public DeliverOrderState next(DeliverOrder deliverOrder) {
        if ("DO30".equals(deliverOrder.getStatus())) {
            return deliverOrderState30;
        }
        return null;
    }
}
