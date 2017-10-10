package com.bx.ant.service.impl;

import com.bx.ant.dao.DeliverOrderItemDaoI;
import com.bx.ant.pageModel.DeliverOrderExt;
import com.bx.ant.service.*;
import com.mobian.absx.F;
import com.bx.ant.dao.DeliverOrderDaoI;
import com.bx.ant.model.TdeliverOrder;
import com.mobian.exception.ServiceException;
import com.mobian.pageModel.*;
import com.mobian.pageModel.DeliverOrder;
import com.mobian.pageModel.DeliverOrderItem;
import com.mobian.pageModel.DeliverOrderShop;
import com.mobian.pageModel.DeliverOrderShopItem;
import com.mobian.util.BeanUtil;
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
public class DeliverOrderServiceImpl extends BaseServiceImpl<DeliverOrder> implements DeliverOrderServiceI {

	@Autowired
	private DeliverOrderDaoI deliverOrderDao;

	@javax.annotation.Resource
	private Map<String, DeliverOrderState> deliverOrderStateFactory;

	@Autowired
	private DeliverOrderShopServiceI deliverOrderShopService;

	@Autowired
	private DeliverOrderItemServiceI deliverOrderItemService;

	@Autowired
	private DeliverOrderItemDaoI deliverOrderItemDao;

	@Autowired
	private DeliverOrderShopItemServiceI deliverOrderShopItemService;


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
	public DeliverOrder get(Long id) {
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
	public void delete(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		deliverOrderDao.executeHql("update TdeliverOrder t set t.isdeleted = 1 where t.id = :id",params);
		//deliverOrderDao.delete(deliverOrderDao.get(TdeliverOrder.class, id));
	}

	@Override
	public void fillInfo(DeliverOrderExt deliverOrderExt) {
		fillDeliverOrderItemInfo(deliverOrderExt);
	}

	@Override
	public void fillDeliverOrderItemInfo(DeliverOrderExt deliverOrderExt) {
		//填充明细信息
		DeliverOrderItem deliverOrderItem = new DeliverOrderItem();
		deliverOrderItem.setDeliverOrderId(deliverOrderExt.getId());
		List<DeliverOrderItem> deliverOrderItems = deliverOrderItemService.list(deliverOrderItem);
		deliverOrderExt.setDeliverOrderItemList(deliverOrderItems);
	}

	@Override
	public void fillDeliverOrderShopItemInfo(DeliverOrderExt deliverOrderExt) {
		//填充明细信息
		DeliverOrderShopItem deliverOrderShopItem = new DeliverOrderShopItem();
		deliverOrderShopItem.setDeliverOrderId(deliverOrderExt.getId());
		List<DeliverOrderShopItem> deliverOrderShopItems = deliverOrderShopItemService.list(deliverOrderShopItem);
		deliverOrderExt.setDeliverOrderShopItemList(deliverOrderShopItems);
	}



	@Override
	public void transform(DeliverOrder deliverOrder) {
		DeliverOrderState deliverOrderState;
		if(F.empty(deliverOrder.getId())) {
			deliverOrderState = deliverOrderStateFactory.get("deliverOrder01StateImpl");
			deliverOrderState.handle(deliverOrder);
		} else {
			deliverOrderState = getCurrentState(deliverOrder.getId());
			if(deliverOrderState.next(deliverOrder) == null) {
				throw new ServiceException("订单状态异常或已变更，请刷新页面重试！");
			}
			deliverOrderState.next(deliverOrder).handle(deliverOrder);
		}
	}

	@Override
	public void transformByShopIdAndStatus(Long id, Integer shopId, String status) {
		//配置deliverOrder
		DeliverOrder deliverOrder =  new DeliverOrder();
		deliverOrder.setId(id);
		deliverOrder.setShopId(shopId);
		deliverOrder.setStatus(status);

		//状态翻转
		transform(deliverOrder);
	}

	@Override
	public DeliverOrderState getCurrentState(Long id) {
		DeliverOrder currentDeliverOrder = get(id);
		DeliverOrderState.deliverOrder.set(currentDeliverOrder);
		String deliverOrderStatus = currentDeliverOrder.getStatus();
		DeliverOrderState deliverOrderState = deliverOrderStateFactory.get("deliverOrder" + deliverOrderStatus.substring(2) + "StateImpl");
		return deliverOrderState;
	}



	@Override
	public List<DeliverOrder> listOrderByOrderShopIdAndShopStatus(Integer shopId, String deliverOrderShopStatus) {
		List<DeliverOrder> ol = new ArrayList<DeliverOrder>();
		DeliverOrderShop deliverOrderShop = new DeliverOrderShop();
		deliverOrderShop.setStatus(deliverOrderShopStatus);
		deliverOrderShop.setShopId(shopId);
		List<DeliverOrderShop> deliverOrderShops = deliverOrderShopService.list(deliverOrderShop);
		if (CollectionUtils.isNotEmpty(deliverOrderShops)) {
			for (DeliverOrderShop orderShop : deliverOrderShops) {
				//通过门店运单获取运单信息
				DeliverOrderExt deliverOrder = new DeliverOrderExt();
				BeanUtils.copyProperties(get(orderShop.getDeliverOrderId()), deliverOrder);
				fillDeliverOrderShopItemInfo(deliverOrder);
				if (deliverOrder != null) {
					ol.add(deliverOrder);
				}
			}
		}
		return ol;
	}

	@Override
	public List<DeliverOrder> listOrderByShopIdAndOrderStatus(Integer shopId, String orderStatus) {
		List<DeliverOrder> ol = new ArrayList<DeliverOrder>();
		DeliverOrderShop deliverOrderShop = new DeliverOrderShop();
		deliverOrderShop.setShopId(shopId);
		List<DeliverOrderShop> deliverOrderShops = deliverOrderShopService.list(deliverOrderShop);
		if (CollectionUtils.isNotEmpty(deliverOrderShops)) {
			for (DeliverOrderShop orderShop : deliverOrderShops) {

				//通过门店运单获取运单信息
				DeliverOrderExt deliverOrder = new DeliverOrderExt();
				DeliverOrder order = get(orderShop.getDeliverOrderId());
				if (order != null) {
				    deliverOrder = (DeliverOrderExt) order;
					fillDeliverOrderShopItemInfo(deliverOrder);
					if (orderStatus.equals(deliverOrder.getStatus())) 	{
						ol.add(deliverOrder);
					}
				}
			}
		}
		return ol;
	}


	@Override
	public DeliverOrder getDeliverOrderExt(Long id) {
		DeliverOrder deliverOrder = get(id);
		DeliverOrderExt deliverOrderExt = new DeliverOrderExt() ;
		if (deliverOrder != null) {
			BeanUtils.copyProperties(deliverOrder, deliverOrderExt);
			fillInfo(deliverOrderExt);
		}
		return deliverOrderExt;
	}
	@Override
	public DataGrid dataGridExt(DeliverOrder deliverOrder, PageHelper ph) {
		DataGrid dg = dataGrid(deliverOrder, ph);
		List<DeliverOrder> l = dg.getRows();
		List<DeliverOrder> ol = new ArrayList<DeliverOrder>();
		if (CollectionUtils.isNotEmpty(l)) {
			for (DeliverOrder o : l ) {
				DeliverOrderExt ox = new DeliverOrderExt();
				BeanUtils.copyProperties(o, ox);
				fillDeliverOrderShopItemInfo(ox);
				ol.add(ox);
			}
		}
		dg.setRows(ol);
		return dg;
	}
}
