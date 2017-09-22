package com.mobian.dao.impl;

import com.mobian.dao.MbItemCategoryDaoI;
import com.mobian.model.TmbItemCategory;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

@Repository
public class MbItemCategoryDaoImpl extends BaseDaoImpl<TmbItemCategory> implements MbItemCategoryDaoI {

    @Override
    @Cacheable(value = "mbItemCategoryDaoCache", key = "#id")
    public TmbItemCategory getById(Integer id) {
        return super.get(TmbItemCategory.class,id);
    }
}
