package com.bx.ant.service;

import com.bx.ant.pageModel.DeliverOrderItem;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;

import java.util.List;

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

    List<DeliverOrderItem> list(DeliverOrderItem DeliverOrderItem);

	/**
	 * 根据运单ID 查询运单明细
	 * @param deliverOrderId
	 * @return
	 */
	List<DeliverOrderItem> getDeliverOrderItemList(Long deliverOrderId);

	/**
	 * 获取DeliverOrderItem对象集合及对应的名称
	 * @param deliverOrderItem
	 * @param ph
	 * @return
	 */
	DataGrid dataGridWithName(DeliverOrderItem deliverOrderItem, PageHelper ph);

    void addBySupplier(DeliverOrderItem orderItem, Integer supplierId);
}
