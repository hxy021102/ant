package com.mobian.service.impl;

import com.mobian.absx.F;
import com.mobian.dao.MbOrderRefundLogDaoI;
import com.mobian.model.TmbOrderRefundLog;
import com.mobian.pageModel.*;
import com.mobian.service.MbCouponsServiceI;
import com.mobian.service.MbOrderRefundLogServiceI;
import com.mobian.service.MbPaymentItemServiceI;
import com.mobian.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MbOrderRefundLogServiceImpl extends BaseServiceImpl<MbOrderRefundLog> implements MbOrderRefundLogServiceI {

	@Autowired
	private MbOrderRefundLogDaoI mbOrderRefundLogDao;
	@Autowired
	private MbPaymentItemServiceI mbPaymentItemService;
	@Autowired
	private MbCouponsServiceI mbCouponsService;

	@Override
	public DataGrid dataGrid(MbOrderRefundLog mbOrderRefundLog, PageHelper ph) {
		List<MbOrderRefundLog> ol = new ArrayList<MbOrderRefundLog>();
		String hql = " from TmbOrderRefundLog t ";
		DataGrid dg = dataGridQuery(hql, ph, mbOrderRefundLog, mbOrderRefundLogDao);
		@SuppressWarnings("unchecked")
		List<TmbOrderRefundLog> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TmbOrderRefundLog t : l) {
				MbOrderRefundLog o = new MbOrderRefundLog();
				BeanUtils.copyProperties(t, o);
				fillOrderRefundLogInfo(o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	
	private void fillOrderRefundLogInfo(MbOrderRefundLog o) {
		fillCouponsInfo(o);
	}
	private void fillCouponsInfo(MbOrderRefundLog o) {
		if((o.getPaymentItemId()) == null) return;
		MbPaymentItem paymentItem = mbPaymentItemService.get(o.getPaymentItemId());
		if (paymentItem == null || paymentItem.getCouponsId() == null) return;
		MbCoupons coupons = mbCouponsService.get(paymentItem.getCouponsId());
		if (coupons == null) return;
		o.setCouponsName(coupons.getName());
	}
	protected String whereHql(MbOrderRefundLog mbOrderRefundLog, Map<String, Object> params) {
		String whereHql = "";	
		if (mbOrderRefundLog != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(mbOrderRefundLog.getTenantId())) {
				whereHql += " and t.tenantId = :tenantId";
				params.put("tenantId", mbOrderRefundLog.getTenantId());
			}		
			if (!F.empty(mbOrderRefundLog.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", mbOrderRefundLog.getIsdeleted());
			}		
			if (!F.empty(mbOrderRefundLog.getOrderId())) {
				whereHql += " and t.orderId = :orderId";
				params.put("orderId", mbOrderRefundLog.getOrderId());
			}		
			if (!F.empty(mbOrderRefundLog.getOrderType())) {
				whereHql += " and t.orderType = :orderType";
				params.put("orderType", mbOrderRefundLog.getOrderType());
			}		
			if (!F.empty(mbOrderRefundLog.getPaymentItemId())) {
				whereHql += " and t.paymentItemId = :paymentItemId";
				params.put("paymentItemId", mbOrderRefundLog.getPaymentItemId());
			}		
			if (!F.empty(mbOrderRefundLog.getAmount())) {
				whereHql += " and t.amount = :amount";
				params.put("amount", mbOrderRefundLog.getAmount());
			}		
			if (!F.empty(mbOrderRefundLog.getPayWay())) {
				whereHql += " and t.payWay = :payWay";
				params.put("payWay", mbOrderRefundLog.getPayWay());
			}		
			if (!F.empty(mbOrderRefundLog.getRefundWay())) {
				whereHql += " and t.refundWay = :refundWay";
				params.put("refundWay", mbOrderRefundLog.getRefundWay());
			}		
			if (!F.empty(mbOrderRefundLog.getReason())) {
				whereHql += " and t.reason = :reason";
				params.put("reason", mbOrderRefundLog.getReason());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(MbOrderRefundLog mbOrderRefundLog) {
		TmbOrderRefundLog t = new TmbOrderRefundLog();
		BeanUtils.copyProperties(mbOrderRefundLog, t);
		//t.setId(jb.absx.UUID.uuid());
		t.setIsdeleted(false);
		mbOrderRefundLogDao.save(t);
		mbOrderRefundLog.setId(t.getId());
	}

	@Override
	public MbOrderRefundLog get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TmbOrderRefundLog t = mbOrderRefundLogDao.get("from TmbOrderRefundLog t  where t.id = :id", params);
		MbOrderRefundLog o = new MbOrderRefundLog();

		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(MbOrderRefundLog mbOrderRefundLog) {
		TmbOrderRefundLog t = mbOrderRefundLogDao.get(TmbOrderRefundLog.class, mbOrderRefundLog.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(mbOrderRefundLog, t, new String[]{"id", "addtime", "isdeleted", "updatetime"}, true);
		}
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		mbOrderRefundLogDao.executeHql("update TmbOrderRefundLog t set t.isdeleted = 1 where t.id = :id",params);
		//mbOrderRefundLogDao.delete(mbOrderRefundLogDao.get(TmbOrderRefundLog.class, id));
	}

}
