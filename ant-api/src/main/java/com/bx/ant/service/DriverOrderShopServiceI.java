package com.bx.ant.service;

import com.bx.ant.pageModel.DriverOrderShop;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;

/**
 * 
 * @author John
 * 
 */
public interface DriverOrderShopServiceI {

	/**
	 * 获取DriverOrderShop数据表格
	 * 
	 * @param driverOrderShop
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(DriverOrderShop driverOrderShop, PageHelper ph);

	/**
	 * 添加DriverOrderShop
	 * 
	 * @param driverOrderShop
	 */
	public void add(DriverOrderShop driverOrderShop);

	/**
	 * 获得DriverOrderShop对象
	 * 
	 * @param id
	 * @return
	 */
	public DriverOrderShop get(Integer id);

	/**
	 * 修改DriverOrderShop
	 * 
	 * @param driverOrderShop
	 */
	public void edit(DriverOrderShop driverOrderShop);

	/**
	 * 删除DriverOrderShop
	 * 
	 * @param id
	 */
	public void delete(Integer id);

}
