package com.bx.ant.service;

import com.bx.ant.pageModel.SupplierOrderBill;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;

/**
 * 
 * @author John
 * 
 */
public interface SupplierOrderBillServiceI {

	/**
	 * 获取SupplierOrderBill数据表格
	 * 
	 * @param supplierOrderBill
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(SupplierOrderBill supplierOrderBill, PageHelper ph);

	/**
	 * 添加SupplierOrderBill
	 * 
	 * @param supplierOrderBill
	 */
	public void add(SupplierOrderBill supplierOrderBill);

	/**
	 * 获得SupplierOrderBill对象
	 * 
	 * @param id
	 * @return
	 */
	public SupplierOrderBill get(Long id);

	/**
	 * 修改SupplierOrderBill
	 * 
	 * @param supplierOrderBill
	 */
	public void edit(SupplierOrderBill supplierOrderBill);

	/**
	 * 删除SupplierOrderBill
	 * 
	 * @param id
	 */
	public void delete(Long id);
	/**
	 * 审核账单
	 */
	Integer editBillStatus(SupplierOrderBill supplierOrderBill, Boolean isAgree);

}
