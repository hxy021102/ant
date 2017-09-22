package com.mobian.service.rulesengine.model;

import com.mobian.absx.F;

import java.io.Serializable;

/**
 * Created by wanxp on 17-9-2.
 * 规则
 */
public class Rule implements Comparable<Rule>,Serializable {

    private static final long serialVersionUID = 5454155825314635342L;
    private Integer id;
    private String type;
    private boolean isExclusive;
    private boolean isMultipleTimes;
    private boolean isVaild;
    private Integer seq;
    @Override
    public int compareTo(Rule o) {
        if ((o == null || o.getSeq() == null) && this.seq == null) return 0;
        if (o == null || o.getSeq() == null) return 1;
        if (this.seq == null) return -1;
        return this.seq - o.getSeq();
    }
    @Override
    public boolean equals(Object object) {
        if (object == null) return false;
        Rule rule = (Rule) object;
        return this.id == rule.getId();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isExclusive() {
        return isExclusive;
    }

    public void setExclusive(boolean exclusive) {
        isExclusive = exclusive;
    }

    public boolean isMultipleTimes() {
        return isMultipleTimes;
    }

    public void setMultipleTimes(boolean multipleTimes) {
        isMultipleTimes = multipleTimes;
    }


    public void setVaild(boolean vaild) {
        isVaild = vaild;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private String condition;
    private String action;

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "MvelRule{" +
                "condition='" + condition + '\'' +
                ", action='" + action + '\'' +
                '}';
    }

    /**
     * 验证是否复合规格
     * @return
     */
    public boolean isVaild(){
//        if(F.empty(getCondition())) {
//            throw new RuntimeException(String.format("规则[%s]的匹配条件为空",1));
//        }
//        if(F.empty(getAction())) {
//            throw new RuntimeException(String.format("规则[%s]的匹配条件为空",1));
//        }
        return true;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }
}
