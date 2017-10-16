package com.bx.ant.service.impl;

import com.bx.ant.dao.DeliverOrderLogDaoI;
import com.bx.ant.model.TdeliverOrderLog;
import com.bx.ant.pageModel.DeliverOrderLog;
import com.bx.ant.pageModel.DeliverOrderLogQuery;
import com.bx.ant.service.DeliverOrderLogServiceI;
import com.mobian.absx.F;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;
import com.mobian.pageModel.User;
import com.mobian.service.UserServiceI;
import com.mobian.util.MyBeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DeliverOrderLogServiceImpl extends BaseServiceImpl<DeliverOrderLog> implements DeliverOrderLogServiceI {

	@Autowired
	private DeliverOrderLogDaoI deliverOrderLogDao;

	@Resource
	private UserServiceI userService;

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

	@Override
	public DataGrid dataGridWithName(DeliverOrderLog deliverOrderLog, PageHelper ph) {
		DataGrid dataGrid = dataGrid(deliverOrderLog, ph);
		List<DeliverOrderLog> deliverOrderLogs = dataGrid.getRows();
		if (CollectionUtils.isNotEmpty(deliverOrderLogs)) {
			List<DeliverOrderLogQuery> deliverOrderLogQueries = new ArrayList<DeliverOrderLogQuery>();
			for (DeliverOrderLog orderLog : deliverOrderLogs) {
				DeliverOrderLogQuery orderLogQuery = new DeliverOrderLogQuery();
				BeanUtils.copyProperties(orderLog, orderLogQuery);
				orderLogQuery.setLogTypeName(orderLog.getLogType());
				User user = userService.getFromCache(orderLog.getLoginId());
				if (user != null) {
					orderLogQuery.setLoginName(user.getName());
				}
				deliverOrderLogQueries.add(orderLogQuery);
			}
			DataGrid dg = new DataGrid();
			dg.setRows(deliverOrderLogQueries);
			dg.setTotal(dataGrid.getTotal());
			return dg;
		}
		return dataGrid;
	}

}
