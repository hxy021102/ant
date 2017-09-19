package com.mobian.service.impl.order;

import com.mobian.pageModel.MbOrder;
import com.mobian.pageModel.MbOrderLog;
import com.mobian.service.MbOrderLogServiceI;
import com.mobian.service.MbOrderServiceI;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 进入捡货状态
 * Created by john on 16/10/30.
 */
@Service("order15StateImpl")
public class Order15StateImpl implements OrderState {
    @Autowired
    private MbOrderServiceI orderService;

    @Autowired
    private MbOrderLogServiceI orderLogService;

    @Resource(name = "order20StateImpl")
    private OrderState orderState20;

    @Resource(name = "order32StateImpl")
    private OrderState orderState32;

    @Override
    public String getStateName() {
        return "15";
    }

    @Override
    public void handle(MbOrder mbOrder) {
        MbOrder newMbOrder = new MbOrder();
        newMbOrder.setId(mbOrder.getId());
        newMbOrder.setStatus(prefix + getStateName());
        orderService.edit(newMbOrder);
        if (StringUtils.isNotEmpty(mbOrder.getLoginId())) {
            MbOrderLog mbOrderLog = new MbOrderLog();
            mbOrderLog.setContent("打印订单成功");
            mbOrderLog.setLoginId(mbOrder.getLoginId());
            mbOrderLog.setLogType("LT015");
            mbOrderLog.setOrderId(mbOrder.getId());
            orderLogService.add(mbOrderLog);
        }
    }

    @Override
    public OrderState next(MbOrder mbOrder) {

        if ("OD32".equals(mbOrder.getStatus())) {
            return orderState32;
        } else if ("OD20".equals(mbOrder.getStatus())) {
            return orderState20;
        }
        return null;
    }
}
