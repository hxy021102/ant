package com.bx.ant.service.qimen;

import com.qimen.api.QimenRequest;
import com.qimen.api.QimenResponse;
import com.qimen.api.request.StockoutCreateRequest;
import com.qimen.api.response.StockoutCreateResponse;
import org.springframework.stereotype.Service;

/**
 * Created by john on 17/11/28.
 */
@Service("taobao.qimen.stockout.create")
public class QimenStockOutCreateServiceImpl extends AbstrcatQimenService {

    @Override
    public QimenResponse execute(QimenRequest request) {
        StockoutCreateRequest stockoutCreateRequest = (StockoutCreateRequest) request;
        StockoutCreateResponse stockoutCreateResponse = new StockoutCreateResponse();
        //TODO 创建出库单
        return stockoutCreateResponse;
    }

    @Override
    public Class getParserRequestClass() {
        return StockoutCreateRequest.class;
    }
}
