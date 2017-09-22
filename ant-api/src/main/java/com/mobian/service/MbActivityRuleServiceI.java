package com.mobian.service;

import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbActivityRule;
import com.mobian.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface MbActivityRuleServiceI {

	/**
	 * 获取MbActivityRule数据表格
	 * 
	 * @param mbActivityRule
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(MbActivityRule mbActivityRule, PageHelper ph);

	/**
	 * 添加MbActivityRule
	 * 
	 * @param mbActivityRule
	 */
	public void add(MbActivityRule mbActivityRule);

	/**
	 * 获得MbActivityRule对象
	 * 
	 * @param id
	 * @return
	 */
	public MbActivityRule get(Integer id);

	/**
	 * 修改MbActivityRule
	 * 
	 * @param mbActivityRule
	 */
	public void edit(MbActivityRule mbActivityRule);

	/**
	 * 删除MbActivityRule
	 * 
	 * @param id
	 */
	public void delete(Integer id);

    void addActivityRuleAndRule(MbActivityRule activityRule);

    void editActivityRuleAndRule(MbActivityRule activityRule);

    /**
	 *
	 * 级联删除rule下对应的action
	 */
	void deleteRule(Integer id);
	/**
	 * 查找同一个活动下的所有rule
	 *
	 */
	List<MbActivityRule> query(MbActivityRule mbActivityRule);

    MbActivityRule getByActivityActionId(Integer activityActionId);
}
