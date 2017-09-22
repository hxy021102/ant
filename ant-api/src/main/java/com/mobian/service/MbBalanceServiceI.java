package com.mobian.service;

import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbBalance;
import com.mobian.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface MbBalanceServiceI {

	/**
	 * 获取MbBalance数据表格
	 * 
	 * @param mbBalance
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	DataGrid dataGrid(MbBalance mbBalance, PageHelper ph);

	/**
	 * 添加MbBalance
	 * 
	 * @param mbBalance
	 */
	void add(MbBalance mbBalance);

	/**
	 * 获得MbBalance对象
	 * 
	 * @param id
	 * @return
	 */
	MbBalance get(Integer id);

	/**
	 * 修改MbBalance
	 * 
	 * @param mbBalance
	 */
	void edit(MbBalance mbBalance);

	/**
	 * 删除MbBalance
	 * 
	 * @param id
	 */
	void delete(Integer id);

	/**
	 * 获得MbBalance对象
	 *
	 * @param shopId
	 */
	MbBalance addOrGetMbBalance(Integer shopId);

    /**
     * 通过shopId查询
     * @param shopId
     * @return
     */
	MbBalance queryByShopId(Integer shopId);

	/**
	 * 桶押金账户
	 * @param shopId
	 * @return
	 */
	MbBalance addOrGetMbBalanceCash(Integer shopId);

	/**
	 * 桶押金账户
	 * @param shopId
	 * @return
	 */
	MbBalance getCashByShopId(Integer shopId);

	/**
	 * 通过欠款类型和欠款进行查询
	 * @param refType
	 * @param amount
	 * @return
	 */
	List<MbBalance> queryByrefTypeAndAmount(Integer refType, Integer amount);


	/**
	 * 通过门店Id查询余额集合
	 * @param shopId
	 * @return
	 */
	List<MbBalance> queryBalanceListByShopId(Integer shopId);

}
