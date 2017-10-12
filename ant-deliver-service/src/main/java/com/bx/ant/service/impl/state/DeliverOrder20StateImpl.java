package com.bx.ant.service.impl.state;

import com.bx.ant.service.*;
import com.mobian.pageModel.DeliverOrder;
import com.mobian.pageModel.DeliverOrderItem;
import com.mobian.pageModel.DeliverOrderShop;
import com.mobian.pageModel.DeliverOrderShopPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 已接单,正在配送状态
 * Created by wanxp on 17-9-26.
 */
@Service("deliverOrder20StateImpl")
public class DeliverOrder20StateImpl implements DeliverOrderState {

    @Resource(name = "deliverOrder25StateImpl")
    private DeliverOrderState deliverOrderState25;

    @Autowired
    private DeliverOrderServiceI deliverOrderService;

    @Autowired
    private DeliverOrderShopServiceI deliverOrderShopService;

    @Autowired
    private DeliverOrderItemServiceI deliverOrderItemService;

    @Autowired
    private DeliverOrderShopItemServiceI deliverOrderShopItemService;

    @Autowired
    private DeliverOrderShopPayServiceI deliverOrderShopPayService;

    @Override
    public String getStateName() {
        return "20";
    }

    @Override
    public void handle(DeliverOrder deliverOrder) {
        //修改运单状态
        DeliverOrder order = new DeliverOrder();
        order.setId(deliverOrder.getId());
        order.setStatus(prefix + getStateName());
        order.setDeliveryStatus(deliverOrderService.DELIVER_STATUS_STANDBY);
        order.setShopPayStatus(deliverOrderService.PAY_STATUS_NOT_PAY);
        deliverOrderService.edit(order);

        //修改门店运单状态
        DeliverOrderShop deliverOrderShop = new DeliverOrderShop();
        deliverOrderShop.setStatus(deliverOrderShopService.STATUS_AUDITING);
        deliverOrderShop.setDeliverOrderId(order.getId());
        deliverOrderShop = deliverOrderShopService.editStatus(deliverOrderShop,deliverOrderShopService.STATUS_ACCEPTED);

        //修改门店运单支付状态
        DeliverOrderShopPay deliverOrderShopPay = new DeliverOrderShopPay();
        deliverOrderShopPay.setDeliverOrderId(order.getId());
        deliverOrderShopPay.setShopId(deliverOrder.getShopId());
        deliverOrderShopPayService.editStatus(deliverOrderShopPay, deliverOrderService.PAY_STATUS_NOT_PAY);
        

//        DeliverOrder order = new DeliverOrder();
//        order = deliverOrderService.get(deliverOrder.getId());
//        order.setShopId(deliverOrder.getShopId());
//        DeliverOrderShop deliverOrderShop = deliverOrderShopService.addByDeliverOrder(order);
        //添加门店运单明细
//        DeliverOrderItem deliverOrderItem = new DeliverOrderItem();
//        deliverOrderItem.setDeliverOrderId(deliverOrder.getId());
//        List<DeliverOrderItem> deliverOrderItems = deliverOrderItemService.list(deliverOrderItem);
//        deliverOrderShopItemService.addByDeliverOrderItemList(deliverOrderItems, deliverOrderShop);
    }

    @Override
    public DeliverOrderState next(DeliverOrder deliverOrder) {
        if ((prefix + "25").equals(deliverOrder.getStatus())) {
            return deliverOrderState25;
        }
        return null;
    }
}
