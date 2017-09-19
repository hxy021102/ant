package com.mobian.service;

import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbLogRecord;
import com.mobian.pageModel.PageHelper;

/**
 * 
 * @author John
 * 
 */
public interface MbLogRecordServiceI {

	/**
	 * 获取MbLogRecord数据表格
	 * 
	 * @param mbLogRecord
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(MbLogRecord mbLogRecord, PageHelper ph);

	/**
	 * 添加MbLogRecord
	 * 
	 * @param mbLogRecord
	 */
	public void add(MbLogRecord mbLogRecord);

	/**
	 * 获得MbLogRecord对象
	 * 
	 * @param id
	 * @return
	 */
	public MbLogRecord get(Integer id);

	/**
	 * 修改MbLogRecord
	 * 
	 * @param mbLogRecord
	 */
	public void edit(MbLogRecord mbLogRecord);

	/**
	 * 删除MbLogRecord
	 * 
	 * @param id
	 */
	public void delete(Integer id);

}
