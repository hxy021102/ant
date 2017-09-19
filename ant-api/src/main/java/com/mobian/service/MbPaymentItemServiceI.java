package com.mobian.service;

import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbPaymentItem;
import com.mobian.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface MbPaymentItemServiceI {

	String PAY_WAY_VOUCHER = "PW04";

	/**
	 * 获取MbPaymentItem数据表格
	 * 
	 * @param mbPaymentItem
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	DataGrid dataGrid(MbPaymentItem mbPaymentItem, PageHelper ph);

	/**
	 * 添加MbPaymentItem
	 * 
	 * @param mbPaymentItem
	 */
	void add(MbPaymentItem mbPaymentItem);

	/**
	 * 获得MbPaymentItem对象
	 * 
	 * @param id
	 * @return
	 */
	MbPaymentItem get(Integer id);
	/**
	 * 获得MbPaymentItem对象
	 *
	 * @param id
	 * @return
	 */
	MbPaymentItem getMbPaymentItemPW03(Integer id);
	/**
	 * 修改MbPaymentItem
	 * 
	 * @param mbPaymentItem
	 */
	void edit(MbPaymentItem mbPaymentItem);
	/**
	 * 修改MbPaymentItem.payCode
	 *
	 * @param mbPaymentItem
	 */
	void editAudit(MbPaymentItem mbPaymentItem);
	/**
	 * 删除MbPaymentItem
	 * 
	 * @param id
	 */
	void delete(Integer id);

	/**
	 * 通过paymentId获取支付明细
	 * @param paymentId
	 * @return
	 */
	List<MbPaymentItem> getByPaymentId(Integer paymentId);

	/**
	 * 通过已知量获取MbpaymentItems
	 * @param mbPaymentItem
	 * @return
	 */
	List<MbPaymentItem> listMbPaymentItem(MbPaymentItem mbPaymentItem);
}
