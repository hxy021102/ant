package com.bx.ant.service.qimen;

import com.qimen.api.QimenRequest;
import com.qimen.api.QimenResponse;
import com.qimen.api.request.SingleitemSynchronizeRequest;
import com.qimen.api.response.SingleitemSynchronizeResponse;
import org.springframework.stereotype.Service;

/**
 * Created by john on 17/11/28.
 */
@Service("taobao.qimen.singleitem.synchronize")
public class QimenSingleitemQimenServiceImpl extends AbstrcatQimenService {
    @Override
    public QimenResponse execute(QimenRequest request) {
        SingleitemSynchronizeRequest req = (SingleitemSynchronizeRequest) request;
        //TODO 商品信息同步
        //同步我们的item表

        SingleitemSynchronizeResponse response = new SingleitemSynchronizeResponse();
        return response;
    }

    @Override
    public Class getParserRequestClass() {
        return SingleitemSynchronizeRequest.class;
    }
}
