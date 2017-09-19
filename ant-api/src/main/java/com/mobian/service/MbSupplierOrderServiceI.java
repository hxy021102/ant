package com.mobian.service;

import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbSupplierOrder;
import com.mobian.pageModel.PageHelper;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 *
 * @author John
 *
 */
public interface MbSupplierOrderServiceI {

	/**
	 * 获取MbSupplierOrder数据表格
	 *
	 * @param mbSupplierOrder
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(MbSupplierOrder mbSupplierOrder, PageHelper ph);

	/**
	 * 添加MbSupplierOrder
	 *
	 * @param mbSupplierOrder
	 */
	public void add(MbSupplierOrder mbSupplierOrder);

	/**
	 * 获得MbSupplierOrder对象
	 *
	 * @param id
	 * @return
	 */
	public MbSupplierOrder get(Integer id);

	/**
	 * 修改MbSupplierOrder
	 *
	 * @param mbSupplierOrder
	 */
	public void edit(MbSupplierOrder mbSupplierOrder);

	/**
	 * 删除MbSupplierOrder
	 *
	 * @param id
	 */
	public void delete(Integer id);

	public Boolean addMbSupplierOrder(MbSupplierOrder mbSupplierOrder, HttpSession session);
	public Boolean editMbSupplierOrder(MbSupplierOrder mbSupplierOrder);
	public MbSupplierOrder showMbSupplierOrder(Integer id);
	public void deleteSupplierOrderAndItem(Integer id);

	/**
	 * 根据条件查询列表
	 * @param supplierOrderReport
	 * @return
	 */
	List<MbSupplierOrder> query(MbSupplierOrder mbSupplierOrder);

}
