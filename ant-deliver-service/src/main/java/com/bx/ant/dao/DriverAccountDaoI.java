package com.bx.ant.dao;

import com.bx.ant.model.TdriverAccount;
import com.bx.ant.pageModel.DriverAccount;

/**
 * DriverAccount数据库操作类
 * 
 * @author John
 * 
 */
public interface DriverAccountDaoI extends BaseDaoI<TdriverAccount> {

    TdriverAccount getById(Integer id);

    void clearShopCache(Integer id);
}
