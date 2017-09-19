package com.mobian.dao;

import com.mobian.model.TmbSupplier;

/**
 * MbSupplier数据库操作类
 * 
 * @author John
 * 
 */
public interface MbSupplierDaoI extends BaseDaoI<TmbSupplier> {
    TmbSupplier getById(Integer id);
}
