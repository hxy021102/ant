package com.mobian.service.rulesengine.impl;



import com.mobian.service.rulesengine.*;
import com.mobian.service.rulesengine.model.Context;
import com.mobian.service.rulesengine.model.Rule;
import com.mobian.service.rulesengine.model.RuleSet;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
@Service(value = "ruleEngine")
public class RuleEngineImpl implements RuleEngine {
    protected Map<String, List<Rule>> ruleSetMap = new ConcurrentHashMap<String, List<Rule>>();
    protected List<RuleExecutor> ruleExecutors = null;
    protected Map<String, RuleExecutor> ruleExecutorMap = new ConcurrentHashMap<String, RuleExecutor>();


    @Override
    public void execute(Context context, String ruleSetName) {
        List<Rule> ruleSet = ruleSetMap.get(ruleSetName);
        if (ruleSet != null) {
            Vector<Rule> newSet = new Vector<Rule>(ruleSet);
            processRuleSet(context, newSet);
        }
    }

    @Override
    public void processRuleSet(Context context, Vector<Rule> newSet) {
        //如果没有后续规则，则退出
        if (newSet.size() == 0) {
            return;
        }
        Rule rule = newSet.get(0);
        RuleExecutor ruleExecutor = ruleExecutorMap.get(rule.getType());
        if (ruleExecutor != null) {
            boolean executed = ruleExecutor.execute(context, rule);
            if (executed) {
                //如果
                if (rule.isExclusive()) {
                    //如果条件成立，则是独占条件，则直接返回
                    return;
                } else if (!rule.isMultipleTimes()) {
                    //如果不是可重复执行的规则，则删除之
                    newSet.remove(0);
                }
            } else {
                //如果不匹配，则删除之
                newSet.remove(0);
            }
        } else {
            throw new RuntimeException("找不到对应" + rule.getType() + "的执行器");
        }
        processRuleSet(context, newSet);
    }

    public void addRules(RuleSet ruleSet) {
        List<Rule> rules = ruleSetMap.get(ruleSet.getName());
        if (rules == null) {
            rules = new Vector<Rule>();
            ruleSetMap.put(ruleSet.getName(), rules);
        }
        //检查规则
        for(Rule rule:ruleSet.getRules()){
            if(rule.isVaild()){
                rules.add(rule);
            }else{
                System.out.println("error id :" + rule.getId());
            }
            rule.isVaild();
        }
        Collections.sort(rules);
    }

    public void removeRules(RuleSet ruleSet) {
        List<Rule> rules = ruleSetMap.get(ruleSet.getName());
        if (rules != null) {
            rules.removeAll(ruleSet.getRules());
        }
    }

    public void setRuleExecutors(List<RuleExecutor> ruleExecutors) {
        this.ruleExecutors = ruleExecutors;
        for (RuleExecutor ruleExecutor : ruleExecutors) {
            ruleExecutorMap.put(ruleExecutor.getType(), ruleExecutor);
        }
    }

    public void addRuleExecutor(RuleExecutor ruleExecutor) {
        if (ruleExecutors == null) {
            ruleExecutors = new ArrayList<RuleExecutor>();
        }
        ruleExecutors.add(ruleExecutor);
        ruleExecutorMap.put(ruleExecutor.getType(), ruleExecutor);
    }

    public void addRuleExecutors(List<RuleExecutor> ruleExecutors) {
        if(ruleExecutors!=null){
            for(RuleExecutor ruleExecutor:ruleExecutors){
                addRuleExecutor(ruleExecutor);
            }
        }
    }

    public void removeRuleExecutors(List<RuleExecutor> ruleExecutors) {
        if(ruleExecutors!=null){
            for(RuleExecutor ruleExecutor:ruleExecutors){
                removeRuleExecutor(ruleExecutor);
            }
        }
    }

    public void removeRuleExecutor(RuleExecutor ruleExecutor) {
        if (ruleExecutors == null) {
            ruleExecutors = new ArrayList<RuleExecutor>();
        }
        ruleExecutors.remove(ruleExecutor);
        ruleExecutorMap.remove(ruleExecutor.getType());
    }
}
