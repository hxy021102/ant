package com.mobian.service;

import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbOrderRefundItem;
import com.mobian.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface MbOrderRefundItemServiceI {

	/**
	 * 获取MbOrderRefundItem数据表格
	 *
	 * @param mbOrderRefundItem 参数
	 * @param ph                分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(MbOrderRefundItem mbOrderRefundItem, PageHelper ph);

	/**
	 * 添加MbOrderRefundItem
	 *
	 * @param mbOrderRefundItem
	 */
	public void add(MbOrderRefundItem mbOrderRefundItem);

	/**
	 * 获得MbOrderRefundItem对象
	 *
	 * @param id
	 * @return
	 */
	public MbOrderRefundItem get(Integer id);

	/**
	 * 修改MbOrderRefundItem
	 *
	 * @param mbOrderRefundItem
	 */
	public void edit(MbOrderRefundItem mbOrderRefundItem);

	/**
	 * 删除MbOrderRefundItem
	 *
	 * @param id
	 */
	public void delete(Integer id);


	List<MbOrderRefundItem> query(MbOrderRefundItem mbOrderRefundItem);
	List<MbOrderRefundItem> queryListByOrderIds(Integer[] orderIds);
}