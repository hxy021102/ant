package com.bx.ant.controller;

import com.bx.ant.pageModel.SupplierInterfaceConfig;
import com.bx.ant.service.SupplierInterfaceConfigServiceI;
import com.bx.ant.service.qimen.QimenRequestService;
import com.bx.ant.service.qimen.QimenService;
import com.bx.ant.util.SpiUtils;
import com.mobian.util.ConvertNameUtil;
import com.taobao.api.internal.spi.CheckResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by john on 17/11/27.
 */
@Controller
@RequestMapping("/api/deliver/qimen")
public class ApiQimenController {

    @Resource
    private Map<String, QimenService> qimenServiceMap;

    @Resource(name = "qimenErrorServiceImpl")
    private QimenService qimenErrorServiceImpl;

    @Resource
    private SupplierInterfaceConfigServiceI supplierInterfaceConfigService;

    @RequestMapping("/service")
    public void service(String method, String customerId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        method = "taobao.qimen." + method;
        QimenService qimenService = qimenServiceMap.get(method);

        CheckResult result = null;
        SupplierInterfaceConfig config = supplierInterfaceConfigService.getByCustomerId(customerId);
        if(config != null) {
//            String targetAppSecret = ConvertNameUtil.getString(QimenRequestService.QIM_02, "sandbox73191330f66ff9ac337012771");
            String targetAppSecret = config.getAppSecret();
            result = SpiUtils.checkSignForBx(request, targetAppSecret); //这里执行验签逻辑

            if (!result.isSuccess() || qimenService == null) {
                qimenService = qimenErrorServiceImpl;
            }
        } else {
            qimenService = qimenErrorServiceImpl;
        }

        qimenService.handle(method, customerId, result.getRequestBody());

    }


}
