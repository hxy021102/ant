package com.mobian.service;

import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbProblemTrack;
import com.mobian.pageModel.PageHelper;

import javax.servlet.http.HttpSession;

/**
 * 
 * @author John
 * 
 */
public interface MbProblemTrackServiceI {

	/**
	 * 获取MbProblemTrack数据表格
	 * 
	 * @param mbProblemTrack
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(MbProblemTrack mbProblemTrack, PageHelper ph);

	/**
	 * 添加MbProblemTrack
	 * 
	 * @param mbProblemTrack
	 */
	public void add(MbProblemTrack mbProblemTrack);

	/**
	 * 获得MbProblemTrack对象
	 * 
	 * @param id
	 * @return
	 */
	public MbProblemTrack get(Integer id);

	/**
	 * 修改MbProblemTrack
	 * 
	 * @param mbProblemTrack
	 */
	public void edit(MbProblemTrack mbProblemTrack);

	/**
	 * 删除MbProblemTrack
	 * 
	 * @param id
	 */
	public void delete(Integer id);

	/**
	 * 显示问题列表和设置名字
	 * @param mbProblemTrack
	 * @param ph
	 * @return
	 */
	public DataGrid dataGridWithSetName(MbProblemTrack mbProblemTrack, PageHelper ph);

	/**
	 * 查询并显示我该处理的订单问题列表
	 * @param mbProblemTrack
	 * @param ph
	 * @return
	 */
	public DataGrid orderProblemDataGrid(MbProblemTrack mbProblemTrack, PageHelper ph, HttpSession session);

}
