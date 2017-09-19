package com.mobian.service.impl.order;

import com.mobian.pageModel.MbOrder;
import com.mobian.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 待支付可发货
 * Created by john on 16/10/30.
 */
@Service("order09StateImpl")
public class Order09StateImpl implements OrderState {
    @Autowired
    private MbOrderServiceI orderService;
    @Autowired
    private MbOrderLogServiceI orderLogService;
    @Autowired
    private MbPaymentServiceI mbPaymentService;
    @Autowired
    private MbPaymentItemServiceI mbPaymentItemService;

    @Autowired
    private MbBalanceServiceI mbBalanceService;

    @Autowired
    private MbBalanceLogServiceI mbBalanceLogService;

    @Resource(name = "order12StateImpl")
    private OrderState orderState12;

    @Resource(name = "order31StateImpl")
    private OrderState orderState31;

    @Override
    public String getStateName() {
        return "09";
    }

    @Override
    public void handle(MbOrder mbOrder) {
        // 修改订单状态
        mbOrder.setStatus(prefix + getStateName());
        mbOrder.setDeliveryStatus("DS01"); // 配送状态-待处理
        mbOrder.setUserRemark(null);
        orderService.edit(mbOrder);

    }

    @Override
    public OrderState next(MbOrder mbOrder) {
        if("OD12".equals(mbOrder.getStatus())){
            return orderState12;
        }else if("OD31".equals(mbOrder.getStatus())){
            return orderState31;
        }
        return null;
    }
}
