package com.bx.ant.service.impl;

import com.bx.ant.pageModel.*;
import com.bx.ant.service.DeliverOrderServiceI;
import com.mobian.absx.F;
import com.bx.ant.dao.DeliverOrderShopDaoI;
import com.bx.ant.model.TdeliverOrderShop;
import com.mobian.exception.ServiceException;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbShop;
import com.mobian.pageModel.PageHelper;
import com.bx.ant.service.DeliverOrderShopServiceI;
import com.mobian.service.MbShopServiceI;
import com.mobian.util.MyBeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class DeliverOrderShopServiceImpl extends BaseServiceImpl<DeliverOrderShop> implements DeliverOrderShopServiceI {

	@Autowired
	private DeliverOrderShopDaoI deliverOrderShopDao;

	@Resource
	private MbShopServiceI mbShopService;

	@Autowired
	private DeliverOrderServiceI deliverOrderService;

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
			if (deliverOrderShop.getUpdatetimeBegin() != null) {
				whereHql += " and t.updatetime >= :updatetimeBegin";
				params.put("updatetimeBegin",deliverOrderShop.getUpdatetimeBegin());
			}
			if (deliverOrderShop.getUpdatetimeEnd() != null) {
				whereHql += " and t.updatetime < :updatetimeEnd";
				params.put("updatetimeEnd",deliverOrderShop.getUpdatetimeEnd());
			}
			if (deliverOrderShop instanceof DeliverOrderShopQuery) {
				DeliverOrderShopQuery ext = (DeliverOrderShopQuery) deliverOrderShop;
				if (ext.getStatusList() != null && ext.getStatusList().length > 0) {
					whereHql += " and t.status in (:alist)";
					params.put("alist", ext.getStatusList());
				}
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
	public DeliverOrderShop addAndGet(DeliverOrderShop deliverOrderShop){
		add(deliverOrderShop);
		return deliverOrderShop;
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
	public List<DeliverOrderShop> query(DeliverOrderShop deliverOrderShop) {
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
		List<DeliverOrderShop> deliverOrderShops = query(deliverOrderShop);
		DeliverOrderShop o = new DeliverOrderShop();
		if (CollectionUtils.isNotEmpty(deliverOrderShops)  && deliverOrderShops.size() == 1) {
			//只对第一个结果进行处理
			o = deliverOrderShops.get(0);
			o.setStatus(status);
			edit(o);
		} else {
			throw new ServiceException("请确认门店订单是否存在且唯一");
		}
		return o;

	}

	@Override
	public DataGrid dataGridWithName(DeliverOrderShop deliverOrderShop, PageHelper ph) {
		DataGrid dataGrid = dataGrid(deliverOrderShop, ph);
		List<DeliverOrderShop> deliverOrderShops = dataGrid.getRows();
		if (CollectionUtils.isNotEmpty(deliverOrderShops)) {
			List<DeliverOrderShopQuery> deliverOrderShopQueries = new ArrayList<DeliverOrderShopQuery>();
			for (DeliverOrderShop orderShop : deliverOrderShops) {
				DeliverOrderShopQuery deliverOrderShopQuery = new DeliverOrderShopQuery();
				BeanUtils.copyProperties(orderShop, deliverOrderShopQuery);
				MbShop mbShop = mbShopService.getFromCache(orderShop.getShopId());
				if (mbShop != null) {
					deliverOrderShopQuery.setShopName(mbShop.getName());
				}
				deliverOrderShopQuery.setStatusName(orderShop.getStatus());
				deliverOrderShopQueries.add(deliverOrderShopQuery);
			}
			DataGrid dg = new DataGrid();
			dg.setRows(deliverOrderShopQueries);
			dg.setTotal(dataGrid.getTotal());
			return dg;
		}
		return dataGrid;
	}
	@Override
	public void checkTimeOutOrder() {
		DeliverOrderShop deliverOrderShop = new DeliverOrderShop();
		deliverOrderShop.setStatus(STATUS_AUDITING);
		List<DeliverOrderShop> deliverOrderShops = dataGrid(deliverOrderShop, new PageHelper()).getRows();
		if (CollectionUtils.isNotEmpty(deliverOrderShops)) {
			Iterator<DeliverOrderShop> orderShopIterator = deliverOrderShops.iterator();
			while (orderShopIterator.hasNext()) {
				DeliverOrderShop orderShop = orderShopIterator.next();
				Date now = new Date();
				if (now.getTime() - orderShop.getUpdatetime().getTime() >TIME_OUT_TO_ACCEPT) {
					DeliverOrder deliverOrder = new DeliverOrder();
					deliverOrder.setId(orderShop.getDeliverOrderId());
					deliverOrder.setStatus(DeliverOrderServiceI.STATUS_SHOP_REFUSE);
					deliverOrder.setRemark("超时未接单");
					deliverOrderService.transform(deliverOrder);
				}
			}
		}
	}



}
