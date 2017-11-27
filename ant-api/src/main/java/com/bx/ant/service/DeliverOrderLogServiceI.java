package com.bx.ant.service;

import com.bx.ant.pageModel.DeliverOrderLog;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;
import com.mobian.pageModel.SessionInfo;

/**
 * 
 * @author John
 * 
 */
public interface DeliverOrderLogServiceI {

	String TYPE_ADD_DELIVER_ORDER = "DLT01";//添加运单
	String TYPE_ASSIGN_DELIVER_ORDER = "DLT02";//分配运单
	String TYPE_ACCEPT_DELIVER_ORDER = "DLT03";//门店接受运单
	String TYPE_REFUSE_DELIVER_ORDER = "DLT04";//门店拒绝运单
	String TYPE_DELIVERING_DELIVER_ORDER = "DLT05";//运单发货
	String TYPE_DELIVERED_DELIVER_ORDER = "DLT06";//运单已配送
	String TYPE_COMPLETE_DELIVER_ORDER = "DLT07";//运单完成
	String TYPE_TIME_OUT_REFUSE_DELIVER_ORDER = "DLT08";//超时未接单
	String TYPE_ASSIGN_SHOP_DELIVER_ORDER = "DLT09";//指派运单
	String TYPE_DRIVER_DELIVERED_DELIVER_ORDER = "DLT10";// 骑手已配送
	String TYPE_DRIVER_TAKE_ITEM = "DLT11";// 骑手已接单

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

	/**
	 * 获取运单日子列表信息
	 * @param deliverOrderLog
	 * @param ph
	 * @return
	 */
	DataGrid dataGridWithName(DeliverOrderLog deliverOrderLog, PageHelper ph);

}
