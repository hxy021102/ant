package com.mobian.service.impl;

import com.mobian.absx.F;
import com.mobian.dao.MbOrderDaoI;
import com.mobian.exception.ServiceException;
import com.mobian.model.TmbOrder;
import com.mobian.pageModel.*;
import com.mobian.service.*;
import com.mobian.service.impl.order.OrderState;
import com.mobian.util.DateUtil;
import com.mobian.util.MyBeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class MbOrderServiceImpl extends BaseServiceImpl<MbOrder> implements MbOrderServiceI {

	@Autowired
	private MbOrderDaoI mbOrderDao;

	@Autowired
	private MbShopServiceI mbShopService;

	@Autowired
	private MbOrderItemServiceI mbOrderItemService;

	@Autowired
	private MbOrderInvoiceServiceI mbOrderInvoiceService;

	@Autowired
	private MbOrderLogServiceI mbOrderLogService;

	@Autowired
	private MbUserServiceI mbUserService;

    @javax.annotation.Resource
    private Map<String, OrderState> orderStateFactory;
    @Autowired
	private RedisOrderLogServiceImpl redisOrderLogService;

    @Override
    public OrderState getCurrentState(Integer id) {
        MbOrder currentOrder = get(id);
        OrderState.order.set(currentOrder);
        String orderStatus = currentOrder.getStatus();
        OrderState orderState = orderStateFactory.get("order" + orderStatus.substring(2) + "StateImpl");
        return orderState;
    }
    @Override
    public DataGrid queryOrderDataGrid(MbOrder mbOrder, PageHelper ph) {
        mbOrder.setStatus("OD40");//查询已完成的订单
        DataGrid dg = new DataGrid();
        List<MbOrder> ol = new ArrayList<MbOrder>();
        String hql = " from TmbOrder t ";
        Map<String, Object> params = new HashMap<String, Object>();
        List<TmbOrder> l = mbOrderDao.find(hql + whereHql(mbOrder, params)/* + orderHql(ph)*/, params, ph.getPage(), ph.getRows());
        if (l != null && l.size() > 0) {
            for (TmbOrder t : l) {
                MbOrder o = new MbOrder();
                BeanUtils.copyProperties(t, o);
                fillOrderInfo(o);
                MbOrderInvoice mbOrderInvoice = mbOrderInvoiceService.getWithOrderId(o.getId());
                if (mbOrderInvoice == null) {
                    o.setInvoiceStatus("IS01");
                    ol.add(o);
                }
            }
        }
        dg.setRows(ol);
        return dg;

    }


	@Override
	public DataGrid dataGrid(MbOrder mbOrder, PageHelper ph) {
		List<MbOrder> ol = new ArrayList<MbOrder>();
		String hql = " from TmbOrder t ";
		DataGrid dg = dataGridQuery(hql, ph, mbOrder, mbOrderDao);
		@SuppressWarnings("unchecked")
		List<TmbOrder> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TmbOrder t : l) {
				MbOrder o = new MbOrder();
				BeanUtils.copyProperties(t, o);

				fillOrderInfo(o);

				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	@Override
	public Integer getOrderDebtMoney(Integer shopId) {
		MbShop _mbShop = mbShopService.getFromCache(shopId);
		if (!F.empty(_mbShop.getParentId()) && _mbShop.getParentId() != -1) {
			shopId = _mbShop.getParentId();
		}


		Map<String, Object> params = new HashMap<String, Object>();
		MbShop mbShop = new MbShop();
		PageHelper ph = new PageHelper();
		ph.setHiddenTotal(true);
		ph.setRows(10000);
		mbShop.setParentId(shopId);
		mbShop.setOnlyBranch(false);

		List<MbShop> mbShopList = mbShopService.dataGrid(mbShop, ph).getRows();
		Integer[] ids = new Integer[mbShopList.size()];
		for (int i = 0; i < mbShopList.size(); i++) {
			ids[i] = mbShopList.get(i).getId();
		}
		params.put("alist", ids);

		List<TmbOrder> mbOrderList = mbOrderDao.find("from TmbOrder where isdeleted=0 and status in ('OD09','OD10','OD12','OD15','OD20','OD30','OD35','OD40') and payStatus='PS01'and shopId in (:alist) ", params);
		Integer sumDebtMoney = 0;
		if (!CollectionUtils.isEmpty(mbOrderList)) {
			for (TmbOrder tmbOrder : mbOrderList) {
				sumDebtMoney += tmbOrder.getTotalPrice();
			}
		}
		return sumDebtMoney;
	}

	@Override
	public void editOrderDeliveryDriver(Integer id,String deliveryDriver,String remark,String loginId){
		MbOrder order = new MbOrder();
		order.setId(id);
		order.setDeliveryDriver(deliveryDriver);
		edit(order);
		MbOrderLog mbOrderLog = new MbOrderLog();
		mbOrderLog.setIsdeleted(false);
		mbOrderLog.setLoginId(loginId);
		mbOrderLog.setContent("分配司机成功");
		mbOrderLog.setRemark(remark);
		mbOrderLog.setLogType("LT010");
		mbOrderLog.setOrderId(id);
		mbOrderLogService.add(mbOrderLog);
	}

	@Override
	public void deleteUnPayOrderByTime() {
		List<TmbOrder> tmbOrderList = mbOrderDao.find("from TmbOrder where TO_DAYS(NOW()) - TO_DAYS(addTime) > 4 and status = 'OD01' and isdeleted = 0");
		if (!CollectionUtils.isEmpty(tmbOrderList)) {
			for (TmbOrder tmbOrder : tmbOrderList) {
				List<MbOrderItem> mbOrderItemList = mbOrderItemService.getMbOrderItemList(tmbOrder.getId());
				if (!CollectionUtils.isEmpty(mbOrderItemList)) {
					for (MbOrderItem mbOrderItem : mbOrderItemList) {
						mbOrderItemService.delete(mbOrderItem.getId());
					}
					delete(tmbOrder.getId());
				}
			}
		}
	}


	private void fillOrderInfo(MbOrder order) {
        fillShopInfo(order);

        fillPriceInfo(order);
    }

	private void fillShopInfo(MbOrder order) {

		if (order.getShopId() == null && order.getUserId() != null) {
			MbUser mbUser = mbUserService.getFromCache(order.getUserId());
			if (mbUser == null || mbUser.getShopId() == null) return;
			MbShop shop = mbShopService.getFromCache(mbUser.getShopId());
			if (shop != null) {
				order.setShopId(shop.getId());
				order.setShopName(shop.getName());
			}
		}else if(order.getShopId() != null){
			MbShop shop = mbShopService.getFromCache(order.getShopId());
			if (shop != null) {
				order.setShopName(shop.getName());
			}
		}
	}
	public List<TmbOrder> queryOrderListByStatus() {
		return mbOrderDao.find("from TmbOrder where isdeleted=0 and status in ('OD12','OD15' ) ");
	}
    private void fillPriceInfo(MbOrder order) {
        Integer deliveryPrice = mbOrderItemService.getDeliveryPrice(order.getId());
        order.setDeliveryPrice(deliveryPrice);

        Integer totalPrice = mbOrderItemService.getTotalPrice(order.getId());
        order.setTotalPrice(totalPrice);

        Integer orderPrice = totalPrice - deliveryPrice;
        order.setOrderPrice(orderPrice);
    }

	@Override
	public DataGrid listUserOrderItem(MbOrder mbOrder, PageHelper ph) {
		mbOrder.setDays(0L);
		ph.setHiddenTotal(true);
		DataGrid dataGrid = dataGrid(mbOrder, ph);
		List<MbOrder> mbOrderList = dataGrid.getRows();
		dataGrid.setRows(mbOrderList);
		return dataGrid;
	}

    protected String whereHql(MbOrder mbOrder, Map<String, Object> params) {
		String whereHql = "";
		if (mbOrder != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(mbOrder.getId())) {
				whereHql += " and t.id = :id";
				params.put("id", mbOrder.getId());
			}
			if (!F.empty(mbOrder.getTenantId())) {
				whereHql += " and t.tenantId = :tenantId";
				params.put("tenantId", mbOrder.getTenantId());
			}
			if (!F.empty(mbOrder.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", mbOrder.getIsdeleted());
			}
			if (!F.empty(mbOrder.getUserId())) {
				whereHql += " and t.userId = :userId";
				params.put("userId", mbOrder.getUserId());
			}
			if (!F.empty(mbOrder.getShopId())) {
				if(mbOrder.getShopIds() != null) {
					whereHql += " and t.shopId in (:alist)";
					params.put("alist", mbOrder.getShopIds());
				}else {
					whereHql += " and t.shopId = :shopId";
					params.put("shopId", mbOrder.getShopId());
				}

			}
			if (!F.empty(mbOrder.getStatus())) {
				int i = 0;
				for (String str : mbOrder.getStatus().split(",")) {
					if (F.empty(str)) continue;
					if (i == 0) whereHql += " and (t.status = :status" + i;
					else whereHql += " or t.status = :status" + i;

					params.put("status" + i, str);

					i++;
				}
				whereHql += ")";
			}
			if (!F.empty(mbOrder.getDeliveryWay())) {
				whereHql += " and t.deliveryWay = :deliveryWay";
				params.put("deliveryWay", mbOrder.getDeliveryWay());
			}
			if (!F.empty(mbOrder.getDeliveryStatus())) {
				whereHql += " and t.deliveryStatus = :deliveryStatus";
				params.put("deliveryStatus", mbOrder.getDeliveryStatus());
			}
			if (!F.empty(mbOrder.getDeliveryAddress())) {
				whereHql += " and t.deliveryAddress = :deliveryAddress";
				params.put("deliveryAddress", mbOrder.getDeliveryAddress());
			}
			if (!F.empty(mbOrder.getDeliveryRegion())) {
				whereHql += " and t.deliveryRegion = :deliveryRegion";
				params.put("deliveryRegion", mbOrder.getDeliveryRegion());
			}
			if (!F.empty(mbOrder.getPayStatus())) {
				whereHql += " and t.payStatus = :payStatus";
				params.put("payStatus", mbOrder.getPayStatus());
			}
			if (!F.empty(mbOrder.getPayWay())) {
				whereHql += " and t.payWay = :payWay";
				params.put("payWay", mbOrder.getPayWay());
			}
			if (!F.empty(mbOrder.getInvoiceWay())) {
				whereHql += " and t.invoiceWay = :invoiceWay";
				params.put("invoiceWay", mbOrder.getInvoiceWay());
			}
			if (!F.empty(mbOrder.getContactPhone())) {
				whereHql += " and t.contactPhone = :contactPhone";
				params.put("contactPhone", mbOrder.getContactPhone());
			}
			if (!F.empty(mbOrder.getContactPeople())) {
				whereHql += " and t.contactPeople = :contactPeople";
				params.put("contactPeople", mbOrder.getContactPeople());
			}
			if (!F.empty(mbOrder.getUserRemark())) {
				whereHql += " and t.userRemark = :userRemark";
				params.put("userRemark", mbOrder.getUserRemark());
			}
			if (mbOrder.getOrderTimeBegin() != null) {
				whereHql += " and t.addtime >= :orderTimeBegin";
				params.put("orderTimeBegin", mbOrder.getOrderTimeBegin());
			}
			if (mbOrder.getOrderTimeEnd() != null) {
				whereHql += " and t.addtime <= :orderTimeEnd";
				params.put("orderTimeEnd", mbOrder.getOrderTimeEnd());
			}
			if (mbOrder.getDays() != null) {
				whereHql += " and TO_DAYS(NOW()) - TO_DAYS(t.addtime) =:days";
				params.put("days", mbOrder.getDays());
			}
			if(mbOrder.getDeliveryTimeBegin() != null){
				whereHql +=" and t.deliveryTime >= :deliveryTimeBegin";
				params.put("deliveryTimeBegin",mbOrder.getDeliveryTimeBegin());
			}
			if (mbOrder.getDeliveryTimeEnd() != null) {
				whereHql += " and t.deliveryTime <= :deliveryTimeEnd";
				params.put("deliveryTimeEnd", mbOrder.getDeliveryTimeEnd());
			}
			if(mbOrder.getDeliveryWarehouseId() != null){
				whereHql +=" and t.deliveryWarehouseId = :deliveryWarehouseId";
				params.put("deliveryWarehouseId",mbOrder.getDeliveryWarehouseId());
			}
		}
		return whereHql;
	}

	@Override
	public void add(MbOrder mbOrder) {
		TmbOrder t = new TmbOrder();
		BeanUtils.copyProperties(mbOrder, t);
		t.setIsdeleted(false);
		mbOrderDao.save(t);
		mbOrder.setId(t.getId());
	}

	@Override
	public MbOrder get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TmbOrder t = mbOrderDao.get("from TmbOrder t  where t.id = :id", params);
		if(t == null) return null;
		MbOrder o = new MbOrder();
		BeanUtils.copyProperties(t, o);
		fillOrderInfo(o);
		return o;
	}

	@Override
	public void edit(MbOrder mbOrder) {
		TmbOrder t = mbOrderDao.get(TmbOrder.class, mbOrder.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(mbOrder, t, new String[]{"id", "addtime", "isdeleted", "updatetime"}, true);
			mbOrder.setTotalPrice(t.getTotalPrice());
			mbOrder.setUserId(t.getUserId());
		}
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		mbOrderDao.executeHql("update TmbOrder t set t.isdeleted = 1 where t.id = :id", params);
		//mbOrderDao.delete(mbOrderDao.get(TmbOrder.class, id));
	}

	@Override
	public void transform(MbOrder mbOrder) {
		OrderState orderState;
		if(F.empty(mbOrder.getId())) {
			orderState = orderStateFactory.get("order01StateImpl");
			orderState.handle(mbOrder);
		} else {
			orderState = getCurrentState(mbOrder.getId());
			if(orderState.next(mbOrder) == null) {
				throw new ServiceException("订单状态异常或已变更，请刷新页面重试！");
			}
			orderState.next(mbOrder).handle(mbOrder);
		}
	}

	@Override
	public void changeInvoiceStatus(Integer id, String remark, String loginId) {

		MbOrderInvoice mbOrderInvoice = mbOrderInvoiceService.getByOrderId(id);
		mbOrderInvoice.setRemark(remark);
		mbOrderInvoice.setInvoiceStatus("IS02");
		mbOrderInvoiceService.edit(mbOrderInvoice);

		MbOrderLog mbOrderLog = new MbOrderLog();
		mbOrderLog.setIsdeleted(false);
		mbOrderLog.setLoginId(loginId);
		mbOrderLog.setContent("开票成功");
		mbOrderLog.setRemark(remark);
		mbOrderLog.setLogType("LT003");
		mbOrderLog.setOrderId(id);
		mbOrderLogService.add(mbOrderLog);

	}

	@Override
	public List<MbOrder> queryRemindOrder(){
		Map<String,Object> params= new HashMap<>();
		Date remindTime = DateUtil.addDayToDate(new Date(), -2);
		params.put("remindTime", remindTime);
		List<TmbOrder> list = mbOrderDao.find("from TmbOrder t  where t.deliveryTime  <=:remindTime  and t.status = 'OD20' and t.isdeleted=0", params);
		List<MbOrder> ol =new ArrayList<MbOrder>();
		if (list != null && list.size() > 0) {
			for (TmbOrder t : list) {
				MbOrder m = new MbOrder();
				BeanUtils.copyProperties(t, m);
				ol.add(m);
			}
		}

		return ol;
	}

    @Override
    public List<MbOrder> query(MbOrder mbOrder) {
		List<MbOrder> ol = new ArrayList<MbOrder>();
		String hql = " from TmbOrder t ";
		Map<String, Object> params = new HashMap<String, Object>();
		String where = whereHql(mbOrder, params);
		List<TmbOrder> l = mbOrderDao.find(hql + where, params);
		if (CollectionUtils.isNotEmpty(l)) {
			for (TmbOrder t : l) {
				MbOrder o= new MbOrder();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

	@Override
	public List<MbOrderDistribution> getOrderDistributionDayData(MbOrder mbOrder, List<MbOrderDistribution> orderData, Map<String, Object> params) {
		String[] timeName = new String[31];
		SimpleDateFormat sdf = new SimpleDateFormat(" MM-dd");
		String endTimeName = sdf.format(mbOrder.getOrderTimeEnd());
		for (int i = 0; i < 31; i++) {
			String key = sdf.format(mbOrder.getOrderTimeBegin().getTime() + i * 24 * 60 * 60 * 1000L);
			timeName[i] = key;
			if (key.equals(endTimeName))
				break;
		}
		//公众号下单数据
		List<TmbOrder> mbOrdersPublic = mbOrderDao.find("from TmbOrder t where t.addtime >=:orderTimeBegin and t.addtime <=:orderTimeEnd " +
				"and t.addLoginId is  null  and t.isdeleted = 0 and t.status not in (:status)", params);
		MbOrderDistribution distributionPublic = setOrderDayNumberAndOrderDayNameValue(mbOrdersPublic, orderData.get(0), timeName);
		//客服下单数据
		List<TmbOrder> mbOrdersServer = mbOrderDao.find("from TmbOrder t where t.addtime >=:orderTimeBegin and t.addtime <=:orderTimeEnd " +
				"and t.addLoginId is not null  and t.isdeleted = 0 and t.status not in (:status)", params);
		MbOrderDistribution distributionServer = setOrderDayNumberAndOrderDayNameValue(mbOrdersServer, orderData.get(1), timeName);
		List<MbOrderDistribution> distributions = new ArrayList<MbOrderDistribution>();
		distributions.add(distributionPublic);
		distributions.add(distributionServer);
		return distributions;
	}

	@Override
	public MbOrderDistribution setOrderDayNumberAndOrderDayNameValue(List<TmbOrder> mbOrders, MbOrderDistribution orderDistribution, String[] timeName) {
		SimpleDateFormat sdf = new SimpleDateFormat(" MM-dd");
		Map<String, Integer> map = new HashMap<String, Integer>();
		Integer[] orderNumber = new Integer[31];
		if (CollectionUtils.isNotEmpty(mbOrders)) {
			for (TmbOrder tmbOrder : mbOrders) {
				String key = sdf.format(tmbOrder.getAddtime());
				Integer orderValue = map.get(key);
				if (orderValue == null) {
					map.put(key, 1);
				} else {
					map.put(key, orderValue += 1);
				}
			}
			for (int i = 0; i < 31; i++) {
				if (timeName[i] != null) {
					if (map.get(timeName[i]) == null) {
						orderNumber[i] = 0;
					} else {
						orderNumber[i] = map.get(timeName[i]);
					}
				} else {
					break;
				}
			}
			orderDistribution.setOrderDayNumber(orderNumber);
		}
		orderDistribution.setOrderDayName(timeName);
		return orderDistribution;
	}

	@Override
	public DataGrid dataGridWithOrderLogMessage(MbOrder mbOrder, PageHelper ph) {
		DataGrid dataGrid = dataGrid(mbOrder, ph);
		List<MbOrder> mbOrderList = dataGrid.getRows();
		List<MbOrderExt> mbOrderExtList = new ArrayList<MbOrderExt>();
		if (CollectionUtils.isNotEmpty(mbOrderList)) {
			Integer sendNumber, backNumber, leaveNumber;
			for (MbOrder order : mbOrderList) {
				MbOrderExt mbOrderExt = new MbOrderExt();
				BeanUtils.copyProperties(order,mbOrderExt);
				mbOrderExtList.add(mbOrderExt);
				sendNumber = redisOrderLogService.getOrderLogMessageNumber(order.getId(), "LT011");
				mbOrderExt.setSendNumber(sendNumber);
				backNumber = redisOrderLogService.getOrderLogMessageNumber(order.getId(), "LT012");
				mbOrderExt.setBackNumber(backNumber);
				leaveNumber = redisOrderLogService.getOrderLogMessageNumber(order.getId(), "LT013");
				mbOrderExt.setLeaveNumber(leaveNumber);
			}
		}
		dataGrid.setRows(mbOrderExtList);
		return dataGrid;
	}

	@Override
	public List<MbOrderDistribution> getOrderDistributionData(MbOrder mbOrder) {
		Map<String, Object> params = new HashMap<String, Object>();
		String[] status = {"OD01","OD05","OD06", "OD31", "OD32",};
		params.put("orderTimeBegin", mbOrder.getOrderTimeBegin());
		params.put("orderTimeEnd", mbOrder.getOrderTimeEnd());
		params.put("status", status);
		//所有订单
		Long count = mbOrderDao.count("select count(*) from TmbOrder t where t.addtime >= :orderTimeBegin and t.addtime <=:orderTimeEnd " +
				" and t.isdeleted = 0 and t.status not in (:status)", params);
		//客服下单
		Long kfCount = mbOrderDao.count("select count(*) from TmbOrder t where t.addtime >=:orderTimeBegin and t.addtime <=:orderTimeEnd " +
				"and t.addLoginId is not null  and t.isdeleted = 0 and t.status not in (:status)", params);
		List<MbOrderDistribution> orders = new ArrayList<MbOrderDistribution>();
		MbOrderDistribution orderPublic = new MbOrderDistribution();
		orderPublic.setOrderKindName("公众号下单");
		orderPublic.setOrderTotal(count.intValue() - kfCount.intValue());
		orders.add(orderPublic);

		MbOrderDistribution orderService = new MbOrderDistribution();
		orderService.setOrderKindName("客服下单");
		orderService.setOrderTotal(kfCount.intValue());
		orders.add(orderService);

		return getOrderDistributionDayData(mbOrder,orders,params);
	}

	@Override
	public List<MbOrder> queryDebtOrders() {
		List<MbOrder> mbOrders = new ArrayList<>();
		//查出所有欠款订单
		List<TmbOrder> mbOrderList = mbOrderDao.find("from TmbOrder where isdeleted=0 and status in ('OD09','OD10','OD12','OD15','OD20','OD30','OD35','OD40') and payStatus='PS01'");
		for(TmbOrder m:mbOrderList) {
			MbOrder o = new MbOrder();
			BeanUtils.copyProperties(m,o);
			mbOrders.add(o);
		}
		return mbOrders;
	}
//	@Override
//	public void addItemByActivity(MbOrder mbOrder, Integer itemId, Integer quantity) {
//
//	}
}
