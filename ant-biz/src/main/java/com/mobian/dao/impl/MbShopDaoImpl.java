package com.mobian.dao.impl;

import com.alibaba.fastjson.JSONObject;
import com.mobian.absx.F;
import com.mobian.dao.MbShopDaoI;
import com.mobian.model.TmbShop;
import com.mobian.pageModel.MbShop;
import com.mobian.thirdpart.redis.Key;
import com.mobian.thirdpart.redis.Namespace;
import com.mobian.thirdpart.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class MbShopDaoImpl extends BaseDaoImpl<TmbShop> implements MbShopDaoI {

    @Autowired
    private RedisUtil redisUtil;

    @Override
//    @Cacheable(value = "mbShopDaoCache", key = "#id")
    public TmbShop getById(Integer id) {
        String key = Key.build(Namespace.MB_SHOP, id + "");
        String shopStr = (String) redisUtil.get(key);
        TmbShop shop;
        if (!F.empty(shopStr)) {
            shop = JSONObject.parseObject(shopStr, TmbShop.class);
        } else {
            shop = super.get(TmbShop.class, id);
            if (shop != null) {
                redisUtil.set(key, JSONObject.toJSONString(shop), 7* 24 * 60 * 60, TimeUnit.SECONDS);
            }
        }
        return shop;
    }

//    @CacheEvict(value = "mbShopDaoCache", key = "#mbShop.getId()")
    public void clearShopCache(MbShop mbShop) {
        String key = Key.build(Namespace.MB_SHOP, mbShop.getId() + "");
        redisUtil.delete(key);
    }
}
