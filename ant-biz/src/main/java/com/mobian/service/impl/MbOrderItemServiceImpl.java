package com.mobian.service.impl;

import com.mobian.absx.F;
import com.mobian.dao.MbOrderItemDaoI;
import com.mobian.model.TmbItemStock;
import com.mobian.model.TmbOrderItem;
import com.mobian.model.TmbWarehouse;
import com.mobian.pageModel.*;
import com.mobian.service.*;
import com.mobian.util.ConvertNameUtil;
import com.mobian.util.DateUtil;
import com.mobian.util.MyBeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MbOrderItemServiceImpl extends BaseServiceImpl<MbOrderItem> implements MbOrderItemServiceI {

	@Autowired
	private MbOrderItemDaoI mbOrderItemDao;

	@Autowired
	private MbItemServiceI mbItemService;
	@Autowired
	private MbWarehouseServiceI mbWarehouseService;
	@Autowired
	private MbOrderServiceI mbOrderService;
	@Autowired
	private MbItemStockServiceI mbItemStockService;
	@Autowired
	private RedisUserServiceImpl redisUserService;
	@Autowired
	private MbOrderRefundItemServiceI mbOrderRefundItemService;
	@Autowired
	private MbShopServiceI mbShopService;


	@Override
	public DataGrid dataGrid(MbOrderItem mbOrderItem, PageHelper ph) {
		List<MbOrderItem> ol = new ArrayList<MbOrderItem>();
		String hql = " from TmbOrderItem t ";
		if(mbOrderItem.getShopId() !=null) {
			MbOrder mbOrder =new MbOrder();
			mbOrder.setOrderTimeBegin(mbOrderItem.getUpdatetimeBegin());
			mbOrder.setOrderTimeEnd(mbOrderItem.getUpdatetimeEnd());
			mbOrder.setShopId(mbOrderItem.getShopId());
			List<MbOrder> list = mbOrderService.query(mbOrder);
			Integer[] orderIds= new Integer[list.size()];
			for (int i = 0; i < list.size(); i++) {
				orderIds[i] = list.get(i).getId();
			}
			mbOrderItem.setOrderIds(orderIds);
		}
		DataGrid dg = dataGridQuery(hql, ph, mbOrderItem, mbOrderItemDao);
		@SuppressWarnings("unchecked")
		List<TmbOrderItem> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TmbOrderItem t : l) {
				MbOrderItem o = new MbOrderItem();
				BeanUtils.copyProperties(t, o);

				if (o.getItemId() == -1) {
					dg.setTotal(dg.getTotal() - 1);
					continue;
				}

				if (o.getItemId() != null) {
					MbItem item = mbItemService.getFromCache(o.getItemId());
					if (item != null) {
						o.setItem(item);
					}
				}

				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}

	public DataGrid dataGridAndStock(MbOrderItem mbOrderItem, PageHelper ph) {
		DataGrid dataGrid = dataGrid(mbOrderItem, ph);
		List<MbOrderItem> rows = dataGrid.getRows();
		MbOrder mbOrder = mbOrderService.get(mbOrderItem.getOrderId());
		if ("OD10".equals(mbOrder.getStatus()) || "OD09".equals(mbOrder.getStatus())) {
			List<TmbWarehouse> warehouseList = mbWarehouseService.getWarehouseListByWarehouseType("WT03");
			if (!CollectionUtils.isEmpty(warehouseList)) {
				Integer[] ids = new Integer[warehouseList.size()];
				for (int i = 0; i < ids.length; i++) {
					ids[i] = warehouseList.get(i).getId();
				}
				for (MbOrderItem row : rows) {

					List<TmbItemStock> tmbItemStockList = mbItemStockService.queryItemStockListByItemIdAndWarehouseIds(row.getItemId(), ids);
					if (!CollectionUtils.isEmpty(tmbItemStockList)) {
						String usableQuantityStr = "";
						for (TmbItemStock stock : tmbItemStockList) {
							Integer orderQuantity = redisUserService.getOrderQuantity(stock.getWarehouseId() + ":" + stock.getItemId());
							orderQuantity = orderQuantity == null ? 0 : orderQuantity;
							Integer stockQuantity = (stock.getQuantity() == null ? 0 : stock.getQuantity()) - orderQuantity;
							MbWarehouse warehouse = mbWarehouseService.getFromCache(stock.getWarehouseId());
							if (warehouse != null && stockQuantity > 0) {
								usableQuantityStr += warehouse.getName() + ":" + stockQuantity + "  ";
							}
						}
						if (usableQuantityStr != null) {
							row.setUsableQuantity(usableQuantityStr);
						}
					}

				}
			}
		}
		return dataGrid;
	}
	public List<TmbOrderItem> queryMbOrderItemByOrderId(Integer orderId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderId", orderId);
		return mbOrderItemDao.find("from TmbOrderItem t where t.isdeleted = 0 and t.orderId = :orderId", map);
	}
	protected String whereHql(MbOrderItem mbOrderItem, Map<String, Object> params) {
		String whereHql = "";	
		if (mbOrderItem != null) {
			whereHql += " where t.isdeleted = 0 and t.itemId != -1 ";
			if (!F.empty(mbOrderItem.getTenantId())) {
				whereHql += " and t.tenantId = :tenantId";
				params.put("tenantId", mbOrderItem.getTenantId());
			}		
			if (!F.empty(mbOrderItem.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", mbOrderItem.getIsdeleted());
			}		
			if (!F.empty(mbOrderItem.getItemId())) {
				whereHql += " and t.itemId = :itemId";
				params.put("itemId", mbOrderItem.getItemId());
			}		
			if (!F.empty(mbOrderItem.getQuantity())) {
				whereHql += " and t.quantity = :quantity";
				params.put("quantity", mbOrderItem.getQuantity());
			}		
			if (!F.empty(mbOrderItem.getMarketPrice())) {
				whereHql += " and t.marketPrice = :marketPrice";
				params.put("marketPrice", mbOrderItem.getMarketPrice());
			}		
			if (!F.empty(mbOrderItem.getBuyPrice())) {
				whereHql += " and t.buyPrice = :buyPrice";
				params.put("buyPrice", mbOrderItem.getBuyPrice());
			}		
			if (!F.empty(mbOrderItem.getOrderId())) {
				whereHql += " and t.orderId = :orderId";
				params.put("orderId", mbOrderItem.getOrderId());
			}
			if (!F.empty(mbOrderItem.getOrderId())) {
				whereHql += " and t.orderId = :orderId";
				params.put("orderId", mbOrderItem.getOrderId());
			}

			if(mbOrderItem.getUpdatetimeBegin()!=null){
				whereHql += " and t.addtime >= :updatetimeBegin";
				params.put("updatetimeBegin", mbOrderItem.getUpdatetimeBegin());
			}
			if(mbOrderItem.getUpdatetimeEnd()!=null){
				whereHql += " and t.addtime <= :updatetimeEnd";
				params.put("updatetimeEnd", mbOrderItem.getUpdatetimeEnd());
			}
			if(mbOrderItem.getOrderIds() !=null) {
				whereHql += " and t.orderId in (:alist)";
				params.put("alist", mbOrderItem.getOrderIds());
			}
		}	
		return whereHql;
	}

	@Override
	public void add(MbOrderItem mbOrderItem) {
		TmbOrderItem t = new TmbOrderItem();
		BeanUtils.copyProperties(mbOrderItem, t);
		//t.setId(jb.absx.UUID.uuid());
		t.setIsdeleted(false);
		mbOrderItemDao.save(t);
	}

	@Override
	public MbOrderItem get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TmbOrderItem t = mbOrderItemDao.get("from TmbOrderItem t  where t.id = :id", params);
		MbOrderItem o = new MbOrderItem();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(MbOrderItem mbOrderItem) {
		TmbOrderItem t = mbOrderItemDao.get(TmbOrderItem.class, mbOrderItem.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(mbOrderItem, t, new String[]{"id", "addtime", "isdeleted", "updatetime"}, true);
		}
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		mbOrderItemDao.executeHql("update TmbOrderItem t set t.isdeleted = 1 where t.id = :id",params);
		//mbOrderItemDao.delete(mbOrderItemDao.get(TmbOrderItem.class, id));
	}

	@Override
	@Cacheable(value = "orderItemDeliveryPriceCache", key = "#orderId")
	public Integer getDeliveryPrice(Integer orderId) {
		TmbOrderItem orderItem = mbOrderItemDao.get("from TmbOrderItem where itemId = -1 and orderId = " + orderId);
		if (orderItem != null) {
			return orderItem.getBuyPrice();
		}
		return 0;
	}

	@Override
	@Cacheable(value = "orderItemTotalPriceCache", key = "#orderId")
	public Integer getTotalPrice(Integer orderId) {
		List<TmbOrderItem> orderItemList = mbOrderItemDao.find("from TmbOrderItem where orderId = " + orderId);
		Integer totalPrice = 0;
		for (TmbOrderItem orderItem : orderItemList) {
			totalPrice += orderItem.getBuyPrice() * orderItem.getQuantity();
		}
		return totalPrice;
	}

	@Override
	public List<MbOrderItem> getMbOrderItemList(Integer orderId) {
		List<TmbOrderItem> orderItemList = mbOrderItemDao.find("from TmbOrderItem t where t.isdeleted = 0 and t.itemId != -1 and t.orderId = " + orderId);
		if(orderItemList != null && orderItemList.size() > 0) {
			List<MbOrderItem> ol = new ArrayList<MbOrderItem>();
			for (TmbOrderItem t : orderItemList) {
				MbOrderItem o = new MbOrderItem();
				BeanUtils.copyProperties(t, o);
				ol.add(o);

				if (o.getItemId() != null) {
					MbItem item = mbItemService.getFromCache(o.getItemId());
					if (item != null) {
						o.setItem(item);
					}
				}
			}
			return ol;
		}

		return null;
	}
	@Override
	public List<MbOrderItem> query(MbOrderItem mbOrderItem){
		List<MbOrderItem> ol = new ArrayList<MbOrderItem>();
		String hql = " from TmbOrderItem t ";
		Map<String, Object> params = new HashMap<String, Object>();
		String where = whereHql(mbOrderItem, params);
		List<TmbOrderItem> l = mbOrderItemDao.find(hql + where, params);
		if (CollectionUtils.isNotEmpty(l)) {
			for (TmbOrderItem t : l) {
				MbOrderItem o = new MbOrderItem();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

	@Override
	public DataGrid dataGridSalesReport(MbSalesReport mbSalesReport) {
		DataGrid dataGrid = new DataGrid();
		Map<Integer, MbSalesReport> map = new HashMap<Integer, MbSalesReport>();
		Map<String, Integer> priceMap = new HashMap<String, Integer>();
		Map<String, Integer> costPriceMap = new HashMap<String, Integer>();
		List<MbSalesReport> ol = new ArrayList<MbSalesReport>();
		MbOrder mbOrder = new MbOrder();
		mbOrder.setDeliveryTimeBegin(mbSalesReport.getStartDate());
		mbOrder.setDeliveryTimeEnd(mbSalesReport.getEndDate());
		mbOrder.setDeliveryWarehouseId(mbSalesReport.getWarehouseId());
		mbOrder.setStatus(mbSalesReport.getOrderStatus());
		if (!F.empty(mbSalesReport.getShopId())) {
			List<MbShop> shopList = mbShopService.queryListById(mbSalesReport.getShopId());
			Integer[] shopIds = new Integer[shopList.size()];
			for (int i = 0; i < shopList.size(); i++) {
				shopIds[i] = shopList.get(i).getId();
			}
			mbOrder.setShopId(mbSalesReport.getShopId());
			mbOrder.setShopIds(shopIds);
		}
		if ("OD40".equals(mbSalesReport.getOrderStatus())) {
			mbOrder.setPayStatus("PS01");
		}
		String split = "_";
		//先找到所有符合条件的订单
		List<MbOrder> mbOrders = mbOrderService.query(mbOrder);
		if (!CollectionUtils.isEmpty(mbOrders)) {
			List<Integer> orderIdList = new ArrayList<Integer>();
			String excludeShop = ConvertNameUtil.getDesc("SV101");
			for (MbOrder order : mbOrders) {
				if (excludeShop.indexOf("|" + order.getShopId() + "|") > -1) {
					continue;
				}
				if (F.empty(mbSalesReport.getShopType())) {
					orderIdList.add(order.getId());
				} else if (!F.empty(mbSalesReport.getShopType())) {
					MbShop mbShop = mbShopService.getFromCache(order.getShopId());
					if (mbShop != null && mbShop.getShopType().equals(mbSalesReport.getShopType())) {
						orderIdList.add(order.getId());
					}
				}
			}
			Integer[] orderIds = orderIdList.toArray(new Integer[orderIdList.size()]);

			//找到所有订单项
			List<MbOrderItem> orderItems = queryListByOrderIds(orderIds);
			List<MbOrderRefundItem> orderRefundItems = mbOrderRefundItemService.queryListByOrderIds(orderIds);
			//将同种商品在不同订单里的不同价格都保存
			for (MbOrder m : mbOrders) {
				for (MbOrderItem item : orderItems) {
					priceMap.put(m.getId() + split + item.getItemId(), item.getBuyPrice());
					costPriceMap.put(m.getId() + split + item.getItemId(), item.getCostPrice());
				}
			}

			//遍历所有订单的所有订单项
			for (MbOrderItem item : orderItems) {
				//把所有订单项数量累加起来
				if (map.containsKey(item.getItemId())) {
					MbSalesReport oldItem = map.get(item.getItemId());
					oldItem.setQuantity(oldItem.getQuantity() + item.getQuantity());
					oldItem.setTotalPrice(oldItem.getTotalPrice() + item.getQuantity() * item.getBuyPrice());
					if (item.getCostPrice() != null)
						oldItem.setTotalCost(oldItem.getTotalCost() + item.getQuantity() * item.getCostPrice());
				} else {
					MbSalesReport salesReport = new MbSalesReport();
					if (item.getCostPrice() != null) {
						salesReport.setTotalCost(item.getQuantity() * item.getCostPrice());
					} else {
						salesReport.setTotalCost(0);
					}
					salesReport.setTotalPrice(item.getQuantity() * item.getBuyPrice());
					salesReport.setQuantity(item.getQuantity());
					map.put(item.getItemId(), salesReport);
				}

			}
			//遍历所有订单里面所有的退货
			for (MbOrderRefundItem rf : orderRefundItems) {
				Integer price = priceMap.get(rf.getOrderId() + split + rf.getItemId());
				Integer costPrice = costPriceMap.get(rf.getOrderId() + split + rf.getItemId());
				if (map.get(rf.getItemId()) != null) {
					MbSalesReport salesReport = map.get(rf.getItemId());
					if (salesReport.getBackQuantity() == null) {
						salesReport.setBackQuantity(0);
					}
					salesReport.setBackQuantity(salesReport.getBackQuantity() + rf.getQuantity());
					if (salesReport.getBackMoney() == null) {
						salesReport.setBackMoney(0);
					}
					salesReport.setBackMoney(salesReport.getBackMoney() + rf.getQuantity() * price);
					if (costPrice != null) {
						salesReport.setTotalCost(salesReport.getTotalCost() - rf.getQuantity() * costPrice);
					}
				}

			}

		}
		//统计报表
		Integer totalPrice = 0;
		Integer totalCost = 0;
		Integer total = 0;
		Integer backTotal = 0;
		Integer salesTotal = 0;
		Set<Integer> keys = map.keySet();
		for (Integer key : keys) {
			MbItem mbItem = mbItemService.getFromCache(key);
			MbSalesReport salesReport = new MbSalesReport();
			salesReport.setItemId(key);
			salesReport.setItemCode(mbItem.getCode());
			salesReport.setItemName(mbItem.getName());
			salesReport.setQuantity(map.get(key).getQuantity());
			salesReport.setBackQuantity(map.get(key).getBackQuantity());
			salesReport.setTotalCost(map.get(key).getTotalCost());
			if (!F.empty(map.get(key).getBackQuantity())) {
				salesReport.setSalesQuantity(salesReport.getQuantity() - map.get(key).getBackQuantity());
				backTotal += salesReport.getBackQuantity();
			} else {
				salesReport.setSalesQuantity(salesReport.getQuantity());
			}
			if (!F.empty(map.get(key).getBackMoney())) {
				salesReport.setTotalPrice(map.get(key).getTotalPrice() - map.get(key).getBackMoney());
			} else {
				salesReport.setTotalPrice(map.get(key).getTotalPrice());
			}
			ol.add(salesReport);
			total += salesReport.getQuantity();
			salesTotal += salesReport.getSalesQuantity();
			totalPrice += salesReport.getTotalPrice();
			if (!F.empty(map.get(key).getTotalCost()))
				totalCost += map.get(key).getTotalCost();
		}
		//将销售明细按照数量高低来排序
		Collections.sort(ol, new Comparator<MbSalesReport>() {
			public int compare(MbSalesReport arg0, MbSalesReport arg1) {
				return arg1.getQuantity().compareTo(arg0.getQuantity());
			}
		});
		//最后一栏加上合计
		if (!CollectionUtils.isEmpty(ol)) {
			MbSalesReport mbsalesReport = new MbSalesReport();
			mbsalesReport.setItemCode("合计");
			mbsalesReport.setTotalPrice(totalPrice);
			mbsalesReport.setSalesQuantity(salesTotal);
			mbsalesReport.setQuantity(total);
			mbsalesReport.setBackQuantity(backTotal);
			mbsalesReport.setTotalCost(totalCost);
			//ol.add(mbsalesReport);
			dataGrid.setFooter(Arrays.asList(mbsalesReport));
		}
		dataGrid.setRows(ol);

		return dataGrid;
	}

	@Override
	public List<MbOrderItem> queryListByOrderIds(Integer[] orderIds) {
		List<MbOrderItem> ol = new ArrayList<MbOrderItem>();
		if (orderIds != null && orderIds.length > 0) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("alist", orderIds);
			List<TmbOrderItem> l = mbOrderItemDao.find("from TmbOrderItem t where t.orderId in (:alist) and t.isdeleted=0", params);
			if (CollectionUtils.isNotEmpty(l)) {
				for (TmbOrderItem t : l) {
					MbOrderItem o = new MbOrderItem();
					BeanUtils.copyProperties(t, o);
					ol.add(o);
				}
			}
		}
		return ol;
	}
	@Override
	public void addOffSet(MbOrder mbOrder, Integer itemId, Integer quantity) {
		MbOrderItem mbOrderItem = new MbOrderItem();
		mbOrderItem.setOrderId(mbOrder.getId());
		mbOrderItem.setItemId(itemId);
		mbOrderItem.setQuantity(quantity);
		MbOrder newMbOrder = mbOrderService.get(mbOrderItem.getOrderId());
		Integer contractPrice = redisUserService.getContractPrice(newMbOrder.getShopId(), mbOrderItem.getItemId());
		if (contractPrice == null) {
			MbItem mbItem = mbItemService.getFromCache(mbOrderItem.getItemId());
			mbOrderItem.setMarketPrice(mbItem.getMarketPrice());
		}else{
			mbOrderItem.setMarketPrice(contractPrice);
		}
		mbOrderItem.setBuyPrice(0);
		add(mbOrderItem);
	}

	@Override
	public List<MbOrderItem> queryListByWithoutCostPrice() {
		List<MbOrderItem> ol = new ArrayList<MbOrderItem>();
		String sql = "from TmbOrderItem t where t.isdeleted = 0 and t.costPrice is null and t.addtime >= :updatetimeBegin";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("updatetimeBegin", DateUtil.addDayToDate(new Date(), -1));
		List<TmbOrderItem> l =  mbOrderItemDao.find(sql,params, 1,200);
		if (CollectionUtils.isNotEmpty(l)) {
			for (TmbOrderItem t : l) {
				MbOrderItem o = new MbOrderItem();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}
}
