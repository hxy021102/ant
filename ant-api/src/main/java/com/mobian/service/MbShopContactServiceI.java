package com.mobian.service;

import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbShopContact;
import com.mobian.pageModel.PageHelper;

/**
 * 
 * @author John
 * 
 */
public interface MbShopContactServiceI {

	/**
	 * 获取MbShopContact数据表格
	 * 
	 * @param mbShopContact
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(MbShopContact mbShopContact, PageHelper ph);

	/**
	 * 添加MbShopContact
	 * 
	 * @param mbShopContact
	 */
	public void add(MbShopContact mbShopContact);

	/**
	 * 获得MbShopContact对象
	 * 
	 * @param id
	 * @return
	 */
	public MbShopContact get(Integer id);

	/**
	 * 修改MbShopContact
	 * 
	 * @param mbShopContact
	 */
	public void edit(MbShopContact mbShopContact);

	/**
	 * 删除MbShopContact
	 * 
	 * @param id
	 */
	public void delete(Integer id);

}
