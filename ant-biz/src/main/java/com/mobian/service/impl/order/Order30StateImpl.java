package com.mobian.service.impl.order;

import com.mobian.pageModel.MbOrder;
import com.mobian.service.MbOrderServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 确认收货交易完成
 * Created by john on 16/10/30.
 */
@Service("order30StateImpl")
public class Order30StateImpl implements OrderState {
    @Autowired
    private MbOrderServiceI orderService;

    @Resource(name = "order35StateImpl")
    private OrderState orderState35;

    @Resource(name = "order32StateImpl")
    private OrderState orderState32;

    @Override
    public String getStateName() {
        return "30";
    }

    @Override
    public void handle(MbOrder mbOrder) {
        // 修改订单状态为交易完成，配送状态已签收
        mbOrder.setStatus(prefix + getStateName());
        mbOrder.setDeliveryStatus("DS30"); // 配送状态-已签收
        //mbOrder.setDeliveryRequireTime(new Date());
        orderService.edit(mbOrder);
    }

    @Override
    public OrderState next(MbOrder mbOrder) {
        if ("OD32".equals(mbOrder.getStatus())) {
            return orderState32;
        } else if ("OD35".equals(mbOrder.getStatus())){
            return orderState35;
        }
        return null;
    }
}
