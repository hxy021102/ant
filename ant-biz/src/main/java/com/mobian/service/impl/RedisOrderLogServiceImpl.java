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
     * 设置订单日志催送或者摧回或者留言条数
     *
     * @param orderId
     * @param logType
     * @param orderLogNumber
     */
    public void setOrderLogMessage(Integer orderId, String logType, Integer orderLogNumber) {
        redisUtil.set(Key.build(Namespace.ORDERLOG_MESSAGE, orderId + ":" + logType), orderLogNumber + "", 3600 * 24 * 30, TimeUnit.SECONDS);
    }

    /**
     * 获取订单日志催送或者摧回或者留言条数
     *
     * @param orderId
     * @param logType
     * @return
     */
    public Integer getOrderLogMessage(Integer orderId, String logType) {
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
