package com.bx.ant.service;

import com.bx.ant.pageModel.ShopOrderBill;
import com.bx.ant.pageModel.ShopOrderBillQuery;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.Json;
import com.mobian.pageModel.PageHelper;



/**
 * 
 * @author John
 * 
 */
public interface ShopOrderBillServiceI {

	/**
	 * 获取ShopOrderBill数据表格
	 * 
	 * @param shopOrderBill
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(ShopOrderBill shopOrderBill, PageHelper ph);

	/**
	 * 添加ShopOrderBill
	 * 
	 * @param shopOrderBill
	 */
	 void add(ShopOrderBill shopOrderBill);

	/**
	 * 获得ShopOrderBill对象
	 * 
	 * @param id
	 * @return
	 */
	 ShopOrderBill get(Long id);

	/**
	 * 修改ShopOrderBill
	 * 
	 * @param shopOrderBill
	 */
	 void edit(ShopOrderBill shopOrderBill);

	/**
	 * 删除ShopOrderBill
	 * 
	 * @param id
	 */
	 void delete(Integer id);

	/**
	 * 获取门店账单及所对应的名称
	 * @param shopOrderBill
	 * @param ph
	 * @return
	 */
	DataGrid dataGridWithName(ShopOrderBill shopOrderBill, PageHelper ph);

	/**
	 * 创建并结算门店结算对账单和运单门店结算明细
	 */
	void addShopOrderBillAndShopPay(ShopOrderBillQuery shopOrderBillQuery);

	/**
	 * 创建并结算门店结算对账单和运单门店结算明细
	 * @param shopOrderBillQuery
	 */
	void addAndPayShopOrderBillAndShopPay(ShopOrderBillQuery shopOrderBillQuery);

	/**
	 * 获取ShopOrderBill详情页面信息
	 * @param id
	 * @return
	 */
	ShopOrderBillQuery getViewShopOrderBill(Long id);

	/**
	 * 修改门店账单审核状态和账单支付状态
	 * @param shopOrderBill
	 */
	void editBillStatusAndPayStatus(ShopOrderBill shopOrderBill);

	/**
	 *修改门店账单结算方式和账单支付状态及方式、以及运单和门店订单状态
	 * 同时修改门店账单余额
	 * @param shopOrderBill
	 */
	void editDeliverOrderStatusAndShopBalance(ShopOrderBill shopOrderBill);
}
