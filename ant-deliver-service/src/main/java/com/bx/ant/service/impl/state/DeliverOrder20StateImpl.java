package com.bx.ant.service.impl.state;

import com.bx.ant.pageModel.*;
import com.bx.ant.service.*;
import com.mobian.absx.F;
import com.mobian.exception.ServiceException;
import com.mobian.pageModel.PageHelper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 已接单,等待骑手接单
 * Created by wanxp on 17-9-26.
 */
@Service("deliverOrder20StateImpl")
public class DeliverOrder20StateImpl implements DeliverOrderState {

    @Resource(name = "deliverOrder21StateImpl")
    private DeliverOrderState deliverOrderState21;

    @Resource(name = "deliverOrder25StateImpl")
    private DeliverOrderState deliverOrderState25;

    @Autowired
    private DeliverOrderServiceI deliverOrderService;

    @Autowired
    private DeliverOrderShopServiceI deliverOrderShopService;


    @Autowired
    private DeliverOrderLogServiceI deliverOrderLogService;

    @Resource
    private DriverOrderShopServiceI driverOrderShopService;

    @Autowired
    private ShopDeliverApplyServiceI shopDeliverApplyService;


    @Override
    public String getStateName() {
        return "20";
    }

    @Override
    public void handle(DeliverOrder deliverOrder) {
        if (!F.empty(deliverOrder.getShopId())) {
            //门店申请者的配送方式
            ShopDeliverApply apply = new ShopDeliverApply();
            apply.setStatus(ShopDeliverApplyServiceI.DAS_02);
            apply.setShopId(deliverOrder.getShopId());
            PageHelper ph = new PageHelper();
            ph.setHiddenTotal(true);
            List<ShopDeliverApply> deliverApplies = shopDeliverApplyService.dataGrid(apply, ph).getRows();

            if (CollectionUtils.isNotEmpty(deliverApplies)) {
                apply = deliverApplies.get(0);

                    //修改运单状态
                    DeliverOrder orderNew = new DeliverOrder();
                    orderNew.setId(deliverOrder.getId());
                    orderNew.setStatus(prefix + getStateName());
                    orderNew.setDeliveryStatus(DeliverOrderServiceI.DELIVER_STATUS_STANDBY);
        //orderNew.setShopPayStatus(DeliverOrderServiceI.SHOP_PAY_STATUS_NOT_PAY);
                    String type = DeliverOrderServiceI.DELIVER_TYPE_AUTO.equals(deliverOrder.getDeliveryType()) ?
                            "(自动)" : (DeliverOrderServiceI.DELIVER_TYPE_FORCE.equals(deliverOrder.getDeliveryType()) ?
                            "(强制)" : "(手动)");
                    orderNew.setDeliveryWay(apply.getDeliveryWay());
                    deliverOrderService.editAndAddLog(orderNew,deliverOrderLogService.TYPE_ACCEPT_DELIVER_ORDER, "运单被接" + type);

                    //修改门店运单状态
                    DeliverOrderShop deliverOrderShop = new DeliverOrderShop();
                    deliverOrderShop.setStatus(DeliverOrderShopServiceI.STATUS_AUDITING);
                    deliverOrderShop.setDeliverOrderId(orderNew.getId());
                    DeliverOrderShop orderShopEdit=new DeliverOrderShop();
                    orderShopEdit.setStatus(DeliverOrderShopServiceI.STATUS_ACCEPTED);
                    deliverOrderShop = deliverOrderShopService.editStatus(deliverOrderShop,orderShopEdit);




                //配送方式为骑手,则添加骑手订单
                if (ShopDeliverApplyServiceI.DELIVER_WAY_DRIVER.equals(apply.getDeliveryWay())) {
                    //添加骑手订单
                    DriverOrderShop driverOrderShop = new DriverOrderShop();
//                    driverOrderShop.setShopId(deliverOrder.getShopId());
//
//                    DeliverOrderShop deliverOrderShopQuery = new DeliverOrderShop();
//                    deliverOrderShopQuery.setShopId(deliverOrder.getShopId());
//                    deliverOrderShopQuery.setDeliverOrderId(deliverOrder.getId());
//                    deliverOrderShopQuery.setStatus(DeliverOrderShopServiceI.STATUS_ACCEPTED);
//                    List<DeliverOrderShop> deliverOrderShops = deliverOrderShopService.query(deliverOrderShopQuery);
//                    if (CollectionUtils.isEmpty(deliverOrderShops)) {
//                        throw new ServiceException(String.format("订单ID:%1s未被门店接单", deliverOrder.getId()));
//                    }

                    driverOrderShop.setDeliverOrderShopId(deliverOrderShop.getId());
                    driverOrderShop.setStatus(DriverOrderShopServiceI.PAY_STATUS_NOT_PAY);
                    driverOrderShopService.transform(driverOrderShop);
                }
            }

        }

    }

    @Override
    public DeliverOrderState next(DeliverOrder deliverOrder) {
        if ((prefix + "21").equals(deliverOrder.getStatus())) {
            return deliverOrderState21;
        }
        if ((prefix + "25").equals(deliverOrder.getStatus())) {
            return deliverOrderState25;
        }
        return null;
    }
}
