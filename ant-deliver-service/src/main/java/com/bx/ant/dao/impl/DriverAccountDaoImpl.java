package com.bx.ant.dao.impl;

import com.alibaba.fastjson.JSONObject;
import com.bx.ant.dao.DriverAccountDaoI;
import com.bx.ant.model.TdriverAccount;

import com.bx.ant.pageModel.DriverAccount;
import com.mobian.absx.F;
import com.mobian.thirdpart.redis.Key;
import com.mobian.thirdpart.redis.Namespace;
import com.mobian.thirdpart.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DriverAccountDaoImpl extends BaseDaoImpl<TdriverAccount> implements DriverAccountDaoI {
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public TdriverAccount getById(Integer id) {
        String key = Key.build(Namespace.DRIVER_ACCOUNT_CACHE, id + "");
        String accountStr = (String)redisUtil.get(key);
        TdriverAccount account = new TdriverAccount();
        if (!F.empty(accountStr)) {
            account = JSONObject.parseObject(accountStr, TdriverAccount.class);
        }else {
            account = super.get(TdriverAccount.class, id);
            if (account != null) {
                redisUtil.set(key, JSONObject.toJSONString(account));
            }
        }
        return account;
    }
    @Override
    public void clearShopCache(Integer id) {
        String key = Key.build(Namespace.DRIVER_ACCOUNT_CACHE, id +"");
        redisUtil.delete(key);
    }
}
