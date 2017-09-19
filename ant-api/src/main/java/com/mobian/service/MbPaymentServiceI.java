package com.mobian.service;

import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbPayment;
import com.mobian.pageModel.PageHelper;

/**
 * 
 * @author John
 * 
 */
public interface MbPaymentServiceI {

	/**
	 * 获取MbPayment数据表格
	 * 
	 * @param mbPayment
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	DataGrid dataGrid(MbPayment mbPayment, PageHelper ph);

	/**
	 * 添加MbPayment
	 * 
	 * @param mbPayment
	 */
	void add(MbPayment mbPayment);

	/**
	 * 获得MbPayment对象
	 * 
	 * @param id
	 * @return
	 */
	MbPayment get(Integer id);

	/**
	 * 修改MbPayment
	 * 
	 * @param mbPayment
	 */
	void edit(MbPayment mbPayment);

	/**
	 * 保存并修改
	 * @param payment
	 */
	void addOrUpdate(MbPayment payment);

	/**
	 * 删除MbPayment
	 * 
	 * @param id
	 */
	void delete(Integer id);

	MbPayment getByOrderId(Integer orderId);

	MbPayment getByOrderIdWithCache(Integer orderId);

	MbPayment getFromCache(Integer id);


}
