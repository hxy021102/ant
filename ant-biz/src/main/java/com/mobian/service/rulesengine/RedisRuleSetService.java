package com.mobian.service.rulesengine;

import com.mobian.pageModel.MbActivity;
import com.mobian.pageModel.MbActivityAction;
import com.mobian.pageModel.MbActivityRule;
import com.mobian.service.rulesengine.model.RuleSet;

import java.util.List;
import java.util.Set;

/**
 * Created by wanxp on 17-9-7.
 */
public interface RedisRuleSetService {
    /**
     * 数据库中的规则组合成规则引擎中的规则
     *
     * @param mbActivity
     * @return
     */
    RuleSet buildRuleSetByAtivity(MbActivity mbActivity);

    /**
     * 将RuleSet添加至Redis
     *
     * @param ruleSet
     * @return
     */
    boolean add(RuleSet ruleSet);

    /**
     * 删除RuleSet
     *
     * @param ruleSet
     * @return
     */
    boolean delete(RuleSet ruleSet);

    /**
     * 通过新的ruleSet刷新旧的ruleSet
     *
     * @param ruleSet
     * @return
     */
    boolean reflash(RuleSet ruleSet);

    /**
     * 通过ruleSet.name 删除ruleSet
     *
     * @param name
     * @return
     */
    boolean deleteByKey(String name);

    /**
     * 编辑RuleSet
     *
     * @param ruleSet
     * @return
     */
    boolean edit(RuleSet ruleSet);

    /**
     * 获取RuleSet
     *
     * @param name
     * @return
     */
    RuleSet get(String name);


    /**
     * 将所有RuleSet添加至Redis
     *
     * @return
     */
    boolean addAll();

    /**
     * 将所有RuleSet更新至Redis
     *
     * @return
     */
    boolean updateAll();

    /**
     * 通过ruleSet建立redis上ruleSetName-list<ruleSetName>键值
     *
     * @param ruleSet
     * @return
     */
    String buildKey(RuleSet ruleSet);

    /**
     * 通过ruleSet名称建立redis上ruleSetName-list<ruleSetName>键值
     *
     * @param name
     * @return
     */
    String buildKeyByName(String name);

    String buildRuleSetListKey(String type, String refId);

    void deleteRuleSetListByActivityId(Integer activityId);

    void deleteRuleSetListByActivityRuleId(Integer activityRuleId);

    void deleteRuleSetListByActivityActionId(Integer activityActionId);

    void deleteRuleSetList(String type, String refId);

    /**
     * 通过mySQL表上的所有活动表的数据建立Redis缓存
     *
     * @return
     */
    boolean addAllRuleSetByActivityTable();


    /**
     * 通过ruleSetType建立redis上的ruleSetTypeName-list<ruleSetName>键值
     *
     * @param type
     * @return
     */

    List<RuleSet> buildAndAddRuleSetList(String type, String refId);

    List<RuleSet> listRuleSet(String type, String refId);

    /**
     * 通过代码片段找出service并建立service集
     *
     * @param codeStr
     * @return
     */
    Set<String> getServiceStrSetFromStr(String codeStr);
}
