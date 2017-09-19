package com.mobian.service;

import com.mobian.model.TmbOrderItem;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbOrderItem;
import com.mobian.pageModel.MbSalesReport;
import com.mobian.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface MbOrderItemServiceI {

	/**
	 * 获取MbOrderItem数据表格
	 * 
	 * @param mbOrderItem
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	DataGrid dataGrid(MbOrderItem mbOrderItem, PageHelper ph);

	/**
	 * 添加MbOrderItem
	 * 
	 * @param mbOrderItem
	 */
	void add(MbOrderItem mbOrderItem);

	/**
	 * 获得MbOrderItem对象
	 * 
	 * @param id
	 * @return
	 */
	MbOrderItem get(Integer id);

	/**
	 * 修改MbOrderItem
	 * 
	 * @param mbOrderItem
	 */
	void edit(MbOrderItem mbOrderItem);

	/**
	 * 删除MbOrderItem
	 * 
	 * @param id
	 */
	void delete(Integer id);

    /**
     * 通过订单ID获取配送费用
     * @param orderId
     * @return
     */
	Integer getDeliveryPrice(Integer orderId);

    /**
     * 通过订单ID获取总金额
     * @param orderId
     * @return
     */
	Integer getTotalPrice(Integer orderId);

	/**
	 * 通过订单ID获取商品信息
	 */
	List<MbOrderItem> getMbOrderItemList(Integer orderId);

	List<MbOrderItem> query(MbOrderItem mbOrderItem);

	/**
	 * 通过订单id查询订单信息
	 * @param orderId
	 * @return
	 */
	List<TmbOrderItem> queryMbOrderItemByOrderId(Integer orderId);

	/**
	 * 获取订单信息和库存量
	 * @param mbOrderItem
	 * @param ph
	 * @return
	 */
	DataGrid dataGridAndStock(MbOrderItem mbOrderItem, PageHelper ph);
	/***
	 *
	 * 获取出货的订单商品数目详情
	 */
	DataGrid dataGridSalesReport(MbSalesReport mbSalesReport);
	List<MbOrderItem> queryListByOrderIds(Integer[] orderIds);
}
