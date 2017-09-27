package com.bx.ant.service.session;

import com.alibaba.fastjson.JSONObject;
import com.bx.ant.pageModel.session.TokenWrap;
import com.mobian.absx.F;
import com.mobian.thirdpart.redis.Key;
import com.mobian.thirdpart.redis.Namespace;
import com.mobian.thirdpart.redis.RedisUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * Created by john on 17/9/26.
 */
@Service
public class TokenServiceImpl implements TokenServiceI {
    private long timeout = 60 * 30;
    @Resource
    private RedisUtil redisUtil;

    @Override
    public boolean validToken(String tokenId) {
        if (TokenServiceI.DEFAULT_TOKEN.equals(tokenId)) return true;

        boolean flag = getToken(tokenId) == null ? false : true;
        if (flag) {
            String key = Key.build(Namespace.SHOP_USER_LOGIN_TOKEN, tokenId);
            redisUtil.expire(key, timeout, TimeUnit.SECONDS);
        }
        return flag;
    }

    @Override
    public boolean setToken(TokenWrap tokenWrap) {
        String key = Key.build(Namespace.SHOP_USER_LOGIN_TOKEN, tokenWrap.getTokenId());
        redisUtil.set(key, JSONObject.toJSONString(tokenWrap), timeout, TimeUnit.SECONDS);
        return false;
    }

    @Override
    public TokenWrap getToken(String tokenId) {
        String key = Key.build(Namespace.SHOP_USER_LOGIN_TOKEN, tokenId);
        String json = (String) redisUtil.get(key);
        if (F.empty(json)) return null;
        return JSONObject.parseObject(json, TokenWrap.class);
    }

    @Override
    public TokenWrap getToken(HttpServletRequest request) {
        return getToken(request.getParameter(TOKEN_FIELD));
    }
}
