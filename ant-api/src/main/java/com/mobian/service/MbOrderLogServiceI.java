package com.mobian.service;

import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbOrderLog;
import com.mobian.pageModel.PageHelper;

/**
 * 
 * @author John
 * 
 */
public interface MbOrderLogServiceI {

	/**
	 * 获取MbOrderLog数据表格
	 * 
	 * @param mbOrderLog
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	DataGrid dataGrid(MbOrderLog mbOrderLog, PageHelper ph);

	/**
	 * 添加MbOrderLog
	 * 
	 * @param mbOrderLog
	 */
	void add(MbOrderLog mbOrderLog);

	/**
	 * 获得MbOrderLog对象
	 * 
	 * @param id
	 * @return
	 */
	MbOrderLog get(Integer id);

	/**
	 * 修改MbOrderLog
	 * 
	 * @param mbOrderLog
	 */
	void edit(MbOrderLog mbOrderLog);

	/**
	 * 删除MbOrderLog
	 * 
	 * @param id
	 */
	void delete(Integer id);

	/**
	 * 订单日志
	 * @param orderId
	 * @param logType
	 * @return
	 */
	MbOrderLog getByIdAndType(Integer orderId, String logType);

}
