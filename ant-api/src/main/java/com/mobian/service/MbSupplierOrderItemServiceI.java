package com.mobian.service;

import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbSupplierOrderItem;
import com.mobian.pageModel.MbSupplierOrderReport;
import com.mobian.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface MbSupplierOrderItemServiceI {

	/**
	 * 获取MbSupplierOrderItem数据表格
	 * 
	 * @param mbSupplierOrderItem
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(MbSupplierOrderItem mbSupplierOrderItem, PageHelper ph);

	/**
	 * 添加MbSupplierOrderItem
	 * 
	 * @param mbSupplierOrderItem
	 */
	public void add(MbSupplierOrderItem mbSupplierOrderItem);

	/**
	 * 获得MbSupplierOrderItem对象
	 * 
	 * @param id
	 * @return
	 */
	public MbSupplierOrderItem get(Integer id);

	/**
	 * 修改MbSupplierOrderItem
	 * 
	 * @param mbSupplierOrderItem
	 */
	public void edit(MbSupplierOrderItem mbSupplierOrderItem);

	/**
	 * 删除MbSupplierOrderItem
	 * 
	 * @param id
	 */
	public void delete(Integer id);
	/**
	 * 通过订单ID获取总金额
	 * @param orderId
	 * @return
	 */
	Integer getTotalPrice(Integer orderId);

	public List<MbSupplierOrderItem> mbSupplierOrderItemByOrderId(Integer id);
	List<MbSupplierOrderItem> query(MbSupplierOrderItem mbSupplierOrderItem);

	/**
	 * 获取采购订单明细列表和对应商品的入库数量
	 * @param mbSupplierOrderItem
	 * @param ph
	 * @return
	 */
	public DataGrid dataGridSupplierOrderItemWithStockQuantity(MbSupplierOrderItem mbSupplierOrderItem, PageHelper ph);


	DataGrid dataGridReport(MbSupplierOrderReport supplierOrderReport);
}
