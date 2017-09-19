package com.mobian.concurrent;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by john on 15/9/10.
 */
public abstract class ThreadCache {
    private static ThreadLocal<Map> cacheMap = new ThreadLocal<Map>() {
        protected synchronized Map initialValue() {
            return new HashMap();
        }
    };
    private Class c;

    public ThreadCache(Class c) {
        this.c = c;
    }

    private String buildKey(Object key) {
        String keyStr = c.getName() + "." + key.toString();
        return keyStr;
    }

    public <T> T getValue(Object key) {
        String keyStr = buildKey(key);
        Object value = cacheMap.get().get(keyStr);
        if (value == null) {
            value = handle(key);
            if (value == null) {
                try {
                    value = c.newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            cacheMap.get().put(keyStr, value);
        }
        return (T) value;
    }

    public static void clear() {
        cacheMap.remove();
    }

    protected abstract Object handle(Object key);
}
