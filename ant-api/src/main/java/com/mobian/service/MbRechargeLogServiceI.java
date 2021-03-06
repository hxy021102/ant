package com.mobian.service;

import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbRechargeLog;
import com.mobian.pageModel.PageHelper;
import com.mobian.pageModel.SessionInfo;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface MbRechargeLogServiceI {

	String HS02 = "HS02";
	String HS03 = "HS03";

	/**
	 * 获取MbRechargeLog数据表格
	 * 
	 * @param mbRechargeLog
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	DataGrid dataGrid(MbRechargeLog mbRechargeLog, PageHelper ph);

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

	/**
	 * 批量添加充值记录数据
	 * @param mbRechargeLogList
	 */
	void addBatchMbRechargeLog(List<MbRechargeLog> mbRechargeLogList, String  loginId);

	/**
	 * 查询充值记录集合
	 * @param mbRechargeLog
	 * @return
	 */
	List<MbRechargeLog> query(MbRechargeLog mbRechargeLog);

	/**
	 * 审核时校验银行转账信息是否唯一
	 * @param mbRechargeLog
	 * @return
	 */
	MbRechargeLog checkRechargeLogPayCode(MbRechargeLog mbRechargeLog);

	/**
	 * 添加充值记录和余额日志
	 * @param mbRechargeLog
	 */
	void addRechargeLogAndBalanceLog(MbRechargeLog mbRechargeLog);
}
