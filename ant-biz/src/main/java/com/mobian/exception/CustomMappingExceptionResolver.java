package com.mobian.exception;

import com.alibaba.fastjson.JSON;
import com.mobian.pageModel.Json;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 统一异常处理
 * @author John
 *
 */
public class CustomMappingExceptionResolver extends
		SimpleMappingExceptionResolver {

	private AnnotationMethodHandler methodHandler;
	
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		// Expose ModelAndView for chosen error view.
		String viewName = determineViewName(ex, request);
		boolean isAjax = false;
		try {
			isAjax = methodHandler.isResponseBody(request, handler);
		} catch (ServletException e1) {
			e1.printStackTrace();
		}
		if (!(isAjax)) {
			// 如果不是异步请求
			// Apply HTTP status code for error views, if specified.
			// Only apply it if we're processing a top-level request.
			if(viewName == null)return null;
			Integer statusCode = determineStatusCode(request, viewName);
			if (statusCode != null) {
				applyStatusCodeIfPossible(request, response, statusCode);
			}
			return getModelAndView(viewName, ex, request);
		} else {// JSON格式返回
			try {
				Json j = new Json();
				if(ex instanceof ServiceException){
					ServiceException e = (ServiceException)ex;
					j.setMsg(e.getMsg());
				}else{
					j.setMsg(ex.getMessage());
				}				
				response.setCharacterEncoding("UTF-8");
				PrintWriter writer = response.getWriter();
				writer.write(JSON.toJSONString(j));
				writer.flush();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
	}

	public void setMethodHandler(AnnotationMethodHandler methodHandler) {
		this.methodHandler = methodHandler;
	}
	
	
	public boolean validRequest(HttpServletRequest request){
		return request.getHeader("accept").indexOf("application/json") > -1 || (request  
                .getHeader("X-Requested-With")!= null && request  
                .getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1);
	}
	
}
