package com.mobian.dao.impl;

import com.mobian.dao.MbContractDaoI;
import com.mobian.model.TmbContract;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

@Repository
public class MbContractDaoImpl extends BaseDaoImpl<TmbContract> implements MbContractDaoI {

    @Override
    @Cacheable(value = "mbContractDaoCache", key = "#id")
    public TmbContract getById(Integer id) {
        return super.get(TmbContract.class, id);
    }
}
