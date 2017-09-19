package com.mobian.service;

import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbOrderInvoice;
import com.mobian.pageModel.PageHelper;

/**
 * 
 * @author John
 * 
 */
public interface MbOrderInvoiceServiceI {

	/**
	 * 获取MbOrderInvoice数据表格
	 * 
	 * @param mbOrderInvoice
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(MbOrderInvoice mbOrderInvoice, PageHelper ph);

	/**
	 * 添加MbOrderInvoice
	 * 
	 * @param mbOrderInvoice
	 */
	public void add(MbOrderInvoice mbOrderInvoice);

	/**
	 * 获得MbOrderInvoice对象
	 * 
	 * @param id
	 * @return
	 */
	public MbOrderInvoice get(Integer id);

	/**
	 * 修改MbOrderInvoice
	 * 
	 * @param mbOrderInvoice
	 */
	public void edit(MbOrderInvoice mbOrderInvoice);

	/**
	 * 删除MbOrderInvoice
	 * 
	 * @param id
	 */
	public void delete(Integer id);

	/**
	 * 通过订单ID获取订单发票信息
	 * @param orderId
	 * @return
	 */
	MbOrderInvoice getByOrderId(Integer orderId);
	/**
	 * 通过订单ID获取订单发票信息  无缓存
	 */
   MbOrderInvoice getWithOrderId(Integer orderId);
	/**
	 * 订单开票操作
	 */
	void  addOrderInvoice(MbOrderInvoice mbOrderInvoice, String mbOrderInvoiceList);
}
