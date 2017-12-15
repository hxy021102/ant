package com.mobian.service;

import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbStockOut;
import com.mobian.pageModel.MbStockOutOrder;
import com.mobian.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface MbStockOutServiceI {

	/**
	 * 获取MbStockOut数据表格
	 * 
	 * @param mbStockOut
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(MbStockOut mbStockOut, PageHelper ph);

	/**
	 * 添加MbStockOut
	 * 
	 * @param mbStockOut
	 */
	public void add(MbStockOut mbStockOut);

	/**
	 * 获得MbStockOut对象
	 * 
	 * @param id
	 * @return
	 */
	public MbStockOut get(Integer id);

	/**
	 * 修改MbStockOut
	 * 
	 * @param mbStockOut
	 */
	public void edit(MbStockOut mbStockOut);

	/**
	 * 删除MbStockOut
	 * 
	 * @param id
	 */
	public void delete(Integer id);
	/**
	 * 添加出库明细
	 */
 	void addStockOut(MbStockOut mbStockOut, String dataGrid);

	void addStockOut(MbStockOut mbStockOut, String dataGrid, String deliverOrderIds);
	/**
	 * 添加出库商品项
	 */
    void addStockOutItem(MbStockOut mbStockOut, String dataGrid);
	/**
	 * 通过出库id删除出库明细
	 */
	void deleteStockOutItem(Integer id);

}
