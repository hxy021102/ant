package com.mobian.service.impl;

import com.mobian.concurrent.CompletionService;
import com.mobian.concurrent.ExecutorCompletionServiceImpl;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.Executor;

/**
 * Created by john on 16/8/13.
 */
@Service
public class CompletionFactory implements InitializingBean {

    @Resource
    private Executor taskExecutor;

    private static Executor executor;

    public static CompletionService initCompletion(){
        return new ExecutorCompletionServiceImpl(executor);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        executor = taskExecutor;
    }
}
