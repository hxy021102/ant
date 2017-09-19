package com.mobian.service;

import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbCouponsItem;
import com.mobian.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface MbCouponsItemServiceI {

	/**
	 * 获取MbCouponsItem数据表格
	 * 
	 * @param mbCouponsItem
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(MbCouponsItem mbCouponsItem, PageHelper ph);

	/**
	 * 添加MbCouponsItem
	 * 
	 * @param mbCouponsItem
	 */
	public void add(MbCouponsItem mbCouponsItem);

	/**
	 * 获得MbCouponsItem对象
	 * 
	 * @param id
	 * @return
	 */
	public MbCouponsItem get(Integer id);

	/**
	 * 修改MbCouponsItem
	 * 
	 * @param mbCouponsItem
	 */
	public void edit(MbCouponsItem mbCouponsItem);

	/**
	 * 删除MbCouponsItem
	 * 
	 * @param id
	 */
	public void delete(Integer id);

	MbCouponsItem getByCouponsIdAndItemId(Integer couponsId, Integer itemId);

	/**
	 * 查询符合参数的0-100条记录
	 * @param m
	 * @return
	 */
    List<MbCouponsItem> listCouponsItem(MbCouponsItem m);

	/**
	 * 查询复合参数的0-rows条记录
	 * @param m
	 * @param rows
	 * @return
	 */
    List<MbCouponsItem> listCouponsItem(MbCouponsItem m, Integer rows);

	/**
	 * 查询复合参数的page-rows 条记录
	 * @param m
	 * @param page
	 * @param rows
	 * @return
	 */
    List<MbCouponsItem> listCouponsItem(MbCouponsItem m, Integer page, Integer rows);
}
