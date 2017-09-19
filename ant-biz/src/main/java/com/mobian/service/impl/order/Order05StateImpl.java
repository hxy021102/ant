package com.mobian.service.impl.order;

import com.mobian.pageModel.MbOrder;
import com.mobian.pageModel.MbPaymentItem;
import com.mobian.service.MbOrderServiceI;
import com.mobian.service.MbPaymentItemServiceI;
import com.mobian.util.Constants;
import com.mobian.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 汇款的方式支付提交状态（支付成功-待审核）
 * Created by john on 16/10/30.
 */
@Service("order05StateImpl")
public class Order05StateImpl implements OrderState {
    @Autowired
    private MbOrderServiceI orderService;
    @Autowired
    private MbPaymentItemServiceI mbPaymentItemService;

    @Resource(name = "order06StateImpl")
    private OrderState orderState06;
    @Resource(name = "order10StateImpl")
    private OrderState orderState10;

    @Override
    public String getStateName() {
        return "05";
    }

    @Override
    public void handle(MbOrder mbOrder) {
        // 修改订单状态
        mbOrder.setStatus(prefix + getStateName());
        orderService.edit(mbOrder);

        // 插入支付明细
        MbPaymentItem paymentItem = new MbPaymentItem();
        paymentItem.setPaymentId(mbOrder.getPaymentId());
        paymentItem.setPayWay(mbOrder.getPayWay());
        paymentItem.setAmount(mbOrder.getTotalPrice());
        paymentItem.setBankCode(mbOrder.getBankCode());
        paymentItem.setRemitter(mbOrder.getRemitter());
        paymentItem.setRemitterTime(DateUtil.parse(mbOrder.getRemitterTime(), Constants.DATE_FORMAT_YMDHM));
        paymentItem.setRemark(mbOrder.getRemark());
        paymentItem.setRefId(mbOrder.getRefId());
        mbPaymentItemService.add(paymentItem);
    }

    @Override
    public OrderState next(MbOrder mbOrder) {
        if (mbOrder.getStatus().equals("OD06")) {
            return orderState06;
        } else if (mbOrder.getStatus().equals("OD10")) {
            return orderState10;
        }
        return null;
    }
}
