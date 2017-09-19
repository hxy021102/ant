package com.mobian.dao;

import com.mobian.model.Tuser;

import java.util.List;

/**
 * 用户数据库操作类
 * 
 * @author John
 * 
 */
public interface UserDaoI extends BaseDaoI<Tuser> {
	public List<Tuser> getTusers(String... userids);
	 Tuser getById(String id);
}
