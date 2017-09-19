package com.mobian.service;

import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbItemCategory;
import com.mobian.pageModel.PageHelper;

/**
 * 
 * @author John
 * 
 */
public interface MbItemCategoryServiceI {

	/**
	 * 获取MbItemCategory数据表格
	 * 
	 * @param mbItemCategory
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	DataGrid dataGrid(MbItemCategory mbItemCategory, PageHelper ph);

	/**
	 * 添加MbItemCategory
	 * 
	 * @param mbItemCategory
	 */
	void add(MbItemCategory mbItemCategory);

	/**
	 * 获得MbItemCategory对象
	 * 
	 * @param id
	 * @return
	 */
	MbItemCategory get(Integer id);

	/**
	 * 获得MbItemCategory对象
	 *
	 * @param id
	 * @return
	 */
	MbItemCategory getFromCache(Integer id);



	/**
	 * 修改MbItemCategory
	 * 
	 * @param mbItemCategory
	 */
	void edit(MbItemCategory mbItemCategory);

	/**
	 * 删除MbItemCategory
	 * 
	 * @param id
	 */
	void delete(Integer id);

	boolean isItemCategoryExists(MbItemCategory mbItemCategory);

}
