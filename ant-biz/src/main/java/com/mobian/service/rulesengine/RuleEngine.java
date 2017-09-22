package com.mobian.service.rulesengine;

import com.mobian.service.rulesengine.model.Context;
import com.mobian.service.rulesengine.model.Rule;
import com.mobian.service.rulesengine.model.RuleSet;
import org.mvel2.ParserContext;

import java.util.List;
import java.util.Vector;

/**
 * Created by wanxp on 17-9-2.
 */
public interface RuleEngine {

    /**
     * 初始化规则
     */

    /**
     * 规则引擎处理器
     * @param context
     * @param newSet
     */
    void processRuleSet(Context context, Vector<Rule> newSet);

    /**
     * 对指定上下文执行指定类型的规则
     ** @param context
     * @param ruleSetName
     */
    void execute(Context context, String ruleSetName);

    /**
     * 添加一组规则
     *
     * @param ruleSet
     */
    void addRules(RuleSet ruleSet);

    /**
     * 删除一组规则
     *
     * @param ruleSet
     */
    void removeRules(RuleSet ruleSet);

    /**
     * 添加规则执行器列表
     *
     * @param ruleExecutors
     */
    void addRuleExecutors(List<RuleExecutor> ruleExecutors);

    /**
     * 添加一个规则执行器
     *
     * @param ruleExecutor
     */
    void addRuleExecutor(RuleExecutor ruleExecutor);

    /**
     * 删除规则执行器列表
     *
     * @param ruleExecutors
     */
    void removeRuleExecutors(List<RuleExecutor> ruleExecutors);

    /**
     * 删除一个规则执行器
     *
     * @param ruleExecutor
     */
    void removeRuleExecutor(RuleExecutor ruleExecutor);

    /**
     * 设置一批规则执行器
     * @param ruleExecutors
     */
    void setRuleExecutors(List<RuleExecutor> ruleExecutors);
}
