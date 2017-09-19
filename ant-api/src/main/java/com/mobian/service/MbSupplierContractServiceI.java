package com.mobian.service;

import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.Json;
import com.mobian.pageModel.MbSupplierContract;
import com.mobian.pageModel.PageHelper;

/**
 *
 * @author John
 *
 */
public interface MbSupplierContractServiceI {

	/**
	 * 获取MbSupplierContract数据表格
	 *
	 * @param mbSupplierContract
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(MbSupplierContract mbSupplierContract, PageHelper ph);

	/**
	 * 添加MbSupplierContract
	 *
	 * @param mbSupplierContract
	 */
	public void add(MbSupplierContract mbSupplierContract);

	/**
	 * 获得MbSupplierContract对象
	 *
	 * @param id
	 * @return
	 */
	public MbSupplierContract get(Integer id);

	/**
	 * 修改MbSupplierContract
	 *
	 * @param mbSupplierContract
	 */
	public void edit(MbSupplierContract mbSupplierContract);

	/**
	 * 删除MbSupplierContract
	 *
	 * @param id
	 */
	public void delete(Integer id);
	public boolean isContractExists(MbSupplierContract mbSupplierContract);
	/**
	 * 判断相同供应商是否存在
	 *
	 * @param
	 */
	public boolean isSupplierExists(MbSupplierContract mbSupplierContract);

	public Json editMbSupplierContract(MbSupplierContract mbSupplierContract);

}
