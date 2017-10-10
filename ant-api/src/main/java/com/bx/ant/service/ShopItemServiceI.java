package com.bx.ant.service;

import com.mobian.pageModel.ShopItem;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;

import javax.persistence.criteria.CriteriaBuilder;

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
	public DataGrid dataGrid(ShopItem shopItem, PageHelper ph);

	/**
	 * 添加ShopItem
	 * 
	 * @param shopItem
	 */
	public void add(ShopItem shopItem);

	/**
	 * 获得ShopItem对象
	 * 
	 * @param id
	 * @return
	 */
	public ShopItem get(Integer id);

	/**
	 * 修改ShopItem
	 * 
	 * @param shopItem
	 */
	public void edit(ShopItem shopItem);

	/**
	 * 删除ShopItem
	 * 
	 * @param id
	 */
	public void delete(Integer id);

    ShopItem getByShopIdAndItemId(Integer shopId, Integer itemId, boolean isOnline);

	ShopItem getByShopIdAndItemId(Integer shopId, Integer itemId);

	/**
	 * 商品批量上架
	 * @param itemList
	 * @param shopId
	 */
	void updateBatchItemOnline(String itemList,Integer shopId);

	/**
	 * 某种商品上架
	 * @param shopItemId
	 */
	void updateItemOnline(Integer shopItemId);

	/**
	 * 门店商品批量下架
	 * @param ShopItemList
	 * @param shopId
	 */
	void updateBatchShopItemOffline(String ShopItemList,Integer shopId);

	/**
	 * 门店某种商品下架
	 * @param shopItemId
	 */
	void updateShopItemOffline(Integer shopItemId);

	/**
	 * 批量删除门店商品
	 * @param ShopItemList
	 * @param shopId
	 */
	void deleteBatchShopItem(String ShopItemList,Integer shopId);

	/**
	 * 修改门店商品数量
	 * @param shopId
	 * @param itemId
	 * @param quantity
	 */
	void updateShopItemQuantity(Integer shopId, Integer itemId,Integer quantity);

	/**
	 * 获取门店商品和商品名称
	 * @param shopItem
	 * @param ph
	 * @return
	 */
	DataGrid dataGridWithItemName(ShopItem shopItem, PageHelper ph);


}
