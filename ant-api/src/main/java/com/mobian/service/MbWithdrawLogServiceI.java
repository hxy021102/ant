package com.mobian.service;

import com.mobian.pageModel.MbWithdrawLog;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;

/**
 * 
 * @author John
 * 
 */
public interface MbWithdrawLogServiceI {

	/**
	 * 获取MbWithdrawLog数据表格
	 * 
	 * @param mbWithdrawLog
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(MbWithdrawLog mbWithdrawLog, PageHelper ph);

	/**
	 * 添加MbWithdrawLog
	 * 
	 * @param mbWithdrawLog
	 */
	public void add(MbWithdrawLog mbWithdrawLog);

	/**
	 * 获得MbWithdrawLog对象
	 * 
	 * @param id
	 * @return
	 */
	public MbWithdrawLog get(Integer id);

	/**
	 * 修改MbWithdrawLog
	 * 
	 * @param mbWithdrawLog
	 */
	public void edit(MbWithdrawLog mbWithdrawLog);

	/**
	 * 删除MbWithdrawLog
	 * 
	 * @param id
	 */
	public void delete(Integer id);

	/**
	 * 审核提现申请
	 * @param mbWithdrawLog
	 * @param login
	 */
    void editAudit(MbWithdrawLog mbWithdrawLog, String login);

	/**
	 * 通过门店ID填充并添加withdrawLog
	 * @param shopId
	 * @param withdrawLog
	 */
	void addByShopId(Integer shopId, MbWithdrawLog withdrawLog);
}
