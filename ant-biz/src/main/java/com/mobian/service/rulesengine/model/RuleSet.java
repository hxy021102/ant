package com.mobian.service.rulesengine.model;


import com.mobian.service.rulesengine.model.Rule;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by wanxp on 17-9-4.
 * 一组规则集
 */
public class RuleSet implements Serializable {
    /**
     * 同种名称的规则集会自动合并
     */

    private static final long serialVersionUID = 5454155825314635342L;

    private String name;

    private List<Rule> rules;

    private String type;

    private Set<String> serviceStrSet;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Rule> getRules() {
        if(rules==null){
            rules = new ArrayList<Rule>();
        }
        return rules;
    }
    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<String> getServiceStrSet() {
        return serviceStrSet;
    }

    public void setServiceStrSet(Set<String> serviceStrSet) {
        this.serviceStrSet = serviceStrSet;
    }
}
