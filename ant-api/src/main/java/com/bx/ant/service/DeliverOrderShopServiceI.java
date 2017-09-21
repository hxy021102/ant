package com.bx.ant.service;

import com.mobian.pageModel.DeliverOrderShop;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;

/**
 * 
 * @author John
 * 
 */
public interface DeliverOrderShopServiceI {

	/**
	 * 获取DeliverOrderShop数据表格
	 * 
	 * @param deliverOrderShop
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(DeliverOrderShop deliverOrderShop, PageHelper ph);

	/**
	 * 添加DeliverOrderShop
	 * 
	 * @param deliverOrderShop
	 */
	public void add(DeliverOrderShop deliverOrderShop);

	/**
	 * 获得DeliverOrderShop对象
	 * 
	 * @param id
	 * @return
	 */
	public DeliverOrderShop get(Integer id);

	/**
	 * 修改DeliverOrderShop
	 * 
	 * @param deliverOrderShop
	 */
	public void edit(DeliverOrderShop deliverOrderShop);

	/**
	 * 删除DeliverOrderShop
	 * 
	 * @param id
	 */
	public void delete(Integer id);

}
