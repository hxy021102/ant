package com.mobian.service.impl;

import com.mobian.absx.F;
import com.mobian.dao.MbPaymentDaoI;
import com.mobian.model.TmbPayment;
import com.mobian.pageModel.*;
import com.mobian.service.MbOrderServiceI;
import com.mobian.service.MbPaymentItemServiceI;
import com.mobian.service.MbPaymentServiceI;
import com.mobian.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MbPaymentServiceImpl extends BaseServiceImpl<MbPayment> implements MbPaymentServiceI {

	@Autowired
	private MbPaymentDaoI mbPaymentDao;

	@Autowired
	private MbPaymentItemServiceI mbPaymentItemService;

	@Autowired
	private MbOrderServiceI mbOrderService;

	@Override
	public DataGrid dataGrid(MbPayment mbPayment, PageHelper ph) {
		List<MbPayment> ol = new ArrayList<MbPayment>();
		String hql = " from TmbPayment t ";
		DataGrid dg = dataGridQuery(hql, ph, mbPayment, mbPaymentDao);
		@SuppressWarnings("unchecked")
		List<TmbPayment> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TmbPayment t : l) {
				MbPayment o = new MbPayment();
				BeanUtils.copyProperties(t, o);

				fillPaymentItemInfo(o);

				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}

	private void fillPaymentItemInfo(MbPayment mbPayment) {
		List<MbPaymentItem> mbPaymentItemList = mbPaymentItemService.getByPaymentId(mbPayment.getId());
		mbPayment.setMbPaymentItems(mbPaymentItemList);
		StringBuilder remitter = new StringBuilder("");
		StringBuilder remitterTime = new StringBuilder("");
		StringBuilder remark = new StringBuilder("");
		StringBuilder refId = new StringBuilder("");
		String bankCode = null;
		for (MbPaymentItem mbPaymentItem : mbPaymentItemList) {
			//payConsist.append(mbPaymentItem.getPayWayName()).append(mbPaymentItem.getAmount()).append(";");
			if (!F.empty(mbPaymentItem.getRemitter())) {
				remitter.append(mbPaymentItem.getRemitter()).append(";");
			}
			if (mbPaymentItem.getRemitterTime() != null) {
				String remitterTimeStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(mbPaymentItem.getRemitterTime());
				if (!F.empty(remitterTimeStr)) {
					remitterTime.append(remitterTimeStr).append(";");
				}
			}
			if (!F.empty(mbPaymentItem.getRemark())) {
				remark.append(mbPaymentItem.getRemark()).append(";");
			}
			if (!F.empty(mbPaymentItem.getRefId())) {
				refId.append(mbPaymentItem.getRefId()).append(";");
			}
			if(!F.empty(mbPaymentItem.getBankCode()))
				bankCode = mbPaymentItem.getBankCode();
		}
		if (mbPaymentItemList.size() > 0) {
			if (!F.empty(remitter.toString())) {
				mbPayment.setRemitter(remitter.toString().substring(0, remitter.toString().length() - 1));
			} else {
				mbPayment.setRemitter("");
			}
			if (!F.empty(remitterTime.toString())) {
				mbPayment.setRemitterTime(remitterTime.toString().substring(0, remitterTime.toString().length() - 1));
			} else {
				mbPayment.setRemitterTime("");
			}
			if (!F.empty(remark.toString())) {
				mbPayment.setRemark(remark.toString().substring(0, remark.toString().length() - 1));
			} else {
				mbPayment.setRemark("");
			}
			if (!F.empty(refId.toString())) {
				mbPayment.setRefId(refId.toString().substring(0, refId.toString().length() - 1));
			} else {
				mbPayment.setRefId("");
			}
			mbPayment.setBankCode(bankCode);
		}
	}

	protected String whereHql(MbPayment mbPayment, Map<String, Object> params) {
		String whereHql = "";	
		if (mbPayment != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(mbPayment.getId())) {
				whereHql += " and t.id = :id";
				params.put("id", mbPayment.getId());
			}
			if (!F.empty(mbPayment.getTenantId())) {
				whereHql += " and t.tenantId = :tenantId";
				params.put("tenantId", mbPayment.getTenantId());
			}		
			if (!F.empty(mbPayment.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", mbPayment.getIsdeleted());
			}		
			if (!F.empty(mbPayment.getOrderId())) {
				whereHql += " and t.orderId = :orderId";
				params.put("orderId", mbPayment.getOrderId());
			}		
			if (!F.empty(mbPayment.getOrderType())) {
				whereHql += " and t.orderType = :orderType";
				params.put("orderType", mbPayment.getOrderType());
			}		
			if (!F.empty(mbPayment.getAmount())) {
				whereHql += " and t.amount = :amount";
				params.put("amount", mbPayment.getAmount());
			}		
			if (!F.empty(mbPayment.getPayWay())) {
				whereHql += " and t.payWay = :payWay";
				params.put("payWay", mbPayment.getPayWay());
			}		
			if (!F.empty(mbPayment.getStatus())) {
				whereHql += " and t.status = :status";
				params.put("status", mbPayment.getStatus());
			}		
			if (!F.empty(mbPayment.getReason())) {
				whereHql += " and t.reason = :reason";
				params.put("reason", mbPayment.getReason());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(MbPayment mbPayment) {
		TmbPayment t = new TmbPayment();
		BeanUtils.copyProperties(mbPayment, t);
		//t.setId(jb.absx.UUID.uuid());
		t.setIsdeleted(false);
		mbPaymentDao.save(t);
		mbPayment.setId(t.getId());
	}

	@Override
	public MbPayment get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TmbPayment t = mbPaymentDao.get("from TmbPayment t  where t.id = :id", params);
		MbPayment o = new MbPayment();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(MbPayment mbPayment) {
		TmbPayment t = mbPaymentDao.get(TmbPayment.class, mbPayment.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(mbPayment, t, new String[]{"id", "addtime", "isdeleted", "updatetime"}, true);
		}
	}

	@Override
	public void addOrUpdate(MbPayment payment) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderId", payment.getOrderId());
		TmbPayment t = mbPaymentDao.get("from TmbPayment t where t.orderId = :orderId", params);
		boolean transformFlag = true;
		if(t == null) {
			t = new TmbPayment();
			BeanUtils.copyProperties(payment, t);
			t.setIsdeleted(false);
			mbPaymentDao.save(t);
		} else {
			if(t.getStatus()) transformFlag = false; // 防止重复支付
			payment.setUpdatetime(new Date());
			MyBeanUtils.copyProperties(payment, t, new String[]{"id"}, true);
		}
		if(transformFlag) {
			MbOrder order = new MbOrder();
			order.setId(payment.getOrderId());
			order.setPayWay(t.getPayWay());
			if("PW03".equals(t.getPayWay())) {
				order.setStatus("OD05"); // 转账汇款
			}else if("PW02".equals(t.getPayWay())){
				payment.setAmount(t.getAmount());
				order.setStatus("OD10"); // 微信
			}else {
				order.setStatus("OD10"); // 微信、余额支付
			}

			order.setPaymentId(t.getId());
			order.setRemitter(payment.getRemitter());
			order.setRemitterTime(payment.getRemitterTime());
			order.setRemark(payment.getRemark());
			order.setRefId(payment.getRefId());
			order.setBankCode(payment.getBankCode());
//			order.setShopId(payment.getShopId());
			order.setTotalPrice(payment.getAmount());
			mbOrderService.transform(order);
		}
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		mbPaymentDao.executeHql("update TmbPayment t set t.isdeleted = 1 where t.id = :id",params);
		//mbPaymentDao.delete(mbPaymentDao.get(TmbPayment.class, id));
	}

	@Override
	public MbPayment getByOrderId(Integer orderId) {
		TmbPayment tmbPayment = mbPaymentDao.get("from TmbPayment t where t.orderId = " + orderId);
		if (tmbPayment != null) {
			MbPayment mbPayment = new MbPayment();
			BeanUtils.copyProperties(tmbPayment, mbPayment);
			return mbPayment;
		}
		return null;
	}

	@Override
	@Cacheable(value = "paymentGetByOrderIdCache", key = "#orderId")
	public MbPayment getByOrderIdWithCache(Integer orderId) {
		return getByOrderId(orderId);
	}

	@Override
	public MbPayment getFromCache(Integer id) {
		TmbPayment source = mbPaymentDao.getById(id);
		MbPayment target = new MbPayment();
		BeanUtils.copyProperties(source, target);
		return target;
	}

}
