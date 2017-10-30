package com.mobian.pageModel;

import com.mobian.util.ConvertNameUtil;

/**
 * Created by Administrator on 2017/10/27.
 */
public class MbSupplierInvoiceExt extends MbSupplierInvoice{
    private  String  invoiceUseName;
    private  String  invoiceTypeName;

    public String getInvoiceUseName() {
        return ConvertNameUtil.getString(this.invoiceUseName);
    }

    public void setInvoiceUseName(String invoiceUseName) {
        this.invoiceUseName = invoiceUseName;
    }

    public String getInvoiceTypeName() {
        return ConvertNameUtil.getString(this.invoiceTypeName);
    }

    public void setInvoiceTypeName(String invoiceTypeName) {
        this.invoiceTypeName = invoiceTypeName;
    }
}
