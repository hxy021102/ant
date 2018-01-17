package com.bx.ant.service.impl.state;

import com.bx.ant.pageModel.DeliverOrder;
import com.bx.ant.pageModel.DeliverOrderLog;
import com.bx.ant.service.AbstractDeliverOrderState;
import com.bx.ant.service.DeliverOrderLogServiceI;
import com.bx.ant.service.DeliverOrderServiceI;
import com.bx.ant.service.DeliverOrderState;
import com.mobian.absx.F;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 订单已产生,待支付状态
 * 该状态已废弃
 * Created by wanxp on 17-9-26.
 */
@Service("deliverOrder01StateImpl")
public class DeliverOrder01StateImpl extends AbstractDeliverOrderState {

    @Resource(name = "deliverOrder10StateImpl")
    private DeliverOrderState deliverOrderState10;

    @Resource(name = "deliverOrder60StateImpl")
    private DeliverOrderState deliverOrderState60;

    @Autowired
    private DeliverOrderServiceI deliverOrderService;

    @Autowired
    private DeliverOrderLogServiceI deliverOrderLogService;

    @Override
    public String getStateName() {
        return "01";
    }

    @Override
    public void execute(DeliverOrder deliverOrder) {
        deliverOrder.setStatus(prefix + getStateName());
        deliverOrder.setPayStatus(DeliverOrderServiceI.PAY_STATUS_NOT_PAY);
        deliverOrder.setShopPayStatus(DeliverOrderServiceI.SHOP_PAY_STATUS_NOT_PAY);
        deliverOrder.setDeliveryStatus(DeliverOrderServiceI.DELIVER_STATUS_STANDBY);

        deliverOrderService.add(deliverOrder);

        DeliverOrderLog log = new DeliverOrderLog();
        log.setDeliverOrderId(deliverOrder.getId());
        log.setLogType(DeliverOrderLogServiceI.TYPE_ADD_DELIVER_ORDER);
        log.setContent(F.empty(deliverOrder.getOrderLogRemark()) ?  "添加订单等待分配" : deliverOrder.getOrderLogRemark());
        if (!F.empty(deliverOrder.getLoginId())) {
            log.setLoginId(deliverOrder.getLoginId());
        }
        deliverOrderLogService.add(log);
    }

    @Override
    public DeliverOrderState next(DeliverOrder deliverOrder) {
        if ((prefix + "10").equals(deliverOrder.getStatus())) {
            return deliverOrderState10;
        }else if((prefix + "60").equals(deliverOrder.getStatus())){
            return deliverOrderState60;
        }
        return null;
    }
}
