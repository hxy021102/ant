package com.mobian.util;

import java.io.UnsupportedEncodingException;
import java.util.ResourceBundle;

/**
 * 项目参数工具类
 * 
 * @author John
 * 
 */
public class ConfigUtil {

	private static final ResourceBundle bundle = ResourceBundle.getBundle("config");

	/**
	 * 获得sessionInfo名字
	 * 
	 * @return
	 */
	public static final String getSessionInfoName() {
		return bundle.getString("sessionInfoName");
	}

	/**
	 * 通过键获取值
	 * 
	 * @param key
	 * @return
	 */
	public static final String get(String key) {
		return bundle.getString(key);
	}

	public static final String getChinese(String key) {
		String value = null;
		try {
			value = new String(bundle.getString(key).getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return value;
	}
}
