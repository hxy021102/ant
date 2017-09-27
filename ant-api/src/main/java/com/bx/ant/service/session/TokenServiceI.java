package com.bx.ant.service.session;

import com.bx.ant.pageModel.session.TokenWrap;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by john on 17/9/26.
 */
public interface TokenServiceI {

    String TOKEN_FIELD = "tokenId";

    String DEFAULT_TOKEN = "89";

    /**
     * token校验
     * @param tokenId
     * @return
     */
    boolean validToken(String tokenId);

    /**
     * 设置token
     * @param tokenWrap
     * @return
     */
    boolean setToken(TokenWrap tokenWrap);

    /**
     * 获取token
     * @param tokenId
     * @return
     */
    TokenWrap getToken(String tokenId);
    TokenWrap getToken(HttpServletRequest request);

}
