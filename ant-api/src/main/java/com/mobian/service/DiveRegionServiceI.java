package com.mobian.service;

import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.DiveRegion;
import com.mobian.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface DiveRegionServiceI {

	/**
	 * 获取DiveRegion数据表格
	 * 
	 * @param diveRegion
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	DataGrid dataGrid(DiveRegion diveRegion, PageHelper ph);

	/**
	 * 添加DiveRegion
	 * 
	 * @param diveRegion
	 */
	void add(DiveRegion diveRegion);

	/**
	 * 获得DiveRegion对象
	 * 
	 * @param id
	 * @return
	 */
	DiveRegion get(Integer id);

	/**
	 *
	 * @param regionId
	 * @return
	 */
	DiveRegion getFromCache(String regionId);

	/**
	 * 获取省市区
	 * @param regionId
	 * @return
	 */
	String getRegionPath(String regionId);

	/**
	 * 修改DiveRegion
	 * 
	 * @param diveRegion
	 */
	void edit(DiveRegion diveRegion);

	/**
	 * 删除DiveRegion
	 * 
	 * @param id
	 */
	void delete(Integer id);

	List<DiveRegion> findAllByParams(DiveRegion diveRegion);

	/**
	 * 检测是否为父区域
	 * @param parentRegionId
	 * @param sonRegionId
	 * @return
	 */
    Boolean isParent(String parentRegionId, String sonRegionId);
}
