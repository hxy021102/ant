package com.mobian.service.impl;

import com.mobian.absx.F;
import com.mobian.dao.MbRechargeLogDaoI;
import com.mobian.exception.ServiceException;
import com.mobian.model.TmbRechargeLog;
import com.mobian.pageModel.*;
import com.mobian.service.*;
import com.mobian.util.MyBeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MbRechargeLogServiceImpl extends BaseServiceImpl<MbRechargeLog> implements MbRechargeLogServiceI {

	@Autowired
	private MbRechargeLogDaoI mbRechargeLogDao;

	@Autowired
	private MbBalanceLogServiceI mbBalanceLogService;

	@Autowired
	private UserServiceI userService;

	@Autowired
	private MbUserServiceI mbUserService;

	@Autowired
	private MbPaymentItemServiceI mbPaymentItemService;

	@Override
	public DataGrid dataGrid(MbRechargeLog mbRechargeLog, PageHelper ph) {
		List<MbRechargeLog> ol = new ArrayList<MbRechargeLog>();
		String hql = " from TmbRechargeLog t ";
		DataGrid dg = dataGridQuery(hql, ph, mbRechargeLog, mbRechargeLogDao);
		@SuppressWarnings("unchecked")
		List<TmbRechargeLog> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TmbRechargeLog t : l) {
				MbRechargeLog dbMbMechargeLog = new MbRechargeLog();
				BeanUtils.copyProperties(t, dbMbMechargeLog);

				String userId = dbMbMechargeLog.getApplyLoginId();

				if(userId != null){
					if("BT003".equals(dbMbMechargeLog.getRefType())) {
						MbUser user = mbUserService.get(Integer.valueOf(userId));
						dbMbMechargeLog.setApplyLoginName(user.getNickName());
					} else {
						User user = userService.get(userId);
						dbMbMechargeLog.setApplyLoginName(user.getName());
					}

				}



				ol.add(dbMbMechargeLog);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(MbRechargeLog mbRechargeLog, Map<String, Object> params) {
		String whereHql = "";	
		if (mbRechargeLog != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(mbRechargeLog.getTenantId())) {
				whereHql += " and t.tenantId = :tenantId";
				params.put("tenantId", mbRechargeLog.getTenantId());
			}		
			if (!F.empty(mbRechargeLog.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", mbRechargeLog.getIsdeleted());
			}		
			if (!F.empty(mbRechargeLog.getBalanceId())) {
				whereHql += " and t.balanceId = :balanceId";
				params.put("balanceId", mbRechargeLog.getBalanceId());
			}		
			if (!F.empty(mbRechargeLog.getAmount())) {
				whereHql += " and t.amount = :amount";
				params.put("amount", mbRechargeLog.getAmount());
			}		
			if (!F.empty(mbRechargeLog.getApplyLoginId())) {
				whereHql += " and t.applyLoginId = :applyLoginId";
				params.put("applyLoginId", mbRechargeLog.getApplyLoginId());
			}		
			if (!F.empty(mbRechargeLog.getContent())) {
				whereHql += " and t.content = :content";
				params.put("content", mbRechargeLog.getContent());
			}		
			if (!F.empty(mbRechargeLog.getHandleStatus())) {
				whereHql += " and t.handleStatus = :handleStatus";
				params.put("handleStatus", mbRechargeLog.getHandleStatus());
			}		
			if (!F.empty(mbRechargeLog.getHandleLoginId())) {
				whereHql += " and t.handleLoginId = :handleLoginId";
				params.put("handleLoginId", mbRechargeLog.getHandleLoginId());
			}		
			if (!F.empty(mbRechargeLog.getHandleRemark())) {
				whereHql += " and t.handleRemark = :handleRemark";
				params.put("handleRemark", mbRechargeLog.getHandleRemark());
			}
			if (!F.empty(mbRechargeLog.getPayCode())) {
				whereHql += " and t.bankCode = :bankCode";
				params.put("bankCode", mbRechargeLog.getBankCode());
			}
			if (!F.empty(mbRechargeLog.getPayCode())) {
				whereHql += " and t.payCode = :payCode";
				params.put("payCode", mbRechargeLog.getPayCode());
			}
		}
		return whereHql;
	}

	@Override
	public void add(MbRechargeLog mbRechargeLog) {
		TmbRechargeLog tmbRechargeLog = new TmbRechargeLog();
		BeanUtils.copyProperties(mbRechargeLog, tmbRechargeLog);
		tmbRechargeLog.setIsdeleted(false);
		Integer rechargeLogId = (Integer) mbRechargeLogDao.save(tmbRechargeLog);
		mbRechargeLog.setId(rechargeLogId);

	}

	@Override
	public void addAndUpdateBalance(MbRechargeLog mbRechargeLog) {
		add(mbRechargeLog);
		MbBalanceLog tmbBalanceLog = new MbBalanceLog();
		BeanUtils.copyProperties(mbRechargeLog, tmbBalanceLog);
		tmbBalanceLog.setIsdeleted(false);
		tmbBalanceLog.setRefId(String.valueOf(mbRechargeLog.getId()));
		if(mbRechargeLog.getRefType()!=null) {
			tmbBalanceLog.setRefType(mbRechargeLog.getRefType());
		}else{
			tmbBalanceLog.setRefType("BT004");
		}
		tmbBalanceLog.setRemark(mbRechargeLog.getContent());
		mbBalanceLogService.addAndUpdateBalance(tmbBalanceLog);
	}

	@Override
	public MbRechargeLog get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TmbRechargeLog t = mbRechargeLogDao.get("from TmbRechargeLog t  where t.id = :id", params);
		MbRechargeLog o = new MbRechargeLog();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(MbRechargeLog mbRechargeLog) {
		TmbRechargeLog t = mbRechargeLogDao.get(TmbRechargeLog.class, mbRechargeLog.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(mbRechargeLog, t, new String[]{"id", "addtime", "isdeleted", "updatetime"}, true);
			mbRechargeLog.setBalanceId(t.getBalanceId());
			mbRechargeLog.setAmount(t.getAmount());
		}
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		mbRechargeLogDao.executeHql("update TmbRechargeLog t set t.isdeleted = 1 where t.id = :id",params);
		//mbRechargeLogDao.delete(mbRechargeLogDao.get(TmbRechargeLog.class, id));
	}

	@Override
	public void editAudit(MbRechargeLog mbRechargeLog) {
		/*
			1.从数据库中获取要修改的PaymentItem记录中得到银行bankcode
			2.从前端得到paycode
			3.通过以上两个参数去数据查找是否存在同时拥有两点的记录存在
			4.若存在则不通过,不存在则继续
			5.不通过:通过抛出异常,在controller中catch异常
			6.继续:将上面两个参数和handleStatus为"HS02"共三个参数同时去MbRechargeLog表中查询
			7.若存在则不通过,不存在则继续
			8.不通过:通过抛出异常,在controller中catch异常
			9.继续:将付款成功更新TmbRecharge记录
		*/
		TmbRechargeLog t = mbRechargeLogDao.get(TmbRechargeLog.class,mbRechargeLog.getId());

		MbPaymentItem paymentItem = new MbPaymentItem();
		paymentItem.setBankCode(t.getBankCode());
		paymentItem.setPayCode(mbRechargeLog.getPayCode());

		MbRechargeLog rechargeLog = new MbRechargeLog();
		rechargeLog.setBankCode(t.getBankCode());
		rechargeLog.setPayCode(mbRechargeLog.getPayCode());
		rechargeLog.setHandleStatus("HS02");
		//TODO
		synchronized (this) {
			if ("HS02".equals(mbRechargeLog.getHandleStatus())) {
					List<MbPaymentItem> paymentItems = mbPaymentItemService.listMbPaymentItem(paymentItem);
					List<MbRechargeLog> rechargeLogs = listMbRechargeLog(rechargeLog);
				if (CollectionUtils.isEmpty(paymentItems) && CollectionUtils.isEmpty(rechargeLogs)) {
					edit(mbRechargeLog);
				} else {
					throw new ServiceException("银行汇款单号已存在,请重新确认!");
				}
			} else if ("HS03".equals(mbRechargeLog.getHandleStatus())) {
				mbRechargeLog.setPayCode(null);
				edit(mbRechargeLog);
			}
		}
		// 审核成功更新用户余额
		if("HS02".equals(mbRechargeLog.getHandleStatus())) {
			MbBalanceLog tmbBalanceLog = new MbBalanceLog();
			tmbBalanceLog.setBalanceId(mbRechargeLog.getBalanceId());
			tmbBalanceLog.setAmount(mbRechargeLog.getAmount());
			tmbBalanceLog.setRefId(String.valueOf(mbRechargeLog.getId()));
			tmbBalanceLog.setRefType("BT003"); // 转账充值
			tmbBalanceLog.setRemark(mbRechargeLog.getHandleRemark());
			mbBalanceLogService.addAndUpdateBalance(tmbBalanceLog);
		}
	}

	@Override
	public List<MbRechargeLog> listMbRechargeLog(MbRechargeLog mbRechargeLog){
		String hql = " from TmbRechargeLog t ";
		List<MbRechargeLog> ol= new ArrayList<MbRechargeLog>();
		Map<String, Object> params = new HashMap<String, Object>();
		String whereHql = whereHql(mbRechargeLog, params);
		List<TmbRechargeLog> ts = mbRechargeLogDao.find(hql + whereHql,params);
		for (TmbRechargeLog t : ts
			 ) {
			MbRechargeLog o = new MbRechargeLog();
			BeanUtils.copyProperties(t,o);
			ol.add(o);
		}
		return ol;
	}
}
