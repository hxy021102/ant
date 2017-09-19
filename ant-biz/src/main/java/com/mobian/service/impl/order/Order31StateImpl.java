package com.mobian.service.impl.order;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.mobian.absx.F;
import com.mobian.exception.ServiceException;
import com.mobian.pageModel.*;
import com.mobian.service.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 交易失败-退余额
 * Created by john on 16/10/30.
 */
@Service("order31StateImpl")
public class Order31StateImpl implements OrderState {
    @Autowired
    private MbOrderServiceI orderService;
    @Autowired
    private MbPaymentServiceI mbPaymentService;
    @Autowired
    private MbPaymentItemServiceI mbPaymentItemService;
    @Autowired
    private MbOrderLogServiceI orderLogService;
    @Autowired
    private MbOrderRefundLogServiceI mbOrderRefundLogService;
    @Autowired
    private MbBalanceServiceI mbBalanceService;

    @Autowired
    private MbBalanceLogServiceI mbBalanceLogService;
    @Autowired
    private MbShopCouponsLogServiceI mbShopCouponsLogService;
    @Autowired
    private MbShopCouponsServiceI mbShopCouponsService;

    @Override
    public String getStateName() {
        return "31";
    }

    @Override
    public void handle(MbOrder mbOrder) {
        // 修改订单状态为交易完成，配送状态已签收
        mbOrder.setStatus(prefix + getStateName());
        String remark = mbOrder.getUserRemark();
        mbOrder.setUserRemark(null);
        orderService.edit(mbOrder);
        MbPayment mbPayment = mbPaymentService.getByOrderId(mbOrder.getId());
        if (mbPayment != null) {
            MbOrderLog mbOrderLog = new MbOrderLog();
            mbOrderLog.setContent("取消订单，退余额");
            mbOrderLog.setRemark(remark);
            mbOrderLog.setLoginId(mbOrder.getLoginId());
            mbOrderLog.setLogType("LT006");
            mbOrderLog.setOrderId(mbOrder.getId());
            orderLogService.add(mbOrderLog);
            List<MbPaymentItem> mbPaymentItemList = mbPaymentItemService.getByPaymentId(mbPayment.getId());
            if (CollectionUtils.isNotEmpty(mbPaymentItemList)) {
                Iterator<MbPaymentItem> paymentItemIterator = mbPaymentItemList.iterator();
                while (paymentItemIterator.hasNext()) {
                    MbPaymentItem mbPaymentItem = paymentItemIterator.next();
                    MbOrderRefundLog mbOrderRefundLog = new MbOrderRefundLog();
                    mbOrderRefundLog.setAmount(mbPaymentItem.getAmount());
                    mbOrderRefundLog.setPayWay(mbPaymentItem.getPayWay());
                    mbOrderRefundLog.setPaymentItemId(mbPaymentItem.getId());
                    mbOrderRefundLog.setOrderId(mbPayment.getOrderId());
                    mbOrderRefundLog.setOrderType(mbPayment.getOrderType());
                    mbOrderRefundLog.setReason(remark);
                    if (mbPaymentItemService.PAY_WAY_VOUCHER.equals(mbPaymentItem.getPayWay())) {
                        mbOrderRefundLog.setRefundWay(mbOrderRefundLogService.REFUND_WAY_VOUCHER);
                        mbOrderRefundLogService.add(mbOrderRefundLog);
                        //退水票
                        try {
                            Map<Integer, Integer> shopCouUsedMap = JSON.parseObject(mbPaymentItem.getRemark(), HashMap.class);
                            if (shopCouUsedMap != null && !shopCouUsedMap.isEmpty()) {
                                for (Map.Entry<Integer, Integer> entry : shopCouUsedMap.entrySet()) {
                                    MbShopCouponsLog mbShopCouponsLog = new MbShopCouponsLog();
                                    mbShopCouponsLog.setShopCouponsId(entry.getKey());
                                    mbShopCouponsLog.setQuantityUsed(-entry.getValue());
                                    mbShopCouponsLog.setShopCouponsStatus(mbShopCouponsService.SHOP_COUPONS_STATUS_ACTIVE);
                                    mbShopCouponsLog.setReason(String.format("接单审核,不同意订单[ID：%s],退回券票", mbOrder.getId()));
                                    mbShopCouponsLog.setRefId(mbOrderRefundLog.getId().toString());
                                    mbShopCouponsLog.setRefType(mbShopCouponsLogService.SHOP_COUPONS_LOG_TYPE_REFUND_BY_VOUCHER);
                                    mbShopCouponsLog.setLoginId(mbOrder.getLoginId());
                                    mbShopCouponsLogService.addLogAndUpdateShopCoupons(mbShopCouponsLog);
                                }
                            }
                        } catch (JSONException e) {
                            throw new ServiceException("支付明细Id:" + mbPaymentItem.getId() + ",字段remark(备注)非JSON格式:" + mbPaymentItem.getRemark());
                        }
                    } else {
                        //退余额
                        mbOrderRefundLog.setRefundWay("RW01");
                        mbOrderRefundLogService.add(mbOrderRefundLog);
                        if (!F.empty(mbPaymentItem.getAmount())) {
                            MbBalance balance = mbBalanceService.addOrGetMbBalance(OrderState.order.get().getShopId());
                            MbBalanceLog log = new MbBalanceLog();
                            log.setBalanceId(balance.getId());
                            log.setAmount(mbPaymentItem.getAmount());
                            log.setRefType("BT005"); //订单取消，退余额
                            log.setRefId(mbOrderRefundLog.getId() + "");
                            mbBalanceLogService.addAndUpdateBalance(log);
                        }
                    }
                }
            }
        }
    }

    @Override
    public OrderState next(MbOrder mbOrder) {
        return null;
    }
}
