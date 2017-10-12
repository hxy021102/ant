package com.bx.ant.service;

import com.mobian.pageModel.DeliverOrder;
import com.mobian.pageModel.DeliverOrderShop;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface DeliverOrderShopServiceI {
	String STATUS_AUDITING = "DS01"; //等待审核
	String STATUS_ACCEPTED = "DS02"; //接受
	String STATUS_REFUSED = "DS03"; //拒绝
	String STATUS_COMPLETE = "DS04"; //正常完成
	String STATUS_INACTIVE = "DS05"; //失效

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
	 * 获得DeliverOrderShop对象
	 * 
	 * @param id
	 * @return
	 */
	public DeliverOrderShop get(Integer id);

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

	DeliverOrderShop addByDeliverOrder(DeliverOrder deliverOrder);

	List<DeliverOrderShop> query(DeliverOrderShop deliverOrderShop);

	DeliverOrderShop editStatus(DeliverOrderShop deliverOrderShop, String status);
}
