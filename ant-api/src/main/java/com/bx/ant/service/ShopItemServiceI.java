package com.bx.ant.service;

import com.bx.ant.pageModel.DeliverOrder;
import com.mobian.pageModel.MbItem;
import com.bx.ant.pageModel.ShopItem;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface ShopItemServiceI {

	/**
	 * 获取ShopItem数据表格
	 * 
	 * @param shopItem
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	DataGrid dataGrid(ShopItem shopItem, PageHelper ph);

	/**
	 * 添加ShopItem
	 * 
	 * @param shopItem
	 */
	void add(ShopItem shopItem);

	/**
	 * 获得ShopItem对象
	 * 
	 * @param id
	 * @return
	 */
	ShopItem get(Integer id);

	/**
	 * 修改ShopItem
	 * 
	 * @param shopItem
	 */
	void edit(ShopItem shopItem);

	/**
	 * 删除ShopItem
	 * 
	 * @param id
	 */
	void delete(Integer id);

	/**
	 * 通过门店Id和商品Id和上架状态获取门店商品
	 * @param shopId
	 * @param itemId
	 * @param isOnline
	 * @return
	 */
    ShopItem getByShopIdAndItemId(Integer shopId, Integer itemId, boolean isOnline);

	/**
	 * 通过门店Id和商品Id和上架状态和审核状态获取门店商品
	 * @param shopId
	 * @param itemId
	 * @param status
	 * @return
	 */
	ShopItem getByShopIdAndItemId(Integer shopId, Integer itemId, boolean isOnline, String status);

	/**
	 * 通过门店Id和商品Id获取上架的门店商品
	 * @param shopId
	 * @param itemId
	 * @return
	 */
	ShopItem getByShopIdAndItemId(Integer shopId, Integer itemId);

	/**
	 * 查询ShopItem对象
	 * @param shopItem
	 * @return
	 */
	List<ShopItem> query(ShopItem shopItem);


	/**
	 * 批量修改门店商品上架
	 * @param itemIds
	 * @param shopId
	 */
	void updateBatchItemOnline(String itemIds,Integer shopId);

	/**
	 * 修改门店某种商品状态为上架
	 * @param itemId
	 * @param shopId
	 */
	void updateItemOnline(Integer itemId,Integer shopId);

	/**
	 * 门店商品批量下架
	 * @param ShopItemList
	 * @param shopId
	 */
	void updateBatchShopItemOffline(String ShopItemList,Integer shopId);

	/**
	 * 门店某种商品下架
	 * @param itemId
	 * @param shopId
	 */
	void updateShopItemOffline(Integer itemId,Integer shopId);

	/**
	 * 批量删除门店商品
	 * @param ShopItemList
	 * @param shopId
	 */
	void deleteBatchShopItem(String ShopItemList,Integer shopId);

	/**
	 * 修改门店商品数量
	 * @param itemId
	 * @param shopId
	 * @param quantity
	 */
	void updateShopItemQuantity( Integer itemId,Integer shopId,Integer quantity);

	/**
	 * 获取门店商品和商品名称
	 * @param shopItem
	 * @param ph
	 * @return
	 */
	DataGrid dataGridWithItemName(ShopItem shopItem, PageHelper ph);

	/**
	 * 获取商品数据列表并设置门店商品库存
	 * @param mbItem
	 * @param ph
	 * @return
	 */
	DataGrid dataGridWithQuantity(MbItem mbItem, PageHelper ph, Integer shopId);

	/**
	 * 删除指定的商品
	 * @param itemId
	 * @param shopId
	 */
	void deleteShopItem(Integer itemId,Integer shopId);

	/**
	 * 通过deliverOrder返还shopItem商品数量
	 * deliverOrder必须包含:id,shopId属性
	 * @param deliverOrder
	 */
	void updateForRefundByDeliverOrder(DeliverOrder deliverOrder);


    void updateQuantity(ShopItem shopItem);
}
