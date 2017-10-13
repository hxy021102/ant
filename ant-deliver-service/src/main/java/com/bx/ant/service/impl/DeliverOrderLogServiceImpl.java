package com.bx.ant.service.impl;

import com.mobian.absx.F;
import com.bx.ant.dao.DeliverOrderLogDaoI;
import com.bx.ant.model.TdeliverOrderLog;
import com.mobian.pageModel.DataGrid;
import com.bx.ant.pageModel.DeliverOrderLog;
import com.mobian.pageModel.PageHelper;
import com.bx.ant.service.DeliverOrderLogServiceI;
import com.mobian.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DeliverOrderLogServiceImpl extends BaseServiceImpl<DeliverOrderLog> implements DeliverOrderLogServiceI {

	@Autowired
	private DeliverOrderLogDaoI deliverOrderLogDao;

	@Override
	public DataGrid dataGrid(DeliverOrderLog deliverOrderLog, PageHelper ph) {
		List<DeliverOrderLog> ol = new ArrayList<DeliverOrderLog>();
		String hql = " from TdeliverOrderLog t ";
		DataGrid dg = dataGridQuery(hql, ph, deliverOrderLog, deliverOrderLogDao);
		@SuppressWarnings("unchecked")
		List<TdeliverOrderLog> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TdeliverOrderLog t : l) {
				DeliverOrderLog o = new DeliverOrderLog();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(DeliverOrderLog deliverOrderLog, Map<String, Object> params) {
		String whereHql = "";	
		if (deliverOrderLog != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(deliverOrderLog.getTenantId())) {
				whereHql += " and t.tenantId = :tenantId";
				params.put("tenantId", deliverOrderLog.getTenantId());
			}		
			if (!F.empty(deliverOrderLog.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", deliverOrderLog.getIsdeleted());
			}		
			if (!F.empty(deliverOrderLog.getDeliverOrderId())) {
				whereHql += " and t.deliverOrderId = :deliverOrderId";
				params.put("deliverOrderId", deliverOrderLog.getDeliverOrderId());
			}		
			if (!F.empty(deliverOrderLog.getLogType())) {
				whereHql += " and t.logType = :logType";
				params.put("logType", deliverOrderLog.getLogType());
			}		
			if (!F.empty(deliverOrderLog.getContent())) {
				whereHql += " and t.content = :content";
				params.put("content", deliverOrderLog.getContent());
			}		
			if (!F.empty(deliverOrderLog.getLoginId())) {
				whereHql += " and t.loginId = :loginId";
				params.put("loginId", deliverOrderLog.getLoginId());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(DeliverOrderLog deliverOrderLog) {
		TdeliverOrderLog t = new TdeliverOrderLog();
		BeanUtils.copyProperties(deliverOrderLog, t);
		//t.setId(jb.absx.UUID.uuid());
		t.setIsdeleted(false);
		deliverOrderLogDao.save(t);
	}

	@Override
	public DeliverOrderLog get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TdeliverOrderLog t = deliverOrderLogDao.get("from TdeliverOrderLog t  where t.id = :id", params);
		DeliverOrderLog o = new DeliverOrderLog();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(DeliverOrderLog deliverOrderLog) {
		TdeliverOrderLog t = deliverOrderLogDao.get(TdeliverOrderLog.class, deliverOrderLog.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(deliverOrderLog, t, new String[] { "id" , "addtime", "isdeleted","updatetime" },true);
		}
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		deliverOrderLogDao.executeHql("update TdeliverOrderLog t set t.isdeleted = 1 where t.id = :id",params);
		//deliverOrderLogDao.delete(deliverOrderLogDao.get(TdeliverOrderLog.class, id));
	}

}
