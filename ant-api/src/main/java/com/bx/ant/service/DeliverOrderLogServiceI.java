package com.bx.ant.service;

import com.mobian.pageModel.DeliverOrderLog;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;

/**
 * 
 * @author John
 * 
 */
public interface DeliverOrderLogServiceI {

	/**
	 * 获取DeliverOrderLog数据表格
	 * 
	 * @param deliverOrderLog
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(DeliverOrderLog deliverOrderLog, PageHelper ph);

	/**
	 * 添加DeliverOrderLog
	 * 
	 * @param deliverOrderLog
	 */
	public void add(DeliverOrderLog deliverOrderLog);

	/**
	 * 获得DeliverOrderLog对象
	 * 
	 * @param id
	 * @return
	 */
	public DeliverOrderLog get(Integer id);

	/**
	 * 修改DeliverOrderLog
	 * 
	 * @param deliverOrderLog
	 */
	public void edit(DeliverOrderLog deliverOrderLog);

	/**
	 * 删除DeliverOrderLog
	 * 
	 * @param id
	 */
	public void delete(Integer id);

}
