package com.mobian.dao;

import com.mobian.model.TmbShop;
import com.mobian.pageModel.MbShop;

/**
 * MbShop数据库操作类
 *
 * @author John
 */
public interface MbShopDaoI extends BaseDaoI<TmbShop> {
    TmbShop getById(Integer id);

    /**
     * 清除shop的Id缓存
     *
     * @param mbShop
     */
    void clearShopCache(MbShop mbShop);
}
