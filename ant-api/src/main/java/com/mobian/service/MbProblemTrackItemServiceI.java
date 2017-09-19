package com.mobian.service;

import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbProblemTrackItem;
import com.mobian.pageModel.PageHelper;

/**
 * 
 * @author John
 * 
 */
public interface MbProblemTrackItemServiceI {

	/**
	 * 获取MbProblemTrackItem数据表格
	 * 
	 * @param mbProblemTrackItem
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(MbProblemTrackItem mbProblemTrackItem, PageHelper ph);

	/**
	 * 添加MbProblemTrackItem
	 * 
	 * @param mbProblemTrackItem
	 */
	public void add(MbProblemTrackItem mbProblemTrackItem);

	/**
	 * 获得MbProblemTrackItem对象
	 * 
	 * @param id
	 * @return
	 */
	public MbProblemTrackItem get(Integer id);

	/**
	 * 修改MbProblemTrackItem
	 * 
	 * @param mbProblemTrackItem
	 */
	public void edit(MbProblemTrackItem mbProblemTrackItem);

	/**
	 * 删除MbProblemTrackItem
	 * 
	 * @param id
	 */
	public void delete(Integer id);

	/**
	 * 添加订单处理明细和修改订单问题状态
	 * @param mbProblemTrackItem
	 */
	public void addProblemTrackAndUpdateStatus(MbProblemTrackItem mbProblemTrackItem);

	/**
	 * 显示问题订单列表和设置显示名字
	 * @param mbProblemTrackItem
	 * @param ph
	 * @return
	 */
	public DataGrid dataGridWithSetName(MbProblemTrackItem mbProblemTrackItem, PageHelper ph);

}
