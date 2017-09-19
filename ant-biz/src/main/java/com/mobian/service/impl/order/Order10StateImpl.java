package com.mobian.service.impl.order;

import com.mobian.absx.F;
import com.mobian.exception.ServiceException;
import com.mobian.pageModel.*;
import com.mobian.service.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 支付成功待发货状态
 * Created by john on 16/10/30.
 */
@Service("order10StateImpl")
public class Order10StateImpl implements OrderState {
    @Autowired
    private MbOrderServiceI orderService;
    @Autowired
    private MbOrderLogServiceI orderLogService;
    @Autowired
    private MbPaymentServiceI mbPaymentService;
    @Autowired
    private MbPaymentItemServiceI mbPaymentItemService;

    @Autowired
    private MbBalanceServiceI mbBalanceService;

    @Autowired
    private MbBalanceLogServiceI mbBalanceLogService;

    @Resource(name = "order12StateImpl")
    private OrderState orderState12;

    @Resource(name = "order31StateImpl")
    private OrderState orderState31;
    @Autowired
    private MbShopCouponsServiceI mbShopCouponsService;

    @Override
    public String getStateName() {
        return "10";
    }

    @Override
    public void handle(MbOrder mbOrder) {
        MbPaymentItem mbPaymentItem = new MbPaymentItem();
        // 修改订单状态，支付状态
        mbOrder.setStatus(prefix + getStateName());
        mbOrder.setPayStatus("PS05");
        mbOrder.setPayTime(new Date());
        mbOrder.setDeliveryStatus("DS01"); // 配送状态-待处理
        mbOrder.setUserRemark(null);
        orderService.edit(mbOrder);

        mbOrder.setShopId(OrderState.order.get().getShopId());

        //后台审核过来的逻辑
        if(StringUtils.isNotEmpty(mbOrder.getLoginId())) {
            //审核的日志
            MbOrderLog mbOrderLog = new MbOrderLog();
            mbOrderLog.setContent("【同意】" + mbOrder.getRemark());
            mbOrderLog.setRemark(mbOrder.getRemark());
            mbOrderLog.setLoginId(mbOrder.getLoginId());
            mbOrderLog.setLogType("LT004");
            mbOrderLog.setOrderId(mbOrder.getId());
            orderLogService.add(mbOrderLog);
            //用户备注置为空

            //修改订单的银行汇款单号和原因
            mbPaymentItem = mbPaymentItemService.getMbPaymentItemPW03(mbOrder.getId());
            mbPaymentItem.setPayCode(mbOrder.getPayCode());
            mbPaymentItem.setRefId(mbPaymentItem.getPayCode());
            mbPaymentItemService.editAudit(mbPaymentItem);

            mbOrder.setTotalPrice(mbPaymentItem.getAmount());
            mbOrder.setPayWay(mbPaymentItem.getPayWay());
            mbOrder.setPaymentId(mbPaymentItem.getPaymentId());
            mbOrder.setShopId(OrderState.order.get().getShopId());
        } else {
            // 插入支付明细
            mbPaymentItem.setPaymentId(mbOrder.getPaymentId());
            mbPaymentItem.setPayWay(mbOrder.getPayWay());
            mbPaymentItem.setAmount(mbOrder.getTotalPrice());
            mbPaymentItem.setRefId(mbOrder.getRefId());
            mbPaymentItemService.add(mbPaymentItem);
        }


        //水票支付处理
        if (mbOrder.getUsedByCoupons() != null && mbOrder.getUsedByCoupons()) {
            List<MbOrderItem> orderItems = mbOrder.getMbOrderItemList();
            if (CollectionUtils.isNotEmpty(orderItems)) {
                for (MbOrderItem orderItem : orderItems) {
                    //添加水票支付明细
                    MbPaymentItem paymentItem = new MbPaymentItem();
                    paymentItem.setPaymentId(mbOrder.getPaymentId());
                    paymentItem.setPayWay(mbPaymentItemService.PAY_WAY_VOUCHER);
                    //将水票数量写在支付金额里
                    paymentItem.setAmount(orderItem.getVoucherQuantityUsed());

                    //更新门店折券
                    mbShopCouponsService.editShopCouponsAndAddPaymentItem(mbOrder.getShopId(), orderItem.getItemId(),
                            paymentItem, OrderState.order.get().getAddLoginId(), mbOrder.getId());
                }
            }
        }

        // 余额支付，扣除余额插入余额明细,若支付全使用水票导致未更改余额则不添加余额明细
        if("PW01".equals(mbOrder.getPayWay())) {
            MbBalance balance = mbBalanceService.addOrGetMbBalance(mbOrder.getShopId());
            MbBalanceLog log = new MbBalanceLog();
            log.setBalanceId(balance.getId());
            log.setAmount(-mbOrder.getTotalPrice());
            log.setRefType("BT002"); // 余额付款
            log.setRefId(mbOrder.getPaymentId()+"");
            mbBalanceLogService.addAndUpdateBalance(log);
        }else{
            String refTypeIn, refTypeOut;
            if ("PW02".equals(mbOrder.getPayWay())) {
                refTypeIn = "BT007";
                refTypeOut = "BT008";
            } else if ("PW03".equals(mbOrder.getPayWay())) {
                refTypeIn = "BT009";
                refTypeOut = "BT010";
            } else {
                throw new ServiceException("请选择付款方式");
            }
            Integer shopId = mbOrder.getShopId();
            if(F.empty(shopId)){
                shopId = OrderState.order.get().getShopId();
            }

            MbBalance balance = mbBalanceService.addOrGetMbBalance(shopId);
            MbBalanceLog log = new MbBalanceLog();
            log.setBalanceId(balance.getId());
            log.setAmount(mbOrder.getTotalPrice());
            log.setRefType(refTypeIn);
            log.setRefId(mbOrder.getPaymentId() + "");
            mbBalanceLogService.add(log);
            log = new MbBalanceLog();
            log.setBalanceId(balance.getId());
            log.setAmount(-mbOrder.getTotalPrice());
            log.setRefType(refTypeOut);
            log.setRefId(mbOrder.getPaymentId() + "");
            mbBalanceLogService.add(log);
        }
    }

    @Override
    public OrderState next(MbOrder mbOrder) {
        if("OD12".equals(mbOrder.getStatus())){
            return orderState12;
        }else if("OD31".equals(mbOrder.getStatus())){
            return orderState31;
        }
        return null;
    }
}
