package com.mobian.thirdpart.wx;

import com.mobian.listener.Application;
import com.mobian.thirdpart.redis.Key;
import com.mobian.thirdpart.redis.Namespace;
import com.mobian.thirdpart.redis.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by wenming on 2016/7/9.
 */
public class AccessTokenInstance {

    private static Logger log = LoggerFactory.getLogger(AccessTokenInstance.class);
    public static final String ACCESS_TOKEN = "WP100";
    public static final String JSAPI_TICKET = "WP200";
    public static final String WX_PREFIX = "WP";

    public static AccessToken accessToken = null;
    public static JsapiTicket jsapiTicket = null;

    private static AccessTokenInstance instance;

    public static AccessTokenInstance getInstance() {
        // return instance;
        if (instance == null) {
            synchronized (AccessTokenInstance.class) {
                instance = new AccessTokenInstance();
            }
        }
        return instance;
    }

    private AccessTokenInstance() {
        if (instance != null) {
            throw new IllegalStateException("A server is already running");
        }
        instance = this;
        start();
    }

    private void start() {
        Application.executors.execute(new Runnable(){
            public void run() {
                while (true) {
                    try {
                        accessToken = WeixinUtil.getAccessToken();
                        if (null != accessToken) {
                            log.info("获取微信access_token成功，有效时长{}秒 token:{}", accessToken.getExpiresIn(), accessToken.getToken());
                            System.out.println("获取微信access_token成功，有效时长{}秒 token:{}" + " " + accessToken.getExpiresIn() + " " + accessToken.getToken());
                            jsapiTicket = WeixinUtil.getJsapiTicket(accessToken.getToken());
                            if (null != jsapiTicket) {
                                log.info("获取微信jsapiTicket成功，有效时长{}秒 token:{}", jsapiTicket.getExpiresIn(), jsapiTicket.getJsapi_ticket());
                            }

                            RedisUtil redisUtil = Application.getBean(RedisUtil.class);
                            redisUtil.set(Key.build(Namespace.WX_CONFIG, "wx_access_token"), accessToken.getToken(), accessToken.getExpiresIn(), TimeUnit.SECONDS);
                            redisUtil.set(Key.build(Namespace.WX_CONFIG, "wx_jsapi_ticket"), jsapiTicket.getJsapi_ticket(), jsapiTicket.getExpiresIn(), TimeUnit.SECONDS);

                            // 休眠7000秒
                            Thread.sleep(((long) accessToken.getExpiresIn() - 200) * 1000);
                        } else {
                            // 如果access_token为null，60秒后再获取
                            Thread.sleep(60 * 1000);
                        }

                    } catch (InterruptedException e) {
                        log.error("刷微信token线程中断了", e);
                        break;
                    }
                }
            }
        });
    }
}
