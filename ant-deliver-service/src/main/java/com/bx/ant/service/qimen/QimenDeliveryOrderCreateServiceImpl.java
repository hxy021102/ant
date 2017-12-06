package com.bx.ant.service.qimen;

import com.bx.ant.pageModel.DeliverOrder;
import com.bx.ant.pageModel.SupplierItemRelation;
import com.bx.ant.pageModel.SupplierItemRelationView;
import com.bx.ant.service.DeliverOrderServiceI;
import com.bx.ant.service.SupplierItemRelationServiceI;
import com.mobian.exception.ServiceException;
import com.mobian.pageModel.PageHelper;
import com.mobian.util.Constants;
import com.mobian.util.ConvertNameUtil;
import com.mobian.util.DateUtil;
import com.qimen.api.QimenRequest;
import com.qimen.api.QimenResponse;
import com.qimen.api.request.DeliveryorderCreateRequest;
import com.qimen.api.response.DeliveryorderCreateResponse;
import com.taobao.api.internal.util.XmlWriter;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanTradesSoldGetResult;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by john on 17/11/28.
 * 发货单创建
 */
@Service("taobao.qimen.deliveryorder.create")
public class QimenDeliveryOrderCreateServiceImpl extends AbstrcatQimenService {

    @Resource
    private SupplierItemRelationServiceI supplierItemRelationService;

    @Resource
    private DeliverOrderServiceI deliverOrderService;

    @Override
    public QimenResponse execute(QimenRequest request) {
        DeliveryorderCreateRequest req = (DeliveryorderCreateRequest) request;
        Integer supplierId = Integer.parseInt(ConvertNameUtil.getString(QimenRequestService.QIM_06));
        DeliveryorderCreateRequest.DeliveryOrder deliveryOrder = req.getDeliveryOrder();
        /*if(QimenService.JYCK.equals(deliveryOrder.getOrderType())) {
        }*/
        DeliveryorderCreateResponse response = new DeliveryorderCreateResponse();
        response.setFlag("success");
        DeliverOrder order = new DeliverOrder();
        order.setSupplierId(supplierId);
        order.setSupplierOrderId(deliveryOrder.getDeliveryOrderCode());
        //TODO shopNick 淘宝店 isdelte false
        DeliveryorderCreateRequest.ReceiverInfo receiverInfo = deliveryOrder.getReceiverInfo();
        order.setContactPeople(receiverInfo.getReceiverName());
        order.setContactPhone(receiverInfo.getReceiverMobile());
        order.setDeliveryAddress(receiverInfo.getProvince() + receiverInfo.getCity() + receiverInfo.getArea() + receiverInfo.getDetailAddress());
        order.setRemark(deliveryOrder.getBuyerMessage());
        List<SupplierItemRelationView> supplierItemRelations = new ArrayList<SupplierItemRelationView>();
        List<DeliveryorderCreateRequest.OrderLine> orderLines = req.getOrderLines();
        for (DeliveryorderCreateRequest.OrderLine orderLine : orderLines) {
            SupplierItemRelationView itemRelation = new SupplierItemRelationView();
            itemRelation.setSupplierId(supplierId);
            itemRelation.setSupplierItemCode(orderLine.getItemCode());
            itemRelation.setOnline(true);
            PageHelper ph = new PageHelper();
            ph.setHiddenTotal(true);
            List<SupplierItemRelation> itemRelations = supplierItemRelationService.dataGrid(itemRelation, ph).getRows();
            // 无法找到接入方对应商品
            if (CollectionUtils.isEmpty(itemRelations)) {
                response.setFlag("failure");
                logger.error(orderLine.getItemCode() + "找不到对应的商品", new ServiceException("找不到对应的商品"));
                return response;
            }

            itemRelation.setItemId(itemRelations.get(0).getItemId());
            itemRelation.setPrice(itemRelations.get(0).getPrice());
            itemRelation.setQuantity(Integer.parseInt(orderLine.getPlanQty()));
            //TODO 原订单号sourceOrderCode
            supplierItemRelations.add(itemRelation);
        }
        // 添加订单和订单明细
        deliverOrderService.addAndItems(order, supplierItemRelations);
        response.setDeliveryOrderId(order.getId() + "");
        response.setWarehouseCode(ConvertNameUtil.getString(QimenRequestService.QIM_08));
        response.setLogisticsCode(ConvertNameUtil.getString(QimenRequestService.QIM_09));
        response.setCreateTime(DateUtil.format(new Date(), Constants.DATE_FORMAT_FOR_ENTITY));
        return response;
    }

    @Override
    public Class getParserRequestClass() {
        return DeliveryorderCreateRequest.class;
    }
}
