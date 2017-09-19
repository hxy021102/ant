package com.mobian.dao;

import com.mobian.model.TdiveRegion;

/**
 * DiveRegion数据库操作类
 * 
 * @author John
 * 
 */
public interface DiveRegionDaoI extends BaseDaoI<TdiveRegion> {
    TdiveRegion getById(String regionId);
}
