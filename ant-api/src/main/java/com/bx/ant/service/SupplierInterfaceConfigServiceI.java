package com.bx.ant.service;


import com.bx.ant.pageModel.SupplierInterfaceConfig;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;

/**
 * 
 * @author John
 * 
 */
public interface SupplierInterfaceConfigServiceI {

	/**
	 * 获取SupplierInterfaceConfig数据表格
	 * 
	 * @param supplierInterfaceConfig
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(SupplierInterfaceConfig supplierInterfaceConfig, PageHelper ph);

	/**
	 * 添加SupplierInterfaceConfig
	 * 
	 * @param supplierInterfaceConfig
	 */
	public void add(SupplierInterfaceConfig supplierInterfaceConfig);

	/**
	 * 获得SupplierInterfaceConfig对象
	 * 
	 * @param id
	 * @return
	 */
	public SupplierInterfaceConfig get(Integer id);

	/**
	 * 修改SupplierInterfaceConfig
	 * 
	 * @param supplierInterfaceConfig
	 */
	public void edit(SupplierInterfaceConfig supplierInterfaceConfig);

	/**
	 * 删除SupplierInterfaceConfig
	 * 
	 * @param id
	 */
	public void delete(Integer id);

	SupplierInterfaceConfig getByCustomerId(String customerId);
	SupplierInterfaceConfig getBySupplierId(Integer supplierId);
}
