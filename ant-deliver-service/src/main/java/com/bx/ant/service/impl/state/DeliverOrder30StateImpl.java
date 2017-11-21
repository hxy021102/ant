package com.bx.ant.service.impl.state;

import com.bx.ant.pageModel.DeliverOrderShop;
import com.bx.ant.service.DeliverOrderLogServiceI;
import com.bx.ant.service.DeliverOrderServiceI;
import com.bx.ant.service.DeliverOrderShopServiceI;
import com.bx.ant.service.DeliverOrderState;
import com.bx.ant.pageModel.DeliverOrder;
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

    @Autowired
    private DeliverOrderLogServiceI deliverOrderLogService;
    @Autowired
    private DeliverOrderShopServiceI deliverOrderShopService;

    @Override
    public String getStateName() {
        return "30";
    }

    @Override
    public void handle(DeliverOrder deliverOrder) {

        //修改运单状态
        DeliverOrder orderNew = new DeliverOrder();
        orderNew.setId(deliverOrder.getId());
        orderNew.setStatus(prefix + getStateName());
        orderNew.setDeliveryStatus(DeliverOrderServiceI.DELIVER_STATUS_DELIVERED);
        orderNew.setCompleteImages(deliverOrder.getCompleteImages());
        orderNew.setCompleteRemark(deliverOrder.getCompleteRemark());
        deliverOrderService.editAndAddLog(orderNew, DeliverOrderLogServiceI.TYPE_DELIVERED_DELIVER_ORDER, "运单已被派送至目的地");

        //门店订单状态
        DeliverOrderShop deliverOrderShop = new DeliverOrderShop();
        deliverOrderShop.setStatus(DeliverOrderShopServiceI.STATUS_ACCEPTED);
        deliverOrderShop.setDeliverOrderId(deliverOrder.getId());
        DeliverOrderShop orderShopEdit = new DeliverOrderShop();
        orderShopEdit.setStatus(DeliverOrderShopServiceI.STAUS_SERVICE);
        deliverOrderShop = deliverOrderShopService.editStatus(deliverOrderShop,orderShopEdit);
    }

    @Override
    public DeliverOrderState next(DeliverOrder deliverOrder) {
        if ((prefix + "40").equals(deliverOrder.getStatus())) {
            return deliverOrderState40;
        }
        return null;
    }
}
