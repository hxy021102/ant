package com.mobian.service.impl.order;

import com.mobian.pageModel.MbOrder;
import com.mobian.pageModel.MbPayment;
import com.mobian.service.MbOrderServiceI;
import com.mobian.service.MbPaymentServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 后台操作添加订单
 * Created by john on 16/10/30.
 */
@Service("order02StateImpl")
public class Order02StateImpl implements OrderState {
    @Autowired
    private MbOrderServiceI orderService;

    @Resource(name = "order05StateImpl")
    private OrderState orderState05;
    @Resource(name = "order10StateImpl")
    private OrderState orderState10;

    @Resource(name = "order01StateImpl")
    private OrderState orderState01;

    @Autowired
    private MbPaymentServiceI mbPaymentService;

    @Override
    public String getStateName() {
        return "02";
    }

    @Override
    public void handle(MbOrder mbOrder) {
        String nextState = mbOrder.getStatus();
        orderState01.handle(mbOrder);
        //自动跳到支付去了
        if("OD10".equals(nextState)){
            MbPayment mbPayment = new MbPayment();
            mbPayment.setPayWay(mbOrder.getPayWay());
            mbPayment.setAmount(mbOrder.getTotalPrice());
            mbPayment.setOrderId(mbOrder.getId());
            mbPayment.setOrderType("OT01");
            mbPayment.setReason("客服操作，余额自动支付");
            mbPayment.setStatus(true);
            mbPaymentService.add(mbPayment);
            MbOrder order = new MbOrder();
            order.setId(mbOrder.getId());
            order.setPayWay(mbOrder.getPayWay());
            order.setTotalPrice(mbOrder.getTotalPrice());
            order.setPaymentId(mbPayment.getId());
            order.setStatus(nextState);
            order.setShopId(mbOrder.getShopId());

            order.setUsedByCoupons(mbOrder.getUsedByCoupons());
            order.setMbOrderItemList(mbOrder.getMbOrderItemList());
            orderService.transform(order);
        }else{
            //未支付可发货
            MbOrder order = new MbOrder();
            order.setStatus(nextState);
            order.setId(mbOrder.getId());
            orderService.transform(order);
        }
    }

    @Override
    public OrderState next(MbOrder mbOrder) {
        return null;
    }
}
