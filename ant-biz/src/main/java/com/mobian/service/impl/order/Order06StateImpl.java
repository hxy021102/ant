package com.mobian.service.impl.order;

import com.mobian.pageModel.MbOrder;
import com.mobian.pageModel.MbOrderLog;
import com.mobian.service.MbOrderLogServiceI;
import com.mobian.service.MbOrderServiceI;
import com.mobian.service.MbPaymentItemServiceI;
import com.mobian.service.MbPaymentServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 支付失败-审核拒绝
 * Created by john on 16/10/30.
 */
@Service("order06StateImpl")
public class Order06StateImpl implements OrderState {
    @Autowired
    private MbPaymentItemServiceI mbPaymentItemService;
    @Autowired
    private MbPaymentServiceI mbPaymentService;

    @Autowired
    private MbOrderServiceI orderService;
    @Autowired
    private MbOrderLogServiceI orderLogService;

    @Override
    public String getStateName() {
        return "06";
    }

    @Override
    public void handle(MbOrder mbOrder) {
        String remark = mbOrder.getRemark();
        mbOrder.setUserRemark(null);
        // 后台审核审核未通过，状态变更为支付失败-审核拒绝，用户端订单不可见
        mbOrder.setStatus(prefix + getStateName());
        mbOrder.setPayStatus("PS04");
        orderService.edit(mbOrder);
        MbOrderLog mbOrderLog = new MbOrderLog();
       /* MbPaymentItem mbPaymentItem = mbPaymentItemService.getMbPaymentItemPW03(mbOrder.getId());
//        mbPaymentItem.setIsdeleted(true);
        mbPaymentItemService.edit(mbPaymentItem);*/

        //审核的日志
        mbOrderLog.setContent("【拒绝】"+remark);
        mbOrderLog.setLoginId(mbOrder.getLoginId());
        mbOrderLog.setRemark(remark);
        mbOrderLog.setLogType("LT004");
        mbOrderLog.setOrderId(mbOrder.getId());
        orderLogService.add(mbOrderLog);
    }

    @Override
    public OrderState next(MbOrder mbOrder) {
        return null;
    }
}
