package com.mobian.service;

import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbUserAddress;
import com.mobian.pageModel.PageHelper;

/**
 * 
 * @author John
 * 
 */
public interface MbUserAddressServiceI {

	/**
	 * 获取MbUserAddress数据表格
	 * 
	 * @param mbUserAddress
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(MbUserAddress mbUserAddress, PageHelper ph);

	/**
	 * 添加MbUserAddress
	 * 
	 * @param mbUserAddress
	 */
	public void add(MbUserAddress mbUserAddress);

	/**
	 * 获得MbUserAddress对象
	 * 
	 * @param id
	 * @return
	 */
	public MbUserAddress get(Integer id);

	MbUserAddress getFromCache(Integer id);

	/**
	 * 修改MbUserAddress
	 * 
	 * @param mbUserAddress
	 */
	public void edit(MbUserAddress mbUserAddress);

	/**
	 * 删除MbUserAddress
	 * 
	 * @param id
	 */
	public void delete(Integer id);

	/**
	 * 获取默认收货地址
	 */
	MbUserAddress getDefaultAddress(Integer userId);

	/**
	 * 设置默认收货地址
	 */
	void setDefaultAddress(MbUserAddress mbUserAddress);

	MbUserAddress get(MbUserAddress mbUserAddress);
}
