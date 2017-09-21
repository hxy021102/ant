package com.bx.ant.service.impl;

import com.mobian.absx.F;
import com.bx.ant.dao.DeliverOrderDaoI;
import com.bx.ant.model.TdeliverOrder;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.DeliverOrder;
import com.mobian.pageModel.PageHelper;
import com.bx.ant.service.DeliverOrderServiceI;
import com.mobian.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DeliverOrderServiceImpl extends BaseServiceImpl<DeliverOrder> implements DeliverOrderServiceI {

	@Autowired
	private DeliverOrderDaoI deliverOrderDao;

	@Override
	public DataGrid dataGrid(DeliverOrder deliverOrder, PageHelper ph) {
		List<DeliverOrder> ol = new ArrayList<DeliverOrder>();
		String hql = " from TdeliverOrder t ";
		DataGrid dg = dataGridQuery(hql, ph, deliverOrder, deliverOrderDao);
		@SuppressWarnings("unchecked")
		List<TdeliverOrder> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TdeliverOrder t : l) {
				DeliverOrder o = new DeliverOrder();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(DeliverOrder deliverOrder, Map<String, Object> params) {
		String whereHql = "";	
		if (deliverOrder != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(deliverOrder.getTenantId())) {
				whereHql += " and t.tenantId = :tenantId";
				params.put("tenantId", deliverOrder.getTenantId());
			}		
			if (!F.empty(deliverOrder.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", deliverOrder.getIsdeleted());
			}		
			if (!F.empty(deliverOrder.getSupplierId())) {
				whereHql += " and t.supplierId = :supplierId";
				params.put("supplierId", deliverOrder.getSupplierId());
			}		
			if (!F.empty(deliverOrder.getAmount())) {
				whereHql += " and t.amount = :amount";
				params.put("amount", deliverOrder.getAmount());
			}		
			if (!F.empty(deliverOrder.getStatus())) {
				whereHql += " and t.status = :status";
				params.put("status", deliverOrder.getStatus());
			}		
			if (!F.empty(deliverOrder.getDeliveryStatus())) {
				whereHql += " and t.deliveryStatus = :deliveryStatus";
				params.put("deliveryStatus", deliverOrder.getDeliveryStatus());
			}		
			if (!F.empty(deliverOrder.getDeliveryAddress())) {
				whereHql += " and t.deliveryAddress = :deliveryAddress";
				params.put("deliveryAddress", deliverOrder.getDeliveryAddress());
			}		
			if (!F.empty(deliverOrder.getDeliveryRegion())) {
				whereHql += " and t.deliveryRegion = :deliveryRegion";
				params.put("deliveryRegion", deliverOrder.getDeliveryRegion());
			}		
			if (!F.empty(deliverOrder.getPayStatus())) {
				whereHql += " and t.payStatus = :payStatus";
				params.put("payStatus", deliverOrder.getPayStatus());
			}		
			if (!F.empty(deliverOrder.getShopPayStatus())) {
				whereHql += " and t.shopPayStatus = :shopPayStatus";
				params.put("shopPayStatus", deliverOrder.getShopPayStatus());
			}		
			if (!F.empty(deliverOrder.getPayWay())) {
				whereHql += " and t.payWay = :payWay";
				params.put("payWay", deliverOrder.getPayWay());
			}		
			if (!F.empty(deliverOrder.getContactPhone())) {
				whereHql += " and t.contactPhone = :contactPhone";
				params.put("contactPhone", deliverOrder.getContactPhone());
			}		
			if (!F.empty(deliverOrder.getContactPeople())) {
				whereHql += " and t.contactPeople = :contactPeople";
				params.put("contactPeople", deliverOrder.getContactPeople());
			}		
			if (!F.empty(deliverOrder.getRemark())) {
				whereHql += " and t.remark = :remark";
				params.put("remark", deliverOrder.getRemark());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(DeliverOrder deliverOrder) {
		TdeliverOrder t = new TdeliverOrder();
		BeanUtils.copyProperties(deliverOrder, t);
		//t.setId(jb.absx.UUID.uuid());
		t.setIsdeleted(false);
		deliverOrderDao.save(t);
	}

	@Override
	public DeliverOrder get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TdeliverOrder t = deliverOrderDao.get("from TdeliverOrder t  where t.id = :id", params);
		DeliverOrder o = new DeliverOrder();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(DeliverOrder deliverOrder) {
		TdeliverOrder t = deliverOrderDao.get(TdeliverOrder.class, deliverOrder.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(deliverOrder, t, new String[] { "id" , "addtime", "isdeleted","updatetime" },true);
		}
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		deliverOrderDao.executeHql("update TdeliverOrder t set t.isdeleted = 1 where t.id = :id",params);
		//deliverOrderDao.delete(deliverOrderDao.get(TdeliverOrder.class, id));
	}

}
