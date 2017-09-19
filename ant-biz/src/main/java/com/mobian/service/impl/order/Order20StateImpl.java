package com.mobian.service.impl.order;

import com.mobian.pageModel.*;
import com.mobian.service.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 已发货等待收货状态
 * Created by john on 16/10/30.
 */
@Service("order20StateImpl")
public class Order20StateImpl implements OrderState {
    @Autowired
    private MbOrderServiceI orderService;

    @Autowired
    private MbOrderLogServiceI orderLogService;
    @Autowired
    private MbItemServiceI mbItemService;

    @Autowired
    private MbItemStockServiceI mbItemStockService;

    @Autowired
    private MbOrderItemServiceI mbOrderItemService;

    @Autowired
    private MbBalanceLogServiceI mbBalanceLogService;

    @Autowired
    private MbBalanceServiceI mbBalanceService;

    @Autowired
    private MbUserServiceI mbUserService;

    @Autowired
    private MbShopServiceI mbShopService;

    @Resource(name = "order30StateImpl")
    private OrderState orderState30;

    @Resource(name = "order35StateImpl")
    private OrderState orderState35;
    @Resource(name = "order32StateImpl")
    private OrderState orderState32;

    @Override
    public String getStateName() {
        return "20";
    }

    @Override
    public void handle(MbOrder mbOrder) {
        MbOrder mbOrderOld = OrderState.order.get();
        //总仓-，分仓+
        List<MbOrderItem> mbOrderItems = mbOrderItemService.getMbOrderItemList(mbOrder.getId());
        for (MbOrderItem mbOrderItem : mbOrderItems) {
            MbItemStock mbItemStock = mbItemStockService.getByWareHouseIdAndItemId(mbOrder.getDeliveryWarehouseId(), mbOrderItem.getItemId());

            MbItemStock change = new MbItemStock();
            change.setId(mbItemStock.getId());
            change.setAdjustment(-mbOrderItem.getQuantity());
            change.setLogType("SL03");
            change.setReason(String.format("订单ID：%s发货出库，库存：%s", mbOrder.getId(),mbItemStock.getQuantity()-mbOrderItem.getQuantity()));
            mbItemStockService.editAndInsertLog(change, mbOrder.getLoginId());
            if(mbOrderOld.getShopId() == null) {
                MbUser mbUser = mbUserService.getFromCache(mbOrderOld.getUserId());
                mbOrderOld.setShopId(mbUser.getShopId());
            }
            MbShop shop = mbShopService.getFromCache(mbOrderOld.getShopId());


            MbItemStock mbItemStockShop = mbItemStockService.getByWareHouseIdAndItemId(shop.getWarehouseId(), mbOrderItem.getItemId());
            MbItemStock changeShop = new MbItemStock();
            changeShop.setId(mbItemStockShop.getId());
            changeShop.setAdjustment(mbOrderItem.getQuantity());
            changeShop.setLogType("SL02");
            changeShop.setReason(String.format("订单ID：%s发货入库，库存：%s", mbOrder.getId(),mbItemStockShop.getQuantity()+mbOrderItem.getQuantity()));
            mbItemStockService.editAndInsertLog(changeShop, mbOrder.getLoginId());

            //包装的空桶数
            MbItem mbItem = mbItemService.getFromCache(mbOrderItem.getItemId());
            if (mbItem != null && mbItem.getPackId() != null) {
                mbItemStockShop = mbItemStockService.getByWareHouseIdAndItemId(shop.getWarehouseId(),mbItem.getPackId());
                changeShop = new MbItemStock();
                changeShop.setId(mbItemStockShop.getId());
                changeShop.setAdjustment(mbOrderItem.getQuantity());
                changeShop.setLogType("SL02");
                changeShop.setReason(String.format("订单ID：%s发货入库，库存：%s", mbOrder.getId(),mbItemStockShop.getQuantity()+mbOrderItem.getQuantity()));
                mbItemStockService.editAndInsertLog(changeShop, mbOrder.getLoginId());

                //扣桶钱
                MbBalance mbBalance = mbBalanceService.addOrGetMbBalanceCash(mbOrderOld.getShopId());
                MbItem packItem = mbItemService.getFromCache(mbItem.getPackId());
                MbBalanceLog mbBalanceLog = new MbBalanceLog();
                mbBalanceLog.setAmount(-packItem.getMarketPrice() * mbOrderItem.getQuantity());
                mbBalanceLog.setRefId(mbOrder.getId() + "");
                mbBalanceLog.setRefType("BT016");
                mbBalanceLog.setBalanceId(mbBalance.getId());
                mbBalanceLog.setReason(String.format("订单ID：%s发货入库 商品[%s],数量[%s]", mbOrder.getId(), packItem.getName(), mbOrderItem.getQuantity()));
                mbBalanceLogService.addAndUpdateBalance(mbBalanceLog);
            }
        }

        // 说明已发货，修改配送状态
        mbOrder.setStatus(prefix + getStateName());
        mbOrder.setDeliveryStatus("DS10"); // 配送状态-已发货
        mbOrder.setDeliveryTime(new Date());
        String remark = mbOrder.getUserRemark();
        mbOrder.setUserRemark(null);
        orderService.edit(mbOrder);

        if (StringUtils.isNotEmpty(mbOrder.getLoginId())) {
            MbOrderLog mbOrderLog = new MbOrderLog();
            mbOrderLog.setContent("发货成功");
            mbOrderLog.setRemark(remark);
            mbOrderLog.setLoginId(mbOrder.getLoginId());
            mbOrderLog.setLogType("LT002");
            mbOrderLog.setOrderId(mbOrder.getId());
            orderLogService.add(mbOrderLog);
        }

    }

    @Override
    public OrderState next(MbOrder mbOrder) {
        // 已发货之后没有其他操作，暂下一个状态为已完成
        if(mbOrder.getStatus().equals("OD35")){//若是司机备注状态则返回OD35状态机
            return orderState35;
        }else if(mbOrder.getStatus().equals("OD30")){//若是用户评论状态则返回OD30状态机
            return orderState30;
        }else if(mbOrder.getStatus().equals("OD32")){
            return orderState32;
        }
        return null;
    }
}
