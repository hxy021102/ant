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

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户已确认,结算完成状态
 * Created by wanxp on 17-9-26.
 */
@Service("deliverOrder40StateImpl")
public class DeliverOrder40StateImpl extends AbstractDeliverOrderState {

    @Autowired
    private DeliverOrderServiceI deliverOrderService;

    @Autowired
    private DeliverOrderShopServiceI deliverOrderShopService;

    @javax.annotation.Resource
    private MbBalanceServiceI mbBalanceService;

    @Resource
    private MbBalanceLogServiceI mbBalanceLogService;

    @Autowired
    private DeliverOrderShopPayServiceI deliverOrderShopPayService;


    @Override
    public String getStateName() {
        return "40";
    }

    @Override
    public void execute(DeliverOrder deliverOrder) {
        //条件检查
        //修改运单状态
        DeliverOrder orderNew = new DeliverOrder();
        orderNew.setId(deliverOrder.getId());
        orderNew.setStatus(prefix + getStateName());
//        orderNew.setDeliveryStatus(DeliverOrderServiceI.DELIVER_STATUS_USER_CHECK);
        orderNew.setShopPayStatus(DeliverOrderServiceI.SHOP_PAY_STATUS_SUCCESS);
        deliverOrderService.editAndAddLog(orderNew, DeliverOrderLogServiceI.TYPE_COMPLETE_DELIVER_ORDER, "运单已完成");

        //修改运单门店状态
        DeliverOrderShop deliverOrderShop = new DeliverOrderShop();
        deliverOrderShop.setStatus(DeliverOrderShopServiceI.STAUS_SERVICE);
        deliverOrderShop.setDeliverOrderId(orderNew.getId());
        deliverOrderShop.setShopPayStatus("SPS01");
        deliverOrderShop.setId(deliverOrder.getOrderShopId());
        DeliverOrderShop orderShopEdit = new DeliverOrderShop();
        orderShopEdit.setStatus(DeliverOrderShopServiceI.STATUS_COMPLETE);
        orderShopEdit.setShopPayStatus("SPS04");
        deliverOrderShopService.editStatusByHql(deliverOrderShop,orderShopEdit.getStatus(),orderShopEdit.getShopPayStatus());

        //门店结算
        DeliverOrderShopPay deliverOrderShopPay = new DeliverOrderShopPay();
        deliverOrderShopPay.setDeliverOrderShopId(deliverOrder.getOrderShopId());
        List<DeliverOrderShopPay> orderShopPays = deliverOrderShopPayService.list(deliverOrderShopPay);
        if (CollectionUtils.isNotEmpty(orderShopPays)) {
            //修改运单门店支付状态
            deliverOrderShopPay = orderShopPays.get(0);
            deliverOrderShopPay.setStatus(DeliverOrderServiceI.SHOP_PAY_STATUS_SUCCESS);
            deliverOrderShopPay.setPayWay(deliverOrder.getPayWay());
            deliverOrderShopPayService.edit(deliverOrderShopPay);

            MbBalance balance = mbBalanceService.addOrGetMbBalanceDelivery(deliverOrder.getShopId());
            MbBalanceLog balanceLog = new MbBalanceLog();
            balanceLog.setBalanceId(balance.getId());
            balanceLog.setRefId(deliverOrderShopPay.getId() + "");
            if (deliverOrder instanceof DeliverOrderExt) {
                DeliverOrderExt orderExt = (DeliverOrderExt) deliverOrder;
                balanceLog.setRefType(orderExt.getBalanceLogType());
            } else {
                balanceLog.setRefType("BT060");
            }
            balanceLog.setAmount(deliverOrderShopPay.getAmount());
            balanceLog.setReason(String.format("门店[ID:%1$s]完成运单[ID:%2$s]结算转入", deliverOrder.getShopId(), orderNew.getId()));
            mbBalanceLogService.addAndUpdateBalance(balanceLog);
        }
    }

    @Override
    public DeliverOrderState next(DeliverOrder deliverOrder) {
        return null;
    }
}
