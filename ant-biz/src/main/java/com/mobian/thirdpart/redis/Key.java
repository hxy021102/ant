package com.mobian.thirdpart.redis;

import com.mobian.listener.Application;
import com.mobian.util.Constants;

/**
 * Created by john on 15/12/30.
 */
public abstract class Key {
    /**
     * 构建redis key；现在开发与正式公用一套redis环境
     *
     * @param namespace
     * @param key
     * @return
     */
    public static String build(String namespace, String key) {
        return Application.getString(Constants.SYSTEM_PUBLISH_SETTING) + ":" + namespace + ":" + key;
    }
}
