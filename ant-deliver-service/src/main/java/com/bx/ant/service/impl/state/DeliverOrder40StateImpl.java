package com.bx.ant.service.impl.state;

import com.bx.ant.pageModel.DeliverOrderExt;
import com.bx.ant.service.*;
import com.mobian.pageModel.*;
import com.bx.ant.pageModel.DeliverOrder;
import com.bx.ant.pageModel.DeliverOrderShop;
import com.bx.ant.pageModel.DeliverOrderShopPay;
import com.mobian.service.MbBalanceLogServiceI;
import com.mobian.service.MbBalanceServiceI;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    private DeliverOrderLogServiceI deliverOrderLogService;

    @Override
    public String getStateName() {
        return "40";
    }

    @Override
    public void handle(DeliverOrder deliverOrder) {
        //修改运单状态
        DeliverOrder orderNew = new DeliverOrder();
        orderNew.setId(deliverOrder.getId());
        orderNew.setStatus(prefix + getStateName());
        orderNew.setDeliveryStatus(DeliverOrderServiceI.DELIVER_STATUS_USER_CHECK);
        orderNew.setShopPayStatus(DeliverOrderServiceI.SHOP_PAY_STATUS_SUCCESS);
        deliverOrderService.editAndAddLog(orderNew, DeliverOrderLogServiceI.TYPE_COMPLETE_DELIVER_ORDER, "运单已完成");

        //修改运单门店状态
        DeliverOrderShop deliverOrderShop = new DeliverOrderShop();
        deliverOrderShop.setStatus(DeliverOrderShopServiceI.STATUS_ACCEPTED);
        deliverOrderShop.setDeliverOrderId(orderNew.getId());
        deliverOrderShop = deliverOrderShopService.editStatus(deliverOrderShop,DeliverOrderShopServiceI.STATUS_COMPLETE);

        //门店结算
        //TODO 只做了给门店运费账增加,未做其他地方减少,要保持一致性,必须完成这一点
        DeliverOrderShopPay deliverOrderShopPay = new DeliverOrderShopPay();
        deliverOrderShopPay.setDeliverOrderId(deliverOrder.getId());
        deliverOrderShopPay.setShopId(deliverOrderShop.getShopId());
        List<DeliverOrderShopPay> orderShopPays = deliverOrderShopPayService.list(deliverOrderShopPay);
        if (CollectionUtils.isNotEmpty(orderShopPays)) {

            //修改运单门店支付状态
            deliverOrderShopPay = orderShopPays.get(0);
            deliverOrderShopPay.setStatus(DeliverOrderServiceI.SHOP_PAY_STATUS_SUCCESS);
            deliverOrderShopPayService.edit(deliverOrderShopPay);

            MbBalance balance = mbBalanceService.addOrGetMbBalanceDelivery(deliverOrderShop.getShopId());
            MbBalanceLog balanceLog = new MbBalanceLog();
            balanceLog.setBalanceId(balance.getId());
            balanceLog.setRefId(deliverOrderShopPay.getId() + "");
            if (deliverOrder instanceof DeliverOrderExt) {
                DeliverOrderExt orderExt = (DeliverOrderExt) deliverOrder;
                balanceLog.setRefType(orderExt.getBalanceLogType());
            } else {
                balanceLog.setRefType("BT060");
            }
            //TODO 这里不知道是否写对?
            balanceLog.setAmount(deliverOrderShopPay.getAmount());

            balanceLog.setReason(String.format("门店[ID:%1$s]完成运单[ID:%2$s]结算转入", deliverOrderShop.getShopId(), orderNew.getId()));
            mbBalanceLogService.addAndUpdateBalance(balanceLog);
        }
    }

    @Override
    public DeliverOrderState next(DeliverOrder deliverOrder) {
        return null;
    }
}
