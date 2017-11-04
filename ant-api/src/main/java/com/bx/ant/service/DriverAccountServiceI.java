package com.bx.ant.service;

import com.bx.ant.pageModel.DriverAccount;
import com.bx.ant.pageModel.DriverAccountView;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;

/**
 * 
 * @author John
 * 
 */
public interface DriverAccountServiceI {

	/**
	 * 获取DriverAccount数据表格
	 * 
	 * @param driverAccount
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(DriverAccount driverAccount, PageHelper ph);

	/**
	 * 添加DriverAccount
	 * 
	 * @param driverAccount
	 */
	public void add(DriverAccount driverAccount);

	/**
	 * 获得DriverAccount对象
	 * 
	 * @param id
	 * @return
	 */
	public DriverAccount get(Integer id);

	/**
	 * 修改DriverAccount
	 * 
	 * @param driverAccount
	 */
	public void edit(DriverAccount driverAccount);

	/**
	 * 删除DriverAccount
	 * 
	 * @param id
	 */
	public void delete(Integer id);

    DriverAccountView getView(Integer id);

	DataGrid dataGridView(DriverAccount driverAccount, PageHelper pageHelper);
}
