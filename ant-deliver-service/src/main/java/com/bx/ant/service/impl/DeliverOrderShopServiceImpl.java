package com.bx.ant.service.impl;

import com.bx.ant.dao.DeliverOrderShopDaoI;
import com.bx.ant.model.TdeliverOrderShop;
import com.bx.ant.pageModel.*;
import com.bx.ant.service.*;
import com.mobian.absx.F;
import com.mobian.exception.ServiceException;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbShop;
import com.mobian.pageModel.PageHelper;
import com.mobian.service.MbShopServiceI;
import com.mobian.util.ConvertNameUtil;
import com.mobian.util.DateUtil;
import com.mobian.util.MyBeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

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
	@Resource
	private DeliverOrderShopPayServiceI deliverOrderShopPayService;

	@Autowired
	private HibernateTransactionManager transactionManager;

	@Autowired
	private DeliverOrderShopItemServiceI deliverOrderShopItemService;


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
			if (deliverOrderShop.getIds() != null && deliverOrderShop.getIds().length > 0) {
				whereHql += " and t.id not in(:ids)";
				params.put("ids", deliverOrderShop.getIds());
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
				params.put("status", deliverOrderShop.getStatus().split(","));
			}
			if (!F.empty(deliverOrderShop.getAmount())) {
				whereHql += " and t.amount = :amount";
				params.put("amount", deliverOrderShop.getAmount());
			}
			if (deliverOrderShop.getUpdatetimeBegin() != null) {
				whereHql += " and t.updatetime >= :updatetimeBegin";
				params.put("updatetimeBegin", deliverOrderShop.getUpdatetimeBegin());
			}
			if (deliverOrderShop.getUpdatetimeEnd() != null) {
				whereHql += " and t.updatetime < :updatetimeEnd";
				params.put("updatetimeEnd", deliverOrderShop.getUpdatetimeEnd());
			}

			if (deliverOrderShop.getAddtimeBegin() != null) {
				whereHql += " and t.addtime >= :addtimeBegin";
				params.put("addtimeBegin", deliverOrderShop.getAddtimeBegin());
			}
			if (deliverOrderShop.getAddtimeEnd() != null) {
				whereHql += " and t.addtime < :addtimeEnd";
				params.put("addtimeEnd", deliverOrderShop.getAddtimeEnd());
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
				if (ext.getDeliverOrderShopIds() != null) {
					Long[] orderShopIds = new Long[ext.getDeliverOrderShopIds().split(",").length];
					int i = 0;
					for (String orderShopId : ext.getDeliverOrderShopIds().split(",")) {
						orderShopIds[i++] = Long.valueOf(orderShopId).longValue();
					}
					whereHql += " and t.id in(:ids)";
					params.put("ids", orderShopIds);
				}
				if (F.empty(deliverOrderShop.getOrderId())) {
					whereHql += " and t.orderId is null";
				}
			}
		}

		return whereHql;
	}

	@Override
	public void add(DeliverOrderShop deliverOrderShop) {
		TdeliverOrderShop t = new TdeliverOrderShop();
		BeanUtils.copyProperties(deliverOrderShop, t);
		t.setIsdeleted(false);
		deliverOrderShopDao.save(t);
		deliverOrderShop.setId(t.getId());
	}

	@Override
	public DeliverOrderShop getByDeliverOrderId(Long deliverOrderId) {
		DeliverOrderShopQuery orderShopQuery = new DeliverOrderShopQuery();
		orderShopQuery.setStatusList(VALID_STATUS);
		orderShopQuery.setDeliverOrderId(deliverOrderId);
		List<DeliverOrderShop> deliverOrderShops = query(orderShopQuery);
		if (CollectionUtils.isNotEmpty(deliverOrderShops)) {
			return deliverOrderShops.get(0);
		}
		return null;
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
		if (CollectionUtils.isNotEmpty(deliverOrderShops) && deliverOrderShops.size() == 1) {
			//只对第一个结果进行处理
			o = deliverOrderShops.get(0);
			o.setStatus(orderShopEdit.getStatus());
			if (!F.empty(orderShopEdit.getShopPayStatus())) {
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
			for (DeliverOrderShop orderShop : deliverOrderShops) {
				Date now = new Date();
				if (now.getTime() - orderShop.getAddtime().getTime() > Integer.valueOf(ConvertNameUtil.getString("DSV100", "10")) * 60 * 1000) {
					DeliverOrder deliverOrder = new DeliverOrder();
					deliverOrder.setShopId(orderShop.getShopId());
					deliverOrder.setId(orderShop.getDeliverOrderId());
					deliverOrder.setStatus(DeliverOrderServiceI.STATUS_SHOP_REFUSE);
					deliverOrder.setRemark("超时未接单");
					DefaultTransactionDefinition def = new DefaultTransactionDefinition();
					def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
					TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态
					try {
						deliverOrderService.transform(deliverOrder);
						transactionManager.commit(status);
					}catch (Exception e){
						transactionManager.rollback(status);
						e.printStackTrace();
						continue;
					}
				}
			}
		}
	}

	@Override
	public DataGrid dataGridShopArtificialPay(DeliverOrderShop deliverOrderShop, PageHelper ph) {
		//已配送完成,等待用户确认状态
		deliverOrderShop.setStatus("DSS06");
		deliverOrderShop.setShopPayStatus("SPS01");
		DeliverOrderShopPay deliverOrderShopPay = new DeliverOrderShopPay();
		deliverOrderShopPay.setStatus("SPS01");
		List<DeliverOrderShopPay> deliverOrderShopPays = deliverOrderShopPayService.query(deliverOrderShopPay);
		if (CollectionUtils.isNotEmpty(deliverOrderShopPays)) {
			Long[] deliverOrderShopIds = new Long[deliverOrderShopPays.size()];
			int i = 0;
			for (DeliverOrderShopPay shopPay : deliverOrderShopPays) {
				deliverOrderShopIds[i++] = shopPay.getDeliverOrderShopId();
			}
			deliverOrderShop.setIds(deliverOrderShopIds);
		}
		DataGrid dataGrid = dataGrid(deliverOrderShop, ph);
		List<DeliverOrderShop> deliverOrderShops = dataGrid.getRows();
		if (CollectionUtils.isNotEmpty(deliverOrderShops)) {
			List<DeliverOrderShopQuery> deliverOrderShopQueries = new ArrayList<DeliverOrderShopQuery>();
			for (DeliverOrderShop order : deliverOrderShops) {
				DeliverOrderShopQuery deliverOrderShopQuery = new DeliverOrderShopQuery();
				BeanUtils.copyProperties(order, deliverOrderShopQuery);
				deliverOrderShopQuery.setStatusName(order.getStatus());
				deliverOrderShopQuery.setShopPayStatusName(order.getShopPayStatus());
				MbShop shop = mbShopService.getFromCache(order.getShopId());
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

	public DataGrid queryUnPayForCount(DeliverOrderShop deliverOrderShop) {
		PageHelper ph = new PageHelper();
		ph.setHiddenTotal(true);
		ph.setRows(100000);
		List<DeliverOrderShopQuery> ol = dataGridShopArtificialPay(deliverOrderShop, ph).getRows();
		Map<Integer, DeliverOrderShopQuery> map = new HashMap<Integer, DeliverOrderShopQuery>();
		for (DeliverOrderShopQuery order : ol) {
			DeliverOrderShopQuery dOrder = map.get(order.getShopId());
			if (dOrder == null) {
				map.put(order.getShopId(), order);
			} else {
				dOrder.setAmount(dOrder.getAmount() + order.getAmount());
			}
		}
		DataGrid dg = new DataGrid();
		dg.setRows(Arrays.asList(map.values().toArray()));
		return dg;
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
		List<DeliverOrderShopItem> deliverOrderShopItems = deliverOrderShopItemService.list(deliverOrderShopItem);
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
				deliverOrderShopView.setOriginalOrderId(deliverOrder.getOriginalOrderId());
				deliverOrderShopView.setOriginalShop(deliverOrder.getOriginalShop());
			}
		}
	}

	@Override
	public void settleShopPay() {
		//1. 找到所有超时门店订单
		DeliverOrderShopQuery deliverOrderShop = new DeliverOrderShopQuery();
		deliverOrderShop.setShopPayStatus("SPS01");
		deliverOrderShop.setStatus(DeliverOrderShopServiceI.STAUS_SERVICE);
	 	Date endDate= DateUtil.addDayToDate(new Date(),-Integer.valueOf(ConvertNameUtil.getString("DSV600", "7")));
		deliverOrderShop.setEndDate(endDate);
		PageHelper ph = new PageHelper();
		List<DeliverOrderShop> deliverOrderShopList = dataGrid(deliverOrderShop, ph).getRows();

		//2. 根据shopId进行分类:一个shop对应一个账单
		Map<Integer, ShopOrderBillQuery> deliverOrderMap = new HashMap<Integer, ShopOrderBillQuery>();
		int listSize = deliverOrderShopList.size();
		for (int i = 0; i < listSize; i++) {
			DeliverOrderShop orderShop = deliverOrderShopList.get(i);
			ShopOrderBillQuery shopOrderBillQuery;
			//2.1初始化一个账单
			if (!deliverOrderMap.containsKey(orderShop.getShopId())) {
				shopOrderBillQuery = new ShopOrderBillQuery();
				List<DeliverOrderShop> deliverOrderShops = new ArrayList<DeliverOrderShop>();
				deliverOrderShops.add(orderShop);
				Long[] deliverOrderIds = {orderShop.getId()};
				shopOrderBillQuery.setAmount(orderShop.getAmount());
				shopOrderBillQuery.setShopId(orderShop.getShopId());
				shopOrderBillQuery.setDeliverOrderIds(deliverOrderIds);
				shopOrderBillQuery.setDeliverOrderShopList(deliverOrderShops);
				shopOrderBillQuery.setPayWay("DPW01");

				//2.2 填充账单
			} else {
				shopOrderBillQuery = deliverOrderMap.get(orderShop.getShopId());

				//2.2.1 添加deliverOrderIds
				int arrayLen = shopOrderBillQuery.getDeliverOrderIds().length;
				Long[] deliverOrderIds = new Long[arrayLen + 1];
				System.arraycopy(shopOrderBillQuery.getDeliverOrderIds(), 0, deliverOrderIds, 0, arrayLen);
				deliverOrderIds[arrayLen] = orderShop.getId();
				shopOrderBillQuery.setDeliverOrderIds(deliverOrderIds);

				//2.2.2 计算金额并填充信息
				shopOrderBillQuery.setAmount(orderShop.getAmount() + shopOrderBillQuery.getAmount());
				shopOrderBillQuery.setDeliverOrderIds(deliverOrderIds);
				shopOrderBillQuery.getDeliverOrderShopList().add(orderShop);
			}
			deliverOrderMap.put(orderShop.getShopId(), shopOrderBillQuery);
		}

		//3. 对账单进行添加并进行结算
		for (ShopOrderBillQuery shopOrderBillQuery : deliverOrderMap.values()) {
			List<DeliverOrderShop> orderShopList = shopOrderBillQuery.getDeliverOrderShopList();
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
			TransactionStatus status = transactionManager.getTransaction(def);
			try {
				shopOrderBillService.addAndPayShopOrderBillAndShopPay(shopOrderBillQuery);
				for (DeliverOrderShop orderShop : orderShopList) {
					DeliverOrderExt orderExt = new DeliverOrderExt();
					orderExt.setId(orderShop.getDeliverOrderId());
					orderExt.setShopId(orderShop.getShopId());
					orderExt.setBalanceLogType("BT060");
					orderExt.setPayWay(DeliverOrderServiceI.PAY_WAY_BALANCE);
					orderExt.setStatus(DeliverOrderServiceI.STATUS_CLOSED);
					orderExt.setOrderShopId(orderShop.getId());
					deliverOrderService.transform(orderExt);
				}
				transactionManager.commit(status);
			}catch (Exception e) {
				transactionManager.rollback(status);
				continue;
			}
		}
	}

	@Override
	public void editStatusByHql(DeliverOrderShop deliverOrderShop, String status, String shopPayStatus) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", deliverOrderShop.getStatus());
		params.put("shopPayStatus", deliverOrderShop.getShopPayStatus());
		params.put("id", deliverOrderShop.getId());
		params.put("newsStatus", status);
		params.put("newsShopPayStatus", shopPayStatus);
		int result = deliverOrderShopDao.executeHql("update TdeliverOrderShop t set t.status = :newsStatus , t.shopPayStatus = :newsShopPayStatus " +
				"where t.status = :status and t.shopPayStatus = :shopPayStatus and t.id = :id", params);
		if (result <= 0) {
			throw new ServiceException("修改门店订单状态失败");
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
		deliverOrderShop.setAddtimeBegin(todayStart);
		deliverOrderShop.setAddtimeEnd(todayEnd);

		PageHelper ph = new PageHelper();
		ph.setHiddenTotal(true);
		return dataGrid(deliverOrderShop, ph).getRows();
	}

	@Override
	public List<DeliverOrderShop> queryByDeliverOrderShopIds(String deliverOrderShopIds) {
		DeliverOrderShopQuery deliverOrderShop = new DeliverOrderShopQuery();
		deliverOrderShop.setDeliverOrderShopIds(deliverOrderShopIds);
		return query(deliverOrderShop);
	}

    @Override
    public DeliverOrderShop getDeliverOrderShop(Long deliverOrderId,String status) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("deliverOrderId", deliverOrderId);
		params.put("status", status.split(","));
		List<TdeliverOrderShop> tdeliverOrderShops = deliverOrderShopDao.find("from TdeliverOrderShop t  where t.deliverOrderId = :deliverOrderId and t.status in (:status)", params);
		if (CollectionUtils.isNotEmpty(tdeliverOrderShops)) {
			DeliverOrderShop deliverOrderShop = new DeliverOrderShop();
			BeanUtils.copyProperties(tdeliverOrderShops.get(0), deliverOrderShop);
			return deliverOrderShop;
		}
		return null;
	}

}
