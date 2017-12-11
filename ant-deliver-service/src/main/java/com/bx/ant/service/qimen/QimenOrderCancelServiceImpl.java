package com.bx.ant.service.qimen;

import com.qimen.api.QimenRequest;
import com.qimen.api.QimenResponse;
import com.qimen.api.request.OrderCancelRequest;
import com.qimen.api.response.OrderCancelResponse;
import org.springframework.stereotype.Service;

/**
 * Created by john on 17/12/6.
 */
@Service("taobao.qimen.order.cancel")
public class QimenOrderCancelServiceImpl extends AbstrcatQimenService {
    @Override
    public QimenResponse execute(QimenRequest request) {
        OrderCancelRequest orderCancelRequest = (OrderCancelRequest)request;
        OrderCancelResponse response = new OrderCancelResponse();
        response.setFlag("success");
        return response;
    }

    @Override
    public Class getParserRequestClass() {
        return OrderCancelRequest.class;
    }
}
