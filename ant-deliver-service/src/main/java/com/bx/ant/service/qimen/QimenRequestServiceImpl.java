package com.bx.ant.service.qimen;

import com.alibaba.fastjson.JSON;
import com.bx.ant.pageModel.DeliverOrder;
import com.mobian.absx.Objectx;
import com.mobian.exception.ServiceException;
import com.mobian.util.ConvertNameUtil;
import com.qimen.api.DefaultQimenClient;
import com.qimen.api.QimenRequest;
import com.qimen.api.request.DeliveryorderConfirmRequest;
import com.qimen.api.request.OrderprocessReportRequest;
import com.qimen.api.response.DeliveryorderConfirmResponse;
import com.qimen.api.response.OrderprocessReportResponse;
import com.taobao.api.ApiException;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by john on 17/11/30.
 */
@Service
public class QimenRequestServiceImpl extends Objectx implements QimenRequestService {


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
