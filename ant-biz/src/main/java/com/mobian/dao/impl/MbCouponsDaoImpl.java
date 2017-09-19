package com.mobian.dao.impl;

import com.mobian.dao.MbCouponsDaoI;
import com.mobian.model.TmbCoupons;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

@Repository
public class MbCouponsDaoImpl extends BaseDaoImpl<TmbCoupons> implements MbCouponsDaoI {
    @Override
    @Cacheable(value = "mbItemDaoCache", key = "#id")
    public TmbCoupons getById(Integer id) {
        return super.get(TmbCoupons.class, id);
    }

}
