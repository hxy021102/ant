package com.bx.ant.service;

import com.mobian.pageModel.*;
import com.bx.ant.pageModel.DeliverOrderItem;
import com.bx.ant.pageModel.DeliverOrderShop;
import com.bx.ant.pageModel.DeliverOrderShopItem;

import java.util.List;
import java.util.Map;

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
	public void delete(Long id);

	/**
	 * 通过delierOrderItemList添加至deliverOrderShopItemList
	 * @param deliverOrderItems
	 * @param deliverOrderShop
	 */
    void addByDeliverOrderItemList(List<DeliverOrderItem> deliverOrderItems, DeliverOrderShop deliverOrderShop);

	/**
	 * 获取list
	 * @param deliverOrderShopItem
	 * @return
	 */
	List<DeliverOrderShopItem> list(DeliverOrderShopItem deliverOrderShopItem);

	/**
	 * 获取DeliverOrderShopItem集合列表及对应的名称
	 * @param deliverOrderShopItem
	 * @param ph
	 * @return
	 */
	DataGrid dataGridWithName(DeliverOrderShopItem deliverOrderShopItem, PageHelper ph);

	/**
	 * 通过门店订单deliverOrderShopIds获取门店订单商品列表
	 * @return
	 */
	Map<Integer, DeliverOrderShopItem> queryOrderShopItem(String deliverOrderShopIds);

}
