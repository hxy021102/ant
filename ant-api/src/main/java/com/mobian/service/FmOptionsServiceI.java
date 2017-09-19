package com.mobian.service;

import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.FmOptions;
import com.mobian.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface FmOptionsServiceI {

	/**
	 * 获取FmOptions数据表格
	 * 
	 * @param fmOptions
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	DataGrid dataGrid(FmOptions fmOptions, PageHelper ph);


	List<FmOptions> query(FmOptions fmOptions);


	/**
	 * 添加FmOptions
	 * 
	 * @param fmOptions
	 */
	void add(FmOptions fmOptions);

	/**
	 * 获得FmOptions对象
	 * 
	 * @param id
	 * @return
	 */
	FmOptions get(String id);

	/**
	 * 修改FmOptions
	 * 
	 * @param fmOptions
	 */
	void edit(FmOptions fmOptions);

	/**
	 * 删除FmOptions
	 * 
	 * @param id
	 */
	void delete(String id);

}
