package com.mobian.service;

import com.mobian.model.TmbWarehouse;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbWarehouse;
import com.mobian.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface MbWarehouseServiceI {

	/**
	 * 获取MbWarehouse数据表格
	 * 
	 * @param mbWarehouse
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	DataGrid dataGrid(MbWarehouse mbWarehouse, PageHelper ph);

	/**
	 * 添加MbWarehouse
	 * 
	 * @param mbWarehouse
	 */
	void add(MbWarehouse mbWarehouse);

	/**
	 * 获得MbWarehouse对象
	 * 
	 * @param id
	 * @return
	 */
	MbWarehouse get(Integer id);

	MbWarehouse getFromCache(Integer id);

	/**
	 * 修改MbWarehouse
	 * 
	 * @param mbWarehouse
	 */
	void edit(MbWarehouse mbWarehouse);

	/**
	 * 删除MbWarehouse
	 * 
	 * @param id
	 */
	void delete(Integer id);

	MbWarehouse getByCode(String code);

	boolean isWarehouseExists(MbWarehouse mbWarehouse);

	/**
	 * 通过仓库的类型查询仓库列表
	 *
	 * @return
	 */
	List<TmbWarehouse> getWarehouseListByWarehouseType(String warehouseType);

}
