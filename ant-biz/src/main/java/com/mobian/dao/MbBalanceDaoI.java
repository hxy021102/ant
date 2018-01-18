package com.mobian.dao;

import com.mobian.model.TmbBalance;

/**
 * MbBalance数据库操作类
 * 
 * @author John
 * 
 */
public interface MbBalanceDaoI extends BaseDaoI<TmbBalance> {
    /**
     * 获取余额
     * @param balanceId
     * @return
     */
    Integer getAmountById(Integer balanceId);
}
