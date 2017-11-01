package com.mobian.service;

import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbStockOutItem;
import com.mobian.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface MbStockOutItemServiceI {

	/**
	 * 获取MbStockOutItem数据表格
	 * 
	 * @param mbStockOutItem
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	DataGrid dataGrid(MbStockOutItem mbStockOutItem, PageHelper ph);

	/**
	 * 添加MbStockOutItem
	 * 
	 * @param mbStockOutItem
	 */
	void add(MbStockOutItem mbStockOutItem);

	/**
	 * 获得MbStockOutItem对象
	 * 
	 * @param id
	 * @return
	 */
	MbStockOutItem get(Integer id);

	/**
	 * 修改MbStockOutItem
	 * 
	 * @param mbStockOutItem
	 */
	void edit(MbStockOutItem mbStockOutItem);

	/**
	 * 删除MbStockOutItem
	 * 
	 * @param id
	 */
	void delete(Integer id);

	/**
	 * 通过出库项查看入库项明细
	 */
	List<MbStockOutItem> queryStockOutItem(MbStockOutItem mbStockOutItem);

	/**
	 * 查询出库无cost price
	 * @return
	 */
	List<MbStockOutItem> queryStockOutItemWithoutCostPrice();

}
