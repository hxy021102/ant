package com.bx.ant.service.qimen;

import com.mobian.exception.ServiceException;
import com.mobian.util.ConvertNameUtil;
import com.qimen.api.DefaultQimenClient;
import com.qimen.api.QimenRequest;
import com.qimen.api.request.DeliveryorderConfirmRequest;
import com.qimen.api.response.DeliveryorderConfirmResponse;
import com.taobao.api.ApiException;
import org.springframework.stereotype.Service;

/**
 * Created by john on 17/11/30.
 */
@Service
public class QimenRequestServiceImpl implements QimenRequestService {


    @Override
    public void updateDeliveryOrderConfirm() {
        DeliveryorderConfirmRequest request = new DeliveryorderConfirmRequest();

        //TODO 拼request Info

        DeliveryorderConfirmResponse response = execute(request);
    }

    private <T> T execute(QimenRequest request) {
        request.setVersion(ConvertNameUtil.getString(QIM_05));
        request.setCustomerId(ConvertNameUtil.getString(QIM_04));
        try {
            return (T) getClient().execute(request);
        } catch (ApiException e) {
            throw new ServiceException("调用奇门接口异常", e);
        }
    }

    private DefaultQimenClient getClient() {
        String serviceUrl = ConvertNameUtil.getString(QIM_03);
        String appKey = ConvertNameUtil.getString(QIM_01);
        String appSecret = ConvertNameUtil.getString(QIM_02);
        DefaultQimenClient qimenClient = new DefaultQimenClient(serviceUrl, appKey, appSecret);
        return qimenClient;
    }
}
