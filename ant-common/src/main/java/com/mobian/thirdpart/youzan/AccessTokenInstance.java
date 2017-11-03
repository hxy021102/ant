package com.mobian.thirdpart.youzan;

import com.mobian.thirdpart.redis.Key;
import com.mobian.thirdpart.redis.Namespace;
import com.mobian.thirdpart.redis.RedisUtil;
import com.mobian.util.BeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 * Created by wenming on 2016/7/9.
 */
public class AccessTokenInstance {

    private static Logger log = LoggerFactory.getLogger(AccessTokenInstance.class);

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
        ((Executor)BeanUtil.getBean("taskExecutor")).execute(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        AccessToken accessToken = YouzanUtil.getAccessToken();
                        if (null != accessToken) {
                            log.info("获取有赞access_token成功，有效时长{}秒 token:{}", accessToken.getExpiresIn(), accessToken.getToken());
                            System.out.println("获取有赞access_token成功，有效时长{}秒 token:{}" + " " + accessToken.getExpiresIn() + " " + accessToken.getToken());

                            RedisUtil redisUtil = BeanUtil.getBean(RedisUtil.class);
                            redisUtil.set(Key.build(Namespace.YOUZAN_CONFIG, "youzan_access_token"), accessToken.getToken(), accessToken.getExpiresIn(), TimeUnit.SECONDS);

                            // 休眠600000秒
                            Thread.sleep(((long) accessToken.getExpiresIn() - 4800) * 1000);
                        } else {
                            // 如果access_token为null，60秒后再获取
                            Thread.sleep(60 * 1000);
                        }

                    } catch (InterruptedException e) {
                        log.error("刷有赞token线程中断了", e);
                        break;
                    }
                }
            }
        });
    }
}
