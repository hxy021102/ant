package com.mobian.service.rulesengine.impl;

import com.alibaba.fastjson.JSONObject;
import com.mobian.absx.F;
import com.mobian.pageModel.*;
import com.mobian.service.MbActivityActionServiceI;
import com.mobian.service.MbActivityRuleServiceI;
import com.mobian.service.MbActivityServiceI;
import com.mobian.service.rulesengine.RedisRuleSetService;
import com.mobian.service.rulesengine.model.Rule;
import com.mobian.service.rulesengine.model.RuleSet;
import com.mobian.thirdpart.redis.Key;
import com.mobian.thirdpart.redis.Namespace;
import com.mobian.thirdpart.redis.RedisUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wanxp on 17-9-7.
 */
@Service
public class RedisRuleSetServiceImpl implements RedisRuleSetService {
    @Resource
    private MbActivityServiceI activityService;
    @Resource
    private MbActivityActionServiceI activityActionService;
    @Resource
    private MbActivityRuleServiceI activityRuleService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private MbActivityServiceI mbActivityService;

    @Autowired
    private MbActivityRuleServiceI mbActivityRuleService;

    @Autowired
    private MbActivityActionServiceI mbActivityActionService;

    /**
     * 将活动表转变为一个规则引擎支持的RuleSet
     *
     * @param mbActivity
     * @return
     */
    @Override
    public RuleSet buildRuleSetByAtivity(MbActivity mbActivity) {
        RuleSet ruleSet = new RuleSet();
        Set<String> serviceStrSet = new HashSet<String>();
        MbActivityRule mbActivityrule = new MbActivityRule();
        mbActivityrule.setActivityId(mbActivity.getId());
        List<MbActivityRule> activityRules = activityRuleService.dataGrid(mbActivityrule, new PageHelper()).getRows();

        Collections.sort(activityRules, new Comparator<MbActivityRule>() {
            @Override
            public int compare(MbActivityRule o1, MbActivityRule o2) {
                if (o1.getSeq() == null && o2.getSeq() == null) return 0;
                if (o1.getSeq() == null && o2.getSeq() != null) return 1;
                if (o1.getSeq() != null && o2.getSeq() == null) return -1;
                return o1.getSeq() - o2.getSeq();
            }
        });
        List<Rule> rules = new ArrayList<>();
        //遍历活动对应的规则
        if ("CS001".equals(mbActivity.getLanguageType())) {
            for (MbActivityRule activityRule : activityRules
                    ) {
                Rule rule = new Rule();
                MbActivityAction activityAction = new MbActivityAction();
                activityAction.setActivityRuleId(activityRule.getId());
                List<MbActivityAction> activityActions = activityActionService.query(activityAction);
                //遍历行动列表前对行动列表进行排序
                Collections.sort(activityActions, new Comparator<MbActivityAction>() {
                    @Override
                    public int compare(MbActivityAction o1, MbActivityAction o2) {
                        if (o1.getSeq() == null && o2.getSeq() == null) return 0;
                        if (o1.getSeq() == null && o2.getSeq() != null) return 1;
                        if (o1.getSeq() != null && o2.getSeq() == null) return -1;
                        return o1.getSeq() - o2.getSeq();
                    }
                });

                if (F.empty(activityRule.getLeftValue())) activityRule.setLeftValue("");
                String action = "";
                //遍历规则对应的行动并转化为MVEL命令的 String
                for (MbActivityAction a : activityActions
                        ) {
                    //action type 为 -1 说明parameter1为纯java动态代码
                    //非-1则说明是actionType包含的是service和方法,如MbOrder.add,而parameter1为参数列表,以逗号分隔开
                    if ("-1".equals(a.getActionType())) {
                        action += a.getParameter1();
                    } else {
                        action +=  a.getActionType().replace("parameter1",a.getParameter1())
                                .replace("parameter2",a.getParameter2()) + ";";
                    }
                }
                rule.setId(activityRule.getId());
                rule.setAction(action);
                rule.setCondition(activityRule.getLeftValue());
                rule.setType(mbActivity.getLanguageTypeName());
                rule.setMultipleTimes(false);
                rule.setExclusive(false);
                rule.setVaild(mbActivity.getValid());

                serviceStrSet = getServiceStrSetFromStr(action);
                if (CollectionUtils.isEmpty(serviceStrSet)) serviceStrSet = getServiceStrSetFromStr(rule.getCondition());
                else serviceStrSet.addAll(getServiceStrSetFromStr(rule.getCondition()));

                rules.add(rule);
            }
            //将rule(包含规则activityRule和activityAction)的列表放置至ruleSet
            ruleSet.setName(mbActivity.getId().toString());
            ruleSet.setRules(rules);
            ruleSet.setType(mbActivity.getType());
            ruleSet.setServiceStrSet(serviceStrSet);
        }
        return ruleSet;
    }

    @Override
    public boolean add(RuleSet ruleSet) {
        redisUtil.set(buildKey(ruleSet), JSONObject.toJSONString(ruleSet));
        return true;
    }

    @Override
    public boolean delete(RuleSet ruleSet) {
        redisUtil.delete(buildKey(ruleSet));
        return true;
    }

    @Override
    public boolean reflash(RuleSet ruleSet) {
        delete(ruleSet);
        add(ruleSet);
        return true;
    }

    @Override
    public boolean deleteByKey(String name) {
        redisUtil.delete(buildKeyByName(name));
        return true;
    }


    @Override
    public boolean edit(RuleSet ruleSet) {

        return false;
    }

    @Override
    public RuleSet get(String name) {
        String ruleSetStr = (String) redisUtil.getString(name);
        RuleSet ruleSet = new RuleSet();
        if (ruleSetStr != null) ruleSet = JSONObject.parseObject(ruleSetStr, RuleSet.class);
        return ruleSet;
    }


    @Override
    public boolean addAll() {

        return false;
    }

    @Override
    public boolean updateAll() {
        return false;
    }

    @Override
    public String buildKey(RuleSet ruleSet) {
        return "ruleSetName" + ruleSet.getName();
    }

    @Override
    public String buildKeyByName(String name) {
        return "ruleSetName" + name;
    }


    @Override
    public String buildRuleSetListKey(String type, String refId) {
        if (type == null && refId == null) {
            throw new SecurityException("获取Redis中RuleSetList数据的键值为空");
        }
        type = type == null ? "" : type;
        refId = refId == null ? "" : refId;
        return Key.build(Namespace.ACTIVITY_TYPE_REFID,type + refId);
    }
    @Override
    public void deleteRuleSetListByActivityId(Integer activityId) {
        MbActivity activity = mbActivityService.get(activityId);
        if (activity != null) {
            deleteRuleSetList(activity.getType(), activity.getRefId());
        }
    }
    @Override
    public void deleteRuleSetListByActivityRuleId(Integer activityRuleId) {
        MbActivity activity = mbActivityService.getByActivityRuleId(activityRuleId);
        if (activity != null) {
            deleteRuleSetList(activity.getType(), activity.getRefId());
        }
    }
    @Override
    public void deleteRuleSetListByActivityActionId(Integer activityActionId) {
        MbActivity activity = mbActivityService.getByActivityActionId(activityActionId);
        if (activity != null) {
            deleteRuleSetList(activity.getType(), activity.getRefId());
        }
    }

    @Override
    public void deleteRuleSetList(String type, String refId) {
        redisUtil.delete(buildRuleSetListKey(type, refId));
    }


    @Override
    public boolean addAllRuleSetByActivityTable() {
        System.out.println(activityService);
        MbActivity activity = new MbActivity();
        DataGrid dataGrid = activityService.dataGrid(activity, new PageHelper());
        List<MbActivity> activities = dataGrid.getRows();
        Map<String, ArrayList<String>> ruleSetTypeMap = new HashMap<String, ArrayList<String>>();
        Iterator<MbActivity> activityIterator = activities.iterator();
        while (activityIterator.hasNext()) {
            RuleSet ruleSet = buildRuleSetByAtivity(activityIterator.next());
            if (ruleSet == null) continue;
            String type = ruleSet.getType();
            ruleSet.setType(type == null ? "normal" : type);
            add(ruleSet);
            if (ruleSetTypeMap.containsKey(type)) {
                ruleSetTypeMap.get(type).add(buildKey(ruleSet));
            } else {
                ArrayList<String> arrayList = new ArrayList<String>();
                arrayList.add(buildKey(ruleSet));
                ruleSetTypeMap.put(type, arrayList);
            }
        }
        for (Map.Entry<String, ArrayList<String>> entry : ruleSetTypeMap.entrySet()) {
            redisUtil.set(entry.getKey(), JSONObject.toJSONString(entry.getValue()));
        }
        return true;
    }


    @Override
    public List<RuleSet> buildAndAddRuleSetList(String type, String refId) {
        List<RuleSet> ol = new ArrayList<RuleSet>();
        MbActivity activity = new MbActivity();
        activity.setType(type);
        activity.setRefId(refId);
        List<MbActivity> activities = mbActivityService.dataGrid(activity,new PageHelper()).getRows();
        if (CollectionUtils.isNotEmpty(activities)) {
            for (MbActivity a : activities) {
                RuleSet ruleSet = buildRuleSetByAtivity(a);
                ol.add(ruleSet);
            }
        }
        redisUtil.set(buildRuleSetListKey(type , refId),JSONObject.toJSONString(ol));
        return ol;
    }

    @Override
    public List<RuleSet> listRuleSet(String type, String refId){
        List<RuleSet> ol = new ArrayList<RuleSet>();
        //通过type找到redis存储的ruleSetType-ruleSetNameList
        String listStr = (String) redisUtil.get(buildRuleSetListKey(type, refId));
        ol = JSONObject.parseObject(listStr, ArrayList.class);
        if (CollectionUtils.isEmpty(ol)) {
            ol = buildAndAddRuleSetList(type, refId);
        }
        return ol;
    }

    @Override
    public Set<String> getServiceStrSetFromStr(String codeStr) {
        Set<String> serviceStrSet = new HashSet<String>();
        String parrtenStr = "\\w*Service\\w*\\.";
        Pattern pattern = Pattern.compile(parrtenStr);
        Matcher matcher = pattern.matcher(codeStr);
        while (matcher.find()) {
            serviceStrSet.add(matcher.group(0).substring(0, matcher.group().length() - 1));
        }
        return serviceStrSet;
    }

}
