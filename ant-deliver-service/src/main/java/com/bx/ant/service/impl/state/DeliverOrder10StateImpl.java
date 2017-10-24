package com.bx.ant.service.impl.state;

import com.bx.ant.pageModel.DeliverOrderItem;
import com.bx.ant.service.*;
import com.bx.ant.pageModel.DeliverOrder;
import com.bx.ant.pageModel.DeliverOrderShop;
import com.bx.ant.service.impl.DeliverOrderShopItemServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * 已分配订单,待接单状态
 * Created by wanxp on 17-9-26.
 */
@Service("deliverOrder10StateImpl")
public class DeliverOrder10StateImpl implements DeliverOrderState {

    @Resource(name = "deliverOrder20StateImpl")
    private DeliverOrderState deliverOrderState20;

    @Resource(name = "deliverOrder15StateImpl")
    private DeliverOrderState deliverOrderState15;



    @Autowired
    private DeliverOrderServiceI deliverOrderService;

    @Autowired
    private DeliverOrderPayServiceI deliverOrderPayService;

    @Autowired
    private DeliverOrderShopServiceI deliverOrderShopService;

    @Autowired
    private DeliverOrderLogServiceI deliverOrderLogService;

    @Autowired
    private DeliverOrderItemServiceI deliverOrderItemService;

    @Autowired
    private DeliverOrderShopItemServiceI deliverOrderShopItemService;

    @Override
    public String getStateName() {
        return "10";
    }

    @Override
    public void handle(DeliverOrder deliverOrder) {

        //添加门店订单
        DeliverOrderShop deliverOrderShop = new DeliverOrderShop();
        deliverOrderShop.setAmount(deliverOrder.getAmount());
        deliverOrderShop.setDeliverOrderId(deliverOrder.getId());
        deliverOrderShop.setShopId(deliverOrder.getShopId());
        deliverOrderShop.setStatus(DeliverOrderShopServiceI.STATUS_AUDITING);
        deliverOrderShop.setDistance(new BigDecimal(deliverOrder.getShopDistance()));
        deliverOrderShopService.add(deliverOrderShop);
        List<DeliverOrderItem> deliverOrderItemList = deliverOrderItemService.getDeliverOrderItemList(deliverOrder.getId());
        deliverOrderShopItemService.addByDeliverOrderItemList(deliverOrderItemList, deliverOrderShop);

        //对门店新订单进行计数
        deliverOrderService.addAllocationOrderRedis(deliverOrder.getShopId());

        //编辑订单并添加修改记录
        deliverOrder.setStatus(prefix + getStateName());

        deliverOrderService.editAndAddLog(deliverOrder, DeliverOrderLogServiceI.TYPE_ASSIGN_DELIVER_ORDER, "系统自动分配订单");
    }

    @Override
    public DeliverOrderState next(DeliverOrder deliverOrder) {
        //跳转至接受状态
        if ((prefix + "20").equals(deliverOrder.getStatus())) {
            return deliverOrderState20;
        }
        //跳转至拒绝状态
        if ((prefix + "15").equals(deliverOrder.getStatus())) {
            return deliverOrderState15;
        }
        return null;
    }
}
