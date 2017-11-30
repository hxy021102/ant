package com.bx.ant.pageModel;

import java.math.BigDecimal;

/**
 * Created by w9777 on 2017/11/13.
 */
public class DriverFreightRuleQuery extends DriverFreightRule {
    private Integer weight;
    private BigDecimal distance;

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public BigDecimal getDistance() {
        return distance;
    }

    public void setDistance(BigDecimal distance) {
        this.distance = distance;
    }
}
