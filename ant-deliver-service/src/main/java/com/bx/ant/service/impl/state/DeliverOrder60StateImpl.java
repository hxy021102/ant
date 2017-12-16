package com.bx.ant.service.impl.state;

import com.bx.ant.pageModel.DeliverOrder;
import com.bx.ant.pageModel.DeliverOrderShop;
import com.bx.ant.pageModel.DriverOrderShop;
import com.bx.ant.service.*;
import com.mobian.absx.F;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 交易关闭
 * Created by wanxp on 17-9-26.
 */
@Service("deliverOrder60StateImpl")
public class DeliverOrder60StateImpl extends AbstractDeliverOrderState {

    @Autowired
    private DeliverOrderServiceI deliverOrderService;

    @Resource
    private DeliverOrderShopServiceI deliverOrderShopService;


    @Override
    public String getStateName() {
        return "60";
    }


    @Override
    public void execute(DeliverOrder deliverOrder) {

        // 修改运单状态
        DeliverOrder orderNew = new DeliverOrder();
        orderNew.setId(deliverOrder.getId());
        orderNew.setStatus(prefix + getStateName());
        if (!F.empty(deliverOrder.getSupplierOrderId()))
            orderNew.setSupplierOrderId(deliverOrder.getSupplierOrderId());
        deliverOrderService.editAndAddLog(orderNew, DeliverOrderLogServiceI.TYPE_CLOSE_ORDER, String.format("订单已关闭，原因:%s", deliverOrder.getRemark()));

        //修改关闭状态
        DeliverOrderShop deliverOrderShop = deliverOrderShopService.getByDeliverOrderId(deliverOrder.getId());
        if (deliverOrderShop != null) {
            deliverOrderShop.setStatus(DeliverOrderShopServiceI.STATUS_INACTIVE);
            deliverOrderShopService.edit(deliverOrderShop);
        }
    }

    @Override
    public DeliverOrderState next(DeliverOrder deliverOrder) {
        return null;
    }
}
