package com.mobian.service;

import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbOrderRefundLog;
import com.mobian.pageModel.PageHelper;

/**
 *
 * @author John
 *
 */
public interface MbOrderRefundLogServiceI {

	String REFUND_WAY_VOUCHER = "RW04";
	/**
	 * 获取MbOrderRefundLog数据表格
	 * 
	 * @param mbOrderRefundLog
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	DataGrid dataGrid(MbOrderRefundLog mbOrderRefundLog, PageHelper ph);

	/**
	 * 添加MbOrderRefundLog
	 * 
	 * @param mbOrderRefundLog
	 */
	void add(MbOrderRefundLog mbOrderRefundLog);

	/**
	 * 获得MbOrderRefundLog对象
	 * 
	 * @param id
	 * @return
	 */
	MbOrderRefundLog get(Integer id);

	/**
	 * 修改MbOrderRefundLog
	 * 
	 * @param mbOrderRefundLog
	 */
	void edit(MbOrderRefundLog mbOrderRefundLog);

	/**
	 * 删除MbOrderRefundLog
	 * 
	 * @param id
	 */
	void delete(Integer id);

}
