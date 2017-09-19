package com.mobian.dao;

import com.mobian.model.TmbItemCategory;

/**
 * MbItemCategory数据库操作类
 * 
 * @author John
 * 
 */
public interface MbItemCategoryDaoI extends BaseDaoI<TmbItemCategory> {

    /**
     * 通过ID获得商品分类
     *
     * @param id
     * @return
     */
    TmbItemCategory getById(Integer id);

}
