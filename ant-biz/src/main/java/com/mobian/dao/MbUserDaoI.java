package com.mobian.dao;

import com.mobian.model.TmbUser;
import com.mobian.pageModel.MbUser;

/**
 * MbUser数据库操作类
 *
 * @author John
 */
public interface MbUserDaoI extends BaseDaoI<TmbUser> {
    TmbUser getById(Integer id);

    /**
     * 清除user的Id缓存
     *
     * @param mbUser
     */
    void clearUserCache(MbUser mbUser);
}
