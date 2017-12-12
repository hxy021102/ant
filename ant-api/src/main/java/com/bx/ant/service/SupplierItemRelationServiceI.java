package com.bx.ant.service;

import com.bx.ant.pageModel.SupplierItemRelation;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;

/**
 * 
 * @author John
 * 
 */
public interface SupplierItemRelationServiceI {

	/**
	 * 获取SupplierItemRelation数据表格
	 * 
	 * @param supplierItemRelation
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(SupplierItemRelation supplierItemRelation, PageHelper ph);

	/**
	 * 添加SupplierItemRelation
	 * 
	 * @param supplierItemRelation
	 */
	public void add(SupplierItemRelation supplierItemRelation);

	/**
	 * 获得SupplierItemRelation对象
	 * 
	 * @param id
	 * @return
	 */
	public SupplierItemRelation get(Integer id);

	/**
	 * 修改SupplierItemRelation
	 * 
	 * @param supplierItemRelation
	 */
	public void edit(SupplierItemRelation supplierItemRelation);

	/**
	 * 删除SupplierItemRelation
	 * 
	 * @param id
	 */
	public void delete(Integer id);

    DataGrid dataGridView(SupplierItemRelation supplierItemRelation, PageHelper ph);
}
