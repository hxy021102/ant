package com.bx.ant.service.impl;

import com.bx.ant.pageModel.session.DeliverOrderPayExt;
import com.mobian.absx.F;
import com.bx.ant.dao.DeliverOrderPayDaoI;
import com.bx.ant.model.TdeliverOrderPay;
import com.mobian.pageModel.DataGrid;
import com.bx.ant.pageModel.DeliverOrderPay;
import com.mobian.pageModel.PageHelper;
import com.bx.ant.service.DeliverOrderPayServiceI;
import com.mobian.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DeliverOrderPayServiceImpl extends BaseServiceImpl<DeliverOrderPay> implements DeliverOrderPayServiceI {

	@Autowired
	private DeliverOrderPayDaoI deliverOrderPayDao;

	@Override
	public DataGrid dataGrid(DeliverOrderPay deliverOrderPay, PageHelper ph) {
		List<DeliverOrderPay> ol = new ArrayList<DeliverOrderPay>();
		String hql = " from TdeliverOrderPay t ";
		DataGrid dg = dataGridQuery(hql, ph, deliverOrderPay, deliverOrderPayDao);
		@SuppressWarnings("unchecked")
		List<TdeliverOrderPay> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TdeliverOrderPay t : l) {
				DeliverOrderPay o = new DeliverOrderPay();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(DeliverOrderPay deliverOrderPay, Map<String, Object> params) {
		String whereHql = "";	
		if (deliverOrderPay != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(deliverOrderPay.getTenantId())) {
				whereHql += " and t.tenantId = :tenantId";
				params.put("tenantId", deliverOrderPay.getTenantId());
			}		
			if (!F.empty(deliverOrderPay.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", deliverOrderPay.getIsdeleted());
			}		
			if (!F.empty(deliverOrderPay.getDeliverOrderId())) {
				whereHql += " and t.deliverOrderId = :deliverOrderId";
				params.put("deliverOrderId", deliverOrderPay.getDeliverOrderId());
			}		
			if (!F.empty(deliverOrderPay.getSupplierId())) {
				whereHql += " and t.supplierId = :supplierId";
				params.put("supplierId", deliverOrderPay.getSupplierId());
			}		
			if (!F.empty(deliverOrderPay.getStatus())) {
				whereHql += " and t.status = :status";
				params.put("status", deliverOrderPay.getStatus());
			}		
			if (!F.empty(deliverOrderPay.getAmount())) {
				whereHql += " and t.amount = :amount";
				params.put("amount", deliverOrderPay.getAmount());
			}		
			if (!F.empty(deliverOrderPay.getPayWay())) {
				whereHql += " and t.payWay = :payWay";
				params.put("payWay", deliverOrderPay.getPayWay());
			}
			if (!F.empty(deliverOrderPay.getSupplierOrderBillId())) {
				whereHql += " and t.supplierOrderBillId = :supplierOrderBillId";
				params.put("supplierOrderBillId",deliverOrderPay.getSupplierOrderBillId());
			}
		}	
		return whereHql;
	}

	@Override
	public void add(DeliverOrderPay deliverOrderPay) {
		TdeliverOrderPay t = new TdeliverOrderPay();
		BeanUtils.copyProperties(deliverOrderPay, t);
		//t.setId(jb.absx.UUID.uuid());
		t.setIsdeleted(false);
		deliverOrderPayDao.save(t);
	}

	@Override
	public DeliverOrderPay get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TdeliverOrderPay t = deliverOrderPayDao.get("from TdeliverOrderPay t  where t.id = :id", params);
		DeliverOrderPay o = new DeliverOrderPay();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(DeliverOrderPay deliverOrderPay) {
		TdeliverOrderPay t = deliverOrderPayDao.get(TdeliverOrderPay.class, deliverOrderPay.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(deliverOrderPay, t, new String[] { "id" , "addtime", "isdeleted","updatetime" },true);
		}
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		deliverOrderPayDao.executeHql("update TdeliverOrderPay t set t.isdeleted = 1 where t.id = :id",params);
		//deliverOrderPayDao.delete(deliverOrderPayDao.get(TdeliverOrderPay.class, id));
	}

	@Override
	public List<DeliverOrderPay> getBySupplierOrderBillId(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("supplierOrderBillId", id);
		List<DeliverOrderPay> l = new ArrayList<DeliverOrderPay>();
	 	List<TdeliverOrderPay> list = deliverOrderPayDao.find("from TdeliverOrderPay t  where t.supplierOrderBillId = :supplierOrderBillId",params);
	 	for(TdeliverOrderPay t : list) {
	 		DeliverOrderPay d = new DeliverOrderPay();
	 		BeanUtils.copyProperties(t,d);
	 		l.add(d);
		}
		return l;
	}
}
