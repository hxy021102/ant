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
	 * 通过真实余额
	 * @param shopId
	 * @return
	 */
	MbBalance queryByRealShopId(Integer shopId);

	/**
	 * 桶押金账户
	 * @param shopId
	 * @return
	 */
	MbBalance addOrGetMbBalanceCash(Integer shopId);

	/**
	 * 门店运单账户
	 * @param shopId
	 * @return
	 */
	MbBalance addOrGetMbBalanceDelivery(Integer shopId);

	/**
	 * 桶押金账户
	 * @param shopId
	 * @return
	 */
	MbBalance getCashByShopId(Integer shopId);

	/**
	 * 桶押金真实账户
	 * @param id
	 * @return
	 */
	MbBalance getCashByRealShopId(Integer id);

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

	/**
	 * 通用创建与获取账户方法
	 * @param refId
	 * @param refType
	 * @param initAmount
	 * @return
	 */
	MbBalance addOrGetMbBalance(Integer refId, Integer refType, Integer initAmount);

	/**
	 * 转移金额
     * 将通过shopId找到源余额账户和目标账户,若目标账户不存在则将建立目标账户
	 * @param shopId
	 * @param amount
	 * @param balanceSourceType
	 * @param balanceTargetType
	 * @param initTargetMoney
	 */
	void transform(Integer shopId, Integer amount, Integer balanceSourceType, Integer balanceTargetType, Integer initTargetMoney);

	/**
	 * 转移金额
	 * 将
	 * @param amount
	 * @param balanceSource
	 * @param balanceTarget
	 */
	void transform(Integer amount, MbBalance balanceSource, MbBalance balanceTarget);

	/**
	 * 通用创建与获取供应商钱包信息
	 * @param refId
	 * @param refType
	 * @param initAmount
	 * @return
	 */
	MbBalance addOrGetSupplierMbBalance(Integer refId, Integer refType, Integer initAmount);

	/**
	 * 通过供应商id,获取供应商钱包信息
	 * @param supplierId
	 * @return
	 */
	MbBalance addOrGetSupplierMbBalance(Integer supplierId);


	/**
	 * 获取骑手账户
	 * @param driverAccountId
	 * @return
	 */
	MbBalance addOrGetDriverBalance(Integer driverAccountId);

}
