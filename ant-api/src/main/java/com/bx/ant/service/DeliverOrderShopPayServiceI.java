package com.bx.ant.service;

import com.bx.ant.pageModel.DeliverOrderShopPay;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;

import java.util.List;

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
	public DeliverOrderShopPay get(Long id);

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
	public void delete(Long id);

    List<DeliverOrderShopPay> list(DeliverOrderShopPay deliverOrderShopPay);

    void editStatus(DeliverOrderShopPay deliverOrderShopPay, String status);

	/**
	 * 获取DeliverOrderShopPay集合列表及对应的名称
	 * @param deliverOrderShopPay
	 * @param ph
	 * @return
	 */
	DataGrid dataWithNameGrid(DeliverOrderShopPay deliverOrderShopPay, PageHelper ph);

	/**
	 * 查询DeliverOrderShopPay集合对象
	 * @param deliverOrderShopPay
	 * @return
	 */
	List<DeliverOrderShopPay> query(DeliverOrderShopPay deliverOrderShopPay);



}
