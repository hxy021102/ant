package com.bx.ant.service;

import com.bx.ant.pageModel.DriverOrderShopBill;
import com.bx.ant.pageModel.DriverOrderShopBillView;
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
	public DriverOrderShopBill get(Long id);

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
	public void delete(Long id);

    DriverOrderShopBillView getView(Long id);

    DataGrid dataGridView(DriverOrderShopBill driverOrderShopBill, PageHelper pageHelper);

	/**
	 * 创建骑手账单和修改骑手运单状态
	 * @param driverOrderShopBillView
	 * @return
	 */
	String addDriverOrderShopBillandPay(DriverOrderShopBillView driverOrderShopBillView);

	/**
	 * 骑手账单审核通过过后进行支付操作，改变账单状态和运单状态，以及修改骑手余额和添加余额日志
	 */
	void editDriverShopBillAndOrderPay(DriverOrderShopBill driverOrderShopBill);

	/**
	 * 添加骑手账单并进行自动支付
	 */
	void addPayOperation();
}
