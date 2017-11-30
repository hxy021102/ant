package com.bx.ant.service.qimen;

import com.qimen.api.QimenRequest;
import com.qimen.api.QimenResponse;
import com.taobao.api.ApiException;
import com.taobao.api.internal.parser.xml.XmlConverter;
import com.taobao.api.internal.util.XmlWriter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by john on 17/11/28.
 */
public abstract class AbstrcatQimenService implements QimenService {
    public static final String RESPONSE = "response";

    @Override
    public QimenResponse handle(String method, String body) {
        QimenRequest request = parserRequest(body);
        QimenResponse response = execute(request);
        print(response);
        return response;
    }

    public abstract QimenResponse execute(QimenRequest request);

    public abstract Class getParserRequestClass();

    protected <T> T parserRequest(String body) {
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

    protected void print(QimenResponse qimenResponse) {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        XmlWriter writer = new XmlWriter(true, RESPONSE, Object.class);
        String text = writer.write(qimenResponse);
        response.setContentType("text/xml");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.print(text);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
