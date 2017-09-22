package com.mobian.service.rulesengine;

import com.mobian.service.rulesengine.model.RuleSet;

import java.util.List;

/**
 * Created by wanxp on 17-9-7.
 */
public interface RuleSetService {
    /**
     * 通过名称获取RuleSet
     * @param name
     * @return
     */
    RuleSet get(String name);

    /**
     * 通过类型获得同类型的RuleSet
     * @param type
     * @return
     */
    List<RuleSet> listRuleSetByType(String type);
}
