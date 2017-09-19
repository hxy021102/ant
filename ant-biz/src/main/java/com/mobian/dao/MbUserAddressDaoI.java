package com.mobian.dao;

import com.mobian.model.TmbUserAddress;

/**
 * MbUserAddress数据库操作类
 * 
 * @author John
 * 
 */
public interface MbUserAddressDaoI extends BaseDaoI<TmbUserAddress> {
    TmbUserAddress getById(Integer id);
}
