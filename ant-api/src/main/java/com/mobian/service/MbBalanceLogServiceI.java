package com.mobian.service;

import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbBalanceLog;
import com.mobian.pageModel.PageHelper;

/**
 * 
 * @author John
 * 
 */
public interface MbBalanceLogServiceI {

	/**
	 * 获取MbBalanceLog数据表格
	 * 
	 * @param mbBalanceLog
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	DataGrid dataGrid(MbBalanceLog mbBalanceLog, PageHelper ph);

	/**
	 * 添加MbBalanceLog
	 * 
	 * @param mbBalanceLog
	 */
	void add(MbBalanceLog mbBalanceLog);

	/**
	 * 添加并刷新余额
	 * @param mbBalanceLog
	 */
	void addAndUpdateBalance(MbBalanceLog mbBalanceLog);

	/**
	 * 修改并刷新余额
	 * @param mbBalanceLog
	 */
	void updateLogAndBalance(MbBalanceLog mbBalanceLog);

	/**
	 * 获得MbBalanceLog对象
	 * 
	 * @param id
	 * @return
	 */
	MbBalanceLog get(Integer id);

	/**
	 * 修改MbBalanceLog
	 * 
	 * @param mbBalanceLog
	 */
	void edit(MbBalanceLog mbBalanceLog);

	/**
	 * 删除MbBalanceLog
	 * 
	 * @param id
	 */
	void delete(Integer id);

}
