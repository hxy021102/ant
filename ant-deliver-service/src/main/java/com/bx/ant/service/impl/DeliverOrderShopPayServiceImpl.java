package com.bx.ant.service.impl;

import com.bx.ant.pageModel.*;
import com.mobian.absx.F;
import com.bx.ant.dao.DeliverOrderShopPayDaoI;
import com.bx.ant.model.TdeliverOrderShopPay;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbShop;
import com.mobian.pageModel.PageHelper;
import com.bx.ant.service.DeliverOrderShopPayServiceI;
import com.mobian.service.MbShopServiceI;
import com.mobian.util.MyBeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DeliverOrderShopPayServiceImpl extends BaseServiceImpl<DeliverOrderShopPay> implements DeliverOrderShopPayServiceI {

	@Autowired
	private DeliverOrderShopPayDaoI deliverOrderShopPayDao;
    @Resource
	private MbShopServiceI mbShopService;
	@Override
	public DataGrid dataGrid(DeliverOrderShopPay deliverOrderShopPay, PageHelper ph) {
		List<DeliverOrderShopPay> ol = new ArrayList<DeliverOrderShopPay>();
		String hql = " from TdeliverOrderShopPay t ";
		DataGrid dg = dataGridQuery(hql, ph, deliverOrderShopPay, deliverOrderShopPayDao);
		@SuppressWarnings("unchecked")
		List<TdeliverOrderShopPay> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TdeliverOrderShopPay t : l) {
				DeliverOrderShopPay o = new DeliverOrderShopPay();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(DeliverOrderShopPay deliverOrderShopPay, Map<String, Object> params) {
		String whereHql = "";	
		if (deliverOrderShopPay != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(deliverOrderShopPay.getTenantId())) {
				whereHql += " and t.tenantId = :tenantId";
				params.put("tenantId", deliverOrderShopPay.getTenantId());
			}		
			if (!F.empty(deliverOrderShopPay.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", deliverOrderShopPay.getIsdeleted());
			}		
			if (!F.empty(deliverOrderShopPay.getDeliverOrderShopId())) {
				whereHql += " and t.deliverOrderShopId = :deliverOrderShopId";
				params.put("deliverOrderShopId", deliverOrderShopPay.getDeliverOrderShopId());
			}		
			if (!F.empty(deliverOrderShopPay.getDeliverOrderId())) {
				whereHql += " and t.deliverOrderId = :deliverOrderId";
				params.put("deliverOrderId", deliverOrderShopPay.getDeliverOrderId());
			}		
			if (!F.empty(deliverOrderShopPay.getShopId())) {
				whereHql += " and t.shopId = :shopId";
				params.put("shopId", deliverOrderShopPay.getShopId());
			}		
			if (!F.empty(deliverOrderShopPay.getStatus())) {
				whereHql += " and t.status = :status";
				params.put("status", deliverOrderShopPay.getStatus());
			}		
			if (!F.empty(deliverOrderShopPay.getAmount())) {
				whereHql += " and t.amount = :amount";
				params.put("amount", deliverOrderShopPay.getAmount());
			}		
			if (!F.empty(deliverOrderShopPay.getPayWay())) {
				whereHql += " and t.payWay = :payWay";
				params.put("payWay", deliverOrderShopPay.getPayWay());
			}
			if (!F.empty(deliverOrderShopPay.getShopOrderBillId())) {
				whereHql += " and t.shopOrderBillId = :shopOrderBillId";
				params.put("shopOrderBillId", deliverOrderShopPay.getShopOrderBillId());
			}
			if(deliverOrderShopPay instanceof  DeliverOrderShopPayQuery) {
				DeliverOrderShopPayQuery deliverOrderShopPayQuery=(DeliverOrderShopPayQuery)deliverOrderShopPay;
				if (deliverOrderShopPayQuery.getDeliverOrderIds() != null && deliverOrderShopPayQuery.getDeliverOrderIds().length > 0) {
					whereHql += " and t.deliverOrderId in(:deliverOrderIds) ";
					params.put("deliverOrderIds",deliverOrderShopPayQuery.getDeliverOrderIds());
				}
			}
		}	
		return whereHql;
	}

	@Override
	public void add(DeliverOrderShopPay deliverOrderShopPay) {
		TdeliverOrderShopPay t = new TdeliverOrderShopPay();
		BeanUtils.copyProperties(deliverOrderShopPay, t);
		//t.setId(jb.absx.UUID.uuid());
		t.setIsdeleted(false);
		deliverOrderShopPayDao.save(t);
	}

	@Override
	public DeliverOrderShopPay get(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TdeliverOrderShopPay t = deliverOrderShopPayDao.get("from TdeliverOrderShopPay t  where t.id = :id", params);
		DeliverOrderShopPay o = new DeliverOrderShopPay();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(DeliverOrderShopPay deliverOrderShopPay) {
		TdeliverOrderShopPay t = deliverOrderShopPayDao.get(TdeliverOrderShopPay.class, deliverOrderShopPay.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(deliverOrderShopPay, t, new String[] { "id" , "addtime", "isdeleted","updatetime" },true);
		}
	}

	@Override
	public void delete(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		deliverOrderShopPayDao.executeHql("update TdeliverOrderShopPay t set t.isdeleted = 1 where t.id = :id",params);
		//deliverOrderShopPayDao.delete(deliverOrderShopPayDao.get(TdeliverOrderShopPay.class, id));
	}

	@Override
	public List<DeliverOrderShopPay> list(DeliverOrderShopPay deliverOrderShopPay) {
		return dataGrid(deliverOrderShopPay, new PageHelper()).getRows();
	}

	@Override
	public void editStatus(DeliverOrderShopPay deliverOrderShopPay, String status) {
		List<DeliverOrderShopPay> orderShopPayList = list(deliverOrderShopPay);
		if (CollectionUtils.isNotEmpty(orderShopPayList)) {
			DeliverOrderShopPay orderShopPay = orderShopPayList.get(0);
			orderShopPay.setStatus(status);
			edit(orderShopPay);
		}
	}

	@Override
	public DataGrid dataWithNameGrid(DeliverOrderShopPay deliverOrderShopPay, PageHelper ph) {
		DataGrid dataGrid = dataGrid(deliverOrderShopPay, ph);
		List<DeliverOrderShopPay> deliverOrderShopPays = dataGrid.getRows();
		if (CollectionUtils.isNotEmpty(deliverOrderShopPays)) {
			List<DeliverOrderShopPayQuery> deliverOrderShopPayQueries = new ArrayList<DeliverOrderShopPayQuery>();
			for (DeliverOrderShopPay shopPay : deliverOrderShopPays) {
				DeliverOrderShopPayQuery shopPayQuery = new DeliverOrderShopPayQuery();
				BeanUtils.copyProperties(shopPay, shopPayQuery);
				MbShop mbShop = mbShopService.getFromCache(shopPay.getShopId());
				if (mbShop != null) {
					shopPayQuery.setShopName(mbShop.getName());
				}
				shopPayQuery.setPayWayName(shopPay.getPayWay());
				shopPayQuery.setStatusName(shopPay.getStatus());
				deliverOrderShopPayQueries.add(shopPayQuery);
			}
			DataGrid dg = new DataGrid();
			dg.setRows(deliverOrderShopPayQueries);
			dg.setTotal(dataGrid.getTotal());
			return dg;
		}
		return dataGrid;
	}

	@Override
	public List<DeliverOrderShopPay> query(DeliverOrderShopPay deliverOrderShopPay) {
		List<DeliverOrderShopPay> ol = new ArrayList<DeliverOrderShopPay>();
		String hql = " from TdeliverOrderShopPay t ";
		Map<String, Object> params = new HashMap<String, Object>();
		String where = whereHql(deliverOrderShopPay, params);
		List<TdeliverOrderShopPay> l = deliverOrderShopPayDao.find(hql + where, params);
		if (CollectionUtils.isNotEmpty(l)) {
			for (TdeliverOrderShopPay t : l) {
				DeliverOrderShopPay o = new DeliverOrderShopPay();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

}
