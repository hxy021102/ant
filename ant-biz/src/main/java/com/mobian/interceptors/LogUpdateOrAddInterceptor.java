package com.mobian.interceptors;

import com.alibaba.fastjson.JSON;
import com.mobian.concurrent.CompletionService;
import com.mobian.concurrent.Task;
import com.mobian.pageModel.MbLogRecord;
import com.mobian.pageModel.Resource;
import com.mobian.pageModel.SessionInfo;
import com.mobian.service.MbLogRecordServiceI;
import com.mobian.service.ResourceServiceI;
import com.mobian.service.impl.CompletionFactory;
import com.mobian.util.ConfigUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by 黄晓渝 on 2017/8/18.
 */
public class LogUpdateOrAddInterceptor extends HandlerInterceptorAdapter {
    public static final String REQ_START_TIME = "reqStartTime";
    public static final String REQ_SESSION = "reqSession";
    @Autowired
    private MbLogRecordServiceI mbLogRecordService;
    @Autowired
    private ResourceServiceI resourceService;

    /**
     * 方法在请求处理之前进行调用
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        request.setAttribute(REQ_START_TIME,new Date());
        SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ConfigUtil.getSessionInfoName());

        request.setAttribute(REQ_SESSION,sessionInfo);
        return true;
    }


    /**
     * 实现处理器的后处理（但在渲染视图之前）
     *
     * @param request
     * @param response
     * @param o
     * @param mav
     * @throws Exception
     */
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o,
                           ModelAndView mav) throws Exception {
    }

    /**
     * 整个请求处理完毕回调方法，即在视图渲染完毕时回调
     * @param request
     * @param response
     * @param o
     * @param exception
     * @throws Exception
     */
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o,
                                Exception exception) throws Exception {

        Date start = (Date) request.getAttribute(REQ_START_TIME);
        SessionInfo sessionInfo = (SessionInfo)request.getAttribute(REQ_SESSION);
        final MbLogRecord mbLogRecord = new MbLogRecord();
        if (sessionInfo != null) {
            mbLogRecord.setLogUserId(sessionInfo.getId());
            mbLogRecord.setLogUserName(sessionInfo.getName());
        }
        mbLogRecord.setUrl(request.getRequestURI());
        mbLogRecord.setFormData(JSON.toJSONString(request.getParameterMap()));
        mbLogRecord.setProcessTime(new Long(new Date().getTime() - start.getTime()).intValue());

        if (exception != null) {
            mbLogRecord.setIsSuccess(false);
            mbLogRecord.setResult(exception.getMessage());
        } else {
            mbLogRecord.setIsSuccess(true);
        }

        CompletionService completionService = CompletionFactory.initCompletion();
        completionService.submit(new Task<MbLogRecord, MbLogRecord>(mbLogRecord) {
            @Override
            public MbLogRecord call() throws Exception {
                Resource resource = resourceService.getResourceByUrl(mbLogRecord.getUrl());
                if (resource != null) {
                    mbLogRecord.setUrlName(resource.getName());
                }
                mbLogRecordService.add(mbLogRecord);
                return null;
            }
        });
    }
}