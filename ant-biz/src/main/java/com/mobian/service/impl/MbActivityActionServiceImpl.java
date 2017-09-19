package com.mobian.service.impl;

import com.mobian.absx.F;
import com.mobian.dao.MbActivityActionDaoI;
import com.mobian.model.TmbActivityAction;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbActivityAction;
import com.mobian.pageModel.PageHelper;
import com.mobian.service.MbActivityActionServiceI;
import com.mobian.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MbActivityActionServiceImpl extends BaseServiceImpl<MbActivityAction> implements MbActivityActionServiceI {

	@Autowired
	private MbActivityActionDaoI mbActivityActionDao;

	@Override
	public DataGrid dataGrid(MbActivityAction mbActivityAction, PageHelper ph) {
		List<MbActivityAction> ol = new ArrayList<MbActivityAction>();
		String hql = " from TmbActivityAction t ";
		DataGrid dg = dataGridQuery(hql, ph, mbActivityAction, mbActivityActionDao);
		@SuppressWarnings("unchecked")
		List<TmbActivityAction> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TmbActivityAction t : l) {
				MbActivityAction o = new MbActivityAction();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}

	@Override
	public List<MbActivityAction> query(MbActivityAction mbActivityAction) {
		List<MbActivityAction> ol = new ArrayList<MbActivityAction>();
		String hql = " from TmbActivityAction t ";
		Map<String, Object> params = new HashMap<String, Object>();
		List<TmbActivityAction> l = mbActivityActionDao.find(hql + whereHql(mbActivityAction, params), params);
		if (l != null && l.size() > 0) {
			for (TmbActivityAction t : l) {
				MbActivityAction o = new MbActivityAction();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

	protected String whereHql(MbActivityAction mbActivityAction, Map<String, Object> params) {
		String whereHql = "";	
		if (mbActivityAction != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(mbActivityAction.getTenantId())) {
				whereHql += " and t.tenantId = :tenantId";
				params.put("tenantId", mbActivityAction.getTenantId());
			}		
			if (!F.empty(mbActivityAction.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", mbActivityAction.getIsdeleted());
			}		
			if (!F.empty(mbActivityAction.getActivityRuleId())) {
				whereHql += " and t.activityRuleId = :activityRuleId";
				params.put("activityRuleId", mbActivityAction.getActivityRuleId());
			}		
			if (!F.empty(mbActivityAction.getName())) {
				whereHql += " and t.name = :name";
				params.put("name", mbActivityAction.getName());
			}		
			if (!F.empty(mbActivityAction.getSeq())) {
				whereHql += " and t.seq = :seq";
				params.put("seq", mbActivityAction.getSeq());
			}		
			if (!F.empty(mbActivityAction.getActionType())) {
				whereHql += " and t.actionType = :actionType";
				params.put("actionType", mbActivityAction.getActionType());
			}		
			if (!F.empty(mbActivityAction.getParameter1())) {
				whereHql += " and t.parameter1 = :parameter1";
				params.put("parameter1", mbActivityAction.getParameter1());
			}		
			if (!F.empty(mbActivityAction.getParameter2())) {
				whereHql += " and t.parameter2 = :parameter2";
				params.put("parameter2", mbActivityAction.getParameter2());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(MbActivityAction mbActivityAction) {
		TmbActivityAction t = new TmbActivityAction();
		BeanUtils.copyProperties(mbActivityAction, t);
		//t.setId(jb.absx.UUID.uuid());
		t.setIsdeleted(false);
		mbActivityActionDao.save(t);
	}

	@Override
	public MbActivityAction get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TmbActivityAction t = mbActivityActionDao.get("from TmbActivityAction t  where t.id = :id", params);
		MbActivityAction o = new MbActivityAction();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(MbActivityAction mbActivityAction) {
		TmbActivityAction t = mbActivityActionDao.get(TmbActivityAction.class, mbActivityAction.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(mbActivityAction, t, new String[]{"id", "addtime", "isdeleted", "updatetime"}, true);
		}
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		mbActivityActionDao.executeHql("update TmbActivityAction t set t.isdeleted = 1 where t.id = :id",params);
		//mbActivityActionDao.delete(mbActivityActionDao.get(TmbActivityAction.class, id));
	}

}
