package com.mobian.dao;

import com.mobian.model.TmbContract;

/**
 * MbContract数据库操作类
 * 
 * @author John
 * 
 */
public interface MbContractDaoI extends BaseDaoI<TmbContract> {
    TmbContract getById(Integer id);
}
