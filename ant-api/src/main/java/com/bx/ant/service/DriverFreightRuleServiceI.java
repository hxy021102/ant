package com.bx.ant.service;

import com.bx.ant.pageModel.DeliverOrderShop;
import com.bx.ant.pageModel.DriverFreightRule;
import com.bx.ant.pageModel.DriverFreightRuleQuery;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface DriverFreightRuleServiceI {

	String TYPE_CONTRACT = "DFRT01";


	/**
	 * 获取DriverFreightRule数据表格
	 * 
	 * @param driverFreightRule
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(DriverFreightRule driverFreightRule, PageHelper ph);

	/**
	 * 添加DriverFreightRule
	 * 
	 * @param driverFreightRule
	 */
	public void add(DriverFreightRule driverFreightRule);

	/**
	 * 获得DriverFreightRule对象
	 * 
	 * @param id
	 * @return
	 */
	public DriverFreightRule get(Integer id);

	/**
	 * 修改DriverFreightRule
	 * 
	 * @param driverFreightRule
	 */
	public void edit(DriverFreightRule driverFreightRule);

	/**
	 * 删除DriverFreightRule
	 * 
	 * @param id
	 */
	public void delete(Integer id);

	/**
	 * 通过指定类型和orderShop找到符合运费规则的费用
	 * @param orderShop
	 * @param type
	 * @return
	 */
    Integer getOrderFreight(DeliverOrderShop orderShop, String type);

	/**
	 * 通过条件回去到符合要求的运费规则
	 * 规则为list
	 * list区域由小到大
	 * @param ruleQuery
	 * @return
	 */
	List<DriverFreightRule> getRuleList(DriverFreightRuleQuery ruleQuery);
}
