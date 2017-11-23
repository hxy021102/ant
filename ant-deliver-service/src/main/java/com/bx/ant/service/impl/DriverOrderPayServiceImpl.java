package com.bx.ant.service.impl;

import com.bx.ant.dao.DriverOrderPayDaoI;
import com.bx.ant.model.TdeliverOrderShopPay;
import com.bx.ant.model.TdriverOrderPay;
import com.bx.ant.pageModel.*;
import com.bx.ant.service.DriverAccountServiceI;
import com.bx.ant.service.DriverOrderPayServiceI;
import com.mobian.absx.F;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;
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
public class DriverOrderPayServiceImpl extends BaseServiceImpl<DriverOrderPay> implements DriverOrderPayServiceI {

	@Autowired
	private DriverOrderPayDaoI driverOrderPayDao;

	@Autowired
	private DriverAccountServiceI driverAccountService;

	@Override
	public DataGrid dataGrid(DriverOrderPay driverOrderPay, PageHelper ph) {
		List<DriverOrderPay> ol = new ArrayList<DriverOrderPay>();
		String hql = " from TdriverOrderPay t ";
		DataGrid dg = dataGridQuery(hql, ph, driverOrderPay, driverOrderPayDao);
		@SuppressWarnings("unchecked")
		List<TdriverOrderPay> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TdriverOrderPay t : l) {
				DriverOrderPay o = new DriverOrderPay();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(DriverOrderPay driverOrderPay, Map<String, Object> params) {
		String whereHql = "";	
		if (driverOrderPay != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(driverOrderPay.getTenantId())) {
				whereHql += " and t.tenantId = :tenantId";
				params.put("tenantId", driverOrderPay.getTenantId());
			}		
			if (!F.empty(driverOrderPay.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", driverOrderPay.getIsdeleted());
			}		
			if (!F.empty(driverOrderPay.getDriverOrderShopId())) {
				whereHql += " and t.driverOrderShopId = :driverOrderShopId";
				params.put("driverOrderShopId", driverOrderPay.getDriverOrderShopId());
			}		
			if (!F.empty(driverOrderPay.getDeliverOrderShopId())) {
				whereHql += " and t.deliverOrderShopId = :deliverOrderShopId";
				params.put("deliverOrderShopId", driverOrderPay.getDeliverOrderShopId());
			}		
			if (!F.empty(driverOrderPay.getDriverOrderShopBillId())) {
				whereHql += " and t.driverOrderShopBillId = :driverOrderShopBillId";
				params.put("driverOrderShopBillId", driverOrderPay.getDriverOrderShopBillId());
			}		
			if (!F.empty(driverOrderPay.getStatus())) {
				whereHql += " and t.status in(:status)";
				params.put("status", driverOrderPay.getStatus().split(","));
			}		
			if (!F.empty(driverOrderPay.getAmount())) {
				whereHql += " and t.amount = :amount";
				params.put("amount", driverOrderPay.getAmount());
			}		
			if (!F.empty(driverOrderPay.getPayWay())) {
				whereHql += " and t.payWay = :payWay";
				params.put("payWay", driverOrderPay.getPayWay());
			}		
			if (!F.empty(driverOrderPay.getDriverAccountId())) {
				whereHql += " and t.driverAccountId = :driverAccountId";
				params.put("driverAccountId", driverOrderPay.getDriverAccountId());
			}
			if(driverOrderPay instanceof DriverOrderPayQuery) {
				DriverOrderPayQuery driverOrderPayQuery=(DriverOrderPayQuery)driverOrderPay;
				if (driverOrderPayQuery.getDriverOrderShopIds() != null && driverOrderPayQuery.getDriverOrderShopIds().length > 0) {
					whereHql += " and t.driverOrderShopId in(:driverOrderShopIds) ";
					params.put("driverOrderShopIds",driverOrderPayQuery.getDriverOrderShopIds());
				}
			}
		}	
		return whereHql;
	}

	@Override
	public void add(DriverOrderPay driverOrderPay) {
		TdriverOrderPay t = new TdriverOrderPay();
		BeanUtils.copyProperties(driverOrderPay, t);
		//t.setId(jb.absx.UUID.uuid());
		t.setIsdeleted(false);
		driverOrderPayDao.save(t);
		driverOrderPay.setId(t.getId());
	}

	@Override
	public DriverOrderPay update(DriverOrderPay driverOrderPay) {
		add(driverOrderPay);
		return driverOrderPay;
	}

	@Override
	public DriverOrderPay get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TdriverOrderPay t = driverOrderPayDao.get("from TdriverOrderPay t  where t.id = :id", params);
		DriverOrderPay o = new DriverOrderPay();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(DriverOrderPay driverOrderPay) {
		TdriverOrderPay t = driverOrderPayDao.get(TdriverOrderPay.class, driverOrderPay.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(driverOrderPay, t, new String[] { "id" , "addtime", "isdeleted","updatetime" },true);
		}
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		driverOrderPayDao.executeHql("update TdriverOrderPay t set t.isdeleted = 1 where t.id = :id",params);
		//driverOrderPayDao.delete(driverOrderPayDao.get(TdriverOrderPay.class, id));
	}

	@Override
	public List<DriverOrderPay> query(DriverOrderPay driverOrderPay) {
		List<DriverOrderPay> ol = new ArrayList<DriverOrderPay>();
		String hql = " from TdriverOrderPay t ";
		Map<String, Object> params = new HashMap<String, Object>();
		String where = whereHql(driverOrderPay, params);
		List<TdriverOrderPay> l = driverOrderPayDao.find(hql + where, params);
		if (CollectionUtils.isNotEmpty(l)) {
			for (TdriverOrderPay t : l) {
				DriverOrderPay o = new DriverOrderPay();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

	@Override
	public DataGrid dataGridView(DriverOrderPay driverOrderPay, PageHelper ph) {
		DataGrid dataGrid = dataGrid(driverOrderPay, ph);
		List<DriverOrderPay> driverOrderPayList = dataGrid.getRows();
		if (CollectionUtils.isNotEmpty(driverOrderPayList)) {
			List<DriverOrderPayView> driverOrderPayViewList = new ArrayList<DriverOrderPayView>();
			for (DriverOrderPay orderPay : driverOrderPayList) {
				DriverOrderPayView driverOrderPayView = new DriverOrderPayView();
				BeanUtils.copyProperties(orderPay, driverOrderPayView);
				DriverAccount driverAccount = driverAccountService.get(orderPay.getDriverAccountId());
				driverOrderPayView.setUserName(driverAccount.getUserName());
				driverOrderPayView.setPayWayName(orderPay.getPayWay());
				driverOrderPayView.setStatusName(orderPay.getStatus());
				driverOrderPayViewList.add(driverOrderPayView);
			}
			dataGrid.setRows(driverOrderPayViewList);
		}
		return dataGrid;
	}
}

