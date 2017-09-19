package com.mobian.service.impl.order;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
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
 * 交易失败-后台取消订单
 * Created by john on 16/10/30.
 */
@Service("order32StateImpl")
public class Order32StateImpl implements OrderState {
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
    private MbItemStockServiceI mbItemStockService;
    @Autowired
    private MbBalanceLogServiceI mbBalanceLogService;
    @Autowired
    private MbOrderItemServiceI mbOrderItemService;
    @Autowired
    private MbShopServiceI mbShopService;
    @Autowired
    private MbUserServiceI mbUserService;

    @Autowired
    private MbItemServiceI mbItemService;

    @Autowired
    private MbShopCouponsServiceI mbShopCouponsService;
    @Autowired
    private MbShopCouponsLogServiceI mbShopCouponsLogService;

    @Override
    public String getStateName() {
        return "32";
    }

    @Override
    public void handle(MbOrder mbOrder) {
        // 修改订单状态为交易完成，配送状态已签收
        mbOrder.setStatus(prefix + getStateName());
        String remark = mbOrder.getRemark();
        mbOrder.setUserRemark(null);
        orderService.edit(mbOrder);
        MbOrder mbOrderOld = OrderState.order.get();
        MbOrderLog mbOrderLog = new MbOrderLog();
        mbOrderLog.setContent("后台取消订单，取消前状态：" + mbOrderOld.getOrderStatusName());
        mbOrderLog.setRemark(remark);
        mbOrderLog.setLoginId(mbOrder.getLoginId());
        mbOrderLog.setLogType("LT020");
        mbOrderLog.setOrderId(mbOrder.getId());
        orderLogService.add(mbOrderLog);
        //1、退钱
        MbPayment mbPayment = mbPaymentService.getByOrderId(mbOrder.getId());
        if (mbPayment != null) {
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
                            Map<Integer, Integer> shopCouUsedMap = JSON.parseObject(mbPaymentItem.getRemark(),HashMap.class);
                            if (shopCouUsedMap != null && !shopCouUsedMap.isEmpty()) {
                                for (Map.Entry<Integer, Integer> entry : shopCouUsedMap.entrySet()
                                     ) {
                                    MbShopCouponsLog mbShopCouponsLog = new MbShopCouponsLog();
                                    mbShopCouponsLog.setShopCouponsId(entry.getKey());
                                    mbShopCouponsLog.setQuantityUsed( - entry.getValue());
                                    mbShopCouponsLog.setShopCouponsStatus(mbShopCouponsService.SHOP_COUPONS_STATUS_ACTIVE);
                                    mbShopCouponsLog.setReason(String.format("后台取消订单[ID：%s],退回券票" ,mbOrder.getId()));
                                    mbShopCouponsLog.setRefId(mbOrderRefundLog.getId().toString());
                                    mbShopCouponsLog.setRefType(mbShopCouponsLogService.SHOP_COUPONS_LOG_TYPE_REFUND_BY_VOUCHER);
                                    mbShopCouponsLog.setLoginId(mbOrder.getLoginId());
                                    mbShopCouponsLogService.addLogAndUpdateShopCoupons(mbShopCouponsLog);
                                }
                            }
                        }catch (JSONException e) {
                            throw new ServiceException("支付明细Id:" + mbPaymentItem.getId() + ",字段remark(备注)非JSON格式:" + mbPaymentItem.getRemark());
                        }


                    }else {
                        //退余额
                        mbOrderRefundLog.setRefundWay("RW01");
                        mbOrderRefundLogService.add(mbOrderRefundLog);
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
        //2.0退货
        if ("OD20|OD30|OD35".indexOf(mbOrderOld.getStatus()) > -1) {
            //总仓+，分仓-
            List<MbOrderItem> mbOrderItems = mbOrderItemService.getMbOrderItemList(mbOrder.getId());
            for (MbOrderItem mbOrderItem : mbOrderItems) {
                MbItemStock mbItemStock = mbItemStockService.getByWareHouseIdAndItemId(mbOrderOld.getDeliveryWarehouseId(), mbOrderItem.getItemId());
                MbItemStock change = new MbItemStock();
                change.setId(mbItemStock.getId());
                change.setAdjustment(mbOrderItem.getQuantity());
                change.setLogType("SL02");
                change.setReason(String.format("订单ID：%s取消入库，库存：%s", mbOrder.getId(),mbItemStock.getQuantity()+mbOrderItem.getQuantity()));
                mbItemStockService.editAndInsertLog(change, mbOrder.getLoginId());
                if (mbOrderOld.getShopId() == null) {
                    MbUser mbUser = mbUserService.getFromCache(mbOrderOld.getUserId());
                    mbOrderOld.setShopId(mbUser.getShopId());
                }
                MbShop shop = mbShopService.getFromCache(mbOrderOld.getShopId());


                MbItemStock mbItemStockShop = mbItemStockService.getByWareHouseIdAndItemId(shop.getWarehouseId(), mbOrderItem.getItemId());
                MbItemStock changeShop = new MbItemStock();
                changeShop.setId(mbItemStockShop.getId());
                changeShop.setAdjustment(-mbOrderItem.getQuantity());
                changeShop.setLogType("SL03");
                changeShop.setReason(String.format("订单ID：%s取消出库，库存:%s", mbOrder.getId(),mbItemStockShop.getQuantity()-mbOrderItem.getQuantity()));
                mbItemStockService.editAndInsertLog(changeShop, mbOrder.getLoginId());

                //包装的空桶数
                MbItem mbItem = mbItemService.getFromCache(mbOrderItem.getItemId());
                if (mbItem != null && mbItem.getPackId() != null) {
                    mbItemStockShop = mbItemStockService.getByWareHouseIdAndItemId(shop.getWarehouseId(), mbItem.getPackId());
                    changeShop = new MbItemStock();
                    changeShop.setId(mbItemStockShop.getId());
                    changeShop.setAdjustment(-mbOrderItem.getQuantity());
                    changeShop.setLogType("SL03");
                    changeShop.setReason(String.format("订单ID：%s取消出库，库存：%s", mbOrder.getId(),mbItemStockShop.getQuantity()-mbOrderItem.getQuantity()));
                    mbItemStockService.editAndInsertLog(changeShop, mbOrder.getLoginId());

                    //加桶钱
                    MbBalance mbBalance = mbBalanceService.addOrGetMbBalanceCash(mbOrderOld.getShopId());
                    MbItem packItem = mbItemService.getFromCache(mbItem.getPackId());
                    MbBalanceLog mbBalanceLog = new MbBalanceLog();
                    mbBalanceLog.setAmount(packItem.getMarketPrice() * mbOrderItem.getQuantity());
                    mbBalanceLog.setRefId(mbOrder.getId() + "");
                    mbBalanceLog.setRefType("BT017");
                    mbBalanceLog.setBalanceId(mbBalance.getId());
                    mbBalanceLog.setReason(String.format("订单ID：%s取消出库 商品[%s],数量[%s]", mbOrder.getId(), packItem.getName(), mbOrderItem.getQuantity()));
                    mbBalanceLogService.addAndUpdateBalance(mbBalanceLog);
                }
            }
        }
        //TODO 40的单子如何取消
        if ("OD40".indexOf(mbOrderOld.getStatus()) > -1){

        }
    }


    @Override
    public OrderState next(MbOrder mbOrder) {
        return null;
    }
}
