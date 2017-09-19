package com.mobian.service;

import com.mobian.pageModel.BaseData;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;

import java.util.List;
import java.util.Map;

/**
 * 基础数据业务逻辑
 * 
 * @author John
 * 
 */
public interface BasedataServiceI {

	/**
	 * 保存基础数据
	 * 
	 * @param baseData
	 */
	void add(BaseData baseData);

	/**
	 * 获得基础数据
	 * 
	 * @param id
	 * @return
	 */
	BaseData get(String id);

	/**
	 * 编辑基础数据
	 * 
	 * @param baseData
	 */
	void edit(BaseData baseData);

	/**
	 * 获取BUG数据表格
	 * 
	 * @param baseData
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	DataGrid dataGrid(BaseData baseData, PageHelper ph);

	/**
	 * 删除基础数据
	 * 
	 * @param id
	 */
	void delete(String id);
	
	/**
	 * 根据类型取基础数据
	 * @param baseData
	 * @return
	 */
	List<BaseData> getBaseDatas(BaseData baseData);
	
	/**
	 * 根据动态sql查找结果
	 * @param sql
	 * @param params
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getSelectMapList(String sql, Map params);
	
	/**
	 * 获取系统环境变量
	 */
	Map<String,BaseData> getAppVariable();

	/**
	 * 商品搜索时，获取商品的名称
	 */
	String[] getGoodsName(String key);

	Map<String, Object> queryHaveChildsByPid(String pid);
}
