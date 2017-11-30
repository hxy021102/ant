package com.bx.ant.service.qimen;

import com.qimen.api.QimenRequest;
import com.qimen.api.QimenResponse;
import com.qimen.api.request.DeliveryorderCreateRequest;
import com.qimen.api.response.DeliveryorderCreateResponse;
import org.springframework.stereotype.Service;

/**
 * Created by john on 17/11/28.
 * 发货单创建
 */
@Service("taobao.qimen.deliveryorder.create")
public class QimenDeliveryOrderCreateServiceImpl extends AbstrcatQimenService {
    @Override
    public QimenResponse execute(QimenRequest request) {
        DeliveryorderCreateRequest req = (DeliveryorderCreateRequest)request;
        DeliveryorderCreateResponse response = new DeliveryorderCreateResponse();
        response.setFlag("success");
        return response;
    }

    @Override
    public Class getParserRequestClass() {
        return DeliveryorderCreateRequest.class;
    }

    @Override
    public Class getParserResponseClass() {
        return DeliveryorderCreateResponse.class;
    }
}
