package com.bx.ant.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bx.ant.pageModel.*;
import com.bx.ant.service.*;
import com.bx.ant.service.qimen.QimenRequestService;
import com.mobian.absx.F;
import com.bx.ant.dao.DeliverOrderDaoI;
import com.bx.ant.model.TdeliverOrder;
import com.mobian.exception.ServiceException;
import com.mobian.pageModel.*;

import com.mobian.service.MbBalanceServiceI;
import com.mobian.service.MbShopServiceI;
import com.mobian.thirdpart.mns.MNSTemplate;
import com.mobian.thirdpart.mns.MNSUtil;
import com.mobian.thirdpart.redis.Key;
import com.mobian.thirdpart.redis.Namespace;
import com.mobian.thirdpart.redis.RedisUtil;
import com.mobian.util.ConvertNameUtil;
import com.mobian.util.DateUtil;
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

	@Autowired
	private ShopItemServiceI shopItemService;
	@Resource
	private QimenRequestService qimenRequestService;

	@Resource
	private MbBalanceServiceI mbBalanceService;


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
			whereHql += " where 1=1 ";
			if (!F.empty(deliverOrder.getTenantId())) {
				whereHql += " and t.tenantId = :tenantId";
				params.put("tenantId", deliverOrder.getTenantId());
			}
			if (!F.empty(deliverOrder.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", deliverOrder.getIsdeleted());
			} else {
				whereHql += " and t.isdeleted = 0";
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
			if (!F.empty(deliverOrder.getSupplierOrderId())) {
				whereHql += " and t.supplierOrderId in (:supplierOrderId)";
				params.put("supplierOrderId", deliverOrder.getSupplierOrderId().split(","));
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
			if (!F.empty(deliverOrder.getDeliveryWay())) {
				whereHql += " and t.deliveryWay in (:deliveryWay)";
				params.put("deliveryWay", deliverOrder.getDeliveryWay().split(","));
			}
			if (!F.empty(deliverOrder.getOriginalShop())) {
				whereHql += " and t.originalShop LIKE :originalShop";
				params.put("originalShop", "%" + deliverOrder.getOriginalShop() + "%");
			}
			if (!F.empty(deliverOrder.getOriginalOrderId())) {
				whereHql += " and t.originalOrderId = :originalOrderId";
				params.put("originalOrderId", deliverOrder.getOriginalOrderId());
			}
			if (!F.empty(deliverOrder.getOriginalOrderStatus())) {
				whereHql += " and t.originalOrderStatus = :originalOrderStatus";
				params.put("originalOrderStatus", deliverOrder.getOriginalOrderStatus());
			}
			if (!F.empty(deliverOrder.getAgentStatus())) {
				whereHql += " and t.agentStatus = :agentStatus and t.status <> 'DOS60' "; // 代送排除关闭订单
				params.put("agentStatus", deliverOrder.getAgentStatus());
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
					if (F.empty(orderQuery.getStatus())) {
						whereHql += " and (t.status='DOS01' or t.status = 'DOS15')";
					}
				}
				if (orderQuery.getIds() != null && orderQuery.getIds().length > 0) {
					whereHql += " and t.id in (:ids) ";
					params.put("ids", orderQuery.getIds());
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
		if(F.empty(deliverOrder.getIsdeleted())) t.setIsdeleted(false);
		deliverOrderDao.save(t);
		deliverOrder.setId(t.getId());
	}

	@Override
	public DeliverOrder get(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TdeliverOrder t = deliverOrderDao.get("from TdeliverOrder t  where t.id = :id", params);
		if(t != null) {
			DeliverOrder o = new DeliverOrder();
			BeanUtils.copyProperties(t, o);
			return o;
		}

		return null;
	}

	@Override
	public void edit(DeliverOrder deliverOrder) {
		TdeliverOrder t = deliverOrderDao.get(TdeliverOrder.class, deliverOrder.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(deliverOrder, t, new String[] { "id" , "addtime", "updatetime" },true);
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
		deliverOrderShopItem.setDeliverOrderShopId(deliverOrderExt.getOrderShopId());
		/*deliverOrderShopItem.setDeliverOrderId(deliverOrderExt.getId());
		deliverOrderShopItem.setShopId(deliverOrderExt.getShopId());*/
		List<DeliverOrderShopItem> deliverOrderShopItems = deliverOrderShopItemService.list(deliverOrderShopItem);
		deliverOrderExt.setDeliverOrderShopItemList(deliverOrderShopItems);
	}

	/**
	 * 该方法将会使amount属性改为shopPayAmount
	 */
	protected void fillDeliverOrderShopInfo(DeliverOrderExt deliverOrderExt) {
		DeliverOrderShop deliverOrderShop = new DeliverOrderShop();
		if (!F.empty(deliverOrderExt.getOrderShopId())) {
			DeliverOrderShop orderShop =deliverOrderShopService.get(deliverOrderExt.getOrderShopId());
			deliverOrderExt.setDistance(orderShop.getDistance());
			deliverOrderExt.setAmount(orderShop.getAmount());
		} else if (!F.empty(deliverOrderExt.getShopId())) {
			deliverOrderShop.setShopId(deliverOrderExt.getShopId());
			deliverOrderShop.setDeliverOrderId(deliverOrderExt.getId());
			if (STATUS_SHOP_ALLOCATION.equals(deliverOrderExt.getStatus())) {
				deliverOrderShop.setStatus("DSS01");

				List<DeliverOrderShop> deliverOrderShops = deliverOrderShopService.query(deliverOrderShop);
				if (CollectionUtils.isNotEmpty(deliverOrderShops)) {
					deliverOrderShop = deliverOrderShops.get(0);
					deliverOrderExt.setDistance(deliverOrderShop.getDistance());
					deliverOrderExt.setAmount(deliverOrderShop.getAmount());
					Date now = new Date();
					deliverOrderExt.setMillisecond(Integer.valueOf(ConvertNameUtil.getString("DSV100", "10")) * 60 * 1000 - now.getTime() + deliverOrderShop.getAddtime().getTime());
				}
			} else {
				deliverOrderShop.setStatus("DSS04,DSS02,DSS06");
				List<DeliverOrderShop> deliverOrderShops = deliverOrderShopService.query(deliverOrderShop);
				if (CollectionUtils.isNotEmpty(deliverOrderShops)) {
					deliverOrderShop = deliverOrderShops.get(0);
					deliverOrderExt.setDistance(deliverOrderShop.getDistance());
					deliverOrderExt.setAmount(deliverOrderShop.getAmount());
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
		transformByShopIdAndStatus(deliverOrder);
	}

	@Override
	public void transformByShopIdAndStatus(DeliverOrder deliverOrder) {

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
				deliverOrder.setOrderShopId(orderShop.getId());
				fillDeliverOrderShopItemInfo(deliverOrder);
				if (deliverOrder != null) {
					ol.add(deliverOrder);
				}
			}
		}
		return ol;
	}

	@Override
	@Deprecated
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
	public DeliverOrder getDeliverOrderExt(DeliverOrderShop deliverOrderShop) {
		DeliverOrder deliverOrder = get(deliverOrderShop.getDeliverOrderId());
		DeliverOrderExt deliverOrderExt = new DeliverOrderExt() ;
		if (deliverOrder != null) {
			BeanUtils.copyProperties(deliverOrder, deliverOrderExt);
			deliverOrderExt.setOrderShopId(deliverOrderShop.getId());
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
				fillIShopInfo(ox);
				ol.add(ox);
			}
		}
		dg.setRows(ol);
		return dg;
	}

	protected void fillIShopInfo(DeliverOrderExt ox) {
		DeliverOrderShop deliverOrderShop = deliverOrderShopService.getByDeliverOrderId(ox.getId());
		if (deliverOrderShop != null) {
			ox.setOrderShopId(deliverOrderShop.getId());
			ox.setDeliverOrderShop(deliverOrderShop);
			fillDeliverOrderShopItemInfo(ox);
			ox.setOrderShopId(null);
			fillDeliverOrderShopInfo(ox);
		}
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
		//扫描发货的时候确定发货
		if(DeliverOrderLogServiceI.TYPE_DLT14.equals(logType)){
			qimenRequestService.updateDeliveryOrderConfirm(deliverOrder);
		}
	}

	@Override
	public void editAndAddLog(DeliverOrder deliverOrder, String logType, String content) {
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
					if(!F.empty(deliverOrderQuery.getShopId())) {
						MbShop mbShop =mbShopService.getFromCache(deliverOrderQuery.getShopId());
						deliverOrderQuery.setShopName(mbShop.getName());
					}
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
		deliverOrderQuery.setOriginalOrderStatusName(deliverOrder.getOriginalOrderStatus());
		deliverOrderQuery.setAgentStatusName(deliverOrder.getAgentStatus());
		if (!F.empty(deliverOrderQuery.getCompleteImages())) {
			String[] imageArray = deliverOrderQuery.getCompleteImages().split(";");
			List<String> imageList = new ArrayList<String>();
			for (String image : imageArray) {
				imageList.add(image);
			}
			deliverOrderQuery.setImage(imageList);
		}
		DeliverOrderShop deliverOrderShop = new DeliverOrderShop();
		deliverOrderShop.setDeliverOrderId(id);
		deliverOrderShop.setStatus("DSS02");
		List<DeliverOrderShop> deliverOrderShops=deliverOrderShopService.query(deliverOrderShop);
		if (CollectionUtils.isNotEmpty(deliverOrderShops)) {
			DeliverOrderShop orderShop = deliverOrderShops.get(0);
			Date outDate = DateUtil.addHourToDate(orderShop.getAddtime(), Integer.valueOf(ConvertNameUtil.getString("DSV700", "2")));
			if (new Date().getTime() > outDate.getTime()) {
				deliverOrderQuery.setStatus("notDriver");
				long nd = 1000 * 24 * 60 * 60;
				long nh = 1000 * 60 * 60;
				long nm = 1000 * 60;
				// long ns = 1000;
				// 获得两个时间的毫秒时间差异
				long diff = new Date().getTime() - orderShop.getAddtime().getTime();
				// 计算差多少天
				long day = diff / nd;
				// 计算差多少小时
				long hour = diff % nd / nh;
				// 计算差多少分钟
				long min = diff % nd % nh / nm;
				deliverOrderQuery.setShowTime(day+"天"+hour+"小时"+min+"分钟");
				deliverOrderQuery.setOrderShopId(orderShop.getId());
			} else {
				deliverOrderQuery.setOrderShopId(0L);
			}
		} else {
			deliverOrderQuery.setOrderShopId(0L);
		}
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
	public List<DeliverOrderPay> addOrderBill(List<DeliverOrder> list,Integer supplierId,Date startTime,Date endTime) {
		//先判断数据库是否已经存在这些账单
		Long[] deliverOrderIds = new Long[list.size()];
		String[] deliverOrderPayStatus = {"DPS02","DPS03"};//状态是待审核  以及审核完成已结算的订单
		for (int i = 0; i < list.size(); i++) {
			deliverOrderIds[i] = list.get(i).getId();
		}
		DeliverOrderPay orderPay = new DeliverOrderPay();
		orderPay.setDeliverOrderIds(deliverOrderIds);
		orderPay.setDeliverOrderPayStatus(deliverOrderPayStatus);
		List<DeliverOrderPay> l = deliverOrderPayService.query(orderPay);
		if (CollectionUtils.isEmpty(l)) {
			Integer amount = 0;//订单总金额
			for (DeliverOrder d : list) {
				if (d.getAmount() != null) {
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
			for (DeliverOrder d : list) {
				DeliverOrderPay deliverOrderPay = new DeliverOrderPay();
				deliverOrderPay.setDeliverOrderId(d.getId());
				deliverOrderPay.setSupplierOrderBillId(supplierOrderBill.getId().intValue());
				deliverOrderPay.setSupplierId(supplierId);
				deliverOrderPay.setAmount(d.getAmount());
				deliverOrderPay.setStatus("DPS03");//结算待审核状态
				deliverOrderPayService.add(deliverOrderPay);
				d.setPayStatus("DPS03");//待审核
				edit(d);
			}
			return null;
		}else {
			return l;
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
		int weight = 0 ;
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
			PageHelper ph = new PageHelper();
			ph.setHiddenTotal(true);
			List<SupplierItemRelationView> supplierItemRelationViews = supplierItemRelationService.dataGridView(supplierItemRelation, ph).getRows();
			if (CollectionUtils.isNotEmpty(supplierItemRelationViews)) {
				SupplierItemRelationView supplierItemRelationView = supplierItemRelationViews.get(0);
				amount += supplierItemRelationView.getPrice() * item.getQuantity();
				weight += supplierItemRelationView.getWeight() * item.getQuantity();
			}
			deliverOrderItemService.addAndFill(orderItem,deliverOrder);
		}

		// 余额不足拦截
		if(deliverOrder.getCheckAmount() != null && deliverOrder.getCheckAmount()) {
			MbBalance mbBalance = mbBalanceService.addOrGetAccessSupplierBalance(deliverOrder.getSupplierId());

			int unPayAmount = 0; // 未结算的金额
			if(mbBalance.getAmount() > 0 && mbBalance.getAmount() >= amount) {
				// 查询接入方未结算的运单集合
				DeliverOrder order = new DeliverOrder();
				order.setSupplierId(deliverOrder.getSupplierId());
				PageHelper ph = new PageHelper();
				ph.setHiddenTotal(true);
				List<DeliverOrder> list = unPayOrderDataGrid(order, ph).getRows();
				if(CollectionUtils.isNotEmpty(list)) {
					for(DeliverOrder o : list) {
						unPayAmount += (o.getAmount() == null ? 0 : o.getAmount());
					}
				}
			}

			// 钱包余额<当前运单金额+未结算运单金额
			if(mbBalance.getAmount() < amount + unPayAmount) {
				// 发送短信通知
				sendArrearsMns(deliverOrder.getSupplierId());
				throw new ServiceException("接入方余额不足");
			}
		}

		DeliverOrder order = new DeliverOrder();
		order.setId(deliverOrder.getId());
		order.setAmount(amount);
		order.setWeight(weight);
		edit(order);
	}

	/**
	 * 发送欠款短信通知
	 * @param supplierId
	 */
	private void sendArrearsMns(Integer supplierId) {
		Supplier supplier = supplierService.get(supplierId);
		if(!F.empty(supplier.getContactPhone())) {
			MNSTemplate template = new MNSTemplate();
			Map<String, String> params = new HashMap<String, String>();
			template.setTemplateCode("SMS_119870125");
			params.put("supplierName", supplier.getName());
			template.setParams(params);
			MNSUtil.sendMns(supplier.getContactPhone(), template);
		}

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

//	@Override
	public Integer reduceAllocationOrderRedis(Integer shopId) {
		return updateAllocationOrderRedis(shopId, -1);
	}

	@Override
	public Integer clearAllocationOrderRedis(Integer shopId) {
		return updateAllocationOrderRedis(shopId, 0);
	}

	@Override
	public DeliverOrder getBySupplierOrderId(String supplierOrderId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("supplierOrderId", supplierOrderId);
		TdeliverOrder t = deliverOrderDao.get("from TdeliverOrder t  where t.isdeleted = 0 and t.supplierOrderId = :supplierOrderId", params);
		if(t != null) {
			DeliverOrder o = new DeliverOrder();
			BeanUtils.copyProperties(t, o);
			return o;
		}
		return null;
	}

	@Override
	public DeliverOrder getBySupplierOrderIdAndSupplierId(Integer supplierId, String supplierOrderId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("supplierOrderId", supplierOrderId);
		params.put("supplierId", supplierId);
		TdeliverOrder t = deliverOrderDao.get("from TdeliverOrder t  where t.supplierOrderId = :supplierOrderId and t.supplierId = :supplierId", params);
		if(t != null) {
			DeliverOrder o = new DeliverOrder();
			BeanUtils.copyProperties(t, o);
			return o;
		}
		return null;
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
	public Boolean handleAssignDeliverOrder(DeliverOrder deliverOrder) {
		//门店已接单，但超时间未送出，从新指派，则将门店订单和订单改成拒绝接单状态
		if(!F.empty(deliverOrder.getOrderShopId())){
			DeliverOrderShop deliverOrderShop =deliverOrderShopService.get(deliverOrder.getOrderShopId());
			deliverOrderShop.setStatus("DSS03");
		 	deliverOrderShopService.edit(deliverOrderShop);
		 	deliverOrder.setStatus("DOS15");
		 	edit(deliverOrder);
		}
		//1、指派时判断配送的商品是否足够，否则提示指派失败，商品不足
		DeliverOrderItem deliverOrderItem = new DeliverOrderItem();
		deliverOrderItem.setDeliverOrderId(deliverOrder.getId());
		List<DeliverOrderItem> deliverOrderItems = deliverOrderItemService.list(deliverOrderItem);
		Boolean result=true;
		if (CollectionUtils.isNotEmpty(deliverOrderItems)) {
			for (DeliverOrderItem orderItem : deliverOrderItems) {
				ShopItem shopItem = new ShopItem();
				shopItem.setItemId(orderItem.getItemId());
				shopItem.setShopId(deliverOrder.getShopId());
				List<ShopItem> shopItemList = shopItemService.query(shopItem);
				if (CollectionUtils.isEmpty(shopItemList) || shopItemList.get(0).getQuantity() < orderItem.getQuantity()) {
					result = false;
					break;
				}
			}
		}
		//2、商品足够，则进行分单和强制接单处理
		if(result) {
			DeliverOrder order = get(deliverOrder.getId());
			MbShop mbShop = mbShopService.getFromCache(deliverOrder.getShopId());
			double distance = GeoUtil.getDistance(order.getLongitude().doubleValue(), order.getLatitude().doubleValue(), mbShop.getLongitude().doubleValue(), mbShop.getLatitude().doubleValue());
			deliverOrder.setShopDistance(BigDecimal.valueOf(distance));
			deliverOrder.setSupplierId(order.getSupplierId());
			deliverOrder.setStatus(DeliverOrderServiceI.STATUS_SHOP_ALLOCATION);
			deliverOrder.setDeliverOrderLogType(DeliverOrderLogServiceI.TYPE_ASSIGN_SHOP_DELIVER_ORDER);
			//设置为强制接单，跳转到已分单，待接收状态机
			deliverOrder.setDeliveryType(DeliverOrderServiceI.DELIVER_TYPE_FORCE);
			transform(deliverOrder);
			//进入强制接单状态机
			deliverOrder.setStatus(DeliverOrderServiceI.STATUS_SHOP_ACCEPT);
			transform(deliverOrder);
			return true;
		}
		return false;
	}

	@Override
	public DataGrid dataGridOutTimeDeliverOrder(DeliverOrderQuery deliverOrderQuery, PageHelper ph) {
		DataGrid dataGrid = dataGridWithName(deliverOrderQuery, ph);
		List<DeliverOrderQuery> deliverOrderQueryList = dataGrid.getRows();
		if (CollectionUtils.isNotEmpty(deliverOrderQueryList)) {
			List<DeliverOrderQuery> deliverOrderQueries = new ArrayList<DeliverOrderQuery>();
			Long total = 0L;
			for (DeliverOrderQuery orderQuery : deliverOrderQueryList) {
				Date outDate = DateUtil.addMinuteToDate(orderQuery.getAddtime(), deliverOrderQuery.getTime());
				if (new Date().getTime() > outDate.getTime()) {
					deliverOrderQueries.add(orderQuery);
					total++;
				}
			}
			DataGrid dg = new DataGrid();
			dg.setRows(deliverOrderQueries);
			dg.setTotal(total);
			return dg;
		}
		return dataGrid;
	}

	@Override
	public Integer editOrderStatus(DeliverOrder deliverorder) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("payStatus", deliverorder.getPayStatus());
		params.put("id", deliverorder.getId());
		int result = deliverOrderDao.executeHql("update TdeliverOrder t set t.payStatus = :payStatus where t.id = :id  and  t.payStatus <>'DPS02'", params);
		return result ;
	}

	@Override
	public DataGrid dataGridNotDriverDeliverOrder(DeliverOrderQuery deliverOrderQuery, PageHelper ph) {
		DeliverOrderShopQuery deliverOrderShopQuery = new DeliverOrderShopQuery();
		String[] statusArray = {"DSS02"};
		deliverOrderShopQuery.setStatusList(statusArray);
		List<DeliverOrderShop> deliverOrderShopList = deliverOrderShopService.query(deliverOrderShopQuery);
		if (CollectionUtils.isNotEmpty(deliverOrderShopList)) {
			Long[] ids = new Long[deliverOrderShopList.size()];
			int i = 0;
			for (DeliverOrderShop orderShop : deliverOrderShopList) {
			 	Date outDate = DateUtil.addHourToDate(orderShop.getAddtime(), Integer.valueOf(ConvertNameUtil.getString("DSV700", "2")));
			//	Date outDate = DateUtil.addMinuteToDate(orderShop.getAddtime(), Integer.valueOf(ConvertNameUtil.getString("DSV700", "2")));
				if (new Date().getTime() > outDate.getTime()) {
					ids[i++] = orderShop.getDeliverOrderId();
				}
			}
			if (ids != null && ids.length > 0) {
				deliverOrderQuery.setStatus("DOS20,DOS25");
				deliverOrderQuery.setIds(ids);
				DataGrid dataGrid = dataGridWithName(deliverOrderQuery, ph);
				return dataGrid;
			}
		}
		return new DataGrid();
	}

	@Override
	public DeliverOrderExt getBalanceLogDetail(DeliverOrderShop deliverOrderShop) {
		//显示商品信息
        DeliverOrderShop orderShop=deliverOrderShopService.query(deliverOrderShop).get(0);
        DeliverOrderShopItem deliverOrderShopItem=new DeliverOrderShopItem();
        deliverOrderShopItem.setDeliverOrderShopId(orderShop.getId());
        List<DeliverOrderShopItem> deliverOrderShopItemList=deliverOrderShopItemService.list(deliverOrderShopItem);
		DeliverOrderExt deliverOrderExt =new DeliverOrderExt();
		deliverOrderExt.setDeliverOrderShopItemList(deliverOrderShopItemList);
		//显示订单信息
		DeliverOrder deliverOrder = get(deliverOrderShop.getDeliverOrderId());
		BeanUtils.copyProperties(deliverOrder,deliverOrderExt);
		deliverOrderExt.setOrderShopId(orderShop.getId());
        deliverOrderExt.setDistance(orderShop.getDistance());
        deliverOrderExt.setAmount(orderShop.getAmount());
		return deliverOrderExt;

	}


	@Override
	public DeliverOrderExt getDetail(Long id) {
		DeliverOrder o = get(id);
		if(o != null) {
			DeliverOrderExt ox = new DeliverOrderExt();
			BeanUtils.copyProperties(o, ox);
			fillIShopInfo(ox);
			return ox;
		}
		return null;
	}

	@Override
	public List<DeliverOrder> queryTodayProfitOrdersByShopId(Integer shopId) {
		List<DeliverOrder> deliverOrderList = new ArrayList<DeliverOrder>();
		List<DeliverOrderShop> deliverOrderShopList = deliverOrderShopService.queryTodayOrdersByShopId(shopId);
		if (CollectionUtils.isNotEmpty(deliverOrderShopList)) {
			for (DeliverOrderShop orderShop : deliverOrderShopList) {
				if (!F.empty(orderShop.getDeliverOrderId())) {
					deliverOrderList.add(getDeliverOrderExt(orderShop));
				}
			}
		}
		return deliverOrderList;
	}

	@Override
	public DeliverOrder getOrderByYouZanTid(String tid) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", tid);
		TdeliverOrder t = deliverOrderDao.get("from TdeliverOrder t  where t.originalOrderId = :id", params);
		if(t != null) {
			DeliverOrder o = new DeliverOrder();
			BeanUtils.copyProperties(t, o);
			return o;
		}

		return null;
	}

	@Override
	public void updateBatchOrderSan(String deliverOrderIds, String sessionInfoId) {
		DeliverOrderQuery deliverOrderQuery = new DeliverOrderQuery();
		int size = deliverOrderIds.split(",").length;
		Long[] orderIds = new Long[size];
		int i = 0;
		for (String id : deliverOrderIds.split(",")) {
			orderIds[i++] = Long.valueOf(id);
		}
		deliverOrderQuery.setIds(orderIds);
		List<DeliverOrder> deliverOrderList = query(deliverOrderQuery);
		if (CollectionUtils.isNotEmpty(deliverOrderList)) {
			for (DeliverOrder deliverOrder : deliverOrderList) {
				if (DeliverOrderServiceI.AGENT_STATUS_DTS01.equals(deliverOrder.getAgentStatus()) && (ShopDeliverApplyServiceI.DELIVER_WAY_AGENT.equals(deliverOrder.getDeliveryWay()) || ShopDeliverApplyServiceI.DELIVER_WAY_CUSTOMER_AGENT.equals(deliverOrder.getDeliveryWay()))) {
					deliverOrder.setAgentStatus(DeliverOrderServiceI.AGENT_STATUS_DTS02);
					editAndAddLog(deliverOrder, DeliverOrderLogServiceI.TYPE_DLT15, "批量打单成功", sessionInfoId);
				}
			}
		}
	}
}
