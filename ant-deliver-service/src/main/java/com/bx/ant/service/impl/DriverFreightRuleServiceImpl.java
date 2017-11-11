package com.bx.ant.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bx.ant.dao.DriverFreightRuleDaoI;
import com.bx.ant.model.TdriverFreightRule;
import com.bx.ant.pageModel.DriverFreightRule;
import com.bx.ant.service.DriverFreightRuleServiceI;
import com.mobian.absx.F;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mobian.util.MyBeanUtils;

@Service
public class DriverFreightRuleServiceImpl extends BaseServiceImpl<DriverFreightRule> implements DriverFreightRuleServiceI {

	@Autowired
	private DriverFreightRuleDaoI driverFreightRuleDao;

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
			if (!F.empty(driverFreightRule.getDistanceLower())) {
				whereHql += " and t.distanceLower = :distanceLower";
				params.put("distanceLower", driverFreightRule.getDistanceLower());
			}		
			if (!F.empty(driverFreightRule.getDistanceUpper())) {
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
			MyBeanUtils.copyProperties(driverFreightRule, t, new String[] { "id" , "addtime", "isdeleted","updatetime" },true);
		}
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		driverFreightRuleDao.executeHql("update TdriverFreightRule t set t.isdeleted = 1 where t.id = :id",params);
		//driverFreightRuleDao.delete(driverFreightRuleDao.get(TdriverFreightRule.class, id));
	}

}
