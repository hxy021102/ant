package com.mobian.dao;

import java.util.List;

import com.mobian.model.Tuser;

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
