package com.mobian.dao.impl;

import com.mobian.dao.MbShopDaoI;
import com.mobian.model.TmbShop;
import com.mobian.pageModel.MbShop;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

@Repository
public class MbShopDaoImpl extends BaseDaoImpl<TmbShop> implements MbShopDaoI {
    @Override
    @Cacheable(value = "mbShopDaoCache", key = "#id")
    public TmbShop getById(Integer id) {
        return super.get(TmbShop.class, id);
    }

    @CacheEvict(value = "mbShopDaoCache", key = "#mbShop.getId()")
    public void clearShopCache(MbShop mbShop) {
    }
}
