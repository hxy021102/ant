package com.mobian.service.rulesengine.impl;

import com.mobian.absx.F;
import com.mobian.service.rulesengine.RedisRuleSetService;
import com.mobian.service.rulesengine.RuleSetService;
import com.mobian.service.rulesengine.model.Context;
import com.mobian.service.rulesengine.model.MvelContext;
import com.mobian.service.rulesengine.model.Rule;
import com.mobian.service.rulesengine.model.RuleSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;

/**
 * Created by wanxp on 17-9-6.
 */
@Service(value = "mbRuleEngine")
public class MbRuleEngineImpl extends RuleEngineImpl{

    @Autowired
    RuleExecutorImpl ruleExecutor;

    @Autowired
    private RedisRuleSetService redisRuleSetService;

    public  void init(){

    }
    public void execute(Object paramsObj, String type, String refId) {
        addRuleExecutor(ruleExecutor);
        MvelContext mvelContext = new MvelContext();
        String paramStr = paramsObj.getClass().getName();
        String[] paramStrArr = paramStr.split("\\.");
        paramStr = paramStrArr[paramStrArr.length-1];
        mvelContext.put(paramStr.substring(0,1).toLowerCase() + paramStr.substring(1), paramsObj);
        executeType(mvelContext, type, refId);
    }
    public void executeType(Context context, String type, String refId) {
        MvelContext mvelContext = new MvelContext(context);
        WebApplicationContext webContext = ContextLoader.getCurrentWebApplicationContext();
        List<RuleSet> ruleSetList = redisRuleSetService.listRuleSet(type, refId);
        Iterator<RuleSet> ruleSetIterator = ruleSetList.iterator();
        while (ruleSetIterator.hasNext()) {
            RuleSet ruleSet = ruleSetIterator.next();
            if (ruleSet != null) {
                Vector<Rule> newSet = new Vector<Rule>(ruleSet.getRules());

                //获取service集,并存入容器context中,使动态代码能获取到
                Set<String> serviceStrSet = ruleSet.getServiceStrSet();
                for (String s : serviceStrSet) {
                    if (F.empty(s)) continue;
                    Object object = webContext.getBean(s + "Impl");
                    mvelContext.put(s,object);
                }
                processRuleSet(mvelContext, newSet);
            }
       }
    }
}
