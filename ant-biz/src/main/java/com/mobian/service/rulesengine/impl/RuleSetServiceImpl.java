package com.mobian.service.rulesengine.impl;

import com.alibaba.fastjson.JSONObject;
import com.mobian.service.rulesengine.RuleSetService;
import com.mobian.service.rulesengine.model.RuleSet;
import com.mobian.thirdpart.redis.RedisUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanxp on 17-9-7.
 */
@Service("ruleSetService")
public class RuleSetServiceImpl implements RuleSetService {

    @Autowired
    RedisUtil redisUtil;
    @Autowired
    RedisRuleSetServiceImpl redisRuleSetService;

    /**
     * 通过名称获取RuleSet
     * @param name
     * @return
     */
    public RuleSet get(String name){
        return redisRuleSetService.get(name);
    }

    /**
     * 通过类型获得同类型的RuleSet
     * @param type
     * @return
     */
    public List<RuleSet> listRuleSetByType(String type){
        return null;
    }
}
