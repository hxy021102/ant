package com.bx.ant.service;

import com.mobian.pageModel.DeliverOrderItem;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;

/**
 * 
 * @author John
 * 
 */
public interface DeliverOrderItemServiceI {

	/**
	 * 获取DeliverOrderItem数据表格
	 * 
	 * @param deliverOrderItem
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	DataGrid dataGrid(DeliverOrderItem deliverOrderItem, PageHelper ph);

	/**
	 * 添加DeliverOrderItem
	 * 
	 * @param deliverOrderItem
	 */
	void add(DeliverOrderItem deliverOrderItem);

	/**
	 * 获得DeliverOrderItem对象
	 * 
	 * @param id
	 * @return
	 */
	DeliverOrderItem get(Integer id);

	/**
	 * 修改DeliverOrderItem
	 * 
	 * @param deliverOrderItem
	 */
	void edit(DeliverOrderItem deliverOrderItem);

	/**
	 * 删除DeliverOrderItem
	 * 
	 * @param id
	 */
	void delete(Integer id);

}
