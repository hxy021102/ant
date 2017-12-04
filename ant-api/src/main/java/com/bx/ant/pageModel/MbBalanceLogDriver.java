package com.bx.ant.pageModel;

import com.mobian.pageModel.MbBalance;
import com.mobian.pageModel.MbBalanceLog;

/**
 * Created by w9777 on 2017/11/6.
 */
public class MbBalanceLogDriver extends MbBalanceLog {
    private Integer accountId;

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }
}
