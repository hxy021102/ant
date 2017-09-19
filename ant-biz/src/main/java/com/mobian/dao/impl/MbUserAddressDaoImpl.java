package com.mobian.dao.impl;

import com.mobian.dao.MbUserAddressDaoI;
import com.mobian.model.TmbUserAddress;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

@Repository
public class MbUserAddressDaoImpl extends BaseDaoImpl<TmbUserAddress> implements MbUserAddressDaoI {

    @Override
    @Cacheable(value = "mbUserAddressDaoCache", key = "#id")
    public TmbUserAddress getById(Integer id) {
        return super.get(TmbUserAddress.class, id);
    }
}
