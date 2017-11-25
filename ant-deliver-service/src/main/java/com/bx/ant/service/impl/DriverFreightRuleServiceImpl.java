package com.bx.ant.service.impl;

import java.lang.reflect.Array;
import java.util.*;

import com.bx.ant.dao.DriverFreightRuleDaoI;
import com.bx.ant.model.TdriverFreightRule;
import com.bx.ant.pageModel.DeliverOrder;
import com.bx.ant.pageModel.DeliverOrderShop;
import com.bx.ant.pageModel.DriverFreightRule;
import com.bx.ant.pageModel.DriverFreightRuleQuery;
import com.bx.ant.service.DeliverOrderServiceI;
import com.bx.ant.service.DriverFreightRuleServiceI;
import com.mobian.absx.F;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.DiveRegion;
import com.mobian.pageModel.MbShop;
import com.mobian.pageModel.PageHelper;

import com.mobian.service.DiveRegionServiceI;
import com.mobian.service.MbShopServiceI;
import com.mobian.util.ConvertNameUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mobian.util.MyBeanUtils;

import javax.annotation.Resource;

import static com.bx.ant.service.DeliverOrderState.deliverOrder;

@Service
public class DriverFreightRuleServiceImpl extends BaseServiceImpl<DriverFreightRule> implements DriverFreightRuleServiceI {

    @Autowired
    private DriverFreightRuleDaoI driverFreightRuleDao;

    @Resource
    private DiveRegionServiceI diveRegionService;

    @Resource
    private DeliverOrderServiceI deliverOrderService;

    @Resource
    private MbShopServiceI mbShopService;

    @Override
    public DataGrid dataGrid(DriverFreightRule driverFreightRule, PageHelper ph) {
        List<DriverFreightRule> ol = new ArrayList<DriverFreightRule>();
        String hql = " from TdriverFreightRule t ";
        DataGrid dg = dataGridQuery(hql, ph, driverFreightRule, driverFreightRuleDao);
        @SuppressWarnings("unchecked")
        List<TdriverFreightRule> l = dg.getRows();
        if (l != null && l.size() > 0) {
            for (TdriverFreightRule t : l) {
                DriverFreightRule o = new DriverFreightRule();
                BeanUtils.copyProperties(t, o);
                ol.add(o);
            }
        }
        dg.setRows(ol);
        return dg;
    }


    protected String whereHql(DriverFreightRule driverFreightRule, Map<String, Object> params) {
        String whereHql = "";
        if (driverFreightRule != null) {
            whereHql += " where t.isdeleted = 0 ";
            if (!F.empty(driverFreightRule.getTenantId())) {
                whereHql += " and t.tenantId = :tenantId";
                params.put("tenantId", driverFreightRule.getTenantId());
            }
            if (!F.empty(driverFreightRule.getIsdeleted())) {
                whereHql += " and t.isdeleted = :isdeleted";
                params.put("isdeleted", driverFreightRule.getIsdeleted());
            }
            if (!F.empty(driverFreightRule.getWeightLower())) {
                whereHql += " and t.weightLower = :weightLower";
                params.put("weightLower", driverFreightRule.getWeightLower());
            }
            if (!F.empty(driverFreightRule.getWeightUpper())) {
                whereHql += " and t.weightUpper = :weightUpper";
                params.put("weightUpper", driverFreightRule.getWeightUpper());
            }
            if (driverFreightRule.getDistanceLower() != null) {
                whereHql += " and t.distanceLower = :distanceLower";
                params.put("distanceLower", driverFreightRule.getDistanceLower());
            }
            if (driverFreightRule.getDistanceUpper() != null) {
                whereHql += " and t.distanceUpper = :distanceUpper";
                params.put("distanceUpper", driverFreightRule.getDistanceUpper());
            }
            if (!F.empty(driverFreightRule.getRegionId())) {
                whereHql += " and t.regionId = :regionId";
                params.put("regionId", driverFreightRule.getRegionId());
            }
            if (!F.empty(driverFreightRule.getFreight())) {
                whereHql += " and t.freight = :freight";
                params.put("freight", driverFreightRule.getFreight());
            }
            if (!F.empty(driverFreightRule.getLoginId())) {
                whereHql += " and t.loginId = :loginId";
                params.put("loginId", driverFreightRule.getLoginId());
            }
            if (!F.empty(driverFreightRule.getType())) {
                whereHql += " and t.type = :type";
                params.put("type", driverFreightRule.getType());
            }
            if (driverFreightRule instanceof DriverFreightRuleQuery) {
                DriverFreightRuleQuery driverFreightRuleQuery = (DriverFreightRuleQuery) driverFreightRule;
                if (!F.empty(driverFreightRuleQuery.getWeight())) {
                    whereHql += " and t.weightLower <= :weight and t.weightUpper > :weight";
                    params.put("weight", driverFreightRuleQuery.getWeight());
                }
                if (!F.empty(driverFreightRuleQuery.getWeight())) {
                    whereHql += " and t.distanceLower <= :distance and t.distanceUpper > :distance";
                    params.put("distance", driverFreightRuleQuery.getDistance());
                }
            }
        }
        return whereHql;
    }

    @Override
    public void add(DriverFreightRule driverFreightRule) {
        TdriverFreightRule t = new TdriverFreightRule();
        BeanUtils.copyProperties(driverFreightRule, t);
        t.setIsdeleted(false);
        driverFreightRuleDao.save(t);
    }

    @Override
    public DriverFreightRule get(Integer id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        TdriverFreightRule t = driverFreightRuleDao.get("from TdriverFreightRule t  where t.id = :id", params);
        DriverFreightRule o = new DriverFreightRule();
        BeanUtils.copyProperties(t, o);
        return o;
    }

    @Override
    public void edit(DriverFreightRule driverFreightRule) {
        TdriverFreightRule t = driverFreightRuleDao.get(TdriverFreightRule.class, driverFreightRule.getId());
        if (t != null) {
            MyBeanUtils.copyProperties(driverFreightRule, t, new String[]{"id", "addtime", "isdeleted", "updatetime"}, true);
        }
    }

    @Override
    public void delete(Integer id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        driverFreightRuleDao.executeHql("update TdriverFreightRule t set t.isdeleted = 1 where t.id = :id", params);
        //driverFreightRuleDao.delete(driverFreightRuleDao.get(TdriverFreightRule.class, id));
    }

    @Override
    public Integer getOrderFreight(DeliverOrderShop orderShop, String type) {
        Integer amount = null;
        DriverFreightRuleQuery query = new DriverFreightRuleQuery();
        if ((!F.empty(orderShop.getDeliverOrderId()))) {
            DeliverOrder order = deliverOrderService.get(orderShop.getDeliverOrderId());
            MbShop shop = mbShopService.getFromCache(orderShop.getShopId());
            if (order != null && shop != null) {
                //通过重量距离区域和类型找出最小区域的运费规则并返回
                query.setWeight(order.getWeight());
                query.setDistance(orderShop.getDistance());
                query.setRegionId(shop.getRegionId());
                query.setType(type);
                List<DriverFreightRule> driverFreightRules = getRuleList(query);
                if (CollectionUtils.isNotEmpty(driverFreightRules)) {
                    DriverFreightRule rule = driverFreightRules.get(0);
                    amount = rule.getFreight();
                }
                //若无法获取运费规则,计算运费规则为
                //起始运费(分) + 重量(千克) * 运费因子(分/千克)
                if (amount == null) {
                    amount = Integer.parseInt(ConvertNameUtil.getString("DDSV105", "1000")) +
                            order.getWeight() / 1000 * Integer.parseInt(ConvertNameUtil.getString("DDSV106", "50"));
                }
            }
        }
        return amount;
    }

    @Override
    public List<DriverFreightRule> getRuleList(DriverFreightRuleQuery ruleQuery) {
        List<DriverFreightRule> ol = new ArrayList<DriverFreightRule>();
        if (!F.empty(ruleQuery.getWeight()) && ruleQuery.getDistance() != null && !F.empty(ruleQuery.getRegionId())
                && !F.empty(ruleQuery.getType())) {
            PageHelper ph = new PageHelper();
            ph.setHiddenTotal(true);
            DriverFreightRuleQuery query = new DriverFreightRuleQuery();

            query.setWeight(ruleQuery.getWeight());
            query.setType(ruleQuery.getType());
            query.setDistance(ruleQuery.getDistance());

            DataGrid dataGrid = dataGrid(query, ph);
            List<DriverFreightRule> l = dataGrid.getRows();
            if (CollectionUtils.isNotEmpty(l)) {
                for (DriverFreightRule d : l) {
                    if (diveRegionService.isParent(d.getRegionId().toString(), ruleQuery.getRegionId().toString())) {
                        ol.add(d);
                    }
                }
            }
            //对符合的区域进行由小到大的排序
            Collections.sort(ol, new Comparator<DriverFreightRule>() {
                @Override
                public int compare(DriverFreightRule t1, DriverFreightRule t2) {
                    DiveRegion d1 = diveRegionService.getFromCache(t1.getRegionId() + "");
                    DiveRegion d2 = diveRegionService.getFromCache(t2.getRegionId() + "");
                    if (d1 == null || d2 == null) return 0;
                    else {
                        return d1.getRegionLevel() - d2.getRegionLevel();
                    }
                }
            });
        }
        return ol;
    }
}
