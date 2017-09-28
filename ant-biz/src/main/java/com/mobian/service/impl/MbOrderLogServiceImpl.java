package com.mobian.service.impl;

import com.mobian.absx.F;
import com.mobian.dao.MbOrderLogDaoI;
import com.mobian.model.TmbOrderLog;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbOrderLog;
import com.mobian.pageModel.PageHelper;
import com.mobian.pageModel.User;
import com.mobian.service.MbOrderLogServiceI;
import com.mobian.service.UserServiceI;
import com.mobian.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MbOrderLogServiceImpl extends BaseServiceImpl<MbOrderLog> implements MbOrderLogServiceI {

	@Autowired
	private MbOrderLogDaoI mbOrderLogDao;

	@Autowired
	private UserServiceI userService;
	@Autowired
	private RedisOrderLogServiceImpl redisOrderLogService;

	@Override
	public DataGrid dataGrid(MbOrderLog mbOrderLog, PageHelper ph) {
		List<MbOrderLog> ol = new ArrayList<MbOrderLog>();
		String hql = " from TmbOrderLog t ";
		DataGrid dg = dataGridQuery(hql, ph, mbOrderLog, mbOrderLogDao);
		@SuppressWarnings("unchecked")
		List<TmbOrderLog> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TmbOrderLog t : l) {
				MbOrderLog o = new MbOrderLog();
				BeanUtils.copyProperties(t, o);

				if (o.getLoginId() != null) {
					User user = userService.getFromCache(o.getLoginId());
					if (user != null) {
						o.setLoginName(user.getName());
					}
				}

				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(MbOrderLog mbOrderLog, Map<String, Object> params) {
		String whereHql = "";	
		if (mbOrderLog != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(mbOrderLog.getTenantId())) {
				whereHql += " and t.tenantId = :tenantId";
				params.put("tenantId", mbOrderLog.getTenantId());
			}		
			if (!F.empty(mbOrderLog.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", mbOrderLog.getIsdeleted());
			}		
			if (!F.empty(mbOrderLog.getOrderId())) {
				whereHql += " and t.orderId = :orderId";
				params.put("orderId", mbOrderLog.getOrderId());
			}		
			if (!F.empty(mbOrderLog.getLoginId())) {
				whereHql += " and t.loginId = :loginId";
				params.put("loginId", mbOrderLog.getLoginId());
			}		
			if (!F.empty(mbOrderLog.getContent())) {
				whereHql += " and t.content = :content";
				params.put("content", mbOrderLog.getContent());
			}		
			if (!F.empty(mbOrderLog.getRemark())) {
				whereHql += " and t.remark = :remark";
				params.put("remark", mbOrderLog.getRemark());
			}		
			if (!F.empty(mbOrderLog.getLogType())) {
				whereHql += " and t.logType = :logType";
				params.put("logType", mbOrderLog.getLogType());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(MbOrderLog mbOrderLog) {
		TmbOrderLog t = new TmbOrderLog();
		BeanUtils.copyProperties(mbOrderLog, t);
		//t.setId(jb.absx.UUID.uuid());
		t.setIsdeleted(false);
		mbOrderLogDao.save(t);
	}

	@Override
	public MbOrderLog get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TmbOrderLog t = mbOrderLogDao.get("from TmbOrderLog t  where t.id = :id", params);
		MbOrderLog o = new MbOrderLog();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(MbOrderLog mbOrderLog) {
		TmbOrderLog t = mbOrderLogDao.get(TmbOrderLog.class, mbOrderLog.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(mbOrderLog, t, new String[]{"id", "addtime", "isdeleted", "updatetime"}, true);
		}
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		mbOrderLogDao.executeHql("update TmbOrderLog t set t.isdeleted = 1 where t.id = :id",params);
		//mbOrderLogDao.delete(mbOrderLogDao.get(TmbOrderLog.class, id));
	}

	@Override
	public MbOrderLog getByIdAndType(Integer orderId, String logType) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderId", orderId);
		params.put("logType", logType);
		TmbOrderLog t = mbOrderLogDao.get("from TmbOrderLog t  where t.orderId = :orderId and t.logType = :logType", params);
		if (t == null) return null;
		MbOrderLog o = new MbOrderLog();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public Boolean addOrderLogAndSetRedisOrderLog(MbOrderLog mbOrderLog) {
		add(mbOrderLog);
		redisOrderLogService.increment(mbOrderLog.getOrderId(), mbOrderLog.getLogType());
		return true;
	}

	@Override
	public void deleteOrderLogAndChangeRedisValue(Integer id) {
		MbOrderLog mbOrderLog = get(id);
		delete(id);
		redisOrderLogService.decrease(mbOrderLog.getOrderId(), mbOrderLog.getLogType());
	}
}
