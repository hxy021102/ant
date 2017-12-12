package com.mobian.thirdpart.youzan;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.mobian.absx.F;
import com.mobian.util.ConvertNameUtil;
import com.mobian.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wenming on 2017/10/31.
 */
public class YouzanUtil {
    private static Logger log = LoggerFactory.getLogger(YouzanUtil.class);

    public final static String CLIENT_ID = "YZ01"; // 有赞client_id
    public final static String CLIENT_SECRET = "YZ02"; // 有赞client_secret
    public final static String KDT_ID = "YZ03"; // 授权的应用店铺id
    public final static String APPKEY = "YZ04"; // 有赞接入方appKey
    public final static String SETTLE_TERM = "YZ05"; // 结算期限

    /**
     * 获取access_token的接口地址（GET） 限200（次/天）
     */
    public final static String ACCESS_TOKEN_URL = "https://open.youzan.com/oauth/token";

    public static AccessToken getAccessToken() {
        AccessToken accessToken = null;
        String params = "client_id=CLIENT_ID&client_secret=CLIENT_SECRET&grant_type=silent&kdt_id=KDT_ID";
        params = params.replace("CLIENT_ID", ConvertNameUtil.getString(CLIENT_ID)).replace("CLIENT_SECRET", ConvertNameUtil.getString(CLIENT_SECRET)).replace("KDT_ID", ConvertNameUtil.getString(KDT_ID));
        JSONObject jsonObject = JSONObject.parseObject(HttpUtil.httpsRequest(ACCESS_TOKEN_URL, "POST", params));
        // 如果请求成功
        if (null != jsonObject && !F.empty(jsonObject.getString("access_token"))) {
            try {
                accessToken = new AccessToken();
                accessToken.setToken(jsonObject.getString("access_token"));
                accessToken.setExpiresIn(jsonObject.getIntValue("expires_in"));
            } catch (JSONException e) {
                accessToken = null;
                // 获取token失败
                log.error("获取有赞token失败 errcode:{} errmsg:{}", jsonObject.getIntValue("errcode"), jsonObject.getString("errmsg"));
            }
        }
        return accessToken;
    }

}
