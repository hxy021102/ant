package com.mobian.dao.impl;

import com.mobian.dao.ResourceDaoI;
import com.mobian.model.Tresource;
import org.hibernate.Query;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ResourceDaoImpl extends BaseDaoImpl<Tresource> implements ResourceDaoI {

    @Cacheable(value = "resourceDaoCache", key = "#url")
    public Tresource getByUrl(String url) {
        Query query = getCurrentSession().createQuery("from Tresource  where url = :url");
        query.setParameter("url",url);
        List<Tresource> l = query.list();
        if (l != null && l.size() > 0) {
            return l.get(0);
        }
        return null;
    }
}
