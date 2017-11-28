package com.bx.ant.service.qimen;

import com.qimen.api.QimenRequest;
import com.qimen.api.QimenResponse;
import com.qimen.api.request.ItemsSynchronizeRequest;
import com.qimen.api.request.SingleitemSynchronizeRequest;
import com.qimen.api.response.ItemsSynchronizeResponse;
import com.qimen.api.response.SingleitemSynchronizeResponse;
import org.springframework.stereotype.Service;

/**
 * Created by john on 17/11/28.
 */
@Service("taobao.qimen.items.synchronize")
public class QimenItemsQimenServiceImpl extends QimenSingleitemQimenServiceImpl {
    @Override
    public QimenResponse execute(QimenRequest request) {
        ItemsSynchronizeRequest req = (ItemsSynchronizeRequest) request;
        //TODO 商品信息同步
        //同步我们的item表

        ItemsSynchronizeResponse response = new ItemsSynchronizeResponse();
        return response;
    }

    @Override
    public Class getParserRequestClass() {
        return ItemsSynchronizeRequest.class;
    }
}
