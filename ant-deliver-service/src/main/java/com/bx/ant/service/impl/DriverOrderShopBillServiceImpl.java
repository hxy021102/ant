package com.bx.ant.service.impl;

import com.bx.ant.dao.DriverOrderShopBillDaoI;
import com.bx.ant.model.TdriverOrderShopBill;
import com.bx.ant.pageModel.DriverOrderShopBill;
import com.bx.ant.service.DriverOrderShopBillServiceI;
import com.mobian.absx.F;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;
import com.mobian.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Service
public class DriverOrderShopBillServiceImpl extends BaseServiceImpl<DriverOrderShopBill> implements DriverOrderShopBillServiceI {

	@Autowired
	private DriverOrderShopBillDaoI driverOrderShopBillDao;

	@Override
	public DataGrid dataGrid(DriverOrderShopBill driverOrderShopBill, PageHelper ph) {
		List<DriverOrderShopBill> ol = new ArrayList<DriverOrderShopBill>();
		String hql = " from TdriverOrderShopBill t ";
		DataGrid dg = dataGridQuery(hql, ph, driverOrderShopBill, driverOrderShopBillDao);
		@SuppressWarnings("unchecked")
		List<TdriverOrderShopBill> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TdriverOrderShopBill t : l) {
				DriverOrderShopBill o = new DriverOrderShopBill();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(DriverOrderShopBill driverOrderShopBill, Map<String, Object> params) {
		String whereHql = "";	
		if (driverOrderShopBill != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(driverOrderShopBill.getTenantId())) {
				whereHql += " and t.tenantId = :tenantId";
				params.put("tenantId", driverOrderShopBill.getTenantId());
			}		
			if (!F.empty(driverOrderShopBill.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", driverOrderShopBill.getIsdeleted());
			}		
			if (!F.empty(driverOrderShopBill.getDriverAccountId())) {
				whereHql += " and t.driverAccountId = :driverAccountId";
				params.put("driverAccountId", driverOrderShopBill.getDriverAccountId());
			}		
			if (!F.empty(driverOrderShopBill.getShopId())) {
				whereHql += " and t.shopId = :shopId";
				params.put("shopId", driverOrderShopBill.getShopId());
			}		
			if (!F.empty(driverOrderShopBill.getHandleStatus())) {
				whereHql += " and t.handleStatus = :handleStatus";
				params.put("handleStatus", driverOrderShopBill.getHandleStatus());
			}		
			if (!F.empty(driverOrderShopBill.getHandleRemark())) {
				whereHql += " and t.handleRemark = :handleRemark";
				params.put("handleRemark", driverOrderShopBill.getHandleRemark());
			}		
			if (!F.empty(driverOrderShopBill.getHandleLoginId())) {
				whereHql += " and t.handleLoginId = :handleLoginId";
				params.put("handleLoginId", driverOrderShopBill.getHandleLoginId());
			}		
			if (!F.empty(driverOrderShopBill.getAmount())) {
				whereHql += " and t.amount = :amount";
				params.put("amount", driverOrderShopBill.getAmount());
			}		
			if (!F.empty(driverOrderShopBill.getPayWay())) {
				whereHql += " and t.payWay = :payWay";
				params.put("payWay", driverOrderShopBill.getPayWay());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(DriverOrderShopBill driverOrderShopBill) {
		TdriverOrderShopBill t = new TdriverOrderShopBill();
		BeanUtils.copyProperties(driverOrderShopBill, t);
		//t.setId(jb.absx.UUID.uuid());
		t.setIsdeleted(false);
		driverOrderShopBillDao.save(t);
	}

	@Override
	public DriverOrderShopBill get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TdriverOrderShopBill t = driverOrderShopBillDao.get("from TdriverOrderShopBill t  where t.id = :id", params);
		DriverOrderShopBill o = new DriverOrderShopBill();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(DriverOrderShopBill driverOrderShopBill) {
		TdriverOrderShopBill t = driverOrderShopBillDao.get(TdriverOrderShopBill.class, driverOrderShopBill.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(driverOrderShopBill, t, new String[] { "id" , "addtime", "isdeleted","updatetime" },true);
		}
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		driverOrderShopBillDao.executeHql("update TdriverOrderShopBill t set t.isdeleted = 1 where t.id = :id",params);
		//driverOrderShopBillDao.delete(driverOrderShopBillDao.get(TdriverOrderShopBill.class, id));
	}

}
