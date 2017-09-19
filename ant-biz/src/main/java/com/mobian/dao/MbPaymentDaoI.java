package com.mobian.dao;

import com.mobian.model.TmbPayment;

/**
 * MbPayment数据库操作类
 * 
 * @author John
 * 
 */
public interface MbPaymentDaoI extends BaseDaoI<TmbPayment> {
    TmbPayment getById(Integer id);
}
