package com.mobian.service;

import com.mobian.pageModel.*;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface MbCouponsServiceI {
	String COUPONS_TYPE_VOUCHER = "CT001";

	/**
	 * 获取MbCoupons数据表格
	 * 
	 * @param mbCoupons
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(MbCoupons mbCoupons, PageHelper ph);

	/**
	 * 添加MbCoupons
	 * 
	 * @param mbCoupons
	 */
	public void add(MbCoupons mbCoupons);

	/**
	 * 获得MbCoupons对象
	 * 
	 * @param id
	 * @return
	 */
	public MbCoupons get(Integer id);

	/**
	 * 修改MbCoupons
	 * 
	 * @param mbCoupons
	 */
	public void edit(MbCoupons mbCoupons);

	/**
	 * 删除MbCoupons
	 * 
	 * @param id
	 */
	void delete(Integer id);

	/**
	 * 缓存中获取MbCoupons
	 * @param id
	 * @return
	 */
	MbCoupons getFromCache(Integer id);
	/**
	 * 查询listMbCoupons
	 * @param mbCoupons
	 * @return
	 */

	List<MbCoupons> listMbCoupons(MbCoupons mbCoupons);

	/**
	 * 添加券与券和Item关系表
	 * @param mbCoupons
	 * @param mbCouponsItem
	 */
	void addCouponsAndCouponsItem(MbCoupons mbCoupons, MbCouponsItem mbCouponsItem);

	/**
	 * 编辑券和券和Item关系
	 * @param mbCoupons
	 * @param mbCouponsItem
	 */
	void editCouponsAndCouponsItem(MbCoupons mbCoupons, MbCouponsItem mbCouponsItem);
}
