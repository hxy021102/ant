package com.bx.ant.service.qimen;

import com.alibaba.fastjson.JSON;
import com.bx.ant.pageModel.DeliverOrder;
import com.bx.ant.pageModel.DeliverOrderItem;
import com.bx.ant.pageModel.SupplierInterfaceConfig;
import com.bx.ant.service.DeliverOrderItemServiceI;
import com.bx.ant.service.DeliverOrderServiceI;
import com.bx.ant.service.SupplierInterfaceConfigServiceI;
import com.mobian.absx.F;
import com.mobian.absx.Objectx;
import com.mobian.exception.ServiceException;
import com.mobian.pageModel.MbItem;
import com.mobian.service.MbItemServiceI;
import com.mobian.util.ConvertNameUtil;
import com.qimen.api.DefaultQimenClient;
import com.qimen.api.QimenRequest;
import com.qimen.api.request.DeliveryorderConfirmRequest;
import com.qimen.api.request.OrderprocessReportRequest;
import com.qimen.api.response.DeliveryorderConfirmResponse;
import com.qimen.api.response.OrderprocessReportResponse;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by john on 17/11/30.
 */
@Service
public class QimenRequestServiceImpl extends Objectx implements QimenRequestService {

    public static final String OTHER = "OTHER";
    @Autowired
    private DeliverOrderItemServiceI deliverOrderItemService;

    @Resource
    private MbItemServiceI mbItemService;

    @Resource
    private SupplierInterfaceConfigServiceI supplierInterfaceConfigService;

    @Override
    public void updateDeliveryOrderConfirm(DeliverOrder deliverOrder) {
//        Integer supplierId = deliverOrder.getSupplierId();
        SupplierInterfaceConfig supplierConfig = supplierInterfaceConfigService.getBySupplierId(deliverOrder.getSupplierId());
//        if (ConvertNameUtil.getString(QimenRequestService.QIM_06).equals(supplierId + "")) {
        if (supplierConfig != null) {
            DeliveryorderConfirmRequest request = new DeliveryorderConfirmRequest();
            DeliveryorderConfirmRequest.DeliveryOrder order = new DeliveryorderConfirmRequest.DeliveryOrder();
//            order.setWarehouseCode(ConvertNameUtil.getString(QimenRequestService.QIM_08));
//            order.setLogisticsCode(ConvertNameUtil.getString(QimenRequestService.QIM_09));
            order.setWarehouseCode(supplierConfig.getWarehouseCode());
            order.setLogisticsCode(supplierConfig.getLogisticsCode());
            order.setDeliveryOrderCode(deliverOrder.getSupplierOrderId());
            order.setDeliveryOrderId(deliverOrder.getId() + "");
            order.setOrderType(deliverOrder.getSupplierOrderType() != null ? deliverOrder.getSupplierOrderType() : JYCK);
            request.setDeliveryOrder(order);
            List<DeliveryorderConfirmRequest.Package> packages = new ArrayList<DeliveryorderConfirmRequest.Package>();
            DeliveryorderConfirmRequest.Package _package = new DeliveryorderConfirmRequest.Package();
            packages.add(_package);
            _package.setLogisticsCode(OTHER);
            // TODO
            _package.setLogisticsName(ConvertNameUtil.getString(QimenRequestService.QIM_12));
            _package.setExpressCode(deliverOrder.getId()+"");
            request.setPackages(packages);
            List<DeliverOrderItem> deliverOrderItemList = deliverOrderItemService.getDeliverOrderItemList(deliverOrder.getId());
            List<DeliveryorderConfirmRequest.Item> items = new ArrayList<DeliveryorderConfirmRequest.Item>();
            for (DeliverOrderItem deliverOrderItem : deliverOrderItemList) {
                DeliveryorderConfirmRequest.Item item = new DeliveryorderConfirmRequest.Item();
                MbItem mbItem = mbItemService.getFromCache(deliverOrderItem.getItemId());
                item.setItemCode(mbItem.getCode());
                item.setPlanQty(deliverOrderItem.getQuantity()+"");
                item.setActualQty(item.getPlanQty());
                item.setQuantity(new Long(deliverOrderItem.getQuantity()));
                items.add(item);
            }
            _package.setItems(items);

            List<DeliveryorderConfirmRequest.OrderLine> orderLines = new ArrayList<DeliveryorderConfirmRequest.OrderLine>();
            for (DeliveryorderConfirmRequest.Item item : items) {
                DeliveryorderConfirmRequest.OrderLine orderLine = new DeliveryorderConfirmRequest.OrderLine();
                orderLine.setItemCode(item.getItemCode());
                orderLine.setPlanQty(item.getQuantity()+"");
                orderLine.setActualQty(orderLine.getPlanQty());
                orderLine.setQuantity(orderLine.getPlanQty());
                orderLines.add(orderLine);
            }
            request.setOrderLines(orderLines);
            DeliveryorderConfirmResponse response = execute(request, supplierConfig);
            logger.info(JSON.toJSONString(response));
        }
    }

    @Override
    public void updateOrderProcessReportRequest(String status, DeliverOrder deliverOrder) {
        SupplierInterfaceConfig supplierConfig = supplierInterfaceConfigService.getBySupplierId(deliverOrder.getSupplierId());
        if (supplierConfig != null) {
            OrderprocessReportRequest request = new OrderprocessReportRequest();
            OrderprocessReportRequest.Order order = new OrderprocessReportRequest.Order();
            order.setWarehouseCode(supplierConfig.getWarehouseCode());
            order.setOrderCode(deliverOrder.getSupplierOrderId());
            order.setOrderId(deliverOrder.getId() + "");
            order.setOrderType(deliverOrder.getSupplierOrderType() != null ? deliverOrder.getSupplierOrderType() : JYCK);
            request.setOrder(order);
            OrderprocessReportRequest.Process process = new OrderprocessReportRequest.Process();
            //process.setProcessStatus(processStatus);
            String json = supplierConfig.getStatusMap();
            Map<String,Object> statusMap = JSON.parseObject(!F.empty(json) ? json : "{}", Map.class);
            status = (String)statusMap.get(status);
            if (status != null) {
                process.setProcessStatus(status);
                request.setProcess(process);
                OrderprocessReportResponse response = execute(request, supplierConfig);
                logger.info(JSON.toJSONString(response));
            }
        }
    }

    private <T> T execute(QimenRequest request, SupplierInterfaceConfig supplierConfig) {
        request.setVersion(supplierConfig.getVersion());
        request.setCustomerId(supplierConfig.getCustomerId());
        try {
            logger.info("开始发起奇门请求："+JSON.toJSONString(request));
            return (T) getClient(supplierConfig).execute(request);
        } catch (ApiException e) {
            throw new ServiceException("调用奇门接口异常", e);
        }
    }

    private DefaultQimenClient getClient(SupplierInterfaceConfig supplierConfig) {
        String serviceUrl = supplierConfig.getServiceUrl();
        String appKey = supplierConfig.getAppKey();
        String appSecret = supplierConfig.getAppSecret();
        DefaultQimenClient qimenClient = new DefaultQimenClient(serviceUrl, appKey, appSecret);
        return qimenClient;
    }
}
