package com.bx.ant.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bx.ant.pageModel.*;
import com.bx.ant.service.DeliverOrderServiceI;
import com.bx.ant.service.DeliverOrderShopItemServiceI;
import com.bx.ant.service.ShopOrderBillServiceI;
import com.mobian.absx.F;
import com.bx.ant.dao.DeliverOrderShopDaoI;
import com.bx.ant.model.TdeliverOrderShop;
import com.mobian.exception.ServiceException;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbShop;
import com.mobian.pageModel.PageHelper;
import com.bx.ant.service.DeliverOrderShopServiceI;
import com.mobian.service.MbShopServiceI;
import com.mobian.thirdpart.redis.Key;
import com.mobian.thirdpart.redis.Namespace;
import com.mobian.thirdpart.redis.RedisUtil;
import com.mobian.util.ConvertNameUtil;
import com.mobian.util.DateUtil;
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
	@Resource
	private ShopOrderBillServiceI shopOrderBillService;

	@Autowired
	private DeliverOrderShopItemServiceI deliverOrderShopItemSerivce;


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
				whereHql += " and t.status in(:status)";
				if (deliverOrderShop.getStatus().split(",") != null && deliverOrderShop.getStatus().split(",").length > 0) {
					params.put("status", deliverOrderShop.getStatus().split(","));
				} else {
					params.put("status", deliverOrderShop.getStatus());
				}
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
			if (!F.empty(deliverOrderShop.getShopPayStatus())) {
				whereHql += " and t.shopPayStatus = :shopPayStatus";
				params.put("shopPayStatus", deliverOrderShop.getShopPayStatus());
			}
			if (deliverOrderShop instanceof DeliverOrderShopQuery) {
				DeliverOrderShopQuery ext = (DeliverOrderShopQuery) deliverOrderShop;
				if (ext.getStatusList() != null && ext.getStatusList().length > 0) {
					whereHql += " and t.status in (:alist)";
					params.put("alist", ext.getStatusList());
				}
				if (ext.getEndDate() != null) {
					whereHql += " and t.addtime <= :endDate ";
					params.put("endDate", ext.getEndDate());

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
	public DeliverOrderShop get(Long id) {
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
		deliverOrderShopDao.executeHql("update TdeliverOrderShop t set t.isdeleted = 1 where t.id = :id", params);
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
	public DeliverOrderShop editStatus(DeliverOrderShop deliverOrderShop, DeliverOrderShop orderShopEdit) {
		List<DeliverOrderShop> deliverOrderShops = query(deliverOrderShop);
		DeliverOrderShop o = new DeliverOrderShop();
		if (CollectionUtils.isNotEmpty(deliverOrderShops)  && deliverOrderShops.size() == 1) {
			//只对第一个结果进行处理
			o = deliverOrderShops.get(0);
			o.setStatus(orderShopEdit.getStatus());
			if(!F.empty(orderShopEdit.getShopPayStatus())){
				o.setShopPayStatus(orderShopEdit.getShopPayStatus());
			}
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
				if (now.getTime() - orderShop.getUpdatetime().getTime() > Integer.valueOf(ConvertNameUtil.getString("DSV100", "10"))*60*1000) {
					DeliverOrder deliverOrder = new DeliverOrder();
					deliverOrder.setShopId(orderShop.getShopId());
					deliverOrder.setId(orderShop.getDeliverOrderId());
					deliverOrder.setStatus(DeliverOrderServiceI.STATUS_SHOP_REFUSE);
					deliverOrder.setRemark("超时未接单");
					deliverOrderService.transform(deliverOrder);
				}
			}
		}
	}

	@Override
	public DataGrid dataGridShopArtificialPay(DeliverOrderShop deliverOrderShop, PageHelper ph) {
		DataGrid dataGrid = dataGrid(deliverOrderShop, ph);
		List<DeliverOrderShop> deliverOrderShops = dataGrid.getRows();
		if (CollectionUtils.isNotEmpty(deliverOrderShops)) {
			List<DeliverOrderShopQuery> deliverOrderShopQueries = new ArrayList<DeliverOrderShopQuery>();
			for (DeliverOrderShop order : deliverOrderShops) {
				DeliverOrderShopQuery deliverOrderShopQuery = new DeliverOrderShopQuery();
				BeanUtils.copyProperties(order, deliverOrderShopQuery);
				deliverOrderShopQuery.setStatusName(order.getStatus());
				deliverOrderShopQuery.setShopPayStatusName(order.getShopPayStatus());
				MbShop shop = mbShopService.get(order.getShopId());
				if (shop != null) {
					deliverOrderShopQuery.setShopName(shop.getName());
				}
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
	public DeliverOrderShopView getView(Long id) {
		DeliverOrderShop deliverOrderShop = get(id);
		DeliverOrderShopView deliverOrderShopView = new DeliverOrderShopView();
		BeanUtils.copyProperties(deliverOrderShop, deliverOrderShopView);
		fillShopItemInfo(deliverOrderShopView);
		fillDeliverOrderInfo(deliverOrderShopView);
		return deliverOrderShopView;
	}

	/**
	 * 填充商品信息
	 * @param deliverOrderShopView
	 */
	protected  void fillShopItemInfo(DeliverOrderShopView deliverOrderShopView) {
		DeliverOrderShopItem deliverOrderShopItem = new DeliverOrderShopItem();
		deliverOrderShopItem.setDeliverOrderShopId(deliverOrderShopView.getId());
		List<DeliverOrderShopItem> deliverOrderShopItems = deliverOrderShopItemSerivce.list(deliverOrderShopItem);
		deliverOrderShopView.setDeliverOrderShopItemList(deliverOrderShopItems);
	}

	/**
	 * 填充order信息
	 * @param deliverOrderShopView
	 */
	protected void fillDeliverOrderInfo(DeliverOrderShopView deliverOrderShopView) {
		if (!F.empty(deliverOrderShopView.getDeliverOrderId())) {
			DeliverOrder deliverOrder = deliverOrderService.get(deliverOrderShopView.getDeliverOrderId());
			if (deliverOrder != null) {
				deliverOrderShopView.setContactPeople(deliverOrder.getContactPeople());
				deliverOrderShopView.setContactPhone(deliverOrder.getContactPhone());
				deliverOrderShopView.setDeliverAddress(deliverOrder.getDeliveryAddress());
				deliverOrderShopView.setDeliverRequireTime(deliverOrder.getDeliveryRequireTime());
				deliverOrderShopView.setLongitude(deliverOrder.getLongitude());
				deliverOrderShopView.setLatitude(deliverOrder.getLatitude());
			}
		}
	}

	@Override
	public void settleShopPay() {
		//1. 找到所有超时门店订单
		DeliverOrderShopQuery deliverOrderShop = new DeliverOrderShopQuery();
		deliverOrderShop.setShopPayStatus("SPS01");
		deliverOrderShop.setStatus(DeliverOrderShopServiceI.STAUS_SERVICE);
	 	Date endDate= DateUtil.addDayToDate(new Date(),-Integer.valueOf(ConvertNameUtil.getString("DVS600", "7")));
		deliverOrderShop.setEndDate(endDate);
		PageHelper ph = new PageHelper();
		List<DeliverOrderShop> deliverOrderShopList = dataGrid(deliverOrderShop, ph).getRows();

		//2. 根据shopId进行分类:一个shop对应一个账单
		Map<Integer, ShopOrderBillQuery> deliverOrderMap = new HashMap<Integer, ShopOrderBillQuery>();
		int listSize = deliverOrderShopList.size();
		for (int i = 0; i < listSize; i++) {
			DeliverOrderShop order = deliverOrderShopList.get(i);
			ShopOrderBillQuery shopOrderBillQuery;
			//2.1初始化一个账单
			if (!deliverOrderMap.containsKey(order.getShopId())) {
				shopOrderBillQuery = new ShopOrderBillQuery();
				List<DeliverOrderShop> deliverOrderShops = new ArrayList<DeliverOrderShop>();
				deliverOrderShops.add(order);
				Long[] deliverOrderIds = {order.getId()};
				shopOrderBillQuery.setAmount(order.getAmount());
				shopOrderBillQuery.setShopId(order.getShopId());
				shopOrderBillQuery.setDeliverOrderIds(deliverOrderIds);
				shopOrderBillQuery.setDeliverOrderShopList(deliverOrderShops);
				shopOrderBillQuery.setPayWay("DPW01");

				//2.2 填充账单
			} else {
				shopOrderBillQuery = deliverOrderMap.get(order.getShopId());

				//2.2.1 添加deliverOrderIds
				int arrayLen = shopOrderBillQuery.getDeliverOrderIds().length;
				Long[] deliverOrderIds = new Long[arrayLen + 1];
				System.arraycopy(shopOrderBillQuery.getDeliverOrderIds(), 0, deliverOrderIds, 0, arrayLen);
				deliverOrderIds[arrayLen] = order.getId();
				shopOrderBillQuery.setDeliverOrderIds(deliverOrderIds);

				//2.2.2 计算金额并填充信息
				shopOrderBillQuery.setAmount(order.getAmount() + shopOrderBillQuery.getAmount());
				shopOrderBillQuery.setDeliverOrderIds(deliverOrderIds);
				shopOrderBillQuery.getDeliverOrderShopList().add(order);
			}
			deliverOrderMap.put(order.getShopId(), shopOrderBillQuery);
		}

		//3. 对账单进行添加并进行结算
		for (Map.Entry entry : deliverOrderMap.entrySet()) {
			ShopOrderBillQuery shopOrderBillQuery = (ShopOrderBillQuery) entry.getValue();
			shopOrderBillService.addAndPayShopOrderBillAndShopPay(shopOrderBillQuery);
			List<DeliverOrderShop> orderShopList = shopOrderBillQuery.getDeliverOrderShopList();
			int size = orderShopList.size();
			for (int i = 0; i < size; i++) {
				DeliverOrderShop order = orderShopList.get(i);
				DeliverOrderExt orderExt = new DeliverOrderExt();
				orderExt.setId(order.getDeliverOrderId());
				orderExt.setShopId(order.getShopId());
				orderExt.setBalanceLogType("BT060");
				orderExt.setStatus("DOS40");
				deliverOrderService.transform(orderExt);
			}
		}
	}

	@Override
	public DeliverOrderShop editStatusByHql(DeliverOrderShop deliverOrderShop, String status, String shopPayStatus) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status",deliverOrderShop.getStatus());
		params.put("shopPayStatus",deliverOrderShop.getShopPayStatus());
		params.put("deliverOrderId",deliverOrderShop.getDeliverOrderId());
		params.put("newsStatus", status);
		params.put("newsShopPayStatus", shopPayStatus);
		DeliverOrderShop orderShop=query(deliverOrderShop).get(0);
		int result=deliverOrderShopDao.executeHql("update TdeliverOrderShop t set t.status = :newsStatus , t.shopPayStatus = :newsShopPayStatus " +
				"where t.status = :status and t.shopPayStatus = :shopPayStatus and t.deliverOrderId = :deliverOrderId", params);
		if (result <= 0) {
			throw new ServiceException("修改门店订单状态失败");
		}else {
			return orderShop;
		}
	}

	@Override
	public List<DeliverOrderShop> queryTodayOrdersByShopId(Integer shopId) {

		//获取当天结束与开始
		Calendar todayC = Calendar.getInstance();
		todayC.set(Calendar.HOUR_OF_DAY,0);
		todayC.set(Calendar.MINUTE,0);
		todayC.set(Calendar.SECOND,0);
		Date todayStart = todayC.getTime();
		todayC.set(Calendar.HOUR_OF_DAY,23);
		todayC.set(Calendar.MINUTE,59);
		todayC.set(Calendar.SECOND,59);
		Date todayEnd = todayC.getTime();

		DeliverOrderShopQuery deliverOrderShop = new DeliverOrderShopQuery();
		deliverOrderShop.setShopId(shopId);
		String[] statusList = {DeliverOrderShopServiceI.STATUS_ACCEPTED,DeliverOrderShopServiceI.STATUS_COMPLETE,DeliverOrderShopServiceI.STAUS_SERVICE};
		deliverOrderShop.setStatusList(statusList);
		deliverOrderShop.setUpdatetimeBegin(todayStart);
		deliverOrderShop.setUpdatetimeEnd(todayEnd);

		PageHelper ph = new PageHelper();
		ph.setHiddenTotal(true);
		return dataGrid(deliverOrderShop, ph).getRows();
	}
}
