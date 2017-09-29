package com.bx.ant.service.impl;

import com.mobian.absx.F;
import com.bx.ant.dao.DeliverOrderShopDaoI;
import com.bx.ant.model.TdeliverOrderShop;
import com.mobian.exception.ServiceException;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.DeliverOrder;
import com.mobian.pageModel.DeliverOrderShop;
import com.mobian.pageModel.PageHelper;
import com.bx.ant.service.DeliverOrderShopServiceI;
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
public class DeliverOrderShopServiceImpl extends BaseServiceImpl<DeliverOrderShop> implements DeliverOrderShopServiceI {

	@Autowired
	private DeliverOrderShopDaoI deliverOrderShopDao;

	@Override
	public DataGrid dataGrid(DeliverOrderShop deliverOrderShop, PageHelper ph) {
		List<DeliverOrderShop> ol = new ArrayList<DeliverOrderShop>();
		String hql = " from TdeliverOrderShop t ";
		DataGrid dg = dataGridQuery(hql, ph, deliverOrderShop, deliverOrderShopDao);
		@SuppressWarnings("unchecked")
		List<TdeliverOrderShop> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TdeliverOrderShop t : l) {
				DeliverOrderShop o = new DeliverOrderShop();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(DeliverOrderShop deliverOrderShop, Map<String, Object> params) {
		String whereHql = "";	
		if (deliverOrderShop != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(deliverOrderShop.getTenantId())) {
				whereHql += " and t.tenantId = :tenantId";
				params.put("tenantId", deliverOrderShop.getTenantId());
			}		
			if (!F.empty(deliverOrderShop.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", deliverOrderShop.getIsdeleted());
			}		
			if (!F.empty(deliverOrderShop.getDeliverOrderId())) {
				whereHql += " and t.deliverOrderId = :deliverOrderId";
				params.put("deliverOrderId", deliverOrderShop.getDeliverOrderId());
			}		
			if (!F.empty(deliverOrderShop.getShopId())) {
				whereHql += " and t.shopId = :shopId";
				params.put("shopId", deliverOrderShop.getShopId());
			}		
			if (!F.empty(deliverOrderShop.getStatus())) {
				whereHql += " and t.status = :status";
				params.put("status", deliverOrderShop.getStatus());
			}		
			if (!F.empty(deliverOrderShop.getAmount())) {
				whereHql += " and t.amount = :amount";
				params.put("amount", deliverOrderShop.getAmount());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(DeliverOrderShop deliverOrderShop) {
		TdeliverOrderShop t = new TdeliverOrderShop();
		BeanUtils.copyProperties(deliverOrderShop, t);
		//t.setId(jb.absx.UUID.uuid());
		t.setIsdeleted(false);
		deliverOrderShopDao.save(t);
		deliverOrderShop.setId(t.getId());
	}

	@Override
	public DeliverOrderShop get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TdeliverOrderShop t = deliverOrderShopDao.get("from TdeliverOrderShop t  where t.id = :id", params);
		DeliverOrderShop o = new DeliverOrderShop();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(DeliverOrderShop deliverOrderShop) {
		TdeliverOrderShop t = deliverOrderShopDao.get(TdeliverOrderShop.class, deliverOrderShop.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(deliverOrderShop, t, new String[] { "id" , "addtime", "isdeleted","updatetime" },true);
		}
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		deliverOrderShopDao.executeHql("update TdeliverOrderShop t set t.isdeleted = 1 where t.id = :id",params);
		//deliverOrderShopDao.delete(deliverOrderShopDao.get(TdeliverOrderShop.class, id));
	}

	@Override
	public DeliverOrderShop addByDeliverOrder(DeliverOrder deliverOrder) {
		//TODO shop状态?true or false
		DeliverOrderShop deliverOrderShop = new DeliverOrderShop();
		deliverOrderShop.setAmount(deliverOrder.getAmount());
		deliverOrderShop.setDeliverOrderId(deliverOrder.getId());
		deliverOrderShop.setShopId(deliverOrder.getShopId());
		deliverOrderShop.setStatus(STATUS_AUDITING);
		add(deliverOrderShop);
		return deliverOrderShop;
	}

	@Override
	public List<DeliverOrderShop> list(DeliverOrderShop deliverOrderShop) {
		List<DeliverOrderShop> ol = new ArrayList<DeliverOrderShop>();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from TdeliverOrderShop t ";
		String where = whereHql(deliverOrderShop, params);
		List<TdeliverOrderShop> l = deliverOrderShopDao.find(hql + where, params);
		if (CollectionUtils.isNotEmpty(l)) {
			for (TdeliverOrderShop t : l) {
				DeliverOrderShop o = new DeliverOrderShop();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}
	@Override
	public DeliverOrderShop editStatus(DeliverOrderShop deliverOrderShop, String status) {
		List<DeliverOrderShop> deliverOrderShops = list(deliverOrderShop);
		DeliverOrderShop o = new DeliverOrderShop();
		if (CollectionUtils.isNotEmpty(deliverOrderShops)) {
			//TODO 只对第一个结果进行处理
			o = deliverOrderShops.get(0);
			o.setStatus(status);
			edit(o);
		} else {
			throw new ServiceException("请确认门店订单是否存在");
		}
		return o;

	}


}
