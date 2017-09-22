package com.mobian.dao.impl;

import com.mobian.dao.MbWarehouseDaoI;
import com.mobian.model.TmbWarehouse;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

@Repository
public class MbWarehouseDaoImpl extends BaseDaoImpl<TmbWarehouse> implements MbWarehouseDaoI {
    @Override
    @Cacheable(value = "mbWarehouseDaoCache", key = "#id")
    public TmbWarehouse getById(Integer id) {
        return super.get(TmbWarehouse.class, id);
    }
}
