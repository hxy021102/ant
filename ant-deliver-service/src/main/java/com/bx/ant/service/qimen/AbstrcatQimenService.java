package com.bx.ant.service.qimen;

import com.qimen.api.QimenRequest;
import com.qimen.api.QimenResponse;
import com.taobao.api.ApiException;
import com.taobao.api.internal.parser.xml.XmlConverter;

/**
 * Created by john on 17/11/28.
 */
public abstract class AbstrcatQimenService implements QimenService {
    @Override
    public QimenResponse handle(String method, String body) {

        QimenRequest request = parserRequest(body);

        return execute(request);
    }

    public abstract QimenResponse execute(QimenRequest request);

    public abstract Class getParserRequestClass();

    private <T> T parserRequest(String body) {
        XmlConverter converter = new XmlConverter();
        T request = null;
        Class<T> _class = getParserRequestClass();
        try {
            request = converter.toResponse(body, _class);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return request;
    }
}
