package com.bx.ant.service;

import com.bx.ant.pageModel.DeliverOrderExt;
import com.bx.ant.pageModel.DeliverOrder;

import com.bx.ant.pageModel.DeliverOrderQuery;
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
	String PAY_STATUS_NOT_PAY = "DPS01"; //待支付
	String PAY_STATUS_AUDIT = "DPS02"; //待审核
	String PAY_STATUS_REFUSE = "DPS03"; //审核拒绝
	String PAY_STATUS_SUCCESS = "DPS04"; //支付成功

	//门店结算状态
	String SHOP_PAY_STATUS_NOT_PAY = "SPS01"; //待支付
	String SHOP_PAY_STATUS_AUDIT = "SPS02"; //待审核
	String SHOP_PAY_STATUS_REFUSE = "SPS03"; //审核拒绝
	String SHOP_PAY_STATUS_SUCCESS = "SPS04"; //支付成功

	//订单状态
	String STATUS_NOT_ALLOCATION = "DOS01"; //待分配
	String STATUS_SHOP_ALLOCATION= "DOS10"; //已分配，待门店接
	String STATUS_SHOP_REFUSE = "DOS15"; //门店拒绝接单
	String STATUS_SHOP_ACCEPT = "DOS20"; //已接单
	String STATUS_DELIVERING = "DOS25"; //已发货
	String STATUS_DELIVERY_COMPLETE = "DOS30"; //已配送完成,等待用户确认状态
	String STATUS_CLOSED = "DOS40"; //订单完成

	//配送状态
	String DELIVER_STATUS_STANDBY = "DDS01"; //待处理
	String DELIVER_STATUS_DELIVERING = "DDS02"; //配送中
	String DELIVER_STATUS_USER_CHECK = "DDS03"; //用户确认
	String DELIVER_STATUS_DELIVERED = "DDS04"; //已配送

	//支付方式
	String PAY_WAY_BALANCE = "DPW01"; //余额
	String PAY_WAY_WECHAT = "DPW02"; //微信
	String PAY_WAY_TRANSFER = "DPW03"; //汇款

	//派单结算时间差
	Long TIME_DIF_SHOP_PAY_SETTLED = new Long(7 * 24 * 60 * 60 * 1000) ;

    void transformByShopIdAndStatus(Long id, Integer shopId, String status);

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
//    void fillInfo(DeliverOrderExt deliverOrderExt);

	/**
	 * 填充商品信息
	 * @param deliverOrderExt
	 */
//	void fillDeliverOrderItemInfo(DeliverOrderExt deliverOrderExt);

	/**
	 *
	 * @param deliverOrderExt
	 */
//    void fillDeliverOrderShopItemInfo(DeliverOrderExt deliverOrderExt);

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

	/**
	 * 通过门店ID和订单状态获取订单列表
	 * @param shopId
	 * @param orderStatus
	 * @return
	 */
	List<DeliverOrder> listOrderByShopIdAndOrderStatus(Integer shopId, String orderStatus);

	/**
	 * 获取到含明细的运单
	 * @param id
	 * @return
	 */
	DeliverOrder getDeliverOrderExt(Long id);

	/**
	 * 获取包含DeliverOrderShopItemList的order
	 * @param deliverOrder
	 * @param ph
	 * @return
	 */
	DataGrid dataGridExt(DeliverOrder deliverOrder, PageHelper ph);

	/**
	 * 编辑deliverOrder并添加记录
	 * @param deliverOrder
	 * @param logType
	 * @param content
	 * @param loginId
	 */
    void editAndAddLog(DeliverOrder deliverOrder, String logType, String content, String loginId);

	/**
	 * 编辑deliverOrder并添加记录
	 * @param deliverOrder
	 * @param logType
	 * @param content
	 */
    void editAndAddLog(DeliverOrder deliverOrder, String logType, String content);

	/**
	 * 获取运单管理列表数据
	 * @param deliverOrder
	 * @param ph
	 * @return
	 */
	DataGrid dataGridWithName(DeliverOrder deliverOrder,PageHelper ph);

	/**
	 * 根据运单ID，查询运单详细
	 * @param id
	 * @return
	 */
	DeliverOrderQuery getDeliverOrderView(Long id );

	void settleShopPay();

	/**
	 *获取需要支付给门店的订单
	 * @param deliverOrder
	 * @param ph
	 * @return
	 */
	DataGrid dataGridShopArtificialPay(DeliverOrder deliverOrder,PageHelper ph);

	/**
	 * 查询DeliverOrder集合列表
	 * @param deliverOrder
	 * @return
	 */
	List<DeliverOrder> query(DeliverOrder deliverOrder);
}
