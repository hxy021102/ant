package com.mobian.service;

import com.mobian.pageModel.MbSupplierInvoice;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface MbSupplierInvoiceServiceI {

	/**
	 * 获取MbSupplierInvoice数据表格
	 * 
	 * @param mbSupplierInvoice
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(MbSupplierInvoice mbSupplierInvoice, PageHelper ph);

	/**
	 * 添加MbSupplierInvoice
	 * 
	 * @param mbSupplierInvoice
	 */
	public void add(MbSupplierInvoice mbSupplierInvoice);

	/**
	 * 获得MbSupplierInvoice对象
	 * 
	 * @param id
	 * @return
	 */
	public MbSupplierInvoice get(Integer id);

	/**
	 * 修改MbSupplierInvoice
	 * 
	 * @param mbSupplierInvoice
	 */
	public void edit(MbSupplierInvoice mbSupplierInvoice);

	/**
	 * 删除MbSupplierInvoice
	 * 
	 * @param id
	 */
	public void delete(Integer id);


	List<MbSupplierInvoice> query(MbSupplierInvoice mbSupplierInvoice);

}
