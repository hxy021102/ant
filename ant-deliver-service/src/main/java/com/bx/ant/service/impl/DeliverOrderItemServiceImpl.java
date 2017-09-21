package com.bx.ant.service.impl;

import com.bx.ant.dao.DeliverOrderItemDaoI;
import com.bx.ant.model.TdeliverOrderItem;
import com.mobian.pageModel.DeliverOrderItem;
import com.bx.ant.service.DeliverOrderItemServiceI;
import com.mobian.absx.F;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;
import com.mobian.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DeliverOrderItemServiceImpl extends BaseServiceImpl<DeliverOrderItem> implements DeliverOrderItemServiceI {

	@Autowired
	private DeliverOrderItemDaoI deliverOrderItemDao;

	@Override
	public DataGrid dataGrid(DeliverOrderItem deliverOrderItem, PageHelper ph) {
		List<DeliverOrderItem> ol = new ArrayList<DeliverOrderItem>();
		String hql = " from TdeliverOrderItem t ";
		DataGrid dg = dataGridQuery(hql, ph, deliverOrderItem, deliverOrderItemDao);
		@SuppressWarnings("unchecked")
		List<TdeliverOrderItem> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TdeliverOrderItem t : l) {
				DeliverOrderItem o = new DeliverOrderItem();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(DeliverOrderItem deliverOrderItem, Map<String, Object> params) {
		String whereHql = "";	
		if (deliverOrderItem != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(deliverOrderItem.getTenantId())) {
				whereHql += " and t.tenantId = :tenantId";
				params.put("tenantId", deliverOrderItem.getTenantId());
			}		
			if (!F.empty(deliverOrderItem.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", deliverOrderItem.getIsdeleted());
			}		
			if (!F.empty(deliverOrderItem.getDeliverOrderId())) {
				whereHql += " and t.deliverOrderId = :deliverOrderId";
				params.put("deliverOrderId", deliverOrderItem.getDeliverOrderId());
			}		
			if (!F.empty(deliverOrderItem.getItemId())) {
				whereHql += " and t.itemId = :itemId";
				params.put("itemId", deliverOrderItem.getItemId());
			}		
			if (!F.empty(deliverOrderItem.getPrice())) {
				whereHql += " and t.price = :price";
				params.put("price", deliverOrderItem.getPrice());
			}		
			if (!F.empty(deliverOrderItem.getInPrice())) {
				whereHql += " and t.inPrice = :inPrice";
				params.put("inPrice", deliverOrderItem.getInPrice());
			}		
			if (!F.empty(deliverOrderItem.getFreight())) {
				whereHql += " and t.freight = :freight";
				params.put("freight", deliverOrderItem.getFreight());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(DeliverOrderItem deliverOrderItem) {
		TdeliverOrderItem t = new TdeliverOrderItem();
		BeanUtils.copyProperties(deliverOrderItem, t);
		//t.setId(jb.absx.UUID.uuid());
		t.setIsdeleted(false);
		deliverOrderItemDao.save(t);
	}

	@Override
	public DeliverOrderItem get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TdeliverOrderItem t = deliverOrderItemDao.get("from TdeliverOrderItem t  where t.id = :id", params);
		DeliverOrderItem o = new DeliverOrderItem();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(DeliverOrderItem deliverOrderItem) {
		TdeliverOrderItem t = deliverOrderItemDao.get(TdeliverOrderItem.class, deliverOrderItem.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(deliverOrderItem, t, new String[]{"id", "addtime", "isdeleted", "updatetime"}, true);
		}
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		deliverOrderItemDao.executeHql("update TdeliverOrderItem t set t.isdeleted = 1 where t.id = :id",params);
		//deliverOrderItemDao.delete(deliverOrderItemDao.get(TdeliverOrderItem.class, id));
	}

}
