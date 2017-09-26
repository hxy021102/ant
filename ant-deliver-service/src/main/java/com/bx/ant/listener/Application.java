package com.bx.ant.listener;

import com.mobian.pageModel.BaseData;
import com.mobian.service.BasedataServiceI;
import com.mobian.util.BeanUtil;
import com.mobian.util.ConfigTransfer;
import com.mobian.util.ConvertNameUtil;
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

	}

	private static void initAppVariable(){
		ApplicationContext app = WebApplicationContextUtils.getWebApplicationContext(context); 
		BasedataServiceI service = app.getBean(BasedataServiceI.class,"basedataService");
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
		});
	}

	/**
	 * 刷新全局变量值
	 */
	public static void refresh(){
		initAppVariable();
	}
	

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		

	}
}
