package com.mobian.service;

import com.bx.ant.pageModel.Supplier;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbSupplier;
import com.mobian.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface MbSupplierServiceI {

	/**
	 * 获取MbSupplier数据表格
	 * 
	 * @param mbSupplier
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(MbSupplier mbSupplier, PageHelper ph);

	/**
	 * 添加MbSupplier
	 * 
	 * @param mbSupplier
	 */
	public void add(MbSupplier mbSupplier);
	MbSupplier getFromCache(Integer id);

	/**
	 * 获得MbSupplier对象
	 * 
	 * @param id
	 * @return
	 */
	public MbSupplier get(Integer id);

	/**
	 * 修改MbSupplier
	 * 
	 * @param mbSupplier
	 */
	public void edit(MbSupplier mbSupplier);

	/**
	 * 删除MbSupplier
	 * 
	 * @param id
	 */
	public void delete(Integer id);
	/**
	 *  通过供应商编码查找
	 */
	 MbSupplier  getBySupplierCode(String supplierCode);


}
