package com.bx.ant.service.qimen;

import com.bx.ant.pageModel.DeliverOrder;
import com.bx.ant.service.DeliverOrderServiceI;
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
        OrderCancelRequest orderCancelRequest = (OrderCancelRequest)request;
        OrderCancelResponse response = new OrderCancelResponse();
        response.setFlag("success");
        DeliverOrder deliverOrderReq = new DeliverOrder();
        Integer supplierId = Integer.parseInt(ConvertNameUtil.getString(QimenRequestService.QIM_06));
        DeliverOrder deliverOrder = deliverOrderService.getBySupplierOrderIdAndSupplierId(supplierId, orderCancelRequest.getOrderId());
        if (deliverOrder != null) {
            String orderId = orderCancelRequest.getOrderId() + "_" + new Date().getTime();
            String[] status = new String[]{DeliverOrderServiceI.STATUS_NOT_ALLOCATION, DeliverOrderServiceI.STATUS_SHOP_ALLOCATION, DeliverOrderServiceI.STATUS_SHOP_REFUSE, DeliverOrderServiceI.STATUS_SHOP_ACCEPT};
            deliverOrderReq.setId(deliverOrder.getId());
            deliverOrderReq.setSupplierOrderId(orderId);
            if (deliverOrder.getIsdeleted()) {
                deliverOrderService.edit(deliverOrderReq);
            } else if (ArrayUtils.contains(status, deliverOrder.getStatus())) {
                deliverOrderReq.setStatus(DeliverOrderServiceI.STATUS_FAILURE_CLOSED);
                deliverOrderReq.setSupplierId(supplierId);
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
