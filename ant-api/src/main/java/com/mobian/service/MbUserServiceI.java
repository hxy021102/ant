package com.mobian.service;

import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbUser;
import com.mobian.pageModel.PageHelper;

/**
 * 
 * @author John
 * 
 */
public interface MbUserServiceI {

	/**
	 * 获取MbUser数据表格
	 * 
	 * @param mbUser
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(MbUser mbUser, PageHelper ph);

	/**
	 * 添加MbUser
	 * 
	 * @param mbUser
	 */
	public void add(MbUser mbUser);

	/**
	 * 获得MbUser对象
	 * 
	 * @param id
	 * @return
	 */
	MbUser get(Integer id);

	MbUser getFromCache(Integer id);

	/**
	 * 修改MbUser
	 * 
	 * @param mbUser
	 */
	public void edit(MbUser mbUser);

	/**
	 * 删除MbUser
	 * 
	 * @param id
	 */
	public void delete(Integer id);

	/**
	 * 获得MbUser对象
	 *
	 * @param mbUser
	 * @return
	 */
	MbUser get(MbUser mbUser);

	/**
	 * 添加MbUser
	 *
	 * @param mbUser
	 */
	MbUser addMbUser(MbUser mbUser);

	/**
	 * 查话手机号码是否已注册绑定
	 *
	 * @param phone
	 */
	boolean hasPhone(String phone);

	MbUser loginByWx(String code, boolean snsapi_userinfo);

	/**
	 * 将shopId置为空
	 * @param mbUser
	 */
    void editMbShopToNull(MbUser mbUser);

}
