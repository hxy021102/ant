package com.mobian.thirdpart.redis;

import com.mobian.util.Constants;
import com.mobian.util.ConvertNameUtil;

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
        return ConvertNameUtil.getString(Constants.SYSTEM_PUBLISH_SETTING) + ":" + namespace + ":" + key;
    }
}
