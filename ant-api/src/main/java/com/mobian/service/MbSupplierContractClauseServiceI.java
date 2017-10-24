package com.mobian.service;

import com.mobian.pageModel.MbSupplierContractClause;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;

/**
 * 
 * @author John
 * 
 */
public interface MbSupplierContractClauseServiceI {

	/**
	 * 获取MbSupplierContractClause数据表格
	 * 
	 * @param mbSupplierContractClause
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(MbSupplierContractClause mbSupplierContractClause, PageHelper ph);

	/**
	 * 添加MbSupplierContractClause
	 * 
	 * @param mbSupplierContractClause
	 */
	public void add(MbSupplierContractClause mbSupplierContractClause);

	/**
	 * 获得MbSupplierContractClause对象
	 * 
	 * @param id
	 * @return
	 */
	public MbSupplierContractClause get(Integer id);

	/**
	 * 修改MbSupplierContractClause
	 * 
	 * @param mbSupplierContractClause
	 */
	public void edit(MbSupplierContractClause mbSupplierContractClause);

	/**
	 * 删除MbSupplierContractClause
	 * 
	 * @param id
	 */
	public void delete(Integer id);

}
