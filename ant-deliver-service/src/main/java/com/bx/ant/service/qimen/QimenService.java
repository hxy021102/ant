package com.bx.ant.service.qimen;

import com.qimen.api.QimenResponse;

/**
 * Created by john on 17/11/28.
 */
public interface QimenService {

    String JYCK = "JYCK";//一般交易出库单

    QimenResponse handle(String method,String body);
}
