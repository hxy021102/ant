package com.mobian.service.impl.order;

import com.mobian.exception.ServiceException;
import com.mobian.pageModel.*;
import com.mobian.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 订单完成后，再支付
 * Created by john on 16/10/30.
 */
@Service("order45StateImpl")
public class Order45StateImpl implements OrderState {

    @Autowired
    private MbOrderServiceI orderService;

    @Autowired
    private MbPaymentServiceI mbPaymentService;

    @Autowired
    private MbPaymentItemServiceI mbPaymentItemService;

    @Autowired
    private MbOrderLogServiceI orderLogService;

    @Autowired
    private MbBalanceLogServiceI mbBalanceLogService;

    @Autowired
    private MbBalanceServiceI mbBalanceService;

    @Autowired
    private MbShopServiceI mbShopService;

    @Override
    public String getStateName() {
        return "45";
    }

    @Override
    public void handle(MbOrder mbOrder) {

        MbOrder mbOrderOld = OrderState.order.get();
        //已经支付过
        if ("PSO5".equals(mbOrderOld.getPayStatus())) {
            throw new ServiceException("已付款");
        }
        MbBalance balance = mbBalanceService.addOrGetMbBalance(mbOrderOld.getShopId());
        MbShop mbShop = mbShopService.getFromCache(mbOrderOld.getShopId());
        if (balance.getAmount() < 0 && mbShop != null && MbShopServiceI.ST01.equals(mbShop.getShopType())) {
            throw new ServiceException("加盟水站，余额不足");
        }

        MbOrder mbOrderNew = new MbOrder();
        mbOrderNew.setId(mbOrder.getId());
        mbOrderNew.setPayStatus("PS05");
        mbOrderNew.setPayWay("PW01");
        orderService.edit(mbOrderNew);

        MbPayment mbPayment = new MbPayment();
        mbPayment.setPayWay(mbOrderNew.getPayWay());
        mbPayment.setAmount(mbOrderOld.getTotalPrice());
        mbPayment.setOrderId(mbOrder.getId());
        mbPayment.setOrderType("OT01");
        mbPayment.setReason("客服操作，使用余额付款");
        mbPayment.setStatus(true);
        mbPaymentService.add(mbPayment);

        // 插入支付明细
        MbPaymentItem mbPaymentItem = new MbPaymentItem();
        mbPaymentItem.setPaymentId(mbPayment.getId());
        mbPaymentItem.setPayWay(mbOrderNew.getPayWay());
        mbPaymentItem.setAmount(mbOrderOld.getTotalPrice()-mbOrderOld.getTotalRefundAmount());
        //mbPaymentItem.setRefId(mbOrder.getRefId());
        mbPaymentItemService.add(mbPaymentItem);


        MbBalanceLog log = new MbBalanceLog();
        log.setBalanceId(balance.getId());
        log.setAmount(-mbPaymentItem.getAmount());
        log.setRefType("BT002"); // 余额付款
        log.setRefId(mbPayment.getId()+"");
        mbBalanceLogService.addAndUpdateBalance(log);

        //审核的日志
        MbOrderLog mbOrderLog = new MbOrderLog();
        mbOrderLog.setContent("【同意】" + mbOrder.getRemark());
        mbOrderLog.setRemark(mbOrder.getRemark());
        mbOrderLog.setLoginId(mbOrder.getLoginId());
        mbOrderLog.setLogType("LT045");
        mbOrderLog.setOrderId(mbOrder.getId());
        orderLogService.add(mbOrderLog);
    }

    @Override
    public OrderState next(MbOrder mbOrder) {
        return null;
    }
}
