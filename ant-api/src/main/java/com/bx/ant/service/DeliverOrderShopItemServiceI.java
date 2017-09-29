package com.bx.ant.service;

import com.mobian.pageModel.*;
import com.mobian.pageModel.DeliverOrderItem;
import com.mobian.pageModel.DeliverOrderShop;
import com.mobian.pageModel.DeliverOrderShopItem;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface DeliverOrderShopItemServiceI {

	/**
	 * 获取DeliverOrderShopItem数据表格
	 * 
	 * @param deliverOrderShopItem
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(DeliverOrderShopItem deliverOrderShopItem, PageHelper ph);

	/**
	 * 添加DeliverOrderShopItem
	 * 
	 * @param deliverOrderShopItem
	 */
	public void add(DeliverOrderShopItem deliverOrderShopItem);

	/**
	 * 获得DeliverOrderShopItem对象
	 * 
	 * @param id
	 * @return
	 */
	public DeliverOrderShopItem get(Integer id);

	/**
	 * 修改DeliverOrderShopItem
	 * 
	 * @param deliverOrderShopItem
	 */
	public void edit(DeliverOrderShopItem deliverOrderShopItem);

	/**
	 * 删除DeliverOrderShopItem
	 * 
	 * @param id
	 */
	public void delete(Integer id);

    void addByDeliverOrderItemList(List<DeliverOrderItem> deliverOrderItems, DeliverOrderShop deliverOrderShop);
}
