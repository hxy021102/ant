package com.bx.ant.service.allocation;

import com.bx.ant.service.*;
import com.mobian.pageModel.*;
import com.mobian.pageModel.DeliverOrder;
import com.mobian.pageModel.DeliverOrderItem;
import com.mobian.pageModel.DeliverOrderShop;
import com.mobian.pageModel.ShopDeliverApply;
import com.mobian.service.MbShopServiceI;
import com.mobian.util.GeoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by john on 17/10/11.
 */
@Service
public class DeliverOrderAllocationServiceImpl implements DeliverOrderAllocationServiceI {

    @Resource
    private MbShopServiceI mbShopService;

    @Autowired
    private DeliverOrderServiceI deliverOrderService;

    @Autowired
    private ShopDeliverApplyServiceI shopDeliverApplyService;

    @Autowired
    private DeliverOrderShopServiceI deliverOrderShopService;

    @Autowired
    private DeliverOrderShopItemServiceI deliverOrderShopItemService;

    @Autowired
    private DeliverOrderItemServiceI deliverOrderItemService;


    @Override
    public void updateOrderOwnerShopId() {
        //1、查开通了派单功能，且状态开启配送的门店List
        List<ShopDeliverApply> shopDeliverApplyList = shopDeliverApplyService.getAvailableAndWorkShop();

        //2、获取待分配的订单
        DeliverOrder request = new DeliverOrder();
        PageHelper ph = new PageHelper();
        ph.setHiddenTotal(true);
        request.setStatus(DeliverOrderServiceI.STATUS_NOT_PAY);
        DataGrid dataGrid = deliverOrderService.dataGrid(request, ph);
        List<DeliverOrder> deliverOrderList = dataGrid.getRows();

        for (DeliverOrder deliverOrder : deliverOrderList) {
            //3、计算待分配订单的数字地址
            if (deliverOrder.getLongitude() == null || deliverOrder.getLatitude() == null) {
                BigDecimal[] point = GeoUtil.getPosition(deliverOrder.getDeliveryAddress());
                deliverOrder.setLongitude(point[0]);
                deliverOrder.setLatitude(point[1]);
                deliverOrderService.edit(deliverOrder);
            }
            //4、计算最近距离点
            MbShop minMbShop = null;
            double minDistance = 0;
            for (ShopDeliverApply shopDeliverApply : shopDeliverApplyList) {
                MbShop mbShop = shopDeliverApply.getMbShop();
                //TODO 已分单且拒绝的店，不再分
                double distance = GeoUtil.getDistance(deliverOrder.getLongitude().doubleValue(), deliverOrder.getLatitude().doubleValue(), mbShop.getLongitude().doubleValue(), mbShop.getLatitude().doubleValue());
                if (distance < minDistance || minDistance == 0) {
                    minMbShop = mbShop;
                    minDistance = distance;
                }
            }
            //5、计算分单价格
            if (minMbShop != null) {
                DeliverOrderShop deliverOrderShop = new DeliverOrderShop();
                deliverOrderShop.setAmount(deliverOrder.getAmount());
                deliverOrderShop.setDeliverOrderId(deliverOrder.getId());
                deliverOrderShop.setShopId(deliverOrder.getShopId());
                deliverOrderShop.setStatus(DeliverOrderShopServiceI.STATUS_AUDITING);
                deliverOrderShop.setDistance(new BigDecimal(minDistance));
                deliverOrderShopService.add(deliverOrderShop);
                List<DeliverOrderItem> deliverOrderItemList = deliverOrderItemService.getDeliverOrderItemList(deliverOrder.getId());
                deliverOrderShopItemService.addByDeliverOrderItemList(deliverOrderItemList,deliverOrderShop);
            }
        }
    }
}
