package com.mobian.service;

import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbActivityAction;
import com.mobian.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface MbActivityActionServiceI {

	/**
	 * 获取MbActivityAction数据表格
	 * 
	 * @param mbActivityAction
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(MbActivityAction mbActivityAction, PageHelper ph);

	/**
	 * 添加MbActivityAction
	 * 
	 * @param mbActivityAction
	 */
	public void add(MbActivityAction mbActivityAction);

	/**
	 * 获得MbActivityAction对象
	 * 
	 * @param id
	 * @return
	 */
	public MbActivityAction get(Integer id);

	/**
	 * 修改MbActivityAction
	 * 
	 * @param mbActivityAction
	 */
	public void edit(MbActivityAction mbActivityAction);

	/**
	 * 删除MbActivityAction
	 * 
	 * @param id
	 */
	public void delete(Integer id);
	/**
	 *
	 */
	public List<MbActivityAction> query(MbActivityAction mbActivityAction);

}
