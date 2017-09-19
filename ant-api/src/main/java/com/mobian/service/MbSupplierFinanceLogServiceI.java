package com.mobian.service;

import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbSupplierFinanceLog;
import com.mobian.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface MbSupplierFinanceLogServiceI {

	/**
	 * 获取MbSupplierFinanceLog数据表格
	 * 
	 * @param mbSupplierFinanceLog
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(MbSupplierFinanceLog mbSupplierFinanceLog, PageHelper ph);

	/**
	 * 添加MbSupplierFinanceLog
	 * 
	 * @param mbSupplierFinanceLog
	 */
	public void add(MbSupplierFinanceLog mbSupplierFinanceLog);

	/**
	 * 获得MbSupplierFinanceLog对象
	 * 
	 * @param id
	 * @return
	 */
	public MbSupplierFinanceLog get(Integer id);

	/**
	 * 修改MbSupplierFinanceLog
	 * 
	 * @param mbSupplierFinanceLog
	 */
	public void edit(MbSupplierFinanceLog mbSupplierFinanceLog);

	/**
	 * 删除MbSupplierFinanceLog
	 * 
	 * @param id
	 */
	public void delete(Integer id);
	public List <MbSupplierFinanceLog> query(MbSupplierFinanceLog mbSupplierFinanceLog);



}
