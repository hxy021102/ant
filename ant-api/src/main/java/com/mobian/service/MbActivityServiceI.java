package com.mobian.service;

import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbActivity;
import com.mobian.pageModel.PageHelper;

/**
 * 
 * @author John
 * 
 */
public interface MbActivityServiceI {

	/**
	 * 获取MbActivity数据表格
	 * 
	 * @param mbActivity
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(MbActivity mbActivity, PageHelper ph);

	/**
	 * 添加MbActivity
	 * 
	 * @param mbActivity
	 */
	public void add(MbActivity mbActivity);

	/**
	 * 获得MbActivity对象
	 * 
	 * @param id
	 * @return
	 */
	public MbActivity get(Integer id);

	/**
	 * 修改MbActivity
	 * 
	 * @param mbActivity
	 */
	public void edit(MbActivity mbActivity);

	/**
	 * 删除MbActivity
	 * 
	 * @param id
	 */
	public void delete(Integer id);

	void addActivityAndRuleSet(MbActivity activity);

	void editActivityAndRuleSet(MbActivity activity);

	/**
	 *
	 * 级联删除活动下的rule和action
	 */
	void deleteActivityAndRuleSet(Integer id);

    MbActivity getByActivityRuleId(Integer activityRuleId);

    MbActivity getByActivityActionId(Integer activityActionId);
}
