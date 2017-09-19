package com.mobian.service;

import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbUserInvoice;
import com.mobian.pageModel.PageHelper;

/**
 * 
 * @author John
 * 
 */
public interface MbUserInvoiceServiceI {

	/**
	 * 获取MbUserInvoice数据表格
	 * 
	 * @param mbUserInvoice
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(MbUserInvoice mbUserInvoice, PageHelper ph);

	/**
	 * 添加MbUserInvoice
	 * 
	 * @param mbUserInvoice
	 */
	public void add(MbUserInvoice mbUserInvoice);

	/**
	 * 获得MbUserInvoice对象
	 * 
	 * @param id
	 * @return
	 */
	public MbUserInvoice get(Integer id);

	/**
	 * 修改MbUserInvoice
	 * 
	 * @param mbUserInvoice
	 */
	public void edit(MbUserInvoice mbUserInvoice);

	/**
	 * 删除MbUserInvoice
	 * 
	 * @param id
	 */
	public void delete(Integer id);

}
