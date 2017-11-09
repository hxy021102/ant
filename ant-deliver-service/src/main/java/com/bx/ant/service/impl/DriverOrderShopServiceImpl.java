package com.bx.ant.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.alibaba.fastjson.JSONObject;
import com.bx.ant.dao.DriverOrderShopDaoI;
import com.bx.ant.model.TdriverOrderShop;
import com.bx.ant.pageModel.DeliverOrderShop;
import com.bx.ant.pageModel.DeliverOrderShopView;
import com.bx.ant.pageModel.DriverOrderShop;
import com.bx.ant.pageModel.DriverOrderShopView;
import com.bx.ant.service.DeliverOrderShopServiceI;
import com.bx.ant.service.DriverOrderShopServiceI;
import com.bx.ant.service.DriverOrderShopState;
import com.mobian.absx.F;
import com.mobian.exception.ServiceException;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;
import com.mobian.thirdpart.redis.Key;
import com.mobian.thirdpart.redis.Namespace;
import com.mobian.thirdpart.redis.RedisUtil;
import com.mobian.util.MyBeanUtils;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;

@Service
public class DriverOrderShopServiceImpl extends BaseServiceImpl<DriverOrderShop> implements DriverOrderShopServiceI {

	@Autowired
	private DriverOrderShopDaoI driverOrderShopDao;

	@Resource
	private Map<String, DriverOrderShopState> driverOrderShopStateFactory ;

	@Resource
	private DeliverOrderShopServiceI deliverOrderShopService;

	@Resource
	private RedisUtil redisUtil;

	@Override
	public DataGrid dataGrid(DriverOrderShop driverOrderShop, PageHelper ph) {
		List<DriverOrderShop> ol = new ArrayList<DriverOrderShop>();
		String hql = " from TdriverOrderShop t ";
		DataGrid dg = dataGridQuery(hql, ph, driverOrderShop, driverOrderShopDao);
		@SuppressWarnings("unchecked")
		List<TdriverOrderShop> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TdriverOrderShop t : l) {
				DriverOrderShop o = new DriverOrderShop();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(DriverOrderShop driverOrderShop, Map<String, Object> params) {
		String whereHql = "";	
		if (driverOrderShop != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(driverOrderShop.getTenantId())) {
				whereHql += " and t.tenantId = :tenantId";
				params.put("tenantId", driverOrderShop.getTenantId());
			}		
			if (!F.empty(driverOrderShop.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", driverOrderShop.getIsdeleted());
			}		
			if (!F.empty(driverOrderShop.getDeliverOrderShopId())) {
				whereHql += " and t.deliverOrderShopId = :deliverOrderShopId";
				params.put("deliverOrderShopId", driverOrderShop.getDeliverOrderShopId());
			}		
			if (!F.empty(driverOrderShop.getShopId())) {
				whereHql += " and t.shopId = :shopId";
				params.put("shopId", driverOrderShop.getShopId());
			}		
			if (!F.empty(driverOrderShop.getStatus())) {
				whereHql += " and t.status = :status";
				params.put("status", driverOrderShop.getStatus());
			}		
			if (!F.empty(driverOrderShop.getAmount())) {
				whereHql += " and t.amount = :amount";
				params.put("amount", driverOrderShop.getAmount());
			}		
			if (!F.empty(driverOrderShop.getPayStatus())) {
				whereHql += " and t.payStatus = :payStatus";
				params.put("payStatus", driverOrderShop.getPayStatus());
			}		
			if (!F.empty(driverOrderShop.getDriverOrderShopBillId())) {
				whereHql += " and t.driverOrderShopBillId = :driverOrderShopBillId";
				params.put("driverOrderShopBillId", driverOrderShop.getDriverOrderShopBillId());
			}
			if (!F.empty(driverOrderShop.getDriverAccountId())) {
				whereHql += " and t.driverAccountId = :driverAccountId";
				params.put("driverAccountId", driverOrderShop.getDriverAccountId());
			}
			if (driverOrderShop instanceof DriverOrderShopView) {
				DriverOrderShopView driverOrderShopView = (DriverOrderShopView) driverOrderShop;
				if (driverOrderShopView.getUpdatetimeBegin() != null) {
					whereHql += " and t.updatetime >= :updatetimeBegin";
					params.put("updatetimeBegin", driverOrderShopView.getUpdatetimeBegin());
				}
				if (driverOrderShopView.getUpdatetimeEnd() != null) {
					whereHql += " and t.updatetime <= :updatetimeEnd";
					params.put("updatetimeEnd", driverOrderShopView.getUpdatetimeEnd());
				}
			}
		}	
		return whereHql;
	}

	@Override
	public void add(DriverOrderShop driverOrderShop) {
		TdriverOrderShop t = new TdriverOrderShop();
		BeanUtils.copyProperties(driverOrderShop, t);
		//t.setId(jb.absx.UUID.uuid());
		t.setIsdeleted(false);
		driverOrderShopDao.save(t);
	}

	@Override
	public DriverOrderShop get(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TdriverOrderShop t = driverOrderShopDao.get("from TdriverOrderShop t  where t.id = :id", params);
		DriverOrderShop o = new DriverOrderShop();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(DriverOrderShop driverOrderShop) {
		TdriverOrderShop t = driverOrderShopDao.get(TdriverOrderShop.class, driverOrderShop.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(driverOrderShop, t, new String[] { "id" , "addtime", "isdeleted","updatetime" },true);
		}
	}

	@Override
	public void delete(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		driverOrderShopDao.executeHql("update TdriverOrderShop t set t.isdeleted = 1 where t.id = :id",params);
		//driverOrderShopDao.delete(driverOrderShopDao.get(TdriverOrderShop.class, id));
	}

	@Override
	public DriverOrderShopView getView(Long id) {
		DriverOrderShop driverOrderShop = get(id);
		DriverOrderShopView driverOrderShopView = new DriverOrderShopView();
		if (driverOrderShop != null) {
			BeanUtils.copyProperties(driverOrderShop, driverOrderShopView);
			fillDeliverOrderShopInfo(driverOrderShopView);
		}
		return driverOrderShopView;
	}

	@Override
	public DataGrid dataGridView(DriverOrderShop driverOrderShop, PageHelper pageHelper) {
		DataGrid dataGrid = new DataGrid();
		List<DriverOrderShop> driverOrderShops = dataGrid(driverOrderShop, pageHelper).getRows();
		List<DriverOrderShopView> ol = new ArrayList<DriverOrderShopView>();
		if (CollectionUtils.isNotEmpty(driverOrderShops)) {
			int size = driverOrderShops.size();
			for (int i = 0 ; i < size; i++) {
				DriverOrderShopView o = new DriverOrderShopView();
				BeanUtils.copyProperties(driverOrderShops.get(i), o);
				fillDeliverOrderShopInfo(o);
				ol.add(o);
			}
			dataGrid.setRows(ol);
		}
		return dataGrid;
	}

	protected void fillUserInfo(DriverOrderShopView driverAccountView) {

	}

	protected void fillDeliverOrderShopInfo(DriverOrderShopView driverOrderShopView) {
		if (!F.empty(driverOrderShopView.getDeliverOrderShopId())) {
			DeliverOrderShopView deliverOrderShopView = deliverOrderShopService.getView(driverOrderShopView.getDeliverOrderShopId());
			if (deliverOrderShopView != null) {
				driverOrderShopView.setDeliverOrderShop(deliverOrderShopView);
			}
		}
	}

	@Override
	public DriverOrderShopState getCurrentState(Long driverOrderShopId) {
		DriverOrderShop currentDriverOrderShop = get(driverOrderShopId);
		DriverOrderShopState.driverOrderShop.set(currentDriverOrderShop);
		String driverOrderShopStatus = currentDriverOrderShop.getStatus();
		DriverOrderShopState driverOrderShopState = driverOrderShopStateFactory.get("driverOrderShop" + currentDriverOrderShop.getStatus().substring(4) + "StateImpl");
		return driverOrderShopState;
	}
	@Override
	public void transform(DriverOrderShop driverOrderShop) {
		DriverOrderShopState driverOrderShopState;
		if (F.empty(driverOrderShop.getId())) {
			driverOrderShopState = driverOrderShopStateFactory.get("driverOrderShop01StateImpl");
			driverOrderShopState.handle(driverOrderShop);
		}else {
			driverOrderShopState = getCurrentState(driverOrderShop.getId());
			if (driverOrderShopState.next(driverOrderShop) == null) {
				throw new ServiceException("订单状态异常或已变更,请刷新页面重试 !");
			}
			driverOrderShopState.next(driverOrderShop).handle(driverOrderShop);
		}
	}
	protected Integer updateAllocationOrderRedis(Integer accountId, Integer quantity){
		int count = 0;
		String key = Key.build(Namespace.DRIVER_ORDER_SHOP_NEW_ASSIGNMENT_COUNT, accountId + "");
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
	public Integer addAllocationOrderRedis(Integer accountId) {
		return updateAllocationOrderRedis(accountId, 1);
	}

	@Override
	public Integer reduseAllocationOrderRedis(Integer accountId) {
		return updateAllocationOrderRedis(accountId, -1);
	}

	@Override
	public Integer clearAllocationOrderRedis(Integer accountId) {
		return updateAllocationOrderRedis(accountId, 0);
	}
}
