package com.bx.ant.dao;

import com.bx.ant.model.TshopDeliverAccount;

/**
 * ShopDeliverAccount数据库操作类
 * 
 * @author John
 * 
 */
public interface ShopDeliverAccountDaoI extends BaseDaoI<TshopDeliverAccount> {
    TshopDeliverAccount getById(Integer id);
}
