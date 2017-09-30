package com.bx.ant.service;

import com.bx.ant.pageModel.DeliverOrderExt;
import com.mobian.pageModel.DeliverOrder;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface DeliverOrderServiceI {

	//供应商/门店支付状态
	String PAY_STATUS_NOT_PAY = "PS01"; //待支付
	String PAY_STATUS_AUDIT = "PS02"; //待审核
	String PAY_STATUS_REFUSE = "PS03"; //审核拒绝
	String PAY_STATUS_SUCCESS = "PS04"; //支付成功

	//订单状态
	String STATUS_NOT_PAY = "DO01"; //待支付
	String STATUS_PAY_SUCCESS = "DO10"; //支付成功待接单
	String STATUS_SHOP_REFUSE = "DO15"; //门店拒绝接单
	String STATUS_SHOP_ACCEPT = "DO20"; //已接单
	String STATUS_DELIVERING = "DO25"; //已发货
	String STATUS_DELIVERY_COMPLETE = "DO30"; //已配送完成,等待用户确认状态
	String STATUS_CLOSED = "DO40"; //订单完成

	//配送状态
	String DELIVER_STATUS_STANDBY = "DS01"; //待处理
	String DELIVER_STATUS_DELIVERING = "DS02"; //配送中
	String DELIVER_STATUS_USER_CHECK = "DS03"; //用户确认
	String DELIVER_STATUS_DELIVERED = "DS04"; //已配送

	//支付方式
	String PAY_WAY_BALANCE = "PW01"; //余额
	String PAY_WAY_WECHAT = "PW02"; //微信
	String PAY_WAY_TRANSFER = "PW03"; //汇款



	/**
	 * 获取当前状态
	 * @return
	 */
	DeliverOrderState getCurrentState(Long id);
	/**
	 * 获取DeliverOrder数据表格
	 * 
	 * @param deliverOrder
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(DeliverOrder deliverOrder, PageHelper ph);

	/**
	 * 添加DeliverOrder
	 * 
	 * @param deliverOrder
	 */
	public void add(DeliverOrder deliverOrder);

	/**
	 * 获得DeliverOrder对象
	 * 
	 * @param id
	 * @return
	 */
	public DeliverOrder get(Long id);

	/**
	 * 修改DeliverOrder
	 * 
	 * @param deliverOrder
	 */
	public void edit(DeliverOrder deliverOrder);

	/**
	 * 删除DeliverOrder
	 * 
	 * @param id
	 */
	public void delete(Long id);

	/**
	 * 填充信息
	 * @param deliverOrderExt
	 */
    void fillInfo(DeliverOrderExt deliverOrderExt);

	/**
	 * 填充商品信息
	 * @param deliverOrderExt
	 */
	void fillDeliverOrderItemInfo(DeliverOrderExt deliverOrderExt);

	/**
	 *
	 * @param deliverOrderExt
	 */
    void fillDeliverOrderShopItemInfo(DeliverOrderExt deliverOrderExt);

    /**
	 * 配送订单状态转换
	 * @param deliverOrder
	 */
	void transform(DeliverOrder deliverOrder);

	/**
	 * 列出门店的运单
	 * @param shopId
	 * @return
	 */
	List<DeliverOrder> listOrderByOrderShopIdAndShopStatus(Integer shopId, String deliverOrderShopStatus);

	List<DeliverOrder> listOrderByShopIdAndOrderStatus(Integer shopId, String orderStatus);

	/**
	 * 获取到含明细的运单
	 * @param id
	 * @return
	 */
	DeliverOrder getDeliverOrderExt(Long id);
}
