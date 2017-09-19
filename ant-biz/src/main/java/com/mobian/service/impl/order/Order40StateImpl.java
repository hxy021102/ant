package com.mobian.service.impl.order;

import com.mobian.pageModel.*;
import com.mobian.service.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 订单回桶完成
 * Created by wanxp on 7/11/17.
 */
@Service("order40StateImpl")
public class Order40StateImpl implements OrderState {
    @Autowired
    private MbOrderServiceI orderService;
    @Autowired
    private MbOrderLogServiceI orderLogService;
    @Autowired
    private MbOrderCallbackItemServiceI mbOrderCallbackItemService;
    @Autowired
    private MbOrderRefundItemServiceI mbOrderRefundItemService;
    @Autowired
    private MbItemStockServiceI mbItemStockService;
    @Autowired
    private MbUserServiceI mbUserService;
    @Autowired
    private MbShopServiceI mbShopService;
    @Autowired
    private MbItemServiceI mbItemService;
    @Autowired
    private MbOrderItemServiceI mbOrderItemService;
    @Autowired
    private MbPaymentServiceI mbPaymentService;
    @Autowired
    private MbPaymentItemServiceI mbPaymentItemService;
    @Autowired
    private MbOrderRefundLogServiceI mbOrderRefundLogService;
    @Autowired
    private MbBalanceServiceI mbBalanceService;
    @Autowired
    private MbBalanceLogServiceI mbBalanceLogService;
    @javax.annotation.Resource(name = "order45StateImpl")
    private OrderState orderState45;

    @Override
    public String getStateName() {
        return "40";
    }

    @Override
    public void handle(MbOrder mbOrder) {
        if (StringUtils.isNotEmpty(mbOrder.getLoginId())) {
            MbOrder mbOrderOld = orderService.get(mbOrder.getId());


            //TmbOrderLog添加订单记录
            MbOrderLog mbOrderLog = new MbOrderLog();
            mbOrderLog.setContent("【确认】" + "回空桶以及商品");
            mbOrderLog.setRemark(mbOrder.getConfirmCallbackRemark());
            mbOrderLog.setLoginId(mbOrder.getLoginId());
            mbOrderLog.setLogType("LT009");
            mbOrderLog.setOrderId(mbOrder.getId());
            orderLogService.add(mbOrderLog);
            //空桶入总仓 : 总仓+ ,分仓-
            MbOrderCallbackItem mbOrderCallbackItemA = new MbOrderCallbackItem();
            mbOrderCallbackItemA.setOrderId(mbOrder.getId());
            mbOrderCallbackItemA.setIsdeleted(false);
            List<MbOrderCallbackItem> mbOrderCallbackItems = mbOrderCallbackItemService.query(mbOrderCallbackItemA);

            MbOrderRefundItem mbOrderRefundItemA = new MbOrderRefundItem();
            mbOrderRefundItemA.setOrderId(mbOrder.getId());
            mbOrderRefundItemA.setIsdeleted(false);
            List<MbOrderRefundItem> mbOrderRefundItems = mbOrderRefundItemService.query(mbOrderRefundItemA);

            //MbUser mbUser = mbUserService.getFromCache(mbOrderOld.getUserId());
            MbShop mbShop = mbShopService.getFromCache(mbOrderOld.getShopId());

            for (MbOrderCallbackItem mbOrderCallbackItem : mbOrderCallbackItems) {

                //总仓增加空桶
                if (mbOrderOld.getDeliveryWarehouseId() != null) {
                    MbItemStock mbItemStock = mbItemStockService.getByWareHouseIdAndItemId(mbOrderOld.getDeliveryWarehouseId(), mbOrderCallbackItem.getItemId());
                    MbItemStock change = new MbItemStock();
                    change.setId(mbItemStock.getId());
                    change.setAdjustment(mbOrderCallbackItem.getQuantity());
                    change.setLogType("SL02");
                    change.setReason(String.format("订单ID:%s空桶入库，库存：%s", mbOrder.getId(),mbItemStock.getQuantity()+mbOrderCallbackItem.getQuantity()));
                    mbItemStockService.editAndInsertLog(change, mbOrder.getLoginId());
                }

                //分仓减少空桶
                MbItemStock mbItemStockShop = mbItemStockService.getByWareHouseIdAndItemId(mbShop.getWarehouseId(), mbOrderCallbackItem.getItemId());
                MbItemStock changeShop = new MbItemStock();
                changeShop.setId(mbItemStockShop.getId());
                changeShop.setAdjustment(-mbOrderCallbackItem.getQuantity());
                changeShop.setLogType("SL03");
                changeShop.setReason(String.format("订单ID:%s空桶出库，库存：%s", mbOrder.getId(),mbItemStockShop.getQuantity()-mbOrderCallbackItem.getQuantity()));
                mbItemStockService.editAndInsertLog(changeShop, mbOrder.getLoginId());

                //加桶钱
                MbBalance mbBalance = mbBalanceService.addOrGetMbBalanceCash(mbOrderOld.getShopId());
                MbItem packItem = mbItemService.getFromCache(mbOrderCallbackItem.getItemId());
                MbBalanceLog mbBalanceLog = new MbBalanceLog();
                mbBalanceLog.setAmount(packItem.getMarketPrice() * mbOrderCallbackItem.getQuantity());
                mbBalanceLog.setRefId(mbOrder.getId() + "");
                mbBalanceLog.setRefType("BT018");
                mbBalanceLog.setBalanceId(mbBalance.getId());
                mbBalanceLog.setReason(String.format("订单ID：%s回桶出库 商品[%s],数量[%s]", mbOrder.getId(), packItem.getName(), mbOrderCallbackItem.getQuantity()));
                mbBalanceLogService.addAndUpdateBalance(mbBalanceLog);


                //TODO 分仓商品减少
//                MbItem mbItemPack = new MbItem();
//                mbItemPack.setPackId(mbOrderCallbackItem.getId());
//                List<MbItem> mbItems = mbItemService.query(mbItemPack);
//                if(mbItems != null && mbItems.size() > 0){
//                    MbItem mbItem = mbItems.get(0);
//                    mbItemStockShop = mbItemStockService.getByWareHouseIdAndItemId(mbShop.getWarehouseId(), mbItem.getId());
//                    changeShop = new MbItemStock();
//                    changeShop.setId(mbItemStockShop.getId());
//                    changeShop.setAdjustment(-mbOrderCallbackItem.getQuantity());
//                    changeShop.setLogType("SL03");
//                    changeShop.setReason(String.format("订单ID:%s发货出库", mbOrder.getId()));
//                    mbItemStockService.editAndInsertLog(changeShop, mbOrder.getLoginId());
//                }


            }


            //退回商品:总仓+ 分仓-
            //退款金额
            Integer refundAmount = 0;
            for (MbOrderRefundItem mbOrderRefundItem : mbOrderRefundItems) {
                if (mbOrderOld.getDeliveryWarehouseId() != null) {
                    //总仓增加商品
                    MbItemStock mbItemStock = mbItemStockService.getByWareHouseIdAndItemId(mbOrderOld.getDeliveryWarehouseId(), mbOrderRefundItem.getItemId());
                    MbItemStock change = new MbItemStock();
                    change.setId(mbItemStock.getId());
                    change.setAdjustment(mbOrderRefundItem.getQuantity());
                    change.setLogType("SL02");
                    change.setReason(String.format("订单ID:%s退货入库，库存：%s", mbOrder.getId(),mbItemStock.getQuantity()+mbOrderRefundItem.getQuantity()));
                    mbItemStockService.editAndInsertLog(change, mbOrder.getLoginId());
                }

                //分仓减少商品
                MbItemStock mbItemStockShop = mbItemStockService.getByWareHouseIdAndItemId(mbShop.getWarehouseId(), mbOrderRefundItem.getItemId());
                MbItemStock changeShop = new MbItemStock();
                changeShop.setId(mbItemStockShop.getId());
                changeShop.setAdjustment(-mbOrderRefundItem.getQuantity());
                changeShop.setLogType("SL03");
                changeShop.setReason(String.format("订单ID:%s退货出库，库存：%s", mbOrder.getId(),mbItemStockShop.getQuantity()-mbOrderRefundItem.getQuantity()));
                mbItemStockService.editAndInsertLog(changeShop, mbOrder.getLoginId());

                //分仓空桶减少
                MbItem mbItem = mbItemService.getFromCache(mbOrderRefundItem.getItemId());
                if (mbItem != null && mbItem.getPackId() != null) {
                    mbItemStockShop = mbItemStockService.getByWareHouseIdAndItemId(mbShop.getWarehouseId(), mbItem.getPackId());
                    changeShop = new MbItemStock();
                    changeShop.setId(mbItemStockShop.getId());
                    changeShop.setAdjustment(-mbOrderRefundItem.getQuantity());
                    changeShop.setLogType("SL03");
                    changeShop.setReason(String.format("订单ID：%s退货出库，库存:%s", mbOrder.getId(),mbItemStockShop.getQuantity()-mbOrderRefundItem.getQuantity()));
                    mbItemStockService.editAndInsertLog(changeShop, mbOrder.getLoginId());

                    //加桶钱
                    MbBalance mbBalance = mbBalanceService.addOrGetMbBalanceCash(mbOrderOld.getShopId());
                    MbItem packItem = mbItemService.getFromCache(mbItem.getPackId());
                    MbBalanceLog mbBalanceLog = new MbBalanceLog();
                    mbBalanceLog.setAmount(packItem.getMarketPrice() * mbOrderRefundItem.getQuantity());
                    mbBalanceLog.setRefId(mbOrder.getId() + "");
                    mbBalanceLog.setRefType("BT017");
                    mbBalanceLog.setBalanceId(mbBalance.getId());
                    mbBalanceLog.setReason(String.format("订单ID：%s退货出库 商品[%s],数量[%s]", mbOrder.getId(), packItem.getName(), mbOrderRefundItem.getQuantity()));
                    mbBalanceLogService.addAndUpdateBalance(mbBalanceLog);

                }

                //根据退货商品计算退款金额
                MbOrderItem mbOrderItem = new MbOrderItem();
                mbOrderItem.setOrderId(mbOrder.getId());
                mbOrderItem.setItemId(mbOrderRefundItem.getItemId());

                List<MbOrderItem> mbOrderItems = mbOrderItemService.query(mbOrderItem);
                if (mbOrderItems != null && mbOrderItems.size() > 0) {
                    Collections.sort(mbOrderItems, new Comparator<MbOrderItem>() {
                        @Override
                        public int compare(MbOrderItem o1, MbOrderItem o2) {
                            return o1.getBuyPrice() - o2.getBuyPrice();
                        }
                    });
                    int refundQuantity = mbOrderRefundItem.getQuantity();
                    for (MbOrderItem orderItem : mbOrderItems) {
                        if (refundQuantity > 0) {
                            int reduce;
                            if (refundQuantity > orderItem.getQuantity()) {
                                reduce = orderItem.getQuantity();
                            } else {
                                reduce = refundQuantity;
                            }
                            refundQuantity = refundQuantity - reduce;
                            refundAmount += reduce * orderItem.getBuyPrice();
                        }else{
                            break;
                        }
                    }
                }
            }

            //将退款金额写入退款记录
            if ("PS05".equals(mbOrderOld.getPayStatus())&&refundAmount>0) {
                MbPayment mbPayment = mbPaymentService.getByOrderId(mbOrder.getId());
                if (mbPayment != null) {
                    List<MbPaymentItem> mbPaymentItems = mbPaymentItemService.getByPaymentId(mbPayment.getId());
                    if (CollectionUtils.isNotEmpty(mbPaymentItems)) {
                        //TODO 支付明细 暂时只有一种
                        MbPaymentItem mbPaymentItem = mbPaymentItems.get(0);

                        mbOrderLog = new MbOrderLog();
                        mbOrderLog.setContent("订单部分商品退货，退相应金额");
                        mbOrderLog.setLoginId(mbOrder.getLoginId());
                        mbOrderLog.setLogType("LT006");
                        mbOrderLog.setOrderId(mbOrder.getId());
                        orderLogService.add(mbOrderLog);
                        MbOrderRefundLog mbOrderRefundLog = new MbOrderRefundLog();
                        mbOrderRefundLog.setAmount(refundAmount);
                        mbOrderRefundLog.setPayWay(mbPaymentItem.getPayWay());
                        mbOrderRefundLog.setPaymentItemId(mbPaymentItem.getId());
                        mbOrderRefundLog.setOrderId(mbPayment.getOrderId());
                        mbOrderRefundLog.setOrderType(mbPayment.getOrderType());
                        mbOrderRefundLog.setReason("订单部分商品退货，退相应金额");
                        //退余额
                        mbOrderRefundLog.setRefundWay("RW02");
                        mbOrderRefundLogService.add(mbOrderRefundLog);


                        MbBalance balance = mbBalanceService.addOrGetMbBalance(mbOrderOld.getShopId());
                        MbBalanceLog log = new MbBalanceLog();
                        log.setBalanceId(balance.getId());
                        log.setAmount(refundAmount);
                        log.setRefType("BT006"); //订单部分退货，退余额
                        log.setRefId(mbOrderRefundLog.getId() + "");
                        mbBalanceLogService.addAndUpdateBalance(log);

                    }
                }
            }
            //TmbOrder状态转换,修改退款总金额
            mbOrder.setStatus(prefix + getStateName());
            if (mbOrderOld.getTotalRefundAmount() == null) mbOrderOld.setTotalRefundAmount(0);
            mbOrder.setTotalRefundAmount(mbOrderOld.getTotalRefundAmount() + refundAmount);
            orderService.edit(mbOrder);

        }


    }

    @Override
    public OrderState next(MbOrder mbOrder) {
        if ("OD45".equals(mbOrder.getStatus())) {
            return orderState45;
        }
        return null;
    }
}
