package com.mobian.util;

import com.mobian.pageModel.BaseData;

import javax.servlet.ServletContext;

/**
 * Created by john on 17/9/18.
 */
public class ConvertNameUtil {
    private static String PREFIX = "SV.";
    private static ServletContext context;

    public static void setContext(ServletContext context) {
        ConvertNameUtil.context = context;
    }

    /**
     * 获取全局变量值
     * @param key
     * @return
     */
    public static String getString(String key){
        BaseData bd = (BaseData)context.getAttribute(PREFIX+key);
        String val = null;
        if(bd != null){
            val = bd.getName();
        }
        return val;
    }

    public static String getString(String key,String defaultVal){
        BaseData bd = (BaseData)context.getAttribute(PREFIX+key);
        String val = null;
        if(bd != null){
            val = bd.getName();
        }
        val = val == null?defaultVal:val;
        return val;
    }
}
