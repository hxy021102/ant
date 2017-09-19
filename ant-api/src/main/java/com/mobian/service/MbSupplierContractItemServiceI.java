package com.mobian.service;

import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbSupplierContractItem;
import com.mobian.pageModel.PageHelper;

/**
 * 
 * @author John
 * 
 */
public interface MbSupplierContractItemServiceI {

	/**
	 * 获取MbSupplierContractItem数据表格
	 * 
	 * @param mbSupplierContractItem
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(MbSupplierContractItem mbSupplierContractItem, PageHelper ph);

	/**
	 * 添加MbSupplierContractItem
	 * 
	 * @param mbSupplierContractItem
	 */
	public void add(MbSupplierContractItem mbSupplierContractItem);

	/**
	 * 获得MbSupplierContractItem对象
	 * 
	 * @param id
	 * @return
	 */
	public MbSupplierContractItem get(Integer id);

	/**
	 * 修改MbSupplierContractItem
	 * 
	 * @param mbSupplierContractItem
	 */
	public void edit(MbSupplierContractItem mbSupplierContractItem);

	/**
	 * 删除MbSupplierContractItem
	 * 
	 * @param id
	 */
	public void delete(Integer id);

	public void editAndHistory(MbSupplierContractItem mbSupplierContractItem);

}
