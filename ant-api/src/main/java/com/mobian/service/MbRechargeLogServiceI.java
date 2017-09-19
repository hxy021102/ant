package com.mobian.service;

import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbRechargeLog;
import com.mobian.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface MbRechargeLogServiceI {

	/**
	 * 获取MbRechargeLog数据表格
	 * 
	 * @param mbRechargeLog
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(MbRechargeLog mbRechargeLog, PageHelper ph);

	/**
	 * 添加MbRechargeLog
	 * 
	 * @param mbRechargeLog
	 */
	void add(MbRechargeLog mbRechargeLog);

	/**
	 * 添加充值记录并更新余额
	 * @param mbRechargeLog
	 */
	void addAndUpdateBalance(MbRechargeLog mbRechargeLog);

	/**
	 * 获得MbRechargeLog对象
	 * 
	 * @param id
	 * @return
	 */
	public MbRechargeLog get(Integer id);

	/**
	 * 修改MbRechargeLog
	 * 
	 * @param mbRechargeLog
	 */
	public void edit(MbRechargeLog mbRechargeLog);

	/**
	 * 删除MbRechargeLog
	 * 
	 * @param id
	 */
	public void delete(Integer id);

	void editAudit(MbRechargeLog mbRechargeLog);

	/**
	 * 通过MBRechargeLog找到List<MbRechargeLog>
	 * @param mbRechargeLog
	 * @return
	 */
	List<MbRechargeLog> listMbRechargeLog(MbRechargeLog mbRechargeLog);
}
