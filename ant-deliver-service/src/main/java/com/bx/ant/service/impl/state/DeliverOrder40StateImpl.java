package com.bx.ant.service.impl.state;

import com.bx.ant.service.DeliverOrderServiceI;
import com.bx.ant.service.DeliverOrderShopPayServiceI;
import com.bx.ant.service.DeliverOrderShopServiceI;
import com.bx.ant.service.DeliverOrderState;
import com.mobian.pageModel.*;
import com.mobian.pageModel.DeliverOrder;
import com.mobian.pageModel.DeliverOrderShop;
import com.mobian.pageModel.DeliverOrderShopPay;
import com.mobian.service.MbBalanceLogServiceI;
import com.mobian.service.MbBalanceServiceI;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户已确认,结算完成状态
 * Created by wanxp on 17-9-26.
 */
@Service("deliverOrder40StateImpl")
public class DeliverOrder40StateImpl implements DeliverOrderState {

    @Autowired
    private DeliverOrderServiceI deliverOrderService;

    @Autowired
    private DeliverOrderShopServiceI deliverOrderShopService;

    @Autowired
    private MbBalanceServiceI mbBalanceService;

    @Autowired
    private MbBalanceLogServiceI mbBalanceLogService;

    @Autowired
    private DeliverOrderShopPayServiceI deliverOrderShopPayService;

    @Override
    public String getStateName() {
        return "40";
    }

    @Override
    public void handle(DeliverOrder deliverOrder) {
        //修改运单状态
        DeliverOrder order = new DeliverOrder();
        order.setId(deliverOrder.getId());
        order.setStatus(prefix + getStateName());
        deliverOrderService.edit(order);

        //修改运单门店状态
        DeliverOrderShop deliverOrderShop = new DeliverOrderShop();
        deliverOrderShop.setStatus(deliverOrderShopService.STATUS_ACCEPTED);
        deliverOrderShop.setDeliverOrderId(order.getId());
        deliverOrderShop = deliverOrderShopService.editStatus(deliverOrderShop,deliverOrderShopService.STATUS_COMPLETE);

        //门店结算
        //TODO 只做了给门店运费账增加,未做其他地方减少,要保持一致性,必须完成这一点
        DeliverOrderShopPay deliverOrderShopPay = new DeliverOrderShopPay();
        deliverOrderShopPay.setDeliverOrderId(deliverOrder.getId());
        deliverOrderShopPay.setShopId(deliverOrderShop.getShopId());
        List<DeliverOrderShopPay> orderShopPays = deliverOrderShopPayService.list(deliverOrderShopPay);
        if (CollectionUtils.isNotEmpty(orderShopPays)) {
            deliverOrderShopPay = orderShopPays.get(0);
            MbBalance balance = mbBalanceService.addOrGetMbBalanceDelivery(deliverOrderShop.getShopId());
            MbBalanceLog balanceLog = new MbBalanceLog();
            balanceLog.setBalanceId(balance.getId());
            balanceLog.setRefId(deliverOrderShopPay.getId() + "");
            balanceLog.setRefType("BT060");
            //TODO 这里不知道是否写对?
            balanceLog.setAmount(deliverOrderShopPay.getAmount());

            balanceLog.setReason(String.format("门店[ID:%1$s]完成运单[ID:%2$s]结算转入", deliverOrderShop.getShopId(), order.getId()));
            mbBalanceLogService.addAndUpdateBalance(balanceLog);
        }
    }

    @Override
    public DeliverOrderState next(DeliverOrder deliverOrder) {
        return null;
    }
}
