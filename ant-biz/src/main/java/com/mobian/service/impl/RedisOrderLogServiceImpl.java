package com.mobian.service.impl;
import com.mobian.thirdpart.redis.Key;
import com.mobian.thirdpart.redis.Namespace;
import com.mobian.thirdpart.redis.RedisUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Created by 黄晓渝 on 17/9/25.
 */
@Service(value = "redisOrderLogService")
public class RedisOrderLogServiceImpl {
    @Resource
    private RedisUtil redisUtil;

    /**
     *
     * @param orderId
     * @param logType
     */
    public long increment(Integer orderId, String logType) {
        String key = Key.build(Namespace.ORDERLOG_MESSAGE, orderId + ":" + logType);
        long l = redisUtil.increment(key,1);
        redisUtil.expire(key, 30, TimeUnit.DAYS);
        return l;
    }

    public long decrease(Integer orderId, String logType){
        String key = Key.build(Namespace.ORDERLOG_MESSAGE, orderId + ":" + logType);
        long l = redisUtil.increment(key,-1);
        redisUtil.expire(key, 30, TimeUnit.DAYS);
        return l;
    }

    /**
     * 获取订单日志催送或者摧回或者留言条数
     *
     * @param orderId
     * @param logType
     * @return
     */
    public Integer getOrderLogMessageNumber(Integer orderId, String logType) {
        Object r = redisUtil.get(Key.build(Namespace.ORDERLOG_MESSAGE, orderId + ":" + logType));
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
