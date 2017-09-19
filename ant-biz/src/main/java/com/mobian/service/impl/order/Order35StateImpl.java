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
 * 订单已配送完成状态
 * Created by wanxp on 7/11/17.
 */
@Service("order35StateImpl")
public class Order35StateImpl implements OrderState {
    @Resource(name = "order40StateImpl")
    private OrderState orderState40;

    @Autowired
    private MbOrderLogServiceI orderLogService;

    @Autowired
    private MbOrderServiceI orderService;

    @Resource(name = "order32StateImpl")
    private OrderState orderState32;

    @Override
    public String getStateName() {
        return "35";
    }

    @Override
    public void handle(MbOrder mbOrder) {
        if(StringUtils.isNotEmpty(mbOrder.getLoginId())) {

            mbOrder.setStatus(prefix + getStateName());
            orderService.edit(mbOrder);
            //审核的日志
            MbOrderLog mbOrderLog = new MbOrderLog();
            mbOrderLog.setContent("【完成】" + "配送");
            mbOrderLog.setRemark(mbOrder.getCompleteDeliveryRemark());
            mbOrderLog.setLoginId(mbOrder.getLoginId());
            mbOrderLog.setLogType("LT008");
            mbOrderLog.setOrderId(mbOrder.getId());
            orderLogService.add(mbOrderLog);
        }
    }

    @Override
    public OrderState next(MbOrder mbOrder) {
        if ("OD32".equals(mbOrder.getStatus())) {
            return orderState32;
        } else if ("OD40".equals(mbOrder.getStatus()))  {
            return orderState40;
        }
        return null;
    }
}
