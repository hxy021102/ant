package com.mobian.service;

import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbItemStockLog;
import com.mobian.pageModel.PageHelper;

import java.util.List;

/**
 * @author John
 */
public interface MbItemStockLogServiceI {

    /**
     * 获取MbItemStockLog数据表格
     *
     * @param mbItemStockLog 参数
     * @param ph             分页帮助类
     * @return
     */
    DataGrid dataGrid(MbItemStockLog mbItemStockLog, PageHelper ph);

    /**
     * 添加MbItemStockLog
     *
     * @param mbItemStockLog
     */
    void add(MbItemStockLog mbItemStockLog);

    /**
     * 获得MbItemStockLog对象
     *
     * @param id
     * @return
     */
    MbItemStockLog get(Integer id);

    /**
     * 修改MbItemStockLog
     *
     * @param mbItemStockLog
     */
    void edit(MbItemStockLog mbItemStockLog);

    /**
     * 删除MbItemStockLog
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * 获取MbItemStockLog的数量不为0和类型为盘点的数据表格
     *
     * @param mbItemStockLog 参数
     * @param ph             分页帮助类
     * @return
     */
    DataGrid dataGridStockInLog(MbItemStockLog mbItemStockLog, PageHelper ph);

    /**
     * 查询未冗余结余单价的记录
     *
     * @return
     */
    List<MbItemStockLog> queryListByWithoutCostPrice();

}
