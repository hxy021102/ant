package com.bx.ant.service.impl.state;

import com.bx.ant.pageModel.DeliverOrder;
import com.bx.ant.pageModel.DeliverOrderItem;
import com.bx.ant.pageModel.DeliverOrderShop;
import com.bx.ant.pageModel.Supplier;
import com.bx.ant.service.*;
import com.bx.ant.service.qimen.QimenRequestService;
import com.mobian.thirdpart.youzan.YouzanUtil;
import com.mobian.util.ConvertNameUtil;
import com.mobian.absx.F;
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
public class DeliverOrder10StateImpl extends AbstractDeliverOrderState {

    @Resource(name = "deliverOrder20StateImpl")
    private DeliverOrderState deliverOrderState20;

    @Resource(name = "deliverOrder15StateImpl")
    private DeliverOrderState deliverOrderState15;



    @Autowired
    private DeliverOrderServiceI deliverOrderService;


    @Autowired
    private DeliverOrderShopServiceI deliverOrderShopService;

    @Autowired
    private DeliverOrderItemServiceI deliverOrderItemService;

    @Autowired
    private DeliverOrderShopItemServiceI deliverOrderShopItemService;


    @Resource
    private SupplierServiceI supplierService;

    @Resource
    private DeliverOrderYouzanServiceI deliverOrderYouzanService;

    @Autowired
    private QimenRequestService qimenRequestService;

    @Override
    public String getStateName() {
        return "10";
    }

    @Override
    public void execute(DeliverOrder deliverOrder) {
        DeliverOrder oldDeliverOrder = DeliverOrderState.deliverOrder.get();

        if (DeliverOrderServiceI.STATUS_SHOP_ALLOCATION.equals(oldDeliverOrder.getStatus())) {
            return;
        }



        //1. 添加门店订单
        DeliverOrderShop deliverOrderShop = new DeliverOrderShop();

        //1.1设置默认金额为订单金额
      //  deliverOrderShop.setAmount(deliverOrder.getAmount());
        deliverOrderShop.setDeliverOrderId(deliverOrder.getId());
        deliverOrderShop.setShopId(deliverOrder.getShopId());
        deliverOrderShop.setStatus(DeliverOrderShopServiceI.STATUS_AUDITING);
        deliverOrderShop.setDistance(deliverOrder.getShopDistance());
        deliverOrderShopService.addAndGet(deliverOrderShop);
        List<DeliverOrderItem> deliverOrderItemList = deliverOrderItemService.getDeliverOrderItemList(deliverOrder.getId());

        //2. 添加门店订单相关
        //2.1 添加门店订单明细List<deliverOrderShopItem>

        //2.3 编辑deliverOrderShop中金额amount字段
        deliverOrderShop.setDeliveryType(deliverOrder.getDeliveryType());
        deliverOrderShopItemService.addByDeliverOrderItemList(deliverOrderItemList, deliverOrderShop);

        //3. 对门店新订单进行计数:非自动接单
        if(!DeliverOrderServiceI.DELIVER_TYPE_AUTO.equals(deliverOrder.getDeliveryType()))
            deliverOrderService.addAllocationOrderRedis(deliverOrder.getShopId());

        //4. 编辑订单并添加修改记录
        deliverOrder.setStatus(prefix + getStateName());

        // 有赞商城订单，对接有赞api-卖家确认发货
        Supplier supplier = supplierService.get(deliverOrder.getSupplierId());
        if(ConvertNameUtil.getString(YouzanUtil.APPKEY).equals(supplier.getAppKey())) {
            deliverOrderYouzanService.youzanOrderConfirm(deliverOrder.getSupplierOrderId());
        }
        if(F.empty(deliverOrder.getDeliverOrderLogType())) {
            deliverOrderService.editAndAddLog(deliverOrder, DeliverOrderLogServiceI.TYPE_ASSIGN_DELIVER_ORDER, "系统自动分配订单");
        }else{
            String content = deliverOrder.getOrderLogRemark() == null ? "指派订单给门店" : "指派订单给门店【" + deliverOrder.getOrderLogRemark() + "】";
            deliverOrderService.editAndAddLog(deliverOrder, deliverOrder.getDeliverOrderLogType(), content);
        }


        DeliverOrder deliverOrderOld = DeliverOrderState.deliverOrder.get();
        qimenRequestService.updateOrderProcessReportRequest(prefix + getStateName(),deliverOrderOld);

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
