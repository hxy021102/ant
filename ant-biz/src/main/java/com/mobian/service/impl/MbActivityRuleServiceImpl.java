package com.mobian.service.impl;

import com.mobian.absx.F;
import com.mobian.dao.MbActivityRuleDaoI;
import com.mobian.model.TmbActivityRule;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbActivityAction;
import com.mobian.pageModel.MbActivityRule;
import com.mobian.pageModel.PageHelper;
import com.mobian.service.MbActivityRuleServiceI;
import com.mobian.service.rulesengine.RedisRuleSetService;
import com.mobian.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MbActivityRuleServiceImpl extends BaseServiceImpl<MbActivityRule> implements MbActivityRuleServiceI {

	@Autowired
	private MbActivityRuleDaoI mbActivityRuleDao;
	@Autowired
	private MbActivityActionServiceImpl mbActivityActionService;
	@Autowired
	private RedisRuleSetService redisRuleSetService;
	@Override
	public DataGrid dataGrid(MbActivityRule mbActivityRule, PageHelper ph) {
		List<MbActivityRule> ol = new ArrayList<MbActivityRule>();
		String hql = " from TmbActivityRule t ";
		DataGrid dg = dataGridQuery(hql, ph, mbActivityRule, mbActivityRuleDao);
		@SuppressWarnings("unchecked")
		List<TmbActivityRule> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TmbActivityRule t : l) {
				MbActivityRule o = new MbActivityRule();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(MbActivityRule mbActivityRule, Map<String, Object> params) {
		String whereHql = "";	
		if (mbActivityRule != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(mbActivityRule.getTenantId())) {
				whereHql += " and t.tenantId = :tenantId";
				params.put("tenantId", mbActivityRule.getTenantId());
			}		
			if (!F.empty(mbActivityRule.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", mbActivityRule.getIsdeleted());
			}		
			if (!F.empty(mbActivityRule.getActivityId())) {
				whereHql += " and t.activityId = :activityId";
				params.put("activityId", mbActivityRule.getActivityId());
			}		
			if (!F.empty(mbActivityRule.getName())) {
				whereHql += " and t.name = :name";
				params.put("name", mbActivityRule.getName());
			}		
			if (!F.empty(mbActivityRule.getSeq())) {
				whereHql += " and t.seq = :seq";
				params.put("seq", mbActivityRule.getSeq());
			}		
			if (!F.empty(mbActivityRule.getLeftValue())) {
				whereHql += " and t.leftValue = :leftValue";
				params.put("leftValue", mbActivityRule.getLeftValue());
			}		
			if (!F.empty(mbActivityRule.getOperator())) {
				whereHql += " and t.operator = :operator";
				params.put("operator", mbActivityRule.getOperator());
			}		
			if (!F.empty(mbActivityRule.getRightValue())) {
				whereHql += " and t.rightValue = :rightValue";
				params.put("rightValue", mbActivityRule.getRightValue());
			}		
			if (!F.empty(mbActivityRule.getRemark())) {
				whereHql += " and t.remark = :remark";
				params.put("remark", mbActivityRule.getRemark());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(MbActivityRule mbActivityRule) {
		TmbActivityRule t = new TmbActivityRule();
		BeanUtils.copyProperties(mbActivityRule, t);
		//t.setId(jb.absx.UUID.uuid());
		t.setIsdeleted(false);
		mbActivityRuleDao.save(t);
		mbActivityRule.setId(t.getId());
	}

	@Override
	public MbActivityRule get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TmbActivityRule t = mbActivityRuleDao.get("from TmbActivityRule t  where t.id = :id", params);
		MbActivityRule o = new MbActivityRule();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(MbActivityRule mbActivityRule) {
		TmbActivityRule t = mbActivityRuleDao.get(TmbActivityRule.class, mbActivityRule.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(mbActivityRule, t, new String[]{"id", "addtime", "isdeleted", "updatetime"}, true);
		}
		mbActivityRule.setId(t.getId());
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		mbActivityRuleDao.executeHql("update TmbActivityRule t set t.isdeleted = 1 where t.id = :id",params);
		//mbActivityRuleDao.delete(mbActivityRuleDao.get(TmbActivityRule.class, id));
	}
	@Override
	public void addActivityRuleAndRule(MbActivityRule activityRule){
		add(activityRule);
		redisRuleSetService.deleteRuleSetListByActivityRuleId(activityRule.getId());
	}
	@Override
	public void editActivityRuleAndRule(MbActivityRule activityRule) {
		edit(activityRule);
		redisRuleSetService.deleteRuleSetListByActivityRuleId(activityRule.getId());
	}
	@Override
	public void deleteRule(Integer id) {
		redisRuleSetService.deleteRuleSetListByActivityRuleId(id);
		delete(id);
		MbActivityAction mbActivityAction = new MbActivityAction();
		mbActivityAction.setActivityRuleId(id);
		List<MbActivityAction> list = mbActivityActionService.query(mbActivityAction);
		if(list!=null&& list.size()>0){
			for (int i = 0; i <list.size();i++) {
				mbActivityActionService.delete(list.get(i).getId());
			}
		}
	}

	@Override
	public List<MbActivityRule> query(MbActivityRule mbActivityRule) {
		List<MbActivityRule> ol = new ArrayList<MbActivityRule>();
		String hql = " from TmbActivityRule t ";
		Map<String, Object> params = new HashMap<String, Object>();
		List<TmbActivityRule> l = mbActivityRuleDao.find(hql + whereHql(mbActivityRule, params), params);
		if (l != null && l.size() > 0) {
			for (TmbActivityRule t : l) {
				MbActivityRule o = new MbActivityRule();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

	@Override
	public MbActivityRule getByActivityActionId(Integer activityActionId) {
	    MbActivityRule activityRule = new MbActivityRule();
	    MbActivityAction activityAction = mbActivityActionService.get(activityActionId);
	    if (activityAction != null && !F.empty(activityAction.getActivityRuleId())) {
	    	activityRule = get(activityAction.getActivityRuleId());
		}
		return activityRule;
	}
}
