package com.mobian.dao.impl;

import com.mobian.dao.MbItemDaoI;
import com.mobian.model.TmbItem;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

@Repository
public class MbItemDaoImpl extends BaseDaoImpl<TmbItem> implements MbItemDaoI {

    @Override
    @Cacheable(value = "mbItemDaoCache", key = "#id")
    public TmbItem getById(Integer id) {
        return super.get(TmbItem.class, id);
    }
}
