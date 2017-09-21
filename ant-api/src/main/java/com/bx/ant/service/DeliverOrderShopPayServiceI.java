package com.bx.ant.service;

import com.mobian.pageModel.DeliverOrderShopPay;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;

/**
 * 
 * @author John
 * 
 */
public interface DeliverOrderShopPayServiceI {

	/**
	 * 获取DeliverOrderShopPay数据表格
	 * 
	 * @param deliverOrderShopPay
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(DeliverOrderShopPay deliverOrderShopPay, PageHelper ph);

	/**
	 * 添加DeliverOrderShopPay
	 * 
	 * @param deliverOrderShopPay
	 */
	public void add(DeliverOrderShopPay deliverOrderShopPay);

	/**
	 * 获得DeliverOrderShopPay对象
	 * 
	 * @param id
	 * @return
	 */
	public DeliverOrderShopPay get(Integer id);

	/**
	 * 修改DeliverOrderShopPay
	 * 
	 * @param deliverOrderShopPay
	 */
	public void edit(DeliverOrderShopPay deliverOrderShopPay);

	/**
	 * 删除DeliverOrderShopPay
	 * 
	 * @param id
	 */
	public void delete(Integer id);

}
