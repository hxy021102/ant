package com.bx.ant.service.qimen;

import com.qimen.api.QimenRequest;
import com.qimen.api.QimenResponse;
import com.qimen.api.response.StockoutCreateResponse;
import org.springframework.stereotype.Service;

/**
 * Created by john on 17/11/30.
 */
@Service("qimenErrorServiceImpl")
public class QimenErrorServiceImpl extends AbstrcatQimenService {
    @Override
    public QimenResponse execute(QimenRequest request) {
        StockoutCreateResponse qimenResponse = new StockoutCreateResponse();
        qimenResponse.setFlag("failure");
        qimenResponse.setCode("500");
        return qimenResponse;
    }

    @Override
    public Class getParserRequestClass() {
        return null;
    }

    protected <T> T parserRequest(String body){
        return null;
    }
}
