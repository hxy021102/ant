package com.mobian.service;

import com.mobian.model.TmbSupplierStockIn;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbSupplierStockIn;
import com.mobian.pageModel.PageHelper;

import java.util.List;

/**
 * @author John
 */
public interface MbSupplierStockInServiceI {

    /**
     * 获取MbSupplierStockIn数据表格
     *
     * @param mbSupplierStockIn 参数
     * @param ph                分页帮助类
     * @return
     */
    DataGrid dataGrid(MbSupplierStockIn mbSupplierStockIn, PageHelper ph);

    /**
     * 添加MbSupplierStockIn
     *
     * @param mbSupplierStockIn
     */
    void add(MbSupplierStockIn mbSupplierStockIn);

    /**
     * 获得MbSupplierStockIn对象
     *
     * @param id
     * @return
     */
    MbSupplierStockIn get(Integer id);

    /**
     * 修改MbSupplierStockIn
     *
     * @param mbSupplierStockIn
     */
    TmbSupplierStockIn edit(MbSupplierStockIn mbSupplierStockIn);

    /**
     * 删除MbSupplierStockIn
     *
     * @param id
     */
    void delete(Integer id);

    MbSupplierStockIn getByOrderId(Integer id);

    void addSupplierStockIn(MbSupplierStockIn mbSupplierStockIn, String dataGrid);

    void addStockInItem(String dataGrid, MbSupplierStockIn mbSupplierStockIn);

    void addPay(Integer id, String remark, String loginId);

    void addInvoice(Integer id, String remark, String loginId, String invoiceNo);
    List<MbSupplierStockIn> getMbSupplierStockInListByOrderId(Integer orderId);

    List<MbSupplierStockIn> getListByOrderIds(Integer[] orderIds);

    List<MbSupplierStockIn> query(MbSupplierStockIn stockIn);

    /**
     * 获取已入库且未结算的库存价值
     * @param supplierId
     * @return
     */
    Integer getUnPayStockIn(Integer supplierId);
}
