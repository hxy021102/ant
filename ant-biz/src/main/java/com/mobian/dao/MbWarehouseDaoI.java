package com.mobian.dao;

import com.mobian.model.TmbWarehouse;

/**
 * MbWarehouse数据库操作类
 * 
 * @author John
 * 
 */
public interface MbWarehouseDaoI extends BaseDaoI<TmbWarehouse> {
    TmbWarehouse getById(Integer id);
}
