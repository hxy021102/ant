package com.bx.ant.dao.impl;

import com.bx.ant.dao.ShopDeliverAccountDaoI;
import com.bx.ant.model.TshopDeliverAccount;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

@Repository
public class ShopDeliverAccountDaoImpl extends BaseDaoImpl<TshopDeliverAccount> implements ShopDeliverAccountDaoI {
    @Override
    @Cacheable(value = "shopDeliverAccountDaoCache", key = "#id")
    public TshopDeliverAccount getById(Integer id) {
        return super.get(TshopDeliverAccount.class, id);
    }
}
