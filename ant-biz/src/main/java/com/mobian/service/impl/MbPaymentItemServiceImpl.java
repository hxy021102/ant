package com.mobian.service.impl;

import com.alibaba.fastjson.JSON;
import com.mobian.absx.F;
import com.mobian.dao.MbPaymentItemDaoI;
import com.mobian.exception.ServiceException;
import com.mobian.model.TmbPaymentItem;
import com.mobian.pageModel.*;
import com.mobian.service.*;
import com.mobian.util.MyBeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MbPaymentItemServiceImpl extends BaseServiceImpl<MbPaymentItem> implements MbPaymentItemServiceI {

	@Autowired
	private MbPaymentItemDaoI mbPaymentItemDao;
	@Autowired
	private MbPaymentServiceI mbPaymentService;
	@Autowired
	private MbRechargeLogServiceI mbRechargeLogService;
	@Autowired
	private MbCouponsServiceI mbCouponsService;
	@Autowired
	private MbShopCouponsServiceI mbShopCouponsService;

	@Override
	public DataGrid dataGrid(MbPaymentItem mbPaymentItem, PageHelper ph) {
		List<MbPaymentItem> ol = new ArrayList<MbPaymentItem>();
		String hql = " from TmbPaymentItem t ";
		DataGrid dg = dataGridQuery(hql, ph, mbPaymentItem, mbPaymentItemDao);
		@SuppressWarnings("unchecked")
		List<TmbPaymentItem> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TmbPaymentItem t : l) {
				MbPaymentItem o = new MbPaymentItem();
				BeanUtils.copyProperties(t, o);
				fillPaymentItemInfo(o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	protected void fillPaymentItemInfo(MbPaymentItem o) {
		fillCouponsInfo(o);
	}
	protected void fillCouponsInfo(MbPaymentItem o) {
		if (!F.empty(o.getRemark())) {
			Map<Integer, Integer> couponsMap = JSON.parseObject(o.getRemark(), HashMap.class);
			Set couponsIdSet = new HashSet();
			String couponsName = "";
			for (Map.Entry<Integer, Integer> entry : couponsMap.entrySet()){
				MbShopCoupons mbShopCoupons = mbShopCouponsService.get(entry.getKey());
				if (F.empty(mbShopCoupons.getCouponsId()) || !couponsIdSet.add(mbShopCoupons.getCouponsId())) continue;
				MbCoupons coupons = mbCouponsService.get(mbShopCoupons.getCouponsId());
				if (coupons != null) {
					couponsName += coupons.getName() + ":" + entry.getValue() + "," ;
				}
			}
			o.setCouponsName(couponsName.substring(0,couponsName.length()-1));
		}
	}

	protected String whereHql(MbPaymentItem mbPaymentItem, Map<String, Object> params) {
		String whereHql = "";	
		if (mbPaymentItem != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(mbPaymentItem.getTenantId())) {
				whereHql += " and t.tenantId = :tenantId";
				params.put("tenantId", mbPaymentItem.getTenantId());
			}		
			if (!F.empty(mbPaymentItem.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", mbPaymentItem.getIsdeleted());
			}		
			if (!F.empty(mbPaymentItem.getPaymentId())) {
				whereHql += " and t.paymentId = :paymentId";
				params.put("paymentId", mbPaymentItem.getPaymentId());
			}		
			if (!F.empty(mbPaymentItem.getPayWay())) {
				whereHql += " and t.payWay = :payWay";
				params.put("payWay", mbPaymentItem.getPayWay());
			}		
			if (!F.empty(mbPaymentItem.getAmount())) {
				whereHql += " and t.amount = :amount";
				params.put("amount", mbPaymentItem.getAmount());
			}		
			if (!F.empty(mbPaymentItem.getRemitter())) {
				whereHql += " and t.remitter = :remitter";
				params.put("remitter", mbPaymentItem.getRemitter());
			}		
			if (!F.empty(mbPaymentItem.getRemark())) {
				whereHql += " and t.remark = :remark";
				params.put("remark", mbPaymentItem.getRemark());
			}		
			if (!F.empty(mbPaymentItem.getRefId())) {
				whereHql += " and t.refId = :refId";
				params.put("refId", mbPaymentItem.getRefId());
			}
			if (!F.empty(mbPaymentItem.getPayCode())) {
				whereHql += " and t.payCode = :payCode";
				params.put("payCode", mbPaymentItem.getPayCode());
			}
			if (!F.empty(mbPaymentItem.getBankCode())) {
				whereHql += " and t.bankCode = :bankCode";
				params.put("bankCode", mbPaymentItem.getBankCode());
			}
			if (!F.empty(mbPaymentItem.getCouponsId())) {
				whereHql += " and t.couponsId = :couponsId";
				params.put("couponsId", mbPaymentItem.getCouponsId());
			}
		}	
		return whereHql;
	}

	@Override
	public void add(MbPaymentItem mbPaymentItem) {
		TmbPaymentItem t = new TmbPaymentItem();
		BeanUtils.copyProperties(mbPaymentItem, t);
		//t.setId(jb.absx.UUID.uuid());
		t.setIsdeleted(false);
		mbPaymentItemDao.save(t);
		mbPaymentItem.setId(t.getId());
	}

	@Override
	public MbPaymentItem get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TmbPaymentItem t = mbPaymentItemDao.get("from TmbPaymentItem t  where t.id = :id", params);
		MbPaymentItem o = new MbPaymentItem();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	public MbPaymentItem getMbPaymentItemPW03(Integer id) {
		MbPayment mbPayment = mbPaymentService.getByOrderIdWithCache(id);
		List<MbPaymentItem> mbPaymentItemList = getByPaymentId(mbPayment.getId());
		for (MbPaymentItem p : mbPaymentItemList) {
			if(p.getPayWay().equals("PW03")) {
				return p;
			}
		}
		return null;

	}

	@Override
	@CacheEvict(value="PaymentItemGetByPaymentIdCache",key="#mbPaymentItem.getId()")
	public void edit(MbPaymentItem mbPaymentItem) {
		TmbPaymentItem t = mbPaymentItemDao.get(TmbPaymentItem.class, mbPaymentItem.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(mbPaymentItem, t, new String[]{"id", "addtime", "isdeleted", "updatetime"}, true);
		}
	}

	@Override
	public void editAudit(MbPaymentItem mbPaymentItem){
		/*
			1.从数据库中获取要修改的PaymentItem记录中得到银行bankcode
			2.从前端得到paycode
			3.通过以上两个参数去数据库查找是否存在同时拥有两点的记录存在
			4.若存在则不通过,不存在则继续
			5.不通过:通过抛出异常,在controller中catch异常
			6.继续:将上面两个参数同时去MbRechargeLog表中查询
			7.若存在则不通过,不存在则继续
			8.不通过:通过抛出异常,在controller中catch异常
			9.继续:将付款成功更新TmbPaymentItem记录
		*/
		TmbPaymentItem t = mbPaymentItemDao.get(TmbPaymentItem.class, mbPaymentItem.getId());
		MbPaymentItem paymentItem = new MbPaymentItem();
		paymentItem.setBankCode(t.getBankCode());
		paymentItem.setPayCode(mbPaymentItem.getPayCode());

		MbRechargeLog rechargeLog = new MbRechargeLog();
		rechargeLog.setBankCode(t.getBankCode());
		rechargeLog.setPayCode(mbPaymentItem.getPayCode());
		rechargeLog.setHandleStatus("HS02");
		//TODO
		synchronized (this) {
			List<MbPaymentItem> paymentItems = listMbPaymentItem(paymentItem);
			List<MbRechargeLog> rechargeLogs = mbRechargeLogService.listMbRechargeLog(rechargeLog);
			if (CollectionUtils.isEmpty(paymentItems) && CollectionUtils.isEmpty(rechargeLogs)) {
				edit(mbPaymentItem);
			} else {
				throw new ServiceException("银行汇款单号已存在,请重新确认!");
			}
		}
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		mbPaymentItemDao.executeHql("update TmbPaymentItem t set t.isdeleted = 1 where t.id = :id",params);
		//mbPaymentItemDao.delete(mbPaymentItemDao.get(TmbPaymentItem.class, id));
	}

	@Override
	@Cacheable(value = "PaymentItemGetByPaymentIdCache", key = "#paymentId")
	public List<MbPaymentItem> getByPaymentId(Integer paymentId) {
		List<TmbPaymentItem> tmbPaymentItemList = mbPaymentItemDao.find("from TmbPaymentItem t where t.paymentId = " + paymentId);
		List<MbPaymentItem> mbPaymentItemList = new ArrayList<>();
		for (TmbPaymentItem tmbPaymentItem : tmbPaymentItemList) {
			MbPaymentItem mbPaymentItem = new MbPaymentItem();
			BeanUtils.copyProperties(tmbPaymentItem, mbPaymentItem);
			mbPaymentItemList.add(mbPaymentItem);
		}
		return mbPaymentItemList;
	}
	@Override
	public List<MbPaymentItem> listMbPaymentItem(MbPaymentItem mbPaymentItem){
		String hql = " from TmbPaymentItem t ";
		List<MbPaymentItem> ol= new ArrayList<MbPaymentItem>();
		Map<String, Object> params = new HashMap<String, Object>();
		String whereHql = whereHql(mbPaymentItem, params);
		List<TmbPaymentItem> ts = mbPaymentItemDao.find(hql + whereHql,params);
		for (TmbPaymentItem t : ts
			 ) {
			MbPaymentItem o = new MbPaymentItem();
			BeanUtils.copyProperties(t,o);
			ol.add(o);
		}
		return ol;
	}
}
