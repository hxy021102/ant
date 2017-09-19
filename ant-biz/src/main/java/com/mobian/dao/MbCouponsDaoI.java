package com.mobian.dao;

import com.mobian.model.TmbCoupons;

/**
 * MbCoupons数据库操作类
 * 
 * @author John
 * 
 */
public interface MbCouponsDaoI extends BaseDaoI<TmbCoupons> {
    TmbCoupons getById(Integer id);
}
