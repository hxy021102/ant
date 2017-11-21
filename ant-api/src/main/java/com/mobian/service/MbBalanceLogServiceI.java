package com.mobian.service;

import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbBalanceLog;
import com.mobian.pageModel.PageHelper;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author John
 * 
 */
public interface MbBalanceLogServiceI {

	String BT013 = "BT013";
	String BT003 = "BT003";

	/**
	 * 获取MbBalanceLog数据表格
	 * 
	 * @param mbBalanceLog
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	DataGrid dataGrid(MbBalanceLog mbBalanceLog, PageHelper ph);

	/**
	 * 添加MbBalanceLog
	 * 
	 * @param mbBalanceLog
	 */
	void add(MbBalanceLog mbBalanceLog);

	/**
	 * 添加并刷新余额
	 * @param mbBalanceLog
	 */
	void addAndUpdateBalance(MbBalanceLog mbBalanceLog);

	/**
	 * 修改并刷新余额
	 * @param mbBalanceLog
	 */
	void updateLogAndBalance(MbBalanceLog mbBalanceLog);

	/**
	 * 获得MbBalanceLog对象
	 * 
	 * @param id
	 * @return
	 */
	MbBalanceLog get(Integer id);

	/**
	 * 修改MbBalanceLog
	 * 
	 * @param mbBalanceLog
	 */
	void edit(MbBalanceLog mbBalanceLog);

	/**
	 * 删除MbBalanceLog
	 * 
	 * @param id
	 */
	void delete(Integer id);

	/**
	 * 获取余额列表和门店名称
	 * @param mbBalanceLog
	 * @param ph
	 * @return
	 */
	DataGrid dataGridWithShopName(MbBalanceLog mbBalanceLog, PageHelper ph);

    List<MbBalanceLog> list(MbBalanceLog mbBalanceLog);

    DataGrid updateDeliveryBalanceLogDataGrid(MbBalanceLog mbBalanceLog, PageHelper pageHelper);

    DataGrid updateDriverBalanceLogDataGrid(MbBalanceLog mbBalanceLog, PageHelper pageHelper);

    /**
	 * 导出余额流水
	 * @param mbBalanceLog
	 * @param ph
	 * @return
	 */
	DataGrid dataGridBalanceLogDownload(MbBalanceLog mbBalanceLog, PageHelper ph);

	Map<String,Integer> totalBalanceByMonth(MbBalanceLog mbBalanceLog);
}
