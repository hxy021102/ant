
/**
 * Created by wanxp on 17-12-1.
 */
package com.bx.ant.service;

import com.bx.ant.pageModel.DeliverOrder;

/**
 * Created by wanxp on 17-11-30.
 */
public abstract class AbstractDeliverOrderState implements DeliverOrderState {

    public void handle(DeliverOrder deliverOrder) {
        preHandle(deliverOrder);
        execute(deliverOrder);
        afterCompletion(deliverOrder);
    }

    public abstract void execute(DeliverOrder deliverOrder);

    protected void preHandle(DeliverOrder deliverOrder) {

    }

    protected void afterCompletion(DeliverOrder deliverOrder) {

    }
}
