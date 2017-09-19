package com.mobian.service.impl;

import com.mobian.absx.F;
import com.mobian.dao.MbBalanceDaoI;
import com.mobian.dao.MbBalanceLogDaoI;
import com.mobian.exception.ServiceException;
import com.mobian.model.TmbBalance;
import com.mobian.model.TmbBalanceLog;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbBalanceLog;
import com.mobian.pageModel.PageHelper;
import com.mobian.service.MbBalanceLogServiceI;
import com.mobian.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MbBalanceLogServiceImpl extends BaseServiceImpl<MbBalanceLog> implements MbBalanceLogServiceI {

	@Autowired
	private MbBalanceLogDaoI mbBalanceLogDao;

	@Autowired
	private MbBalanceDaoI mbBalanceDao;

	@Override
	public DataGrid dataGrid(MbBalanceLog mbBalanceLog, PageHelper ph) {
		List<MbBalanceLog> ol = new ArrayList<MbBalanceLog>();
		String hql = " from TmbBalanceLog t ";
		DataGrid dg = dataGridQuery(hql, ph, mbBalanceLog, mbBalanceLogDao);
		@SuppressWarnings("unchecked")
		List<TmbBalanceLog> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TmbBalanceLog t : l) {
				MbBalanceLog o = new MbBalanceLog();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(MbBalanceLog mbBalanceLog, Map<String, Object> params) {
		String whereHql = "";	
		if (mbBalanceLog != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(mbBalanceLog.getTenantId())) {
				whereHql += " and t.tenantId = :tenantId";
				params.put("tenantId", mbBalanceLog.getTenantId());
			}		
			if (!F.empty(mbBalanceLog.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", mbBalanceLog.getIsdeleted());
			}		
			if (!F.empty(mbBalanceLog.getBalanceId())) {
				whereHql += " and t.balanceId = :balanceId";
				params.put("balanceId", mbBalanceLog.getBalanceId());
			}		
			if (!F.empty(mbBalanceLog.getAmount())) {
				whereHql += " and t.amount = :amount";
				params.put("amount", mbBalanceLog.getAmount());
			}		
			if (!F.empty(mbBalanceLog.getRefId())) {
				whereHql += " and t.refId = :refId";
				params.put("refId", mbBalanceLog.getRefId());
			}		
			if (!F.empty(mbBalanceLog.getRefType())) {
				whereHql += " and t.refType = :refType";
				params.put("refType", mbBalanceLog.getRefType());
			}		
			if (!F.empty(mbBalanceLog.getReason())) {
				whereHql += " and t.reason = :reason";
				params.put("reason", mbBalanceLog.getReason());
			}		
			if (!F.empty(mbBalanceLog.getRemark())) {
				whereHql += " and t.remark = :remark";
				params.put("remark", mbBalanceLog.getRemark());
			}
			if(mbBalanceLog.getUpdatetimeBegin()!=null){
				whereHql += " and t.updatetime >= :updatetimeBegin";
				params.put("updatetimeBegin", mbBalanceLog.getUpdatetimeBegin());
			}
			if(mbBalanceLog.getUpdatetimeEnd()!=null){
				whereHql += " and t.updatetime <= :updatetimeEnd";
				params.put("updatetimeEnd", mbBalanceLog.getUpdatetimeEnd());
			}
			if(mbBalanceLog.getIsShow() != null) {
				whereHql += " and t.isShow = :isShow";
				params.put("isShow", mbBalanceLog.getIsShow());
			}
		}	
		return whereHql;
	}

	@Override
	public void add(MbBalanceLog mbBalanceLog) {
		TmbBalanceLog t = new TmbBalanceLog();
		BeanUtils.copyProperties(mbBalanceLog, t);
		//t.setId(jb.absx.UUID.uuid());
		if(F.empty(t.getIsdeleted())) t.setIsdeleted(false);
		if(F.empty(t.getIsShow())) t.setIsShow(true);
		mbBalanceLogDao.save(t);
		mbBalanceLog.setId(t.getId());
	}

	@Override
	public void addAndUpdateBalance(MbBalanceLog mbBalanceLog) {
		if (mbBalanceLog.getAmount() == null) {
			throw new ServiceException("余额不允许为null");
		}
		TmbBalance tmbBalance = mbBalanceDao.get(TmbBalance.class, mbBalanceLog.getBalanceId());
		String remark = mbBalanceLog.getRemark() == null ? "" : mbBalanceLog.getRemark();
		mbBalanceLog.setRemark(String.format(remark + "【期末余额:%s分】", tmbBalance.getAmount() + mbBalanceLog.getAmount()));
		add(mbBalanceLog);

		int i = mbBalanceDao.executeHql("update TmbBalance t set t.amount=amount+" + mbBalanceLog.getAmount() + " where t.id=" + mbBalanceLog.getBalanceId());
		if (i != 1) {
			throw new ServiceException("余额更新失败");
		}
	}

	@Override
	public void updateLogAndBalance(MbBalanceLog mbBalanceLog) {
		edit(mbBalanceLog);
		if(!mbBalanceLog.getIsdeleted()) {
			if(mbBalanceLog.getAmount() == null) {
				throw new ServiceException("余额不允许为null");
			}
			int i = mbBalanceDao.executeHql("update TmbBalance t set t.amount=amount+" + mbBalanceLog.getAmount() + " where t.id=" + mbBalanceLog.getBalanceId());
			if (i != 1) {
				throw new ServiceException("余额更新失败");
			}
		}
	}

	@Override
	public MbBalanceLog get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TmbBalanceLog t = mbBalanceLogDao.get("from TmbBalanceLog t  where t.id = :id", params);
		MbBalanceLog o = new MbBalanceLog();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(MbBalanceLog mbBalanceLog) {
		TmbBalanceLog t = mbBalanceLogDao.get(TmbBalanceLog.class, mbBalanceLog.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(mbBalanceLog, t, new String[]{"id", "addtime", "updatetime"}, true);
			mbBalanceLog.setBalanceId(t.getBalanceId());
			mbBalanceLog.setAmount(t.getAmount());
		}
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		mbBalanceLogDao.executeHql("update TmbBalanceLog t set t.isdeleted = 1 where t.id = :id",params);
		//mbBalanceLogDao.delete(mbBalanceLogDao.get(TmbBalanceLog.class, id));
	}

}
