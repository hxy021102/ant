package com.bx.ant.service;

import com.bx.ant.pageModel.DriverOrderShopBill;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;


/**
 * 
 * @author John
 * 
 */
public interface DriverOrderShopBillServiceI {

	/**
	 * 获取DriverOrderShopBill数据表格
	 * 
	 * @param driverOrderShopBill
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(DriverOrderShopBill driverOrderShopBill, PageHelper ph);

	/**
	 * 添加DriverOrderShopBill
	 * 
	 * @param driverOrderShopBill
	 */
	public void add(DriverOrderShopBill driverOrderShopBill);

	/**
	 * 获得DriverOrderShopBill对象
	 * 
	 * @param id
	 * @return
	 */
	public DriverOrderShopBill get(Integer id);

	/**
	 * 修改DriverOrderShopBill
	 * 
	 * @param driverOrderShopBill
	 */
	public void edit(DriverOrderShopBill driverOrderShopBill);

	/**
	 * 删除DriverOrderShopBill
	 * 
	 * @param id
	 */
	public void delete(Integer id);

}
