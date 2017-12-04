package com.bx.ant.service;

import com.bx.ant.pageModel.DeliverOrder;
import com.bx.ant.pageModel.DriverOrderShop;

/**
 * Created by wanxp on 17-11-30.
 */
public abstract class AbstractDriverOrderShopState {

    public void handle(DeliverOrder deliverOrder) {
        message(deliverOrder);
        execute(deliverOrder);
    }
    public abstract void execute(DeliverOrder deliverOrder);
    protected void message(DeliverOrder deliverOrder) {

    }
}
