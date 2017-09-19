package com.mobian.dao;

import com.mobian.model.TmbItem;

/**
 * MbItem数据库操作类
 * 
 * @author John
 * 
 */
public interface MbItemDaoI extends BaseDaoI<TmbItem> {
    TmbItem getById(Integer id);
}
