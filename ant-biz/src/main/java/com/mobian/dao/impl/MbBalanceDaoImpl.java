package com.mobian.dao.impl;

import com.mobian.dao.MbBalanceDaoI;
import com.mobian.model.TmbBalance;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

@Repository
public class MbBalanceDaoImpl extends BaseDaoImpl<TmbBalance> implements MbBalanceDaoI {

    @Override
    public Integer getAmountById(Integer balanceId) {
        SQLQuery q = getCurrentSession().createSQLQuery("select amount from mb_balance where id =:id");
        q.setParameter("id", balanceId);
        return (Integer) q.uniqueResult();
    }
}
