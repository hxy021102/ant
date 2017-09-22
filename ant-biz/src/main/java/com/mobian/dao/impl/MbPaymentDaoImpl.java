package com.mobian.dao.impl;

import com.mobian.dao.MbPaymentDaoI;
import com.mobian.model.TmbPayment;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

@Repository
public class MbPaymentDaoImpl extends BaseDaoImpl<TmbPayment> implements MbPaymentDaoI {

    @Override
    @Cacheable(value = "mbPaymentDaoCache", key = "#id")
    public TmbPayment getById(Integer id) {
        return super.get(TmbPayment.class, id);
    }
}
