package com.bx.ant.service;

import com.bx.ant.pageModel.DeliverOrderPay;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;

/**
 * 
 * @author John
 * 
 */
public interface DeliverOrderPayServiceI {

	/**
	 * 获取DeliverOrderPay数据表格
	 * 
	 * @param deliverOrderPay
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(DeliverOrderPay deliverOrderPay, PageHelper ph);

	/**
	 * 添加DeliverOrderPay
	 * 
	 * @param deliverOrderPay
	 */
	public void add(DeliverOrderPay deliverOrderPay);

	/**
	 * 获得DeliverOrderPay对象
	 * 
	 * @param id
	 * @return
	 */
	public DeliverOrderPay get(Integer id);

	/**
	 * 修改DeliverOrderPay
	 * 
	 * @param deliverOrderPay
	 */
	public void edit(DeliverOrderPay deliverOrderPay);

	/**
	 * 删除DeliverOrderPay
	 * 
	 * @param id
	 */
	public void delete(Integer id);

}
