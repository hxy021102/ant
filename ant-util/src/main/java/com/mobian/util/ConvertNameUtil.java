package com.mobian.util;

/**
 * Created by john on 17/9/18.
 */
public class ConvertNameUtil {
    private static ConfigTransfer configTransfer;

    public static void setConfigTransfer(ConfigTransfer configTransfer) {
        ConvertNameUtil.configTransfer = configTransfer;
    }

    /**
     * 获取全局变量值
     * @param key
     * @return
     */
    public static String getString(String key){

        return configTransfer.getString(key);
    }

    public static String getString(String key, String defaultVal) {
        String val = getString(key);
        val = val == null ? defaultVal : val;
        return val;
    }

    public static String getDesc(String key){

        return configTransfer.getDesc(key);
    }

    public static String getDesc(String key, String defaultVal) {
        String desc = getDesc(key);
        desc = desc == null ? defaultVal : desc;
        return desc;
    }

}
