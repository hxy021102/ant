package com.bx.ant.service.impl;

import com.bx.ant.dao.DeliverOrderItemDaoI;
import com.bx.ant.pageModel.*;
import com.bx.ant.service.*;
import com.mobian.absx.F;
import com.bx.ant.dao.DeliverOrderDaoI;
import com.bx.ant.model.TdeliverOrder;
import com.mobian.exception.ServiceException;
import com.mobian.pageModel.*;

import com.mobian.thirdpart.redis.RedisUtil;
import com.mobian.util.MyBeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

	@Autowired
	private DeliverOrderLogServiceI deliverOrderLogService;

	@Autowired
	private SupplierServiceI supplierService;

	@Autowired
	private RedisUtil redisUtil;


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
				whereHql += " and t.status in (:status)";
				params.put("status", deliverOrder.getStatus().split(","));
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

			if (deliverOrder instanceof DeliverOrderExt) {
				DeliverOrderExt ext = (DeliverOrderExt) deliverOrder;
				if (ext.getStatusList() != null && ext.getStatusList().length > 0) {
					whereHql += " and t.status in (:alist)";
					params.put("alist", ext.getStatusList());
				}
			}
			if (!F.empty(deliverOrder.getId())) {
				whereHql += " and t.id = :id";
				params.put("id", deliverOrder.getId());
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
		deliverOrder.setId(t.getId());
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

	protected void fillInfo(DeliverOrderExt deliverOrderExt) {
		fillDeliverOrderShopItemInfo(deliverOrderExt);
	}

	protected void fillDeliverOrderItemInfo(DeliverOrderExt deliverOrderExt) {
		//填充明细信息
		DeliverOrderItem deliverOrderItem = new DeliverOrderItem();
		deliverOrderItem.setDeliverOrderId(deliverOrderExt.getId());
		List<DeliverOrderItem> deliverOrderItems = deliverOrderItemService.list(deliverOrderItem);
		deliverOrderExt.setDeliverOrderItemList(deliverOrderItems);
	}

	protected void fillDeliverOrderShopItemInfo(DeliverOrderExt deliverOrderExt) {
		//填充明细信息
		DeliverOrderShopItem deliverOrderShopItem = new DeliverOrderShopItem();
		deliverOrderShopItem.setDeliverOrderId(deliverOrderExt.getId());
		List<DeliverOrderShopItem> deliverOrderShopItems = deliverOrderShopItemService.list(deliverOrderShopItem);
		deliverOrderExt.setDeliverOrderShopItemList(deliverOrderShopItems);
	}

	protected void fillDeliverOrderShopInfo(DeliverOrderExt deliverOrderExt) {
		DeliverOrderShop deliverOrderShop = new DeliverOrderShop();
		if (!F.empty(deliverOrderExt.getShopId())) {
			deliverOrderShop.setShopId(deliverOrderExt.getShopId());
			deliverOrderShop.setDeliverOrderId(deliverOrderExt.getId());
			List<DeliverOrderShop> deliverOrderShops = deliverOrderShopService.query(deliverOrderShop);
			if (CollectionUtils.isNotEmpty(deliverOrderShops) && deliverOrderShops.size() == 1) {
				deliverOrderShop = deliverOrderShops.get(0);
				deliverOrderExt.setDistance(deliverOrderShop.getDistance());
				if (STATUS_SHOP_ALLOCATION.equals(deliverOrderExt.getStatus()) {
					Date now = new Date();
					deliverOrderExt.setMillisecond(DeliverOrderShopServiceI.TIME_OUT_TO_ACCEPT - now.getTime() + deliverOrderShop.getUpdatetime().getTime());
				}
			}
		}
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
		DeliverOrderState deliverOrderState = deliverOrderStateFactory.get("deliverOrder" + deliverOrderStatus.substring(3) + "StateImpl");
		return deliverOrderState;
	}



	@Override
	public List<DeliverOrder> listOrderByOrderShopIdAndShopStatus(Integer shopId, String deliverOrderShopStatus) {
		List<DeliverOrder> ol = new ArrayList<DeliverOrder>();
		DeliverOrderShop deliverOrderShop = new DeliverOrderShop();
		deliverOrderShop.setStatus(deliverOrderShopStatus);
		deliverOrderShop.setShopId(shopId);
		List<DeliverOrderShop> deliverOrderShops = deliverOrderShopService.query(deliverOrderShop);
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
		List<DeliverOrderShop> deliverOrderShops = deliverOrderShopService.query(deliverOrderShop);
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
				fillDeliverOrderShopInfo(ox);
				ol.add(ox);
			}
		}
		dg.setRows(ol);
		return dg;
	}

	@Override
	public void editAndAddLog(DeliverOrder deliverOrder, String logType, String content, String loginId) {
		edit(deliverOrder);
		DeliverOrderLog deliverOrderLog = new DeliverOrderLog();
		deliverOrderLog.setDeliverOrderId(deliverOrder.getId());
		deliverOrderLog.setLoginId(loginId);
		deliverOrderLog.setContent(content);
		deliverOrderLog.setLogType(logType);
		deliverOrderLogService.add(deliverOrderLog);
	}

	@Override
	public  void editAndAddLog(DeliverOrder deliverOrder, String logType, String content) {
		editAndAddLog(deliverOrder, logType, content, null);
	}

	@Override
	public DataGrid dataGridWithName(DeliverOrder deliverOrder, PageHelper ph) {
		DataGrid dataGrid = dataGrid(deliverOrder, ph);
		List<DeliverOrder> deliverOrders = dataGrid.getRows();
		if (CollectionUtils.isNotEmpty(deliverOrders)) {
			Integer[] supplierIds = new Integer[deliverOrders.size()];
			int index = 0;
			for (DeliverOrder order : deliverOrders) {
				supplierIds[index++] = order.getSupplierId();
			}
			SupplierQuery supplierQuery = new SupplierQuery();
			supplierQuery.setSupplierIds(supplierIds);
			List<Supplier> suppliers = supplierService.query(supplierQuery);
			if (CollectionUtils.isNotEmpty(suppliers)) {
				Map<Integer, String> supplierMap = new HashMap<Integer, String>();
				for (Supplier supplier : suppliers) {
					if (!supplierMap.containsKey(supplier.getId())) {
						supplierMap.put(supplier.getId(), supplier.getName());
					}
				}
				List<DeliverOrderQuery> deliverOrderQueries = new ArrayList<DeliverOrderQuery>();
				for (DeliverOrder order : deliverOrders) {
					DeliverOrderQuery deliverOrderQuery = new DeliverOrderQuery();
					BeanUtils.copyProperties(order, deliverOrderQuery);
					deliverOrderQuery.setSupplierName(supplierMap.get(order.getSupplierId()));
					deliverOrderQuery.setStatusName(order.getStatus());
					deliverOrderQuery.setShopPayStatusName(order.getShopPayStatus());
					deliverOrderQuery.setDeliveryStatusName(order.getDeliveryStatus());
					deliverOrderQueries.add(deliverOrderQuery);
				}
				DataGrid dg = new DataGrid();
				dg.setRows(deliverOrderQueries);
				dg.setTotal(dataGrid.getTotal());
				return dg;
			}
		}
		return dataGrid;
	}

	@Override
	public DeliverOrderQuery getDeliverOrderView(Long id) {
		DeliverOrder deliverOrder = get(id);
		DeliverOrderQuery deliverOrderQuery = new DeliverOrderQuery();
		BeanUtils.copyProperties(deliverOrder, deliverOrderQuery);
		Supplier supplier = supplierService.get(deliverOrderQuery.getSupplierId());
		deliverOrderQuery.setSupplierName(supplier.getName());
		deliverOrderQuery.setStatusName(deliverOrder.getStatus());
		deliverOrderQuery.setShopPayStatusName(deliverOrder.getShopPayStatus());
		deliverOrderQuery.setDeliveryStatusName(deliverOrder.getDeliveryStatus());
		return deliverOrderQuery;
	}
//	@Override
//	public void completeOrder(Long deliverOrderId ) {
//		DeliverOrder deliverOrder = get(deliverOrderId);
//		//门店结算前提:提供商已付款,已配送完,门店未结算
//		if (DeliverOrderServiceI.DELIVER_STATUS_DELIVERED.equals(deliverOrder.getDeliveryStatus())
//				&& DeliverOrderServiceI.PAY_STATUS_SUCCESS.equals(deliverOrder.getPayStatus())
//				&& DeliverOrderServiceI.SHOP_PAY_STATUS_NOT_PAY.equals(deliverOrder.getShopPayStatus())) {
//
//		}
//	}
	@Override
	public void settleShopPay() {
		DeliverOrder deliverOrder = new DeliverOrder();
		deliverOrder.setDeliveryStatus(DELIVER_STATUS_DELIVERED);
		deliverOrder.setPayStatus(PAY_STATUS_SUCCESS);
		deliverOrder.setShopPayStatus(SHOP_PAY_STATUS_NOT_PAY);
		deliverOrder.setStatus(STATUS_DELIVERY_COMPLETE);
		PageHelper ph = new PageHelper();
		List<DeliverOrder> deliverOrderList = dataGrid(deliverOrder, ph).getRows();
		Date now = new Date();
		Iterator<DeliverOrder> deliverIt = deliverOrderList.iterator();
		while (deliverIt.hasNext()) {
			DeliverOrder order = deliverIt.next();
			DeliverOrderShop orderShop = new DeliverOrderShop();
			orderShop.setDeliverOrderId(order.getId());
			orderShop.setStatus(DeliverOrderShopServiceI.STATUS_ACCEPTED);
			List<DeliverOrderShop> orderShops = deliverOrderShopService.query(orderShop);
			if (CollectionUtils.isNotEmpty(orderShops)) {
				if (now.getTime() - orderShops.get(0) .getAddtime().getTime() > TIME_DIF_SHOP_PAY_SETTLED) {
				    //设置下一个状态
					order.setRemark("" + (TIME_DIF_SHOP_PAY_SETTLED / (24 * 60 * 60 * 1000)) + "");
					order.setStatus(STATUS_CLOSED);
					transform(order);
				}
			}
//			else {
//				throw new ServiceException("数据异常");
//			}
		}
	}

}
