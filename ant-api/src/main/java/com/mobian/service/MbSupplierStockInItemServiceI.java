package com.mobian.service;

import com.mobian.model.TmbSupplierStockInItem;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbSupplierStockInItem;
import com.mobian.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface MbSupplierStockInItemServiceI {

	/**
	 * 获取MbSupplierStockInItem数据表格
	 * 
	 * @param mbSupplierStockInItem
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	DataGrid dataGrid(MbSupplierStockInItem mbSupplierStockInItem, PageHelper ph);

	/**
	 * 添加MbSupplierStockInItem
	 * 
	 * @param mbSupplierStockInItem
	 */
	void add(MbSupplierStockInItem mbSupplierStockInItem);

	/**
	 * 获得MbSupplierStockInItem对象
	 * 
	 * @param id
	 * @return
	 */
	MbSupplierStockInItem get(Integer id);

	/**
	 * 修改MbSupplierStockInItem
	 * 
	 * @param mbSupplierStockInItem
	 */
	void edit(MbSupplierStockInItem mbSupplierStockInItem);

	/**
	 * 删除MbSupplierStockInItem
	 * 
	 * @param id
	 */
	void delete(Integer id);
	List<MbSupplierStockInItem> query(MbSupplierStockInItem mbSupplierStockInItem);

	/**
	 * 通过入库Id获取对应的入库明细列表
	 * @param stockInId
	 * @return
	 */
	List<TmbSupplierStockInItem> getStockInItemListByStockInId(Integer stockInId);


	List<MbSupplierStockInItem> getListByStockInIds(Integer[] stockInIds);
}
