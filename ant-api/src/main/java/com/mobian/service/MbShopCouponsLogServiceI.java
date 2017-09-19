package com.mobian.service;

import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbShopCouponsLog;
import com.mobian.pageModel.MbShopCouponsLogView;
import com.mobian.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface MbShopCouponsLogServiceI {
	String SHOP_COUPONS_LOG_TYPE_ADD_WAY_BALANCE = "LS001";
	String SHOP_COUPONS_LOG_TYPE_ADD_WAY_FREE = "LS004";
	String SHOP_COUPONS_LOG_TYPE_DELETE= "LS005";
	String SHOP_COUPONS_LOG_TYPE_PAY_BY_VOUCHER = "LS006";
	String SHOP_COUPONS_LOG_TYPE_REFUND_BY_VOUCHER = "LS010";
	String LOG_REASON_DELETE_FREE_GIVEN = "免费赠送券已被删除";
	/**
	 * 获取MbShopCouponsLog数据表格
	 * 
	 * @param mbShopCouponsLog
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(MbShopCouponsLog mbShopCouponsLog, PageHelper ph);

	/**
	 * 添加MbShopCouponsLog
	 * 
	 * @param mbShopCouponsLog
	 */
	public void add(MbShopCouponsLog mbShopCouponsLog);

	/**
	 * 获得MbShopCouponsLog对象
	 * 
	 * @param id
	 * @return
	 */
	public MbShopCouponsLog get(Integer id);

	/**
	 * 修改MbShopCouponsLog
	 * 
	 * @param mbShopCouponsLog
	 */
	public void edit(MbShopCouponsLog mbShopCouponsLog);

	/**
	 * 删除MbShopCouponsLog
	 * 
	 * @param id
	 */
	public void delete(Integer id);

    void addLogAndUpdateShopCoupons(MbShopCouponsLog mbShopCouponsLog);

    void updateLogAndShopCoupons(MbShopCouponsLog mbShopCouponsLog);

    DataGrid dataGridShopCouponsLogView(MbShopCouponsLog shopCouponsLog);

	List<MbShopCouponsLogView> listShopCouponsLogView(MbShopCouponsLog shopCouponsLog);
}
