package com.mobian.service;

import com.mobian.pageModel.MbStockOutOrder;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;

/**
 * 
 * @author John
 * 
 */
public interface MbStockOutOrderServiceI {

	/**
	 * 获取MbStockOutOrder数据表格
	 * 
	 * @param mbStockOutOrder
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(MbStockOutOrder mbStockOutOrder, PageHelper ph);

	/**
	 * 添加MbStockOutOrder
	 * 
	 * @param mbStockOutOrder
	 */
	public void add(MbStockOutOrder mbStockOutOrder);

	/**
	 * 获得MbStockOutOrder对象
	 * 
	 * @param id
	 * @return
	 */
	public MbStockOutOrder get(Integer id);

	/**
	 * 修改MbStockOutOrder
	 * 
	 * @param mbStockOutOrder
	 */
	public void edit(MbStockOutOrder mbStockOutOrder);

	/**
	 * 删除MbStockOutOrder
	 * 
	 * @param id
	 */
	public void delete(Integer id);

}
