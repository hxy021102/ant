package com.mobian.service;

import com.mobian.pageModel.ResourceType;

import java.util.List;

/**
 * 资源类型服务
 * 
 * @author John
 * 
 */
public interface ResourceTypeServiceI {

	/**
	 * 获取资源类型
	 * 
	 * @return
	 */
	public List<ResourceType> getResourceTypeList();

}
