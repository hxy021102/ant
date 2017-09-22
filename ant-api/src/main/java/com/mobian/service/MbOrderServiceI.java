package com.mobian.service;

import com.mobian.model.TmbOrder;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbOrder;
import com.mobian.pageModel.MbOrderDistribution;
import com.mobian.pageModel.PageHelper;
import com.mobian.service.impl.order.OrderState;

import java.util.List;
import java.util.Map;

/**
 *
 * @author John
 *
 */
public interface MbOrderServiceI {

	/**
	 * 获取当前状态
	 * @return
	 */
	OrderState getCurrentState(Integer id);

	/**
	 * 获取MbOrder数据表格
	 *
	 * @param mbOrder
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	DataGrid dataGrid(MbOrder mbOrder, PageHelper ph);

	/**
	 * 添加MbOrder
	 *
	 * @param mbOrder
	 */
	void add(MbOrder mbOrder);

	/**
	 * 获得MbOrder对象
	 *
	 * @param id
	 * @return
	 */
	MbOrder get(Integer id);

	/**
	 * 修改MbOrder
	 *
	 * @param mbOrder
	 */
	void edit(MbOrder mbOrder);

	/**
	 * 删除MbOrder
	 *
	 * @param id
	 */
	void delete(Integer id);

	/**
	 * 订单状态转换
	 * @param mbOrder
	 */
	void transform(MbOrder mbOrder);

	/**
	 * 发票状态改变
	 * @param id
	 * @param remark
	 * @param loginId
	 */
	void changeInvoiceStatus(Integer id, String remark, String loginId);

	/**
	 * 根据订单状态查询订单信息
	 * @return
	 */
	List<TmbOrder> queryOrderListByStatus();

	/**
	 *根据时间删除订单
	 */
	void deleteUnPayOrderByTime();

	/**
	 * 获取用户订单信息
	 * @param mbOrder
	 * @param ph
	 * @return
	 */
	DataGrid listUserOrderItem(MbOrder mbOrder, PageHelper ph);

	/**
	 * 根据店铺Id和订单、支付状态查询订单列表
	 * @param shopId
	 * @return
	 */
	Integer getOrderDebtMoney(Integer shopId);

	/**
	 * 分配司机
	 * @param id
	 * @param deliveryDriver
	 * @param remark
	 * @param loginId
	 */
	void editOrderDeliveryDriver(Integer id, String deliveryDriver, String remark, String loginId);


	DataGrid queryOrderDataGrid(MbOrder mbOrder, PageHelper ph);

	/**
	 *  查找发货时间在两天前的订单
	 */
	List<MbOrder>  queryRemindOrder();

	/**
	 * 获取订单的分布数据
	 * @param mbOrder
	 */
	List<MbOrderDistribution> getOrderDistributionData(MbOrder mbOrder);


	List<MbOrder> query(MbOrder mbOrder);
	/**
	 * 查询所有欠款的订单
	 *
	 */
	List<MbOrder> queryDebtOrders();

	/**
	 * 获取查询区间的每天公众号和客服数据
	 * @param orderData
	 * @param params
	 * @return
	 */
	List<MbOrderDistribution> getOrderDistributionDayData(MbOrder mbOrder, List<MbOrderDistribution> orderData, Map<String, Object> params);

	/**
	 * 设置查询区间的每天订单数和时间
	 * @param mbOrders
	 * @param orderDistribution
	 * @param timeName
	 * @return
	 */
	MbOrderDistribution setOrderDayNumberAndOrderDayNameValue(List<TmbOrder> mbOrders, MbOrderDistribution orderDistribution, String[] timeName);
}
