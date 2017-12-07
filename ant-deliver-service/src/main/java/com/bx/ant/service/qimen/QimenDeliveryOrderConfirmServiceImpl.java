package com.bx.ant.service.qimen;

import com.qimen.api.QimenResponse;
import org.springframework.stereotype.Service;

/**
 * Created by john on 17/11/28.
 * 发货单确认
 */
@Service("taobao.qimen.deliveryorder.confirm")
public class QimenDeliveryOrderConfirmServiceImpl implements QimenService {
    @Override
    public QimenResponse handle(String method, String body) {
        return null;
    }
}
