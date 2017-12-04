package com.bx.ant.pageModel;

import java.util.Date;

/**
 * Created by 黄晓渝 on 2017/11/9.
 */
public class DriverAccountQuery extends DriverAccount {
    private Date addtimeBegin;
    private Date addtimeEnd;

    public Date getAddtimeBegin() {
        return addtimeBegin;
    }

    public void setAddtimeBegin(Date addtimeBegin) {
        this.addtimeBegin = addtimeBegin;
    }

    public Date getAddtimeEnd() {
        return addtimeEnd;
    }

    public void setAddtimeEnd(Date addtimeEnd) {
        this.addtimeEnd = addtimeEnd;
    }
}
