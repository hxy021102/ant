package com.mobian.util;

import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;

public class MyMultipartResolver extends CommonsMultipartResolver {

	@Override
	public boolean isMultipart(HttpServletRequest request) {
		/**
		 * 解决kindeditor图片上传失败问题
		 */
		if(request.getRequestURI().contains("/fileController/upload")) {
            return false;
        }
		return super.isMultipart(request);
	}
	
}
