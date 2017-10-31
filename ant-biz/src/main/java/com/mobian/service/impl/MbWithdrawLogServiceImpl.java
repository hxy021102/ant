package com.mobian.service.impl;

import com.mobian.absx.F;
import com.mobian.dao.MbWithdrawLogDaoI;
import com.mobian.exception.ServiceException;
import com.mobian.model.TmbWithdrawLog;
import com.mobian.pageModel.*;
import com.mobian.service.MbBalanceServiceI;
import com.mobian.service.MbWithdrawLogServiceI;
import com.mobian.thirdpart.wx.HttpUtil;
import com.mobian.thirdpart.wx.PayCommonUtil;
import com.mobian.thirdpart.wx.WeixinUtil;
import com.mobian.thirdpart.wx.XMLUtil;
import com.mobian.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MbWithdrawLogServiceImpl extends BaseServiceImpl<MbWithdrawLog> implements MbWithdrawLogServiceI {

	@Autowired
	private MbWithdrawLogDaoI mbWithdrawLogDao;

	@Autowired
	private MbBalanceServiceI mbBalanceService;

	@Autowired
	private MbBalanceLogServiceImpl mbBalanceLogService;

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
			if (!F.empty(mbWithdrawLog.getApplyLoginIP())) {
				whereHql += " and t.applyLoginIP = :applyLoginIP";
				params.put("applyLoginIP", mbWithdrawLog.getApplyLoginIP());
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
	public void editAudit(MbWithdrawLog mbWithdrawLog, String loginId) {
		MbWithdrawLog withdrawLog = get(mbWithdrawLog.getId());
		//通过
		if ("HAS02".equals(mbWithdrawLog.getHandleStatus())) {
			//1.  判断是否合规
			if (F.empty(withdrawLog.getBalanceId())) throw new ServiceException("余额账户为空");
			MbBalance balance = mbBalanceService.get(withdrawLog.getBalanceId());
			if (balance.getAmount() < withdrawLog.getAmount()) throw new ServiceException("余额不足");
			//2. 参数填充
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("amount", withdrawLog.getAmount());
			params.put("openid", withdrawLog.getReceiverAccount());
			params.put("partner_trade_no", withdrawLog.getId());
			params.put("re_user_name", withdrawLog.getReceiver());
			params.put("spbill_create_ip",withdrawLog.getApplyLoginIP());

			//3. 扣款
			String requestXml = PayCommonUtil.requestRefundXML(params);
			System.out.println("~~~~~~~~~~~~微信企业付款接口请求参数requestXml:" + requestXml);
			String result = HttpUtil.httpsRequestSSL(WeixinUtil.TRANSFERS_URL, requestXml);
			System.out.println("~~~~~~~~~~~~微信企业付款接口返回结果result:" + result);

			//4. 扣除余额
			MbBalanceLog balanceLog = new MbBalanceLog();
			balanceLog.setBalanceId(balance.getId());
			balanceLog.setAmount( - withdrawLog.getAmount());
			balanceLog.setRefId(withdrawLog.getId() + "");
			balanceLog.setRefType("BT101");
			balanceLog.setRemark("提现扣款");
			mbBalanceLogService.addAndUpdateBalance(balanceLog);

			//5. 编辑提现申请记录
			mbWithdrawLog.setHandleLoginId(loginId);
			mbWithdrawLog.setHandleTime(new Date());
			mbWithdrawLog.setReceiverTime(new Date());
			edit(mbWithdrawLog);
			try {
				Map<String, String> resultMap = XMLUtil.doXMLParse(result);
			} catch (Exception e) {
				withdrawLog.setHandleStatus("HS01");
				withdrawLog.setHandleRemark("提现失败--接口异常");
				edit(withdrawLog);
			}
		}
		//拒绝
		if ("HS03".equals(mbWithdrawLog)) {
		    mbWithdrawLog.setHandleLoginId(loginId);
		    mbWithdrawLog.setHandleTime(new Date());
			edit(withdrawLog);
		}
	}

}
