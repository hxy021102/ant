package com.mobian.service;

import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbShopInvoice;
import com.mobian.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface MbShopInvoiceServiceI {

	/**
	 * 获取MbShopInvoice数据表格
	 * 
	 * @param mbShopInvoice
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(MbShopInvoice mbShopInvoice, PageHelper ph);

	/**
	 * 添加MbShopInvoice
	 * 
	 * @param mbShopInvoice
	 */
	public void add(MbShopInvoice mbShopInvoice);

	/**
	 * 获得MbShopInvoice对象
	 * 
	 * @param id
	 * @return
	 */
	public MbShopInvoice get(Integer id);

	/**
	 * 修改MbShopInvoice
	 * 
	 * @param mbShopInvoice
	 */
	public void edit(MbShopInvoice mbShopInvoice);

	/**
	 * 删除MbShopInvoice
	 * 
	 * @param id
	 */
	public void delete(Integer id);

	/**
	 * 查询各自门店的所有开票模板
	 * @param mbShopInvoice
	 * @return
	 */
	public  List<MbShopInvoice> query(MbShopInvoice mbShopInvoice);

}
