package com.bx.ant.service;

import com.bx.ant.pageModel.DeliverOrder;
import com.bx.ant.pageModel.DeliverOrderShop;
import com.bx.ant.pageModel.DeliverOrderShopView;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface DeliverOrderShopServiceI {
	String STATUS_AUDITING = "DSS01"; //等待审核
	String STATUS_ACCEPTED = "DSS02"; //接受
	String STATUS_REFUSED = "DSS03"; //拒绝
	String STATUS_COMPLETE = "DSS04"; //正常完成
	String STATUS_INACTIVE = "DSS05"; //失效
	String STAUS_SERVICE = "DSS06";  //已送达

	Long TIME_OUT_TO_ACCEPT = new Long(10 * 60 * 1000);

	/**
	 * 获取DeliverOrderShop数据表格
	 * 
	 * @param deliverOrderShop
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(DeliverOrderShop deliverOrderShop, PageHelper ph);

	/**
	 * 添加DeliverOrderShop
	 * 
	 * @param deliverOrderShop
	 */
	public void add(DeliverOrderShop deliverOrderShop);

	/**
	 * 添加并返回包含ID的DeliverOrderShop
	 * @param deliverOrderShop
	 * @return
	 */
    DeliverOrderShop addAndGet(DeliverOrderShop deliverOrderShop);

    /**
	 * 获得DeliverOrderShop对象
	 * 
	 * @param id
	 * @return
	 */
	public DeliverOrderShop get(Long id);

	/**
	 * 修改DeliverOrderShop
	 * 
	 * @param deliverOrderShop
	 */
	public void edit(DeliverOrderShop deliverOrderShop);

	/**
	 * 删除DeliverOrderShop
	 * 
	 * @param id
	 */
	public void delete(Integer id);

	/**
	 * 通过deliverOrder添加DeliverOrderShop'
	 * @param deliverOrder
	 * @return
	 */
	DeliverOrderShop addByDeliverOrder(DeliverOrder deliverOrder);

	/**
	 * 查询deliverOrderShop
	 * @param deliverOrderShop
	 * @return
	 */
	List<DeliverOrderShop> query(DeliverOrderShop deliverOrderShop);

	/**
	 * 通过deliverOrderShop 找到订单并修改订单为status状态
	 * @param deliverOrderShop
	 * @param orderShopEdit
	 * @return
	 */
	DeliverOrderShop editStatus(DeliverOrderShop deliverOrderShop, DeliverOrderShop orderShopEdit);

	/**
	 * 获取DeliverOrderShop集合对象及对应信息名字
	 * @param deliverOrderShop
	 * @param ph
	 * @return
	 */
	DataGrid dataGridWithName(DeliverOrderShop deliverOrderShop, PageHelper ph);

	/**
	 * 自动拒绝所有超时订单
	 */
	void checkTimeOutOrder();

	/**
	 *获取需要支付给门店的订单
	 * @param deliverOrderShop
	 * @param ph
	 * @return
	 */
	DataGrid dataGridShopArtificialPay(DeliverOrderShop deliverOrderShop,PageHelper ph);

	/**
	 * 自动跟门店结算账单
	 */
	void settleShopPay();

	/**
	 * 修改门店订单的状态
	 * @param status
	 * @param deliverOrderShop
	 * @param shopPayStatus
	 */
	DeliverOrderShop editStatusByHql(DeliverOrderShop deliverOrderShop,String status,String shopPayStatus);

	List<DeliverOrderShop> queryTodayOrdersByShopId(Integer shopId);

    DeliverOrderShopView getView(Long id);
}
