package com.mobian.service.impl.order;

import com.mobian.pageModel.MbOrder;
import com.mobian.pageModel.MbOrderLog;
import com.mobian.service.MbOrderLogServiceI;
import com.mobian.service.MbOrderServiceI;
import com.mobian.service.rulesengine.impl.MbRuleEngineImpl;
import com.mobian.service.rulesengine.impl.RuleExecutorImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 打印订单状态（确认可以发货）
 * Created by john on 16/10/30.
 */
@Service("order12StateImpl")
public class Order12StateImpl implements OrderState {
    @Autowired
    private MbOrderServiceI orderService;

    @Autowired
    private MbOrderLogServiceI orderLogService;

    @Autowired
    MbRuleEngineImpl mbRuleEngine;

    @Autowired
    RuleExecutorImpl ruleExecutor;

    @Resource(name = "order15StateImpl")
    private OrderState orderState15;

    @Resource(name = "order32StateImpl")
    private OrderState orderState32;

    @Override
    public String getStateName() {
        return "12";
    }

    @Override
    public void handle(MbOrder mbOrder) {
        MbOrder newMbOrder = new MbOrder();
        newMbOrder.setId(mbOrder.getId());
        newMbOrder.setStatus(prefix + getStateName());
        newMbOrder.setDeliveryRequireTime(mbOrder.getDeliveryRequireTime());
        newMbOrder.setDeliveryWarehouseId(mbOrder.getDeliveryWarehouseId());
        orderService.edit(newMbOrder);
        if (StringUtils.isNotEmpty(mbOrder.getLoginId())) {
            MbOrderLog mbOrderLog = new MbOrderLog();
            mbOrderLog.setContent("订单审核成功");
            mbOrderLog.setRemark(mbOrder.getUserRemark());
            mbOrderLog.setLoginId(mbOrder.getLoginId());
            mbOrderLog.setLogType("LT005");
            mbOrderLog.setOrderId(mbOrder.getId());
            orderLogService.add(mbOrderLog);
        }
    }

    @Override
    public OrderState next(MbOrder mbOrder) {
        if ("OD32".equals(mbOrder.getStatus())) {
            return orderState32;
        } else if("OD15".equals(mbOrder.getStatus())) {
            return orderState15;
        }
        return null;
    }
}
