package com.mobian.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobian.absx.F;
import com.mobian.dao.MbWithdrawLogDaoI;
import com.mobian.exception.ServiceException;
import com.mobian.model.TmbWithdrawLog;
import com.mobian.pageModel.MbBalance;
import com.mobian.pageModel.MbWithdrawLog;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;
import com.mobian.service.MbBalanceServiceI;
import com.mobian.service.MbWithdrawLogServiceI;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mobian.util.MyBeanUtils;

@Service
public class MbWithdrawLogServiceImpl extends BaseServiceImpl<MbWithdrawLog> implements MbWithdrawLogServiceI {

	@Autowired
	private MbWithdrawLogDaoI mbWithdrawLogDao;

	@Autowired
	private MbBalanceServiceI mbBalanceService;

	@Override
	public DataGrid dataGrid(MbWithdrawLog mbWithdrawLog, PageHelper ph) {
		List<MbWithdrawLog> ol = new ArrayList<MbWithdrawLog>();
		String hql = " from TmbWithdrawLog t ";
		DataGrid dg = dataGridQuery(hql, ph, mbWithdrawLog, mbWithdrawLogDao);
		@SuppressWarnings("unchecked")
		List<TmbWithdrawLog> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TmbWithdrawLog t : l) {
				MbWithdrawLog o = new MbWithdrawLog();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(MbWithdrawLog mbWithdrawLog, Map<String, Object> params) {
		String whereHql = "";	
		if (mbWithdrawLog != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(mbWithdrawLog.getTenantId())) {
				whereHql += " and t.tenantId = :tenantId";
				params.put("tenantId", mbWithdrawLog.getTenantId());
			}		
			if (!F.empty(mbWithdrawLog.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", mbWithdrawLog.getIsdeleted());
			}		
			if (!F.empty(mbWithdrawLog.getBalanceId())) {
				whereHql += " and t.balanceId = :balanceId";
				params.put("balanceId", mbWithdrawLog.getBalanceId());
			}		
			if (!F.empty(mbWithdrawLog.getAmount())) {
				whereHql += " and t.amount = :amount";
				params.put("amount", mbWithdrawLog.getAmount());
			}		
			if (!F.empty(mbWithdrawLog.getRefType())) {
				whereHql += " and t.refType = :refType";
				params.put("refType", mbWithdrawLog.getRefType());
			}		
			if (!F.empty(mbWithdrawLog.getApplyLoginId())) {
				whereHql += " and t.applyLoginId = :applyLoginId";
				params.put("applyLoginId", mbWithdrawLog.getApplyLoginId());
			}		
			if (!F.empty(mbWithdrawLog.getReceiver())) {
				whereHql += " and t.receiver= :receiver";
				params.put("remitter", mbWithdrawLog.getReceiver());
			}		
			if (!F.empty(mbWithdrawLog.getContent())) {
				whereHql += " and t.content = :content";
				params.put("content", mbWithdrawLog.getContent());
			}		
			if (!F.empty(mbWithdrawLog.getBankCode())) {
				whereHql += " and t.bankCode = :bankCode";
				params.put("bankCode", mbWithdrawLog.getBankCode());
			}		
			if (!F.empty(mbWithdrawLog.getHandleStatus())) {
				whereHql += " and t.handleStatus = :handleStatus";
				params.put("handleStatus", mbWithdrawLog.getHandleStatus());
			}		
			if (!F.empty(mbWithdrawLog.getHandleLoginId())) {
				whereHql += " and t.handleLoginId = :handleLoginId";
				params.put("handleLoginId", mbWithdrawLog.getHandleLoginId());
			}		
			if (!F.empty(mbWithdrawLog.getHandleRemark())) {
				whereHql += " and t.handleRemark = :handleRemark";
				params.put("handleRemark", mbWithdrawLog.getHandleRemark());
			}		
			if (!F.empty(mbWithdrawLog.getPayCode())) {
				whereHql += " and t.payCode = :payCode";
				params.put("payCode", mbWithdrawLog.getPayCode());
			}
			if (!F.empty(mbWithdrawLog.getReceiverAccount())) {
				whereHql += " and t.receiverAccount = :receiverAccount";
				params.put("receiverAccount", mbWithdrawLog.getReceiverAccount());
			}
		}	
		return whereHql;
	}

	@Override
	public void add(MbWithdrawLog mbWithdrawLog) {
		TmbWithdrawLog t = new TmbWithdrawLog();
		BeanUtils.copyProperties(mbWithdrawLog, t);
		//t.setId(jb.absx.UUID.uuid());
		t.setIsdeleted(false);
		mbWithdrawLogDao.save(t);
	}

	@Override
	public MbWithdrawLog get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TmbWithdrawLog t = mbWithdrawLogDao.get("from TmbWithdrawLog t  where t.id = :id", params);
		MbWithdrawLog o = new MbWithdrawLog();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(MbWithdrawLog mbWithdrawLog) {
		TmbWithdrawLog t = mbWithdrawLogDao.get(TmbWithdrawLog.class, mbWithdrawLog.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(mbWithdrawLog, t, new String[] { "id" , "addtime", "isdeleted","updatetime" },true);
		}
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		mbWithdrawLogDao.executeHql("update TmbWithdrawLog t set t.isdeleted = 1 where t.id = :id",params);
		//mbWithdrawLogDao.delete(mbWithdrawLogDao.get(TmbWithdrawLog.class, id));
	}

	@Override
	public void editAudit(MbWithdrawLog mbWithdrawLog, String login) {
		MbWithdrawLog withdrawLog = get(mbWithdrawLog.getId());
		if ("HAS02".equals(mbWithdrawLog.getHandleStatus())) {
			//1.  判断是否合规
			if (F.empty(withdrawLog.getBalanceId())) throw new ServiceException("余额账户为空");
			MbBalance balance = mbBalanceService.get(withdrawLog.getBalanceId());




			Map<String, Object> params = new HashMap<String, Object>();
		}
	}
}
