package com.bx.ant.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bx.ant.dao.DeliverOrderItemDaoI;
import com.bx.ant.pageModel.*;
import com.bx.ant.service.*;
import com.bx.ant.service.session.TokenServiceI;
import com.mobian.absx.F;
import com.bx.ant.dao.DeliverOrderDaoI;
import com.bx.ant.model.TdeliverOrder;
import com.mobian.exception.ServiceException;
import com.mobian.pageModel.*;

import com.mobian.service.MbShopServiceI;
import com.mobian.thirdpart.redis.Key;
import com.mobian.thirdpart.redis.Namespace;
import com.mobian.thirdpart.redis.RedisUtil;
import com.mobian.util.ConvertNameUtil;
import com.mobian.util.GeoUtil;
import com.mobian.util.MyBeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	@Resource
	private SupplierOrderBillServiceI supplierOrderBillService;
	@Resource
	private DeliverOrderPayServiceI deliverOrderPayService;

	@Autowired
	private RedisUtil redisUtil;

	@javax.annotation.Resource
	private MbShopServiceI mbShopService;

	@javax.annotation.Resource
	private SupplierItemRelationServiceI supplierItemRelationService;
	@Resource
	private TokenServiceI tokenService;

	@Autowired
	private ShopOrderBillServiceI shopOrderBillService;


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
			if (!F.empty(deliverOrder.getShopId())) {
				whereHql += " and t.shopId = :shopId";
				params.put("shopId", deliverOrder.getShopId());
			}
			if (deliverOrder instanceof DeliverOrderQuery) {
				DeliverOrderQuery orderQuery = (DeliverOrderQuery) deliverOrder;
				if (orderQuery.getStartDate() != null) {
					whereHql += " and t.addtime >= :startDate ";
					params.put("startDate", orderQuery.getStartDate());
				}
				if (orderQuery.getEndDate() != null) {
					whereHql += " and t.addtime <= :endDate ";
					params.put("endDate", orderQuery.getEndDate());

				}
				if (orderQuery.getTime() != null && orderQuery.getTime() != 0) {
					whereHql += "and t.status='DOS01' or t.status = 'DOS15' and (CURRENT_TIMESTAMP() - t.addtime) >= :time ";
					params.put("time", orderQuery.getTime() * 100);
				}
			}
		}
//			if (deliverOrder.getAddtimeBegin() != null) {
//				whereHql += " and t.addtime >= :addtimeBegin";
//				params.put("addtimeBegin",deliverOrder.getAddtimeBegin());
//			}
//			if (deliverOrder.getAddtimeEnd() != null) {
//				whereHql += " and t.addtime <= :getAddtimeEnd";
//				params.put("getAddtimeEnd",deliverOrder.getAddtimeEnd());
//			}
//
//		}
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
		fillDeliverOrderShopInfo(deliverOrderExt);
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
		deliverOrderShopItem.setShopId(deliverOrderExt.getShopId());

		List<DeliverOrderShopItem> deliverOrderShopItems = deliverOrderShopItemService.list(deliverOrderShopItem);
		deliverOrderExt.setDeliverOrderShopItemList(deliverOrderShopItems);
	}

	/**
	 * 该方法将会使amount属性改为shopPayAmount
	 */
	protected void fillDeliverOrderShopInfo(DeliverOrderExt deliverOrderExt) {
		DeliverOrderShop deliverOrderShop = new DeliverOrderShop();
		if (!F.empty(deliverOrderExt.getShopId())) {
			deliverOrderShop.setShopId(deliverOrderExt.getShopId());
			deliverOrderShop.setDeliverOrderId(deliverOrderExt.getId());
			List<DeliverOrderShop> deliverOrderShops = deliverOrderShopService.query(deliverOrderShop);
			if (CollectionUtils.isNotEmpty(deliverOrderShops) && deliverOrderShops.size() == 1) {
				deliverOrderShop = deliverOrderShops.get(0);
				deliverOrderExt.setDistance(deliverOrderShop.getDistance());
				deliverOrderExt.setAmount(deliverOrderShop.getAmount());
				if (STATUS_SHOP_ALLOCATION.equals(deliverOrderExt.getStatus())) {
					Date now = new Date();
					deliverOrderExt.setMillisecond(Integer.valueOf(ConvertNameUtil.getString("DSV100", "10"))*60*1000 - now.getTime() + deliverOrderShop.getUpdatetime().getTime());
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
		DataGrid dataGrid = new DataGrid();
		dataGrid = dataGrid(deliverOrder, ph);
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
					deliverOrderQuery.setPayStatusName(order.getPayStatus());
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

	@Override
	public DataGrid unPayOrderDataGrid(DeliverOrder deliverOrder, PageHelper ph) {
		List<DeliverOrder> ol = new ArrayList<DeliverOrder>();
		String hql = " from TdeliverOrder t ";
		deliverOrder.setPayStatus("DPS01");//未结算状态
		deliverOrder.setDeliveryStatus("DDS04");//订单已送达状态
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

	@Override
	public void addOrderBill(List<DeliverOrder> list,Integer supplierId,Date startTime,Date endTime) {
		Integer amount = 0;//订单总金额
		for(DeliverOrder d : list) {
			if(d.getAmount() !=null) {
				amount += d.getAmount();
			}
		}
		//创建账单
		SupplierOrderBill supplierOrderBill = new SupplierOrderBill();
		supplierOrderBill.setSupplierId(supplierId);
		supplierOrderBill.setStatus("BAS01");//待审核状态
		supplierOrderBill.setAmount(amount);
		supplierOrderBill.setStartDate(startTime);
		supplierOrderBill.setEndDate(endTime);
		supplierOrderBill.setPayWay(list.get(0).getPayWay());
		supplierOrderBillService.add(supplierOrderBill);
		//生成订单对账单明细
		for(DeliverOrder d : list) {
			DeliverOrderPay deliverOrderPay = new DeliverOrderPay();
			deliverOrderPay.setDeliverOrderId(d.getId());
			deliverOrderPay.setSupplierOrderBillId(supplierOrderBill.getId().intValue());
			deliverOrderPay.setSupplierId(supplierId);
			deliverOrderPay.setAmount(d.getAmount());
			deliverOrderPay.setStatus("SPS02");//结算待审核状态
			deliverOrderPayService.add(deliverOrderPay);
		}
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
		//1. 找到所有超时订单
		DeliverOrderQuery deliverOrder = new DeliverOrderQuery();
		deliverOrder.setDeliveryStatus(DELIVER_STATUS_DELIVERED);
		deliverOrder.setPayStatus(PAY_STATUS_AUDIT);
		deliverOrder.setShopPayStatus(SHOP_PAY_STATUS_NOT_PAY);
		deliverOrder.setStatus(STATUS_DELIVERY_COMPLETE);
		Date now = new Date();
		deliverOrder.setEndDate(new Date(now.getTime() - TIME_DIF_SHOP_PAY_SETTLED));
		PageHelper ph = new PageHelper();
		List<DeliverOrder> deliverOrderList = dataGridExt(deliverOrder, ph).getRows();

		//2. 根据shopId进行分类:一个shop对应一个账单
		Map<Integer,ShopOrderBillQuery> deliverOrderMap = new HashMap<Integer, ShopOrderBillQuery>();
		int listSize = deliverOrderList.size();
		for (int i = 0;i<listSize;i++) {
			DeliverOrder order = deliverOrderList.get(i);
			ShopOrderBillQuery shopOrderBillQuery;
			//2.1初始化一个账单
			if (!deliverOrderMap.containsKey(order.getShopId())) {
				shopOrderBillQuery = new ShopOrderBillQuery();

				List<DeliverOrder> deliverOrders = new ArrayList<DeliverOrder>();
				deliverOrders.add(order);

				Long[] deliverOrderIds = {order.getId()};
				shopOrderBillQuery.setAmount(order.getAmount());
				shopOrderBillQuery.setShopId(order.getShopId());
				shopOrderBillQuery.setDeliverOrderIds(deliverOrderIds);
				shopOrderBillQuery.setDeliverOrderList(deliverOrders);
				shopOrderBillQuery.setPayWay("DPW01");


			//2.2 填充账单
			} else {
				shopOrderBillQuery = deliverOrderMap.get(order.getShopId());

				//2.2.1 添加deliverOrderIds
				int arrayLen = shopOrderBillQuery.getDeliverOrderIds().length;
				Long[] deliverOrderIds = new Long[arrayLen + 1];
				System.arraycopy(shopOrderBillQuery.getDeliverOrderIds(),0,deliverOrderIds,0, arrayLen);
				deliverOrderIds[arrayLen] = order.getId();
				shopOrderBillQuery.setDeliverOrderIds(deliverOrderIds);


				//2.2.2 计算金额并填充信息
				shopOrderBillQuery.setAmount(order.getAmount() + shopOrderBillQuery.getAmount());
				shopOrderBillQuery.setDeliverOrderIds(deliverOrderIds);
				shopOrderBillQuery.getDeliverOrderList().add(order);
			}
			deliverOrderMap.put(order.getShopId(), shopOrderBillQuery);
		}

		//3. 对账单进行添加并进行结算
		for (Map.Entry entry : deliverOrderMap.entrySet()) {
			ShopOrderBillQuery shopOrderBillQuery = (ShopOrderBillQuery) entry.getValue();
			shopOrderBillService.addAndPayShopOrderBillAndShopPay(shopOrderBillQuery);
			List<DeliverOrder> orderList = shopOrderBillQuery.getDeliverOrderList();
			int size = orderList.size();
			for (int i = 0;i<size;i++) {
				DeliverOrder order = orderList.get(i);
				DeliverOrderExt orderExt = new DeliverOrderExt();
				orderExt.setId(order.getId());
				orderExt.setShopId(order.getShopId());
				orderExt.setBalanceLogType("BT060");
				orderExt.setStatus(STATUS_CLOSED);
				transform(orderExt);
			}
		}
	}
	@Override
	public void addAndItems(DeliverOrder deliverOrder, String itemListStr) {
		List<SupplierItemRelationView> items = JSONObject.parseArray(itemListStr, SupplierItemRelationView.class);

		if (CollectionUtils.isNotEmpty(items)) {
			addAndItems(deliverOrder ,items);
		} else {
			throw new ServiceException("items不能为空");
		}
	}

	@Override
	public void addAndItems(DeliverOrder deliverOrder, List<SupplierItemRelationView> items){
		int amount = 0 ;
		transform(deliverOrder);
		for (SupplierItemRelationView item : items) {
			DeliverOrderItem orderItem = new DeliverOrderItem();
			orderItem.setItemId(item.getItemId());
			orderItem.setQuantity(item.getQuantity());
			orderItem.setDeliverOrderId(deliverOrder.getId());

			SupplierItemRelation supplierItemRelation = new SupplierItemRelation();
			supplierItemRelation.setSupplierId(deliverOrder.getSupplierId());
			supplierItemRelation.setItemId(item.getItemId());
			supplierItemRelation.setOnline(true);
			List<SupplierItemRelation> supplierItemRelations = supplierItemRelationService.dataGrid(supplierItemRelation, new PageHelper()).getRows();
			if (CollectionUtils.isNotEmpty(supplierItemRelations)) {
				supplierItemRelation = supplierItemRelations.get(0);
				amount += supplierItemRelation.getPrice() * item.getQuantity();
			}
			deliverOrderItemService.addAndFill(orderItem,deliverOrder);
		}
		DeliverOrder order = new DeliverOrder();
		order.setId(deliverOrder.getId());
		order.setAmount(amount);
		edit(order);
	}

	@Override
	public DataGrid dataGridShopArtificialPay(DeliverOrder deliverOrder, PageHelper ph) {
		DataGrid dataGrid = dataGrid(deliverOrder, ph);
		List<DeliverOrder> deliverOrders = dataGrid.getRows();
		if (CollectionUtils.isNotEmpty(deliverOrders)) {
			List<DeliverOrderQuery> deliverOrderQueries = new ArrayList<DeliverOrderQuery>();
			for (DeliverOrder order : deliverOrders) {
				DeliverOrderQuery deliverOrderQuery = new DeliverOrderQuery();
				BeanUtils.copyProperties(order, deliverOrderQuery);
				deliverOrderQuery.setStatusName(order.getStatus());
				MbShop shop = mbShopService.get(order.getShopId());
				if (shop != null) {
					deliverOrderQuery.setShopName(shop.getName());
                    DeliverOrderShop deliverOrderShop = new DeliverOrderShop();
                    deliverOrderShop.setShopId(shop.getId());
                    deliverOrderShop.setDeliverOrderId(order.getId());
                    DeliverOrderShop orderShop = deliverOrderShopService.query(deliverOrderShop).get(0);
                    if (orderShop != null) {
                        deliverOrderQuery.setAmount(orderShop.getAmount());
                    }
				}
				deliverOrderQueries.add(deliverOrderQuery);
			}
			DataGrid dg = new DataGrid();
			dg.setRows(deliverOrderQueries);
			dg.setTotal(dataGrid.getTotal());
			return dg;
		}
		return dataGrid;
	}

	@Override
	public List<DeliverOrder> query(DeliverOrder deliverOrder) {
		List<DeliverOrder> ol = new ArrayList<DeliverOrder>();
		String hql = " from TdeliverOrder t ";
		Map<String, Object> params = new HashMap<String, Object>();
		String where = whereHql(deliverOrder, params);
		List<TdeliverOrder> l = deliverOrderDao.find(hql + where, params);
		if (CollectionUtils.isNotEmpty(l)) {
			for (TdeliverOrder t : l) {
				DeliverOrder o = new DeliverOrder();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

	@Override
	public Integer updateAllocationOrderRedis(Integer shopId, Integer quantity){
		int count = 0;
		String key = Key.build(Namespace.DELVIER_ORDER_NEW_ASSIGNMENT_COUNT, shopId + "");
		String value = (String) redisUtil.get(key);
		if (!F.empty(value)) {
			count =  Integer.parseInt(value);
			switch (quantity) {
				case 0:
					redisUtil.delete(key);
					return count;
				case -1:
					if ((count += quantity) <= 0) {
						redisUtil.delete(key);
						return 0;
					}
					break;
				case 1:
					count += quantity;
					break;
				default:
					break;
			}
		} else {
			count += quantity;
		}
		if (count  > 0){
			redisUtil.set(key, JSONObject.toJSONString(count));
		}
		return count;
	}

	@Override
	public Integer addAllocationOrderRedis(Integer shopId) {
		return updateAllocationOrderRedis(shopId, 1);
	}

	@Override
	public Integer reduseAllocationOrderRedis(Integer shopId) {
		return updateAllocationOrderRedis(shopId, -1);
	}

	@Override
	public Integer clearAllocationOrderRedis(Integer shopId) {
		return updateAllocationOrderRedis(shopId, 0);
	}

	@Override
	public void addByTableList(List<Object> lo, Integer supplierId) {
		//1. 填充订单信息
		DeliverOrder order = new DeliverOrder();
		order.setSupplierOrderId((String) lo.get(0));
		order.setContactPeople((String)lo.get(3));
		order.setDeliveryAddress((String)lo.get(4));
		order.setContactPhone((String)lo.get(5));

		//1.2 若没有备注则忽略备注
		if (lo.size() > 6 && lo.get(6) != null ) {
			order.setRemark(((String) lo.get(6)));
		}
		order.setSupplierId(supplierId);


		//2. 订单合法性校验
		List<DeliverOrder> orderList = query(order) ;
		//2.1 检测是否重复导入
		if (CollectionUtils.isNotEmpty(orderList))  throw new ServiceException("检测到存在重复订单:" + JSONObject.toJSONString(order));
		//2.2 剔除非上海订单

		//2.3 检测数据是否合理
		String p = "((\\d{11})|^((\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1})|(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1}))$)";
		Pattern pattern = Pattern.compile(p);
		Matcher matcher = pattern.matcher(order.getContactPhone()) ;
		if (!matcher.find()) throw new  ServiceException("电话号码非法");

		//填充订单明细
		List<SupplierItemRelationView> supplierItemRelations = new  ArrayList<SupplierItemRelationView>();
		SupplierItemRelationView itemRelation = new SupplierItemRelationView();
		itemRelation.setSupplierId(supplierId);
		itemRelation.setSupplierItemCode((String)lo.get(1));

		List<SupplierItemRelation> itemRelations = supplierItemRelationService.dataGrid(itemRelation, new PageHelper()).getRows();
		if (CollectionUtils.isNotEmpty(itemRelations)) {
			itemRelation.setItemId(itemRelations.get(0).getItemId());
			itemRelation.setQuantity(Integer.parseInt((String)lo.get(2)));
			supplierItemRelations.add(itemRelation);

			//添加订单和订单明细

			addAndItems(order, supplierItemRelations);
		}
	}



	@Override
	public void handleAssignDeliverOrder(DeliverOrder deliverOrder) {
		if (tokenService.getTokenByShopId(deliverOrder.getShopId()) == null) {
			throw new ServiceException("门店不在线，token已失效");
		}else{
			DeliverOrder order =get(deliverOrder.getId());
			MbShop mbShop =mbShopService.getFromCache(deliverOrder.getShopId());
			double distance = GeoUtil.getDistance(order.getLongitude().doubleValue(), order.getLatitude().doubleValue(), mbShop.getLongitude().doubleValue(), mbShop.getLatitude().doubleValue());
            //1、添加配送商品信息
			DeliverOrderShop deliverOrderShop = new DeliverOrderShop();
			deliverOrderShop.setAmount(order.getAmount());
			deliverOrderShop.setDeliverOrderId(order.getId());
			deliverOrderShop.setShopId(order.getShopId());
			deliverOrderShop.setStatus(DeliverOrderShopServiceI.STATUS_AUDITING);
			deliverOrderShop.setDistance(new BigDecimal(distance));
			deliverOrderShopService.addAndGet(deliverOrderShop);
			List<DeliverOrderItem> deliverOrderItemList = deliverOrderItemService.getDeliverOrderItemList(deliverOrder.getId());
			//2、添加配送商品信息及修改门店商品数量、并修改配送商品总金额、
			deliverOrderShopItemService.addByDeliverOrderItemList(deliverOrderItemList, deliverOrderShop);
			//3. 对门店新订单进行计数
			 addAllocationOrderRedis(deliverOrder.getShopId());
			//4. 编辑订单并添加修改记录
			deliverOrder.setStatus("DOS10");
			editAndAddLog(deliverOrder, DeliverOrderLogServiceI.TYPE_ASSIGN_DELIVER_ORDER, "指派订单给门店");
		}
	}
}
