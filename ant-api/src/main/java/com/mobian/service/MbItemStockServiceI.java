package com.mobian.service;

import com.mobian.model.TmbItemStock;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbItemStock;
import com.mobian.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface MbItemStockServiceI {

	/**
	 * 获取MbItemStock数据表格
	 * 
	 * @param mbItemStock
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	DataGrid dataGrid(MbItemStock mbItemStock, PageHelper ph);

	/**
	 * 查询
	 * @param mbItemStock
	 * @return
	 */
	List<MbItemStock> query(MbItemStock mbItemStock);

	/**
	 * 为一个MbItemStock填充仓库和商品信息
	 * @param mbItemStock
	 */
	void addWarehouseAndItemInfo(MbItemStock mbItemStock);

	/**
	 * 添加MbItemStock
	 * 
	 * @param mbItemStock
	 */
	void add(MbItemStock mbItemStock);

	/**
	 * 添加MbItemStock并返回主键
	 * @param mbItemStock
	 * @return
	 */
	Integer addAndReturnId(MbItemStock mbItemStock);

	/**
	 * 添加MbItemStock，同时向MbItemStockLog插入一条记录。
	 * @param mbItemStock
	 * @param loginId
	 */
	void addAndInsertLog(MbItemStock mbItemStock, String loginId);

	/**
	 * 获得MbItemStock对象
	 * 
	 * @param id
	 * @return
	 */
	MbItemStock get(Integer id);

	/**
	 * 获取库存
	 * @param wareHouseId
	 * @param itemId
	 * @return
	 */
	MbItemStock getByWareHouseIdAndItemId(Integer wareHouseId, Integer itemId);

	/**
	 * 修改MbItemStock
	 * 
	 * @param mbItemStock
	 */
	void edit(MbItemStock mbItemStock);

	/**
	 * 修改MbItemStock并插入日志
	 * @param mbItemStock
	 * @param loginId
	 */
	Integer editAndInsertLog(MbItemStock mbItemStock, String loginId);

	/**
	 * 删除MbItemStock
	 * 
	 * @param id
	 */
	void delete(Integer id);

	boolean isWarehouseItemPairExist(Integer warehouseId, Integer itemId);

	/**
	 * 获取库存信息和订单总量
	 * @param mbItemStock
	 * @param ph
	 * @return
	 */
	DataGrid dataGridWithOrderSum(MbItemStock mbItemStock, PageHelper ph);

	/**
	 * 通过itemId 和warehouseId获取库存明细列表
	 *
	 * @return
	 */
	List<TmbItemStock> queryItemStockListByItemIdAndWarehouseIds(Integer itemId, Integer[] ids);

	/**
	 * 库存大盘批量导入
	 * @param mbItemStocks
	 */
	void addBatchAndUpdateItemStock(List<MbItemStock> mbItemStocks, String loginId);

	/**
	 * 修改库存大盘商品平均价格
	 * @param mbItemStock
	 * @return
	 */
	boolean editItemStockAveragePrice(MbItemStock mbItemStock);

	/**
	 * 获取库存数据报表
	 * @param mbItemStock
	 * @param ph
	 * @return
	 */
	DataGrid dataGridReport(MbItemStock mbItemStock, PageHelper ph);

	/**
	 * 查询带有空桶的库存数据
	 * @param mbItemStock
	 * @param ph
	 * @return
	 */
	DataGrid dataGridEmptyBucket(MbItemStock mbItemStock, PageHelper ph);

	/**
	 * 修改库存和余额信息
	 * @param mbItemStock
	 * @param  loginId
	 */
    void editStockAndBalance(MbItemStock mbItemStock, String loginId);
	/**
	 * 通过仓库I获取库存列表
	 * @param warehouseId
	 * @return
	 */
	List<TmbItemStock> queryItemStockListByWarehouseId(Integer warehouseId);

	/**
	 * 获取所要查询的商品Id
	 * @param mbItemStock
	 * @return
	 */
	MbItemStock getStockItemIdNumbersValue(MbItemStock mbItemStock);
}
