package com.bx.ant.service;

import com.bx.ant.pageModel.DeliverOrderPay;
import com.bx.ant.pageModel.DriverOrderPay;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface DriverOrderPayServiceI {

	/**
	 * 获取DriverOrderPay数据表格
	 * 
	 * @param driverOrderPay
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(DriverOrderPay driverOrderPay, PageHelper ph);

	/**
	 * 添加DriverOrderPay
	 * 
	 * @param driverOrderPay
	 */
	public void add(DriverOrderPay driverOrderPay);

    DriverOrderPay update(DriverOrderPay driverOrderPay);

    /**
	 * 获得DriverOrderPay对象
	 * 
	 * @param id
	 * @return
	 */
	public DriverOrderPay get(Integer id);

	/**
	 * 修改DriverOrderPay
	 * 
	 * @param driverOrderPay
	 */
	public void edit(DriverOrderPay driverOrderPay);

	/**
	 * 删除DriverOrderPay
	 * 
	 * @param id
	 */
	public void delete(Integer id);

	/**
	 * 获取DeliverOrderPay集合
	 * @param driverOrderPay
	 * @return
	 */
	List<DriverOrderPay> query(DriverOrderPay driverOrderPay);


	/**
	 * 获取DriverOrderPay显示的数据
	 * @param driverOrderPay
	 * @param ph
	 * @return
	 */
	DataGrid dataGridView(DriverOrderPay driverOrderPay, PageHelper ph);

}
