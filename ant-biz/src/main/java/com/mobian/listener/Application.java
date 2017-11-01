package com.mobian.listener;

import com.mobian.interceptors.TokenManage;
import com.mobian.pageModel.BaseData;
import com.mobian.service.BasedataServiceI;
import com.mobian.util.*;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Map;

/**
 * 系统全局容器
 * @author John
 *
 */
public class Application implements ServletContextListener {
	private static ServletContext context;
	private static String PREFIX = "SV.";
	@Override
	public void contextInitialized(ServletContextEvent event) {
		context = event.getServletContext();
		BeanUtil.setContext(context);
		initAppVariable();

		// 启动刷新微信access_token
		com.mobian.thirdpart.wx.AccessTokenInstance.getInstance();
		// 启动刷新有赞access_token
		if("2".equals(ConvertNameUtil.getString(Constants.SYSTEM_PUBLISH_SETTING)))
			com.mobian.thirdpart.youzan.AccessTokenInstance.getInstance();
		FileUtil.updateCommonJs();
	}

	private static void initAppVariable(){
		ApplicationContext app = WebApplicationContextUtils.getWebApplicationContext(context); 
		BasedataServiceI service = app.getBean(BasedataServiceI.class);
		Map<String,BaseData> map = service.getAppVariable();
		for(String key : map.keySet()){
			context.setAttribute(PREFIX+key, map.get(key));
		}


		ConvertNameUtil.setConfigTransfer(new ConfigTransfer() {
			@Override
			public String getString(String key) {
				BaseData bd = (BaseData)context.getAttribute(PREFIX+key);
				String val = null;
				if(bd != null){
					val = bd.getName();
				}
				return val;
			}

			@Override
			public String getDesc(String key) {
				BaseData bd = (BaseData)context.getAttribute(PREFIX+key);
				String val = null;
				if(bd != null){
					val = bd.getDescription();
				}
				return val;
			}
		});
		TokenManage.DEFAULT_TOKEN = ConvertNameUtil.getString("SV010", "89");
	}
	
	/**
	 * 刷新全局变量值
	 */
	public static void refresh(){
		initAppVariable();
	}
	
	/**
	 * 获取全局变量值
	 * @param key
	 * @return
	 *//*
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
	}*/
	
	/**
	 * 获取全局变量值
	 * @param key
	 * @return
	 */
	public static BaseData get(String key){
		BaseData bd = (BaseData)context.getAttribute(PREFIX+key);		
		return bd;
	}
	public static BasedataServiceI getBasedataService(){
		ApplicationContext app = WebApplicationContextUtils.getWebApplicationContext(context); 
		BasedataServiceI service = app.getBean(BasedataServiceI.class);
		return service;
	}
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		

	}
}
