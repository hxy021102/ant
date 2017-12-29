package com.bx.ant.service.qimen;

import com.alibaba.fastjson.JSON;
import com.bx.ant.pageModel.*;
import com.bx.ant.service.*;
import com.mobian.absx.F;
import com.mobian.exception.ServiceException;
import com.mobian.pageModel.MbBalance;
import com.mobian.pageModel.PageHelper;
import com.mobian.service.MbBalanceServiceI;
import com.mobian.thirdpart.mns.MNSTemplate;
import com.mobian.thirdpart.mns.MNSUtil;
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
import java.util.*;

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

    @Resource
    private SupplierInterfaceConfigServiceI supplierInterfaceConfigService;

    @Resource
    private MbBalanceServiceI mbBalanceService;

    @Resource
    private SupplierServiceI supplierService;

    @Override
    public QimenResponse execute(QimenRequest request) {
        DeliveryorderCreateRequest req = (DeliveryorderCreateRequest) request;
        SupplierInterfaceConfig supplierConfig = supplierInterfaceConfigService.getByCustomerId(req.getCustomerId());
//        Integer supplierId = Integer.parseInt(ConvertNameUtil.getString(QimenRequestService.QIM_06));
        Integer supplierId = supplierConfig.getSupplierId();
        DeliveryorderCreateRequest.DeliveryOrder deliveryOrder = req.getDeliveryOrder();
        logger.error("看看数据："+ JSON.toJSONString(req));
        DeliveryorderCreateResponse response = new DeliveryorderCreateResponse();
        response.setFlag("success");
        /*if(!QimenRequestService.JYCK.equals(deliveryOrder.getOrderType())) {
            return response;
        }*/

        MbBalance mbBalance = mbBalanceService.addOrGetAccessSupplierBalance(supplierId);
        if(mbBalance.getAmount() < 0) {
            // 发送短信通知
            sendArrearsMns(supplierId);
            response.setFlag("failure");
            logger.error("接入方"+supplierId+"余额欠款", new ServiceException("接入方余额欠款"));
            return response;
        }
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
//        response.setWarehouseCode(ConvertNameUtil.getString(QimenRequestService.QIM_08));
//        response.setLogisticsCode(ConvertNameUtil.getString(QimenRequestService.QIM_09));
        response.setWarehouseCode(supplierConfig.getWarehouseCode());
        response.setLogisticsCode(supplierConfig.getLogisticsCode());
        response.setCreateTime(DateUtil.format(new Date(), Constants.DATE_FORMAT_FOR_ENTITY));
        return response;
    }

    /**
     * 发送欠款短信通知
     * @param supplierId
     */
    private void sendArrearsMns(Integer supplierId) {
        Supplier supplier = supplierService.get(supplierId);
        if(!F.empty(supplier.getContactPhone())) {
            MNSTemplate template = new MNSTemplate();
            Map<String, String> params = new HashMap<String, String>();
            template.setTemplateCode("SMS_119870125");
            params.put("supplierName", supplier.getName());
            template.setParams(params);
            MNSUtil.sendMns(supplier.getContactPhone(), template);
        }

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
