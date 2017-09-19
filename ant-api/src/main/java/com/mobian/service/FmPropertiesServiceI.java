package com.mobian.service;

import com.alibaba.fastjson.JSONObject;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.FmProperties;
import com.mobian.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface FmPropertiesServiceI {

	/**
	 * 获取FmProperties数据表格
	 * 
	 * @param fmProperties
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	DataGrid dataGrid(FmProperties fmProperties, PageHelper ph);

	/**
	 * 查询
	 * @param fmProperties
	 * @return
	 */
	List<FmProperties> query(FmProperties fmProperties);


	List<FmProperties> getByGoodsId(String goodsId);


	String getAfterMatching(String goodsId, JSONObject json, String format);

	/**
	 * 添加FmProperties
	 * 
	 * @param fmProperties
	 */
	void add(FmProperties fmProperties);

	/**
	 * 获得FmProperties对象
	 * 
	 * @param id
	 * @return
	 */
	FmProperties get(String id);

	/**
	 * 修改FmProperties
	 * 
	 * @param fmProperties
	 */
	void edit(FmProperties fmProperties);

	/**
	 * 删除FmProperties
	 * 
	 * @param id
	 */
	void delete(String id);

}
