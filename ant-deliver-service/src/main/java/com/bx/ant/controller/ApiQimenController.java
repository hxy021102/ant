package com.bx.ant.controller;

import com.bx.ant.service.qimen.QimenRequestService;
import com.bx.ant.service.qimen.QimenService;
import com.bx.ant.util.SpiUtils;
import com.mobian.pageModel.Json;
import com.mobian.util.ConvertNameUtil;
import com.qimen.api.QimenRequest;
import com.qimen.api.QimenResponse;
import com.qimen.api.response.StockoutCreateResponse;
import com.taobao.api.Constants;
import com.taobao.api.internal.spi.CheckResult;
import com.taobao.api.internal.util.XmlWriter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Created by john on 17/11/27.
 */
@Controller
@RequestMapping("/api/qimen")
public class ApiQimenController {

    @Resource
    private Map<String, QimenService> qimenServiceMap;

    @Resource(name = "qimenErrorServiceImpl")
    private QimenService qimenErrorServiceImpl;

    @RequestMapping("/service")
    public void service(String method, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String targetAppSecret = ConvertNameUtil.getString(QimenRequestService.QIM_02, "sandbox73191330f66ff9ac337012771");
        CheckResult result = SpiUtils.checkSignForBx(request, targetAppSecret); //这里执行验签逻辑
        method = "taobao.qimen." + method;
        QimenService qimenService = qimenServiceMap.get(method);
        if (!result.isSuccess() || qimenService == null) {
            qimenService = qimenErrorServiceImpl;
        }
        qimenService.handle(method, result.getRequestBody());

    }


}
