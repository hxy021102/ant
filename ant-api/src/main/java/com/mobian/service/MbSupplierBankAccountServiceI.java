package com.mobian.service;

import com.mobian.pageModel.MbSupplierBankAccount;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;

/**
 * 
 * @author John
 * 
 */
public interface MbSupplierBankAccountServiceI {

	/**
	 * 获取MbSupplierBankAccount数据表格
	 * 
	 * @param mbSupplierBankAccount
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(MbSupplierBankAccount mbSupplierBankAccount, PageHelper ph);

	/**
	 * 添加MbSupplierBankAccount
	 * 
	 * @param mbSupplierBankAccount
	 */
	public void add(MbSupplierBankAccount mbSupplierBankAccount);

	/**
	 * 获得MbSupplierBankAccount对象
	 * 
	 * @param id
	 * @return
	 */
	public MbSupplierBankAccount get(Integer id);

	/**
	 * 修改MbSupplierBankAccount
	 * 
	 * @param mbSupplierBankAccount
	 */
	public void edit(MbSupplierBankAccount mbSupplierBankAccount);

	/**
	 * 删除MbSupplierBankAccount
	 * 
	 * @param id
	 */
	public void delete(Integer id);

}
