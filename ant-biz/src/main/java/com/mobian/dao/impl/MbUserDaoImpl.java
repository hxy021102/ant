package com.mobian.dao.impl;

import com.mobian.dao.MbUserDaoI;
import com.mobian.model.TmbUser;
import com.mobian.pageModel.MbUser;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

@Repository
public class MbUserDaoImpl extends BaseDaoImpl<TmbUser> implements MbUserDaoI {

    @Override
    @Cacheable(value = "mbUserDaoCache", key = "#id")
    public TmbUser getById(Integer id) {
        return super.get(TmbUser.class, id);
    }

    @Override
    @CacheEvict(value = "mbUserDaoCache", key = "#mbUser.getId()")
    public void clearUserCache(MbUser mbUser) {
    }
}
