package com.mobian.service;

import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbItem;
import com.mobian.pageModel.MbItemQuery;
import com.mobian.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface MbItemServiceI {

	/**
	 * 获取MbItem数据表格
	 * 
	 * @param mbItem
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	DataGrid dataGrid(MbItem mbItem, PageHelper ph);

	/**
	 * 添加MbItem
	 * 
	 * @param mbItem
	 */
	void add(MbItem mbItem);

	/**
	 * 获得MbItem对象
	 * 
	 * @param id
	 * @return
	 */
	MbItem get(Integer id);

	MbItem getFromCache(Integer id);

	/**
	 * 修改MbItem
	 * 
	 * @param mbItem
	 */
	void edit(MbItem mbItem);

	/**
	 * 删除MbItem
	 * 
	 * @param id
	 */
	void delete(Integer id);

	boolean isItemExists(MbItem mbItem);

	/**
	 * 减少商品库存
	 */
	int reduceItemCount(Integer id, Integer count);

	List<MbItem> query(MbItem mbItem);

	/**
	 * 获取需要补充商品的DeliverOrderShop商品列表
	 * @param mbItemQuery
	 * @param ph
	 * @return
	 */
	DataGrid dataGridWidthDeliverOrderShop(MbItemQuery mbItemQuery, PageHelper ph);

}
