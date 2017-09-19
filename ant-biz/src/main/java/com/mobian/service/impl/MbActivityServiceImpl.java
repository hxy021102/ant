package com.mobian.service.impl;

import com.mobian.absx.F;
import com.mobian.dao.MbActivityDaoI;
import com.mobian.model.TmbActivity;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbActivity;
import com.mobian.pageModel.MbActivityRule;
import com.mobian.pageModel.PageHelper;
import com.mobian.service.MbActivityRuleServiceI;
import com.mobian.service.MbActivityServiceI;
import com.mobian.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MbActivityServiceImpl extends BaseServiceImpl<MbActivity> implements MbActivityServiceI {

	@Autowired
	private MbActivityDaoI mbActivityDao;
	@Autowired
	private MbActivityRuleServiceI mbActivityRuleService;

	@Override
	public DataGrid dataGrid(MbActivity mbActivity, PageHelper ph) {
		List<MbActivity> ol = new ArrayList<MbActivity>();
		String hql = " from TmbActivity t ";
		DataGrid dg = dataGridQuery(hql, ph, mbActivity, mbActivityDao);
		@SuppressWarnings("unchecked")
		List<TmbActivity> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TmbActivity t : l) {
				MbActivity o = new MbActivity();
				BeanUtils.copyProperties(t, o);
				if (o.getValid()==false){
					o.setValidName("否");
				}else{
					o.setValidName("是");
				}
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(MbActivity mbActivity, Map<String, Object> params) {
		String whereHql = "";	
		if (mbActivity != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(mbActivity.getTenantId())) {
				whereHql += " and t.tenantId = :tenantId";
				params.put("tenantId", mbActivity.getTenantId());
			}		
			if (!F.empty(mbActivity.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", mbActivity.getIsdeleted());
			}		
			if (!F.empty(mbActivity.getName())) {
				whereHql += " and t.name like  :name";
				params.put("name","%" + mbActivity.getName() + "%");
			}		
			if (!F.empty(mbActivity.getValid())) {
				whereHql += " and t.valid = :valid";
				params.put("valid", mbActivity.getValid());
			}		
			if (!F.empty(mbActivity.getRemark())) {
				whereHql += " and t.remark = :remark";
				params.put("remark", mbActivity.getRemark());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(MbActivity mbActivity) {
		TmbActivity t = new TmbActivity();
		BeanUtils.copyProperties(mbActivity, t);
		//t.setId(jb.absx.UUID.uuid());
		t.setIsdeleted(false);
		mbActivityDao.save(t);
	}

	@Override
	public MbActivity get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TmbActivity t = mbActivityDao.get("from TmbActivity t  where t.id = :id", params);
		MbActivity o = new MbActivity();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(MbActivity mbActivity) {
		TmbActivity t = mbActivityDao.get(TmbActivity.class, mbActivity.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(mbActivity, t, new String[]{"id", "addtime", "isdeleted", "updatetime"}, true);
		}
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		mbActivityDao.executeHql("update TmbActivity t set t.isdeleted = 1 where t.id = :id",params);
		//mbActivityDao.delete(mbActivityDao.get(TmbActivity.class, id));
	}

	@Override
	public void deleteActivity(Integer id) {
		delete(id);
		MbActivityRule mbActivityRule = new MbActivityRule();
		mbActivityRule.setActivityId(id);
		List<MbActivityRule> list = mbActivityRuleService.query(mbActivityRule);
		for (MbActivityRule m : list) {
			mbActivityRuleService.deleteRule(m.getId());
		}
	}
}
