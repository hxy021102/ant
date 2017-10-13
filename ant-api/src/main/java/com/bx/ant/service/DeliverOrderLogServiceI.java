package com.bx.ant.service;

import com.bx.ant.pageModel.DeliverOrderLog;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;

/**
 * 
 * @author John
 * 
 */
public interface DeliverOrderLogServiceI {

	String TYPE_ADD_DELIVER_ORDER = "DLT01";
	String TYPE_ASSIGN_DELIVER_ORDER = "DLT02";
	String TYPE_ACCEPT_DELIVER_ORDER = "DLT03";
	String TYPE_REFUSE_DELIVER_ORDER = "DLT04";
	String TYPE_DELIVERING_DELIVER_ORDER = "DLT05";
	String TYPE_DELIVERED_DELIVER_ORDER = "DLT06";
	String TYPE_COMPLETE_DELIVER_ORDER = "DLT07";
	/**
	 * 获取DeliverOrderLog数据表格
	 * 
	 * @param deliverOrderLog
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	 DataGrid dataGrid(DeliverOrderLog deliverOrderLog, PageHelper ph);

	/**
	 * 添加DeliverOrderLog
	 * 
	 * @param deliverOrderLog
	 */
	 void add(DeliverOrderLog deliverOrderLog);

	/**
	 * 获得DeliverOrderLog对象
	 * 
	 * @param id
	 * @return
	 */
	 DeliverOrderLog get(Integer id);

	/**
	 * 修改DeliverOrderLog
	 * 
	 * @param deliverOrderLog
	 */
	 void edit(DeliverOrderLog deliverOrderLog);

	/**
	 * 删除DeliverOrderLog
	 * 
	 * @param id
	 */
	 void delete(Integer id);

}
