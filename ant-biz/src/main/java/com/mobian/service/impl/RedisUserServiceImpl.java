package com.mobian.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.mobian.absx.F;
import com.mobian.interceptors.TokenWrap;
import com.mobian.thirdpart.redis.Key;
import com.mobian.thirdpart.redis.Namespace;
import com.mobian.thirdpart.redis.RedisUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Created by john on 16/1/10.
 */
@Service(value = "redisUserService")
public class RedisUserServiceImpl {
    private long timeout = 60 * 30;
    @Resource
    private RedisUtil redisUtil;

    public boolean setToken(TokenWrap tokenWrap) {
        redisUtil.set(buildKey(tokenWrap.getTokenId()), JSONObject.toJSONString(tokenWrap), timeout, TimeUnit.SECONDS);
        //USER_USERID_TOKEN
        redisUtil.set(Key.build(Namespace.USER_USERID_TOKEN, tokenWrap.getUid()), tokenWrap.getTokenId());
        return true;
    }

    /**
     * 删除存在Radis中存放的用户的Token键值对
     *
     * @param uid
     * @return
     */
    public boolean deleteTokenByUid(String uid) {
        //通过Key.build方法传入的两个参数获取存入Radis中存放的userId的key值
        String uidKey = Key.build(Namespace.USER_USERID_TOKEN, uid);
        //通过已经存放的userIdkey值找到Radis中对应关系的tokenIdKey
        String tokenIdKey = (String) redisUtil.get(uidKey);
        //删除tokenIdKey键值对(Radis存放的是tokenIdKey和对应的mbUser的信息TokenWrap对象)
        if (tokenIdKey != null)
            redisUtil.delete(buildKey(tokenIdKey));
        //删除userIdKey键值对(Radis存放的是userIdkey与对应的tokenIdKey)
        if (uidKey != null)
            redisUtil.delete(uidKey);
        return true;
    }

    /**
     * 设置用户登录所在的服务器
     *
     * @param userId
     * @param serverHost
     * @return
     */
    public boolean setUserConnect(String userId, String serverHost) {
        redisUtil.set(Key.build(Namespace.USER_LOGIN_SERVER_HOST, userId), serverHost);
        return true;
    }

    /**
     * 设置手机apple token
     *
     * @param userId
     * @param appleToken
     * @return
     */
    public boolean setAppleToken(String userId, String appleToken) {
        redisUtil.set(Key.build(Namespace.USER_APPLE_TOKEN, userId), appleToken);
        return true;
    }

    /**
     * 获取apple token
     *
     * @param userId
     * @return
     */
    public String getAppleToken(String userId) {
        return (String) redisUtil.getString(Key.build(Namespace.USER_APPLE_TOKEN, userId));
    }

    /**
     * 获取用户所在服务器
     *
     * @param userId
     * @return
     */
    public String getUserConnect(String userId) {
        return (String) redisUtil.getString(Key.build(Namespace.USER_LOGIN_SERVER_HOST, userId));
    }

    private String buildKey(String token) {
        return Key.build(Namespace.USER_LOGIN_TOKEN, token);
    }

    public TokenWrap getToken(String token) {
        String json = (String) redisUtil.get(buildKey(token));
        if (F.empty(json)) return null;
        return JSONObject.parseObject(json, TokenWrap.class);
    }

    public TokenWrap getTokenByUserId(Integer userId) {
        String tokenId = (String) redisUtil.get(Key.build(Namespace.USER_USERID_TOKEN, userId + ""));
        TokenWrap tokenWrap = getToken(tokenId);
        return tokenWrap;
    }

    /**
     * 刷新token
     *
     * @param token
     */
    public void refresh(String token) {
        redisUtil.expire(buildKey(token), timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置手机验证码
     *
     * @param phone
     * @param code
     */
    public void setValidateCode(String phone, String code) {
        redisUtil.set(Key.build(Namespace.USER_LOGIN_VALIDATE_CODE, phone), code, 60, TimeUnit.SECONDS);
    }

    /**
     * 获取手机验证码
     *
     * @param phone
     */
    public String getValidateCode(String phone) {
        return (String) redisUtil.getString(Key.build(Namespace.USER_LOGIN_VALIDATE_CODE, phone));
    }

    /**
     * 设置钱包支付验证码
     *
     * @param userId
     * @param code
     */
    public void setBalancePayValidateCode(String userId, String code) {
        redisUtil.set(Key.build(Namespace.USER_BALANCE_PAY_VALIDATE_CODE, userId), code, 60, TimeUnit.SECONDS);
    }

    /**
     * 获取钱包支付验证码
     *
     * @param userId
     */
    public String getBalancePayValidateCode(String userId) {
        return (String) redisUtil.getString(Key.build(Namespace.USER_BALANCE_PAY_VALIDATE_CODE, userId));
    }
    /**
     * 设置对应商品的订单量
     * @param key
     * @param orderQuantity
     */
    public void setOrderQuantity(String key, Integer orderQuantity) {
        redisUtil.set(Key.build(Namespace.ORDER_QUANTITY, key), orderQuantity+"", 600, TimeUnit.SECONDS);
    }

    /**
     *获取对应商品的订单量
     * @param key
     * @return
     */
    public Integer getOrderQuantity(String key) {
        Object r = redisUtil.get(Key.build(Namespace.ORDER_QUANTITY, key));
        if (r != null) {
            try {
                return Integer.parseInt(r.toString());
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    /**
     * 设置合同价
     */
    public void setContractPrice(Integer shopId, Integer itemId, Integer price) {
        redisUtil.set(Key.build(Namespace.USER_CONTRACT_PRICE, shopId + ":" + itemId), price + "", 24 * 60 * 60, TimeUnit.SECONDS);
    }

    /**
     * 获取合同价
     */
    public Integer getContractPrice(Integer shopId, Integer itemId) {
        Object r = redisUtil.get(Key.build(Namespace.USER_CONTRACT_PRICE, shopId + ":" + itemId));
        if (r != null) {
            try {
                return Integer.parseInt(r.toString());
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

}
