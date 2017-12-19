package com.bx.ant.service.qimen;

import com.alibaba.fastjson.JSON;
import com.bx.ant.pageModel.DeliverOrder;
import com.bx.ant.pageModel.SupplierItemRelation;
import com.bx.ant.pageModel.SupplierItemRelationView;
import com.bx.ant.service.DeliverOrderServiceI;
import com.bx.ant.service.DeliverOrderYouzanServiceI;
import com.bx.ant.service.ShopDeliverApplyServiceI;
import com.bx.ant.service.SupplierItemRelationServiceI;
import com.mobian.absx.F;
import com.mobian.exception.ServiceException;
import com.mobian.pageModel.PageHelper;
import com.mobian.thirdpart.youzan.YouzanUtil;
import com.mobian.util.Constants;
import com.mobian.util.ConvertNameUtil;
import com.mobian.util.DateUtil;
import com.qimen.api.QimenRequest;
import com.qimen.api.QimenResponse;
import com.qimen.api.response.DeliveryorderCreateResponse;
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

    @Resource
    private DeliverOrderYouzanServiceI deliverOrderYouzanService;

    @Override
    public QimenResponse execute(QimenRequest request) {
        DeliveryorderCreateRequest req = (DeliveryorderCreateRequest) request;
        Integer supplierId = Integer.parseInt(ConvertNameUtil.getString(QimenRequestService.QIM_06));
        DeliveryorderCreateRequest.DeliveryOrder deliveryOrder = req.getDeliveryOrder();
        logger.error("看看数据："+ JSON.toJSONString(req));
        DeliveryorderCreateResponse response = new DeliveryorderCreateResponse();
        response.setFlag("success");
        /*if(!QimenRequestService.JYCK.equals(deliveryOrder.getOrderType())) {
            return response;
        }*/
        DeliverOrder order = new DeliverOrder();
        order.setIsdeleted(true); // 初始化无效
        order.setSupplierId(supplierId);
        order.setSupplierOrderId(deliveryOrder.getDeliveryOrderCode());
        order.setSupplierOrderType(deliveryOrder.getOrderType());
        order.setOriginalShop(deliveryOrder.getShopNick()); // 店铺名称
        order.setOriginalOrderStatus(DeliverOrderServiceI.ORIGINAL_ORDER_STATUS_OTS01);

        DeliveryorderCreateRequest.ReceiverInfo receiverInfo = deliveryOrder.getReceiverInfo();
        order.setContactPeople(receiverInfo.getName());
        order.setContactPhone(receiverInfo.getMobile());
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

            supplierItemRelations.add(itemRelation);

            if(F.empty(order.getOriginalOrderId()))
                order.setOriginalOrderId(orderLine.getSourceOrderCode()); // 原订单号
        }

        // 有赞店铺订单判断是否为自提
        if(ConvertNameUtil.getString(YouzanUtil.KDT_ID).equals(order.getOriginalShop()) && !F.empty(order.getOriginalOrderId())) {
            String shippingType = deliverOrderYouzanService.getShippingTypeByTid(order.getOriginalOrderId());
            if(DeliverOrderYouzanServiceI.FETCH.equals(shippingType))
                order.setDeliveryWay(ShopDeliverApplyServiceI.DELIVER_WAY_CUSTOMER);
        }

        // 添加订单和订单明细
        deliverOrderService.addAndItems(order, supplierItemRelations);
        response.setDeliveryOrderId(order.getId() + "");
        response.setWarehouseCode(ConvertNameUtil.getString(QimenRequestService.QIM_08));
        response.setLogisticsCode(ConvertNameUtil.getString(QimenRequestService.QIM_09));
        response.setCreateTime(DateUtil.format(new Date(), Constants.DATE_FORMAT_FOR_ENTITY));
        return response;
    }

    /*public static void main(String[] args) {
        if(true){
            new QimenDeliveryOrderCreateServiceImpl().test();
            return;
        }
        DeliveryorderCreateRequest req = new DeliveryorderCreateRequest();
        DeliveryorderCreateRequest.DeliveryOrder deliveryOrder = new DeliveryorderCreateRequest.DeliveryOrder();
        req.setDeliveryOrder(deliveryOrder);
        deliveryOrder.setDeliveryOrderCode("ORD0001");
        deliveryOrder.setBuyerMessage("请包装好");
        DeliveryorderCreateRequest.ReceiverInfo receiverInfo = new DeliveryorderCreateRequest.ReceiverInfo();
        deliveryOrder.setReceiverInfo(receiverInfo);
        receiverInfo.setReceiverName("黄智");
        receiverInfo.setReceiverMobile("13818680754");
        receiverInfo.setProvince("上海");
        receiverInfo.setCity("上海市");
        receiverInfo.setArea("闵行区");
        receiverInfo.setDetailAddress("平南三村29号");

        List<DeliveryorderCreateRequest.OrderLine> orderLines = new ArrayList<DeliveryorderCreateRequest.OrderLine>();
        DeliveryorderCreateRequest.OrderLine orderLine = new DeliveryorderCreateRequest.OrderLine();
        orderLine.setItemCode("GMK1012325");
        orderLine.setPlanQty("10");
        orderLines.add(orderLine);
        req.setOrderLines(orderLines);
        XmlWriter writer = new XmlWriter(true, "request", Object.class);
        String text = writer.write(req);
        System.out.println(text);
    }

    public void test(){
        String request = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<request> \n" +
                "  <deliveryOrder> \n" +
                "    <buyerMessage>请包装好</buyerMessage>  \n" +
                "    <deliveryOrderCode>ORD0002</deliveryOrderCode>  \n" +
                "    <receiverInfo> \n" +
                "      <area>闵行区</area>  \n" +
                "      <city>上海市</city>  \n" +
                "      <detailAddress>平南三村29号</detailAddress>  \n" +
                "      <province>上海</province>  \n" +
                "      <receiverMobile>13818680754</receiverMobile>  \n" +
                "      <receiverName>黄智</receiverName> \n" +
                "    </receiverInfo> \n" +
                "  </deliveryOrder>  \n" +
                "<orderLines> \n" +
                "    <orderLine> \n" +
                "      <itemCode>GMK1012325</itemCode>  \n" +
                "      <planQty>10</planQty> \n" +
                "    </orderLine> \n" +
                "  </orderLines> "+
                "</request>";

        QimenDeliveryOrderCreateServiceImpl qimenDeliveryOrderCreateService = new QimenDeliveryOrderCreateServiceImpl();
        DeliveryorderCreateRequest req = qimenDeliveryOrderCreateService.parserRequest(request);
        System.out.println(req);

    }*/


    @Override
    public Class getParserRequestClass() {
        return DeliveryorderCreateRequest.class;
    }
}
