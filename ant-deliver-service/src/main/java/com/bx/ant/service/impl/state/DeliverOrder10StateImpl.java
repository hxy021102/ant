package com.bx.ant.service.impl.state;

import com.bx.ant.service.DeliverOrderPayServiceI;
import com.bx.ant.service.DeliverOrderServiceI;
import com.bx.ant.service.DeliverOrderState;
import com.bx.ant.service.impl.DeliverOrderShopPayServiceImpl;
import com.mobian.pageModel.DeliverOrder;
import com.mobian.pageModel.DeliverOrderPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 已支付,待接单状态
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

        DeliverOrder deliverOrderNew = new DeliverOrder();

        deliverOrderNew.setId(deliverOrder.getId());
//        deliverOrderNew.setPayWay(deliverOrderPay.getPayWay());
        deliverOrderNew.setStatus(prefix + getStateName());
//        deliverOrderNew.setPayStatus(deliverOrderPay.getStatus());

        deliverOrderService.edit(deliverOrderNew);



    }

    @Override
    public DeliverOrderState next(DeliverOrder deliverOrder) {
        if ((prefix + "20").equals(deliverOrder.getStatus())) {
            return deliverOrderState20;
        }
        if ((prefix + "15").equals(deliverOrder.getStatus())) {
            return deliverOrderState15;
        }
        return null;
    }
}
