package com.mobian.dao.impl;

import com.mobian.dao.DiveRegionDaoI;
import com.mobian.model.TdiveRegion;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class DiveRegionDaoImpl extends BaseDaoImpl<TdiveRegion> implements DiveRegionDaoI {

    @Override
    @Cacheable(value = "diveRegionDaoCache", key = "#regionId")
    public TdiveRegion getById(String regionId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("regionId", regionId);
        TdiveRegion t = super.get("from TdiveRegion t  where t.regionId = :regionId", params);
        return t;
    }
}
