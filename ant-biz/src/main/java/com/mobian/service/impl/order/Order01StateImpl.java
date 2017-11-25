package com.mobian.service.impl.order;

import com.bx.ant.pageModel.DeliverOrderShop;
import com.bx.ant.service.DeliverOrderShopServiceI;
import com.mobian.absx.F;
import com.mobian.exception.ServiceException;
import com.mobian.pageModel.*;
import com.mobian.service.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.List;

/**
 * 待付款状态
 * Created by john on 16/10/30.
 */
@Service("order01StateImpl")
public class Order01StateImpl implements OrderState {
    @Autowired
    private MbOrderServiceI orderService;

    @Resource(name = "order05StateImpl")
    private OrderState orderState05;
    @Resource(name = "order10StateImpl")
    private OrderState orderState10;
    @Resource(name = "order09StateImpl")
    private OrderState orderState09;

    @Autowired
    private MbOrderItemServiceI mbOrderItemService;
    @Autowired
    private MbItemServiceI mbItemService;
    @Autowired
    private MbOrderInvoiceServiceI mbOrderInvoiceService;
    @Autowired
    private MbUserServiceI mbUserService;

    @Autowired
    private MbBalanceServiceI mbBalanceService;
    @Resource
    private DeliverOrderShopServiceI deliverOrderShopService;

    @Override
    public String getStateName() {
        return "01";
    }

    @Override
    public void handle(MbOrder mbOrder) {
        // 生成订单
        mbOrder.setStatus(prefix + getStateName());
        mbOrder.setPayStatus("PS01"); // 待支付

        if (mbOrder.getShopId() == null && mbOrder.getUserId() != null) {
            MbUser mbUser = mbUserService.getFromCache(mbOrder.getUserId());
            mbOrder.setShopId(mbUser.getShopId());
        }

        validate(mbOrder);

        orderService.add(mbOrder);
        if (!F.empty(mbOrder.getDeliverOrderShopIds())) {
            List<DeliverOrderShop> deliverOrderShopList = deliverOrderShopService.queryByDeliverOrderShopIds(mbOrder.getDeliverOrderShopIds());
            for (DeliverOrderShop deliverOrderShop : deliverOrderShopList) {
                deliverOrderShop.setOrderId(mbOrder.getId());
                deliverOrderShopService.edit(deliverOrderShop);
            }
        }
        //订单商品信息
        List<MbOrderItem> mbOrderItemList = mbOrder.getMbOrderItemList();
        if(mbOrderItemList != null && mbOrderItemList.size() > 0) {
            for (MbOrderItem mbOrderItem : mbOrderItemList) {
                mbOrderItem.setOrderId(mbOrder.getId());
                mbOrderItemService.add(mbOrderItem);
                int r = mbItemService.reduceItemCount(mbOrderItem.getItemId(), mbOrderItem.getQuantity());
                if(r < 1) {
                    MbItem mbItem = mbItemService.getFromCache(mbOrderItem.getItemId());
                    throw new ServiceException(String.format("%s,库存不足",mbItem.getName()));
                }
            }
        }
        // 新增运费
        if(mbOrder.getDeliveryPrice()!=null&&mbOrder.getDeliveryPrice() > 0) {
            MbOrderItem mbOrderItem = new MbOrderItem();
            mbOrderItem.setItemId(-1);
            mbOrderItem.setBuyPrice(mbOrder.getDeliveryPrice());
            mbOrderItem.setOrderId(mbOrder.getId());
            mbOrderItem.setQuantity(1);
            mbOrderItemService.add(mbOrderItem);
        }
        //订单发票信息
        MbOrderInvoice mbOrderInvoice = mbOrder.getMbOrderInvoice();
        if(mbOrderInvoice != null) {
            mbOrderInvoice.setOrderId(mbOrder.getId());
            mbOrderInvoiceService.add(mbOrderInvoice);
        }
    }

    private void validate(MbOrder mbOrder) {
        MbBalance mbBalance = mbBalanceService.addOrGetMbBalanceCash(mbOrder.getShopId());
        //如果是负数说明，桶账还没有对
        if (mbBalance.getAmount() >= 0) {
            List<MbOrderItem> mbOrderItemList = mbOrder.getMbOrderItemList();
            if (CollectionUtils.isNotEmpty(mbOrderItemList)) {
                Integer total = 0;
                for (MbOrderItem mbOrderItem : mbOrderItemList) {
                    MbItem mbItem = mbItemService.getFromCache(mbOrderItem.getItemId());
                    //说明含桶
                    if (mbItem != null && mbItem.getPackId() != null) {
                        mbItem = mbItemService.getFromCache(mbItem.getPackId());
                        total += mbItem.getMarketPrice() * mbOrderItem.getQuantity();
                    }
                }
                //桶押金不足
                if (total > mbBalance.getAmount()) {
                    throw new ServiceException(String.format("桶押金不足，请充值桶押金%s元", new DecimalFormat("######.00").format((total - mbBalance.getAmount()) / 100L)));
                }
            }
        }
    }

    @Override
    public OrderState next(MbOrder mbOrder) {
        if (mbOrder.getStatus().equals("OD05")) { // 汇款支付返回05状态机
            return orderState05;
        } else if (mbOrder.getStatus().equals("OD10")) { // 微信/余额支付返回10状态机
            return orderState10;
        }else if (mbOrder.getStatus().equals("OD09")) { // 微信/余额支付返回10状态机
            return orderState09;
        }
        return null;
    }
}
