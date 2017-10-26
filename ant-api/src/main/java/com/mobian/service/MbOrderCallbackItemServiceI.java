package com.mobian.service;

import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbOrderCallbackItem;
import com.mobian.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface MbOrderCallbackItemServiceI {

	/**
	 * 获取MbOrderCallbackItem数据表格
	 * 
	 * @param mbOrderCallbackItem
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(MbOrderCallbackItem mbOrderCallbackItem, PageHelper ph);

	/**
	 * 添加MbOrderCallbackItem
	 * 
	 * @param mbOrderCallbackItem
	 */
	public void add(MbOrderCallbackItem mbOrderCallbackItem);

	/**
	 * 获得MbOrderCallbackItem对象
	 * 
	 * @param id
	 * @return
	 */
	public MbOrderCallbackItem get(Integer id);

	/**
	 * 修改MbOrderCallbackItem
	 * 
	 * @param mbOrderCallbackItem
	 */
	public void edit(MbOrderCallbackItem mbOrderCallbackItem);

	/**
	 * 删除MbOrderCallbackItem
	 * 
	 * @param id
	 */
	public void delete(Integer id);

	/**
	 * 根据mbOrderCallbackItem找到所有的回桶记录List<MbOrderCallbackItem>
	 * @param mbOrderCallbackItem
	 * @return
	 */
    List<MbOrderCallbackItem> query(MbOrderCallbackItem mbOrderCallbackItem);
	/**
	 * addCallback
	 */
	void addCallbackItem(MbOrderCallbackItem mbOrderCallbackItem);
}
