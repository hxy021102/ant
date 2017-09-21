package com.bx.ant.service;

import com.mobian.pageModel.ShopItem;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;

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

}
