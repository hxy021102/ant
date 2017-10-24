package com.mobian.service;

import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;
import com.mobian.pageModel.SessionInfo;
import com.mobian.pageModel.User;

import java.util.List;
import java.util.Map;

/**
 * 用户Service
 * 
 * @author John
 * 
 */
public interface UserServiceI {

	/**
	 * 用户登录
	 * 
	 * @param user
	 *            里面包含登录名和密码
	 * @return 用户对象
	 */
	User login(User user);

	/**
	 * 用户注册
	 * 
	 * @param user
	 *            里面包含登录名和密码
	 * @throws Exception
	 */
	void reg(User user) throws Exception;

	/**
	 * 获取用户数据表格
	 * 
	 * @param user
	 * @return
	 */
	DataGrid dataGrid(User user, PageHelper ph);
	
	/**
	 * 感兴趣的
	 * @param user
	 * @param ph
	 * @return
	 */
	DataGrid dataGridHobby(User user, PageHelper ph);
	
	/**
	 * api查询用的
	 * @param user
	 * @param ph
	 * @return
	 */
	DataGrid dataGridForApi(User user, PageHelper ph);
	
	/**
	 * 新好友
	 * @param user
	 * @param ph
	 * @return
	 */
	DataGrid dataGridNewFriend(User user, PageHelper ph);

	/**
	 * 添加用户
	 * 
	 * @param user
	 */
	void add(User user) throws Exception;

	/**
	 * 获得用户对象
	 * 
	 * @param id
	 * @return
	 */
	User get(String id);

	User getIncludeRole(String id);

	/**
	 * 编辑用户
	 * 
	 * @param user
	 */
	void edit(User user) throws Exception;

	/**
	 * 删除用户
	 * 
	 * @param id
	 */
	void delete(String id);

	/**
	 * 用户授权
	 * 
	 * @param ids
	 * @param user
	 *            需要user.roleIds的属性值
	 */
	void grant(String ids, User user);

	/**
	 * 获得用户能访问的资源地址
	 * 
	 * @param id
	 *            用户ID
	 * @return
	 */
	List<String> resourceList(String id);

	/**
	 * 编辑用户密码
	 * 
	 * @param user
	 */
	void editPwd(User user);

	/**
	 * 修改用户自己的密码
	 * 
	 * @param sessionInfo
	 * @param oldPwd
	 * @param pwd
	 * @return
	 */
	boolean editCurrentUserPwd(SessionInfo sessionInfo, String oldPwd, String pwd);

	/**
	 * 用户登录时的autocomplete
	 * 
	 * @param q
	 *            参数
	 * @return
	 */
	List<User> loginCombobox(String q);

	/**
	 * 用户登录时的combogrid
	 * 
	 * @param q
	 * @param ph
	 * @return
	 */
	DataGrid loginCombogrid(String q, PageHelper ph);

	/**
	 * 用户创建时间图表
	 * 
	 * @return
	 */
	List<Long> userCreateDatetimeChart();
		
	
	/**
	 * 我首页
	 * @param userId
	 * @return
	 */
	Map<String,Object> userIndex(String userId);

	User getFromCache(String id);

	/**
	 * 根据资源获取权限
	 * @param resourceId
	 * @return
	 */
	List<User> getUserListByResourceId(String resourceId);

}
