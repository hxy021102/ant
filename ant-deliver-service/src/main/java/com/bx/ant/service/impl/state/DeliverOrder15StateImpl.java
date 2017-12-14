package com.bx.ant.service.impl.state;

import com.bx.ant.service.*;
import com.bx.ant.pageModel.DeliverOrder;
import com.bx.ant.pageModel.DeliverOrderShop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 门店拒绝接单状态
 * Created by wanxp on 17-9-26.
 */
@Service("deliverOrder15StateImpl")
public class DeliverOrder15StateImpl extends AbstractDeliverOrderState {


    @Resource(name = "deliverOrder10StateImpl")
    private DeliverOrderState deliverOrderState10;

    @Autowired
    private DeliverOrderServiceI deliverOrderService;

    @Autowired
    private DeliverOrderShopServiceI deliverOrderShopService;



    @Autowired
    private ShopItemServiceI shopItemService;

    @Override
    public String getStateName() {
        return "15";
    }

    @Override
    public void execute(DeliverOrder deliverOrder) {

        //修改运单状态
        DeliverOrder orderNew = new  DeliverOrder();
        orderNew.setId(deliverOrder.getId());
        orderNew.setStatus(prefix + getStateName());
        deliverOrderService.editAndAddLog(orderNew, DeliverOrderLogServiceI.TYPE_REFUSE_DELIVER_ORDER,"[运单被拒绝]:" + deliverOrder.getRemark());

        //修改运单门店状态
        DeliverOrderShop deliverOrderShop = new DeliverOrderShop();
        deliverOrderShop.setStatus(DeliverOrderShopServiceI.STATUS_AUDITING);
        deliverOrderShop.setDeliverOrderId(orderNew.getId());
        DeliverOrderShop orderShopEdit = new DeliverOrderShop();
        orderShopEdit.setStatus(DeliverOrderShopServiceI.STATUS_REFUSED);
        deliverOrderShopService.editStatus(deliverOrderShop,orderShopEdit);

        //返还门店商品库存
        shopItemService.updateForRefundByDeliverOrder(deliverOrder);
        //TODO 这里应该执行重新分配订单方法

        //对门店新订单进行计数
        deliverOrderService.reduceAllocationOrderRedis(deliverOrder.getShopId());

    }

    @Override
    public DeliverOrderState next(DeliverOrder deliverOrder) {
        if ((prefix + "10").equals(deliverOrder.getStatus())) {
            return deliverOrderState10;
        }
        return null;
    }
}
