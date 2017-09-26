package com.mobian.util;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;

/**
 * Created by john on 17/9/26.
 */
public abstract class BeanUtil {


    private static ServletContext context;

    public static void setContext(ServletContext context) {
        BeanUtil.context = context;
    }

    public static <T> T getBean(Class<T> requiredType){
        ApplicationContext app = WebApplicationContextUtils.getWebApplicationContext(context);
        return app.getBean(requiredType);
    }
}
