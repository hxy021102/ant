package com.mobian.dao.impl;

import java.util.List;

import com.mobian.dao.UserDaoI;
import com.mobian.model.Tuser;

import org.hibernate.Query;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl extends BaseDaoImpl<Tuser> implements UserDaoI {

	@SuppressWarnings("unchecked")
	@Override
	public List<Tuser> getTusers(String... userids) {
		if (userids == null || userids.length == 0) return null;
		String hql = "FROM Tuser t WHERE t.id in (:alist)";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameterList("alist", userids);
		List<Tuser> l = query.list();
		return l;
	}

	@Override
	@Cacheable(value = "userDaoCache", key = "#id")
	public Tuser getById(String id) {
		return super.get(Tuser.class, id);
	}

}
