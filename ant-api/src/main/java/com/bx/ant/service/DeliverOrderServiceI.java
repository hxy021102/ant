package com.bx.ant.service;

import com.mobian.pageModel.DeliverOrder;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;

/**
 * 
 * @author John
 * 
 */
public interface DeliverOrderServiceI {

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
	public DeliverOrder get(Integer id);

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
	public void delete(Integer id);

}
