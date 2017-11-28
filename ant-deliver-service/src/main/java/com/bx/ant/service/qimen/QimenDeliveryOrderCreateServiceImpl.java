package com.bx.ant.service.qimen;

import com.qimen.api.QimenRequest;
import com.qimen.api.QimenResponse;
import org.springframework.stereotype.Service;

/**
 * Created by john on 17/11/28.
 * 发货单创建
 */
@Service("taobao.qimen.deliveryorder.create")
public class QimenDeliveryOrderCreateServiceImpl extends AbstrcatQimenService {
    @Override
    public QimenResponse execute(QimenRequest request) {
        return null;
    }

    @Override
    public Class getParserRequestClass() {
        return null;
    }
}
