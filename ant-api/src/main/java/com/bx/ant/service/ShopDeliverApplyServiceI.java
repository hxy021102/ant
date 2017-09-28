package com.bx.ant.service;

import com.mobian.pageModel.ShopDeliverApply;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;

/**
 * 
 * @author John
 * 
 */
public interface ShopDeliverApplyServiceI {

	/**
	 * 获取ShopDeliverApply数据表格
	 * 
	 * @param shopDeliverApply
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(ShopDeliverApply shopDeliverApply, PageHelper ph);

	/**
	 * 添加ShopDeliverApply
	 * 
	 * @param shopDeliverApply
	 */
	public void add(ShopDeliverApply shopDeliverApply);

	/**
	 * 获得ShopDeliverApply对象
	 * 
	 * @param id
	 * @return
	 */
	public ShopDeliverApply get(Integer id);

	/**
	 * 修改ShopDeliverApply
	 * 
	 * @param shopDeliverApply
	 */
	public void edit(ShopDeliverApply shopDeliverApply);

	/**
	 * 删除ShopDeliverApply
	 * 
	 * @param id
	 */
	public void delete(Integer id);

	ShopDeliverApply getByAccountId(Integer accountId);
}
