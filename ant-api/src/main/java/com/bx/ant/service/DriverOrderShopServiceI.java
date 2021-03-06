package com.bx.ant.service;

import com.bx.ant.pageModel.DriverOrderShop;
import com.bx.ant.pageModel.DriverOrderShopView;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface DriverOrderShopServiceI {

	//状态
	String STATUS_STANDBY = "DDSS01";//待分配
	String STATUS_ALLOCATION = "DDSS03";//分配中
	String STATUS_ACCEPTED = "DDSS05"; //已接单
	String STATUS_ITEM_TAKEN = "DDSS08"; //取货完成
	String STATUS_DELVIERING = "DDSS10"; //派送中
	String STATUS_DELIVERED_AUDIT = "DDSS15"; //派送完成等待門店审核
	String STATUS_DELIVERED= "DDSS20"; //已送达
	String STATUS_SETTLEED = "DDSS30"; //已结算

	//支付状态
	String PAY_STATUS_NOT_PAY = "DDPS01";
	String PAY_STATUS_PAID = "DDPS02";
	String PAY_STSTUS_WAITE_AUDIT="DDPS03";


	/**
	 * 获取DriverOrderShop数据表格
	 * 
	 * @param driverOrderShop
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(DriverOrderShop driverOrderShop, PageHelper ph);

	/**
	 * 添加DriverOrderShop
	 * 
	 * @param driverOrderShop
	 */
	public void add(DriverOrderShop driverOrderShop);

	/**
	 * 获得DriverOrderShop对象
	 * 
	 * @param id
	 * @return
	 */
	public DriverOrderShop get(Long id);

	/**
	 * 修改DriverOrderShop
	 * 
	 * @param driverOrderShop
	 */
	public void edit(DriverOrderShop driverOrderShop);

	DriverOrderShop update(DriverOrderShop driverOrderShop);

	/**
	 * 删除DriverOrderShop
	 * 
	 * @param id
	 */
	public void delete(Long id);

	/**
	 * DriverOrderShopView
	 * @param id
	 * @return
	 */
    DriverOrderShopView getView(Long id);

	/**
	 * 通过DriverOrderPayId找到DriverOrderShop
	 * @param driverOrderPayId
	 * @return
	 */
	DriverOrderShop getByPayId(Long driverOrderPayId);

    /**
	 * dataGrid.DriverOrderShopView
	 * @param driverOrderShop
	 * @param pageHelper
	 * @return
	 */
	DataGrid dataGridView(DriverOrderShop driverOrderShop, PageHelper pageHelper);

	/**
	 * 获取现有状态机
	 * @param driverOrderShopId
	 * @return
	 */
    DriverOrderShopState getCurrentState(Long driverOrderShopId);

	/**
	 * 状态机执行器
	 * @param driverOrderShop
	 */
	void transform(DriverOrderShop driverOrderShop);

	/**
	 * 添加并返回新分配订单数量
	 * @param accountId
	 * @return
	 */
    Integer addAllocationOrderRedis(Integer accountId);

	/**
	 * 减少并返回新分配订单数量
	 * @param accountId
	 * @return
	 */
    Integer reduseAllocationOrderRedis(Integer accountId);

	/**
	 * 清除并返回新分配订单数量
	 * @param accountId
	 * @return
	 */
	Integer clearAllocationOrderRedis(Integer accountId);

	/**
	 * 获取DriverOrderShop的集合列表
	 * @param driverOrderShop
	 * @return
	 */
	List<DriverOrderShop> query(DriverOrderShop driverOrderShop);

	/**
	 * 修改骑手运单状态
	 * @param id
	 * @return
	 */
	void editStatusByHql(Long id,String handleStatus);

	/**
	 *填充骑手信息
	 * @param driverOrderShopView
	 */
	void fillDriverAccountInfo(DriverOrderShopView driverOrderShopView);

	/**
	 * 拒绝订单
	 * @param driverOrderShop
	 */
	void refuseOrder(DriverOrderShop driverOrderShop);

	/**
	 * 今日有效订单
	 * @param driverAccountId
	 * @return
	 */
    DataGrid listTodayOrderByAccountId(Integer driverAccountId);

	/**
	 * 接单
	 * @param driverOrderShop
	 * @return
	 */
	Boolean editOrderAccept(DriverOrderShop driverOrderShop);

	/**
	 * 获取骑手订单
	 * @param deliverOrderShopId
	 * @return
	 */
	DriverOrderShop getByDeliverOrderShopId(Long deliverOrderShopId);

	/**
	 * 未结算汇总
	 * @param driverOrderShop
	 * @return
	 */
	DataGrid queryUnPayForCount(DriverOrderShop driverOrderShop);
}
