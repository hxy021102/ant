package com.bx.ant.service;

import com.bx.ant.pageModel.DriverFreightRule;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;

/**
 * 
 * @author John
 * 
 */
public interface DriverFreightRuleServiceI {

	/**
	 * 获取DriverFreightRule数据表格
	 * 
	 * @param driverFreightRule
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(DriverFreightRule driverFreightRule, PageHelper ph);

	/**
	 * 添加DriverFreightRule
	 * 
	 * @param driverFreightRule
	 */
	public void add(DriverFreightRule driverFreightRule);

	/**
	 * 获得DriverFreightRule对象
	 * 
	 * @param id
	 * @return
	 */
	public DriverFreightRule get(Integer id);

	/**
	 * 修改DriverFreightRule
	 * 
	 * @param driverFreightRule
	 */
	public void edit(DriverFreightRule driverFreightRule);

	/**
	 * 删除DriverFreightRule
	 * 
	 * @param id
	 */
	public void delete(Integer id);

}
