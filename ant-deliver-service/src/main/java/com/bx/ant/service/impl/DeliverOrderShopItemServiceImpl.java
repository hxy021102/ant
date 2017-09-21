package com.bx.ant.service.impl;

import com.mobian.absx.F;
import com.bx.ant.dao.DeliverOrderShopItemDaoI;
import com.bx.ant.model.TdeliverOrderShopItem;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.DeliverOrderShopItem;
import com.mobian.pageModel.PageHelper;
import com.bx.ant.service.DeliverOrderShopItemServiceI;
import com.mobian.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DeliverOrderShopItemServiceImpl extends BaseServiceImpl<DeliverOrderShopItem> implements DeliverOrderShopItemServiceI {

	@Autowired
	private DeliverOrderShopItemDaoI deliverOrderShopItemDao;

	@Override
	public DataGrid dataGrid(DeliverOrderShopItem deliverOrderShopItem, PageHelper ph) {
		List<DeliverOrderShopItem> ol = new ArrayList<DeliverOrderShopItem>();
		String hql = " from TdeliverOrderShopItem t ";
		DataGrid dg = dataGridQuery(hql, ph, deliverOrderShopItem, deliverOrderShopItemDao);
		@SuppressWarnings("unchecked")
		List<TdeliverOrderShopItem> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TdeliverOrderShopItem t : l) {
				DeliverOrderShopItem o = new DeliverOrderShopItem();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(DeliverOrderShopItem deliverOrderShopItem, Map<String, Object> params) {
		String whereHql = "";	
		if (deliverOrderShopItem != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(deliverOrderShopItem.getTenantId())) {
				whereHql += " and t.tenantId = :tenantId";
				params.put("tenantId", deliverOrderShopItem.getTenantId());
			}		
			if (!F.empty(deliverOrderShopItem.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", deliverOrderShopItem.getIsdeleted());
			}		
			if (!F.empty(deliverOrderShopItem.getDeliverOrderShopId())) {
				whereHql += " and t.deliverOrderShopId = :deliverOrderShopId";
				params.put("deliverOrderShopId", deliverOrderShopItem.getDeliverOrderShopId());
			}		
			if (!F.empty(deliverOrderShopItem.getDeliverOrderId())) {
				whereHql += " and t.deliverOrderId = :deliverOrderId";
				params.put("deliverOrderId", deliverOrderShopItem.getDeliverOrderId());
			}		
			if (!F.empty(deliverOrderShopItem.getShopId())) {
				whereHql += " and t.shopId = :shopId";
				params.put("shopId", deliverOrderShopItem.getShopId());
			}		
			if (!F.empty(deliverOrderShopItem.getItemId())) {
				whereHql += " and t.itemId = :itemId";
				params.put("itemId", deliverOrderShopItem.getItemId());
			}		
			if (!F.empty(deliverOrderShopItem.getPrice())) {
				whereHql += " and t.price = :price";
				params.put("price", deliverOrderShopItem.getPrice());
			}		
			if (!F.empty(deliverOrderShopItem.getInPrice())) {
				whereHql += " and t.inPrice = :inPrice";
				params.put("inPrice", deliverOrderShopItem.getInPrice());
			}		
			if (!F.empty(deliverOrderShopItem.getFreight())) {
				whereHql += " and t.freight = :freight";
				params.put("freight", deliverOrderShopItem.getFreight());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(DeliverOrderShopItem deliverOrderShopItem) {
		TdeliverOrderShopItem t = new TdeliverOrderShopItem();
		BeanUtils.copyProperties(deliverOrderShopItem, t);
		//t.setId(jb.absx.UUID.uuid());
		t.setIsdeleted(false);
		deliverOrderShopItemDao.save(t);
	}

	@Override
	public DeliverOrderShopItem get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TdeliverOrderShopItem t = deliverOrderShopItemDao.get("from TdeliverOrderShopItem t  where t.id = :id", params);
		DeliverOrderShopItem o = new DeliverOrderShopItem();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(DeliverOrderShopItem deliverOrderShopItem) {
		TdeliverOrderShopItem t = deliverOrderShopItemDao.get(TdeliverOrderShopItem.class, deliverOrderShopItem.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(deliverOrderShopItem, t, new String[] { "id" , "addtime", "isdeleted","updatetime" },true);
		}
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		deliverOrderShopItemDao.executeHql("update TdeliverOrderShopItem t set t.isdeleted = 1 where t.id = :id",params);
		//deliverOrderShopItemDao.delete(deliverOrderShopItemDao.get(TdeliverOrderShopItem.class, id));
	}

}
