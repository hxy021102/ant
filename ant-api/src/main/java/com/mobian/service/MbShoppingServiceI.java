package com.mobian.service;

import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbShopping;
import com.mobian.pageModel.PageHelper;

/**
 * 
 * @author John
 * 
 */
public interface MbShoppingServiceI {

	/**
	 * 获取MbShopping数据表格
	 * 
	 * @param mbShopping
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(MbShopping mbShopping, PageHelper ph);

	/**
	 * 添加MbShopping
	 * 
	 * @param mbShopping
	 */
	public void add(MbShopping mbShopping);

	/**
	 * 获得MbShopping对象
	 * 
	 * @param id
	 * @return
	 */
	public MbShopping get(Integer id);

	/**
	 * 修改MbShopping
	 * 
	 * @param mbShopping
	 */
	public void edit(MbShopping mbShopping);

	/**
	 * 删除MbShopping
	 * 
	 * @param id
	 */
	public void delete(Integer id);

	/**
	 * 获得MbShopping对象
	 */
	MbShopping get(MbShopping mbShopping);

	/**
	 * 修改MbShopping
	 *
	 * @param mbShopping
	 */
	void editMbShopping(MbShopping mbShopping);

	/**
	 * 删除MbShopping，并清空数量为0
	 *
	 * @param id
	 */
	void deleteMbShopping(Integer id);

	/**
	 * 获取购物车商品数量接口
	 */
	Long count(Integer userId);

}
