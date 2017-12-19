package com.bx.ant.service.qimen;

import com.alibaba.fastjson.JSON;
import com.bx.ant.pageModel.DeliverOrder;
import com.bx.ant.service.DeliverOrderServiceI;
import com.bx.ant.service.ShopDeliverApplyServiceI;
import com.mobian.util.ConvertNameUtil;
import com.qimen.api.QimenRequest;
import com.qimen.api.QimenResponse;
import com.qimen.api.request.OrderCancelRequest;
import com.qimen.api.response.OrderCancelResponse;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by john on 17/12/6.
 */
@Service("taobao.qimen.order.cancel")
public class QimenOrderCancelServiceImpl extends AbstrcatQimenService {

    @Resource
    private DeliverOrderServiceI deliverOrderService;

    @Override
    public QimenResponse execute(QimenRequest request) {
        OrderCancelRequest orderCancelRequest = (OrderCancelRequest) request;
        OrderCancelResponse response = new OrderCancelResponse();
        response.setFlag("success");
        logger.error("taobao.qimen.order.cancel：" + JSON.toJSONString(orderCancelRequest));
        DeliverOrder deliverOrderReq = new DeliverOrder();
        Integer supplierId = Integer.parseInt(ConvertNameUtil.getString(QimenRequestService.QIM_06));
        String orderCode = orderCancelRequest.getOrderCode();
        DeliverOrder deliverOrder = deliverOrderService.getBySupplierOrderIdAndSupplierId(supplierId, orderCode);
        if (deliverOrder != null) {
            String orderId = orderCode + "_" + new Date().getTime();
            String[] status = new String[]{DeliverOrderServiceI.STATUS_NOT_ALLOCATION, DeliverOrderServiceI.STATUS_SHOP_ALLOCATION, DeliverOrderServiceI.STATUS_SHOP_REFUSE, DeliverOrderServiceI.STATUS_SHOP_ACCEPT};
            deliverOrderReq.setId(deliverOrder.getId());
            deliverOrderReq.setSupplierOrderId(orderId);
            if (deliverOrder.getIsdeleted()) {
                deliverOrderReq.setOriginalOrderStatus(DeliverOrderServiceI.ORIGINAL_ORDER_STATUS_OTS03);
                deliverOrderService.edit(deliverOrderReq);
            } else if (ArrayUtils.contains(status, deliverOrder.getStatus())) {
                if (ShopDeliverApplyServiceI.DELIVER_WAY_AGENT.equals(deliverOrder.getDeliveryWay()) || ShopDeliverApplyServiceI.DELIVER_WAY_CUSTOMER_AGENT.equals(deliverOrder.getDeliveryWay())) {
                    if (!DeliverOrderServiceI.AGENT_STATUS_DTS01.equals(deliverOrder.getAgentStatus()) && !DeliverOrderServiceI.AGENT_STATUS_DTS02.equals(deliverOrder.getAgentStatus())) {
                        response.setFlag("failure");
                        return response;
                    }
                }
                deliverOrderReq.setStatus(DeliverOrderServiceI.STATUS_FAILURE_CLOSED);
                deliverOrderReq.setSupplierId(supplierId);
                deliverOrderReq.setRemark(orderCancelRequest.getCancelReason());
                deliverOrderService.transform(deliverOrderReq);
            } else {
                response.setFlag("failure");
                response.setMessage(String.format("运单不可取消状态为%s%s", deliverOrder.getStatus(), ConvertNameUtil.getString(deliverOrder.getStatus())));
            }
        }
        return response;
    }

    @Override
    public Class getParserRequestClass() {
        return OrderCancelRequest.class;
    }
}
