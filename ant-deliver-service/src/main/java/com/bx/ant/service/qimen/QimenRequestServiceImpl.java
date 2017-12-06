package com.bx.ant.service.qimen;

import com.alibaba.fastjson.JSON;
import com.bx.ant.pageModel.DeliverOrder;
import com.bx.ant.pageModel.DeliverOrderItem;
import com.bx.ant.service.DeliverOrderItemServiceI;
import com.bx.ant.service.DeliverOrderServiceI;
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

    @Autowired
    private DeliverOrderItemServiceI deliverOrderItemService;

    @Resource
    private MbItemServiceI mbItemService;

    @Override
    public void updateDeliveryOrderConfirm(DeliverOrder deliverOrder) {
        Integer supplierId = deliverOrder.getSupplierId();
        if (ConvertNameUtil.getString(QimenRequestService.QIM_06).equals(supplierId + "")) {
            DeliveryorderConfirmRequest request = new DeliveryorderConfirmRequest();
            DeliveryorderConfirmRequest.DeliveryOrder order = new DeliveryorderConfirmRequest.DeliveryOrder();
            order.setWarehouseCode(ConvertNameUtil.getString(QimenRequestService.QIM_08));
            order.setLogisticsCode(ConvertNameUtil.getString(QimenRequestService.QIM_09));
            order.setDeliveryOrderCode(deliverOrder.getSupplierOrderId());
            order.setDeliveryOrderId(deliverOrder.getId() + "");
            order.setOrderType(QimenRequestService.JYCK);
            request.setDeliveryOrder(order);
            List<DeliveryorderConfirmRequest.Package> packages = new ArrayList<DeliveryorderConfirmRequest.Package>();
            DeliveryorderConfirmRequest.Package _package = new DeliveryorderConfirmRequest.Package();
            packages.add(_package);
            _package.setLogisticsCode("OTHER");
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
                items.add(item);
            }
            _package.setItems(items);
            DeliveryorderConfirmResponse response = execute(request);
            logger.info(JSON.toJSONString(response));
        }
    }

    @Override
    public void updateOrderProcessReportRequest(String status, DeliverOrder deliverOrder) {
        Integer supplierId = deliverOrder.getSupplierId();
        if (ConvertNameUtil.getString(QimenRequestService.QIM_06).equals(supplierId + "")) {
            OrderprocessReportRequest request = new OrderprocessReportRequest();
            OrderprocessReportRequest.Order order = new OrderprocessReportRequest.Order();
            order.setWarehouseCode(ConvertNameUtil.getString(QimenRequestService.QIM_08));
            order.setOrderCode(deliverOrder.getSupplierOrderId());
            order.setOrderId(deliverOrder.getId() + "");
            order.setOrderType(QimenRequestService.JYCK);
            request.setOrder(order);
            OrderprocessReportRequest.Process process = new OrderprocessReportRequest.Process();
            //process.setProcessStatus(processStatus);
            String json = ConvertNameUtil.getDesc(QIM_10,"{}");
            Map<String,Object> statusMap = JSON.parseObject(json, Map.class);
            status = (String)statusMap.get(status);
            if (status != null) {
                process.setProcessStatus(status);
                request.setProcess(process);
                OrderprocessReportResponse response = execute(request);
                logger.info(JSON.toJSONString(response));
            }
        }
    }

    private <T> T execute(QimenRequest request) {
        request.setVersion(ConvertNameUtil.getString(QIM_05));
        request.setCustomerId(ConvertNameUtil.getString(QIM_04));
        try {
            logger.info("开始发起奇门请求："+JSON.toJSONString(request));
            return (T) getClient().execute(request);
        } catch (ApiException e) {
            throw new ServiceException("调用奇门接口异常", e);
        }
    }

    private DefaultQimenClient getClient() {
        String serviceUrl = ConvertNameUtil.getString(QIM_03);
        String appKey = ConvertNameUtil.getString(QIM_01);
        String appSecret = ConvertNameUtil.getString(QIM_02);
        DefaultQimenClient qimenClient = new DefaultQimenClient(serviceUrl, appKey, appSecret);
        return qimenClient;
    }
}
