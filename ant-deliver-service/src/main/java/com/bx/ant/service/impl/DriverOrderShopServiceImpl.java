package com.bx.ant.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.bx.ant.dao.DriverOrderShopDaoI;
import com.bx.ant.model.TdriverOrderShop;
import com.bx.ant.pageModel.DriverOrderShop;
import com.bx.ant.service.DriverOrderShopServiceI;
import com.mobian.absx.F;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;
import com.mobian.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverOrderShopServiceImpl extends BaseServiceImpl<DriverOrderShop> implements DriverOrderShopServiceI {

	@Autowired
	private DriverOrderShopDaoI driverOrderShopDao;

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
	public DriverOrderShop get(Integer id) {
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
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		driverOrderShopDao.executeHql("update TdriverOrderShop t set t.isdeleted = 1 where t.id = :id",params);
		//driverOrderShopDao.delete(driverOrderShopDao.get(TdriverOrderShop.class, id));
	}

}
