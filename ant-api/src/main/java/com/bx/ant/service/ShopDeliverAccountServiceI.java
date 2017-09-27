package com.bx.ant.service;

import com.mobian.pageModel.ShopDeliverAccount;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;

/**
 * 
 * @author John
 * 
 */
public interface ShopDeliverAccountServiceI {

	/**
	 * 获取ShopDeliverAccount数据表格
	 * 
	 * @param shopDeliverAccount
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(ShopDeliverAccount shopDeliverAccount, PageHelper ph);

	/**
	 * 添加ShopDeliverAccount
	 * 
	 * @param shopDeliverAccount
	 */
	public void add(ShopDeliverAccount shopDeliverAccount);

	/**
	 * 获得ShopDeliverAccount对象
	 * 
	 * @param id
	 * @return
	 */
	public ShopDeliverAccount get(Integer id);

	/**
	 * 修改ShopDeliverAccount
	 * 
	 * @param shopDeliverAccount
	 */
	public void edit(ShopDeliverAccount shopDeliverAccount);

	/**
	 * 删除ShopDeliverAccount
	 * 
	 * @param id
	 */
	public void delete(Integer id);

	/**
	 * 第三方账号查询门店账号
	 * @param refId
	 * @param refType
	 * @return
	 */
	ShopDeliverAccount getByRef(String refId, String refType);

	boolean checkUserName(String userName);
}
