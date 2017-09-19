package com.mobian.service;

import com.mobian.pageModel.Resource;
import com.mobian.pageModel.SessionInfo;
import com.mobian.pageModel.Tree;

import java.util.List;

/**
 * 资源Service
 * 
 * @author John
 * 
 */
public interface ResourceServiceI {

	/**
	 * 获得资源树(资源类型为菜单类型)
	 * 
	 * 通过用户ID判断，他能看到的资源
	 * 
	 * @param sessionInfo
	 * @return
	 */
	public List<Tree> tree(SessionInfo sessionInfo);

	/**
	 * 获得资源树(包括所有资源类型)
	 * 
	 * 通过用户ID判断，他能看到的资源
	 * 
	 * @param sessionInfo
	 * @return
	 */
	public List<Tree> allTree(SessionInfo sessionInfo);

	/**
	 * 获得资源列表
	 * 
	 * @param sessionInfo
	 * 
	 * @return
	 */
	public List<Resource> treeGrid(SessionInfo sessionInfo);

	/**
	 * 添加资源
	 * 
	 * @param resource
	 * @param sessionInfo
	 */
	public void add(Resource resource, SessionInfo sessionInfo);

	/**
	 * 删除资源
	 * 
	 * @param id
	 */
	public void delete(String id);

	/**
	 * 修改资源
	 * 
	 * @param resource
	 */
	public void edit(Resource resource);

	/**
	 * 获得一个资源
	 * 
	 * @param id
	 * @return
	 */
	public Resource get(String id);

	/**
	 * 通过资源的地址获取资源的名称
	 * @param url
	 * @return
	 */
	public Resource getResourceByUrl(String url);

}
