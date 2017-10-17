package com.bx.ant.service.impl.state;

import com.bx.ant.service.*;
import com.bx.ant.pageModel.DeliverOrder;
import com.bx.ant.pageModel.DeliverOrderShop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 已分配订单,待接单状态
 * Created by wanxp on 17-9-26.
 */
@Service("deliverOrder10StateImpl")
public class DeliverOrder10StateImpl implements DeliverOrderState {

    @Resource(name = "deliverOrder20StateImpl")
    private DeliverOrderState deliverOrderState20;

    @Resource(name = "deliverOrder15StateImpl")
    private DeliverOrderState deliverOrderState15;



    @Autowired
    private DeliverOrderServiceI deliverOrderService;

    @Autowired
    private DeliverOrderPayServiceI deliverOrderPayService;

    @Autowired
    private DeliverOrderShopServiceI deliverOrderShopService;

    @Autowired
    private DeliverOrderLogServiceI deliverOrderLogService;

    @Override
    public String getStateName() {
        return "10";
    }

    @Override
    public void handle(DeliverOrder deliverOrder) {


//        DeliverOrderPay deliverOrderPay = new DeliverOrderPay();

//        deliverOrderPay.setAmount(deliverOrder.getAmount());
//        deliverOrderPay.setDeliverOrderId(deliverOrder.getId());
//        deliverOrderPay.setPayWay(deliverOrder.getPayWay());
//        deliverOrderPay.setSupplierId(deliverOrder.getSupplierId());
//        deliverOrderPay.setStatus(deliverOrder.getPayStatus());
//        deliverOrderPayService.add(deliverOrderPay);

        DeliverOrder orderNew = new DeliverOrder();

        orderNew.setId(deliverOrder.getId());
        orderNew.setStatus(prefix + getStateName());
        deliverOrderService.editAndAddLog(orderNew, DeliverOrderLogServiceI.TYPE_ASSIGN_DELIVER_ORDER,
                "运单被分配");

        DeliverOrderShop deliverOrderShop = new DeliverOrderShop();
        deliverOrderShop.setDeliverOrderId(deliverOrder.getId());
        deliverOrderShop.setShopId(deliverOrder.getShopId());
        deliverOrderShopService.editStatus(deliverOrderShop,DeliverOrderShopServiceI.STATUS_AUDITING);

    }

    @Override
    public DeliverOrderState next(DeliverOrder deliverOrder) {
        //跳转至接受状态
        if ((prefix + "20").equals(deliverOrder.getStatus())) {
            return deliverOrderState20;
        }
        //跳转至拒绝状态
        if ((prefix + "15").equals(deliverOrder.getStatus())) {
            return deliverOrderState15;
        }
        return null;
    }
}
