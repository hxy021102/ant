package com.bx.ant.service.impl;

import com.bx.ant.dao.DriverOrderShopBillDaoI;
import com.bx.ant.model.TdriverOrderShopBill;
import com.bx.ant.pageModel.*;
import com.bx.ant.service.DriverAccountServiceI;
import com.bx.ant.service.DriverOrderShopBillServiceI;
import com.bx.ant.service.DriverOrderShopServiceI;
import com.mobian.absx.F;
import com.mobian.pageModel.*;
import com.mobian.service.MbBalanceLogServiceI;
import com.mobian.service.MbBalanceServiceI;
import com.mobian.service.UserServiceI;
import com.mobian.util.MyBeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Service
public class DriverOrderShopBillServiceImpl extends BaseServiceImpl<DriverOrderShopBill> implements DriverOrderShopBillServiceI {

	@Autowired
	private DriverOrderShopBillDaoI driverOrderShopBillDao;
	@Resource
	private UserServiceI userService;
	@Resource
	private DriverOrderShopServiceI driverOrderShopService;
	@Resource
	private DriverAccountServiceI driverAccountService;
	@Resource
	private MbBalanceServiceI mbBalanceService;
	@Resource
	private MbBalanceLogServiceI mbBalanceLogService;

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
		driverOrderShopBill.setId(t.getId());
	}

	@Override
	public DriverOrderShopBill get(Long id) {
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
	public void delete(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		driverOrderShopBillDao.executeHql("update TdriverOrderShopBill t set t.isdeleted = 1 where t.id = :id",params);
		//driverOrderShopBillDao.delete(driverOrderShopBillDao.get(TdriverOrderShopBill.class, id));
	}
	@Override
	public DriverOrderShopBillView getView(Long id) {
		DriverOrderShopBill driverOrderShopBill = get(id);
		DriverOrderShopBillView driverOrderShopBillView = new DriverOrderShopBillView();
		if (driverOrderShopBill != null) {
			BeanUtils.copyProperties(driverOrderShopBill, driverOrderShopBillView);
			fillUserInfo(driverOrderShopBillView);
		}
		return driverOrderShopBillView;
	}

	@Override
	public DataGrid dataGridView(DriverOrderShopBill driverOrderShopBill, PageHelper pageHelper) {
		DataGrid dataGrid = dataGrid(driverOrderShopBill, pageHelper);
		List<DriverOrderShopBill> driverOrderShopBills = dataGrid.getRows();
		List<DriverOrderShopBillView> ol = new ArrayList<DriverOrderShopBillView>();
		if (CollectionUtils.isNotEmpty(driverOrderShopBills)) {
			int size = driverOrderShopBills.size();
			for (int i = 0 ; i < size; i++) {
				DriverOrderShopBillView o = new DriverOrderShopBillView();
				BeanUtils.copyProperties(driverOrderShopBills.get(i), o);
				DriverAccount driverAccount=driverAccountService.get(o.getDriverAccountId());
				o.setUserName(driverAccount.getUserName());
				fillUserInfo(o);
				ol.add(o);
			}
			dataGrid.setRows(ol);
		}
		return dataGrid;
	}

	protected void fillUserInfo(DriverOrderShopBillView driverOrderShopBillView) {
		if (!F.empty(driverOrderShopBillView.getHandleLoginId())){
			User user  = userService.getFromCache(driverOrderShopBillView.getHandleLoginId());
			if (user != null) {
				driverOrderShopBillView.setHandleLoginName(user.getName());
			}
		}
	}

	@Override
	public String addDriverOrderShopBillandPay(DriverOrderShopBillView driverOrderShopBillView) {
		DriverOrderShopView driverOrderShopView = new DriverOrderShopView();
		//判断骑手运单是否重复 通过id和状态为待审核状态
		driverOrderShopView.setIds(driverOrderShopBillView.getOrderShopIds());
		driverOrderShopView.setPayStatus(driverOrderShopService.PAY_STSTUS_WAITE_AUDIT);
		List<DriverOrderShop> driverOrderShopList = driverOrderShopService.query(driverOrderShopView);
		if (CollectionUtils.isEmpty(driverOrderShopList)) {
			//创建账单
			DriverAccount driverAccount = new DriverAccount();
			driverAccount.setUserName(driverOrderShopBillView.getUserName());
			List<DriverAccount> driverAccountList = driverAccountService.query(driverAccount);
			if (CollectionUtils.isNotEmpty(driverAccountList)) {
				driverOrderShopBillView.setDriverAccountId(driverAccountList.get(0).getId());
			}
			driverOrderShopBillView.setStartDate(driverOrderShopBillView.getAddtimeBegin());
			driverOrderShopBillView.setEndDate(driverOrderShopBillView.getAddtimeEnd());
			add(driverOrderShopBillView);
			//修改运单为待审核状态
			DriverOrderShopView orderShopView = new DriverOrderShopView();
			orderShopView.setIds(driverOrderShopBillView.getOrderShopIds());
			List<DriverOrderShop> driverOrderShops = driverOrderShopService.query(orderShopView);
			for (DriverOrderShop driverOrderShop : driverOrderShops) {
				driverOrderShop.setPayStatus(driverOrderShopService.PAY_STSTUS_WAITE_AUDIT);
				driverOrderShop.setDriverOrderShopBillId(driverOrderShopBillView.getId());
				driverOrderShopService.edit(driverOrderShop);
			}
		} else {
			String result = "运单ID:";
			for (DriverOrderShop driverOrderShop : driverOrderShopList) {
				result += driverOrderShop.getId().toString() + " ";
			}
		}
		return null;
	}

	@Override
	public void editDriverShopBillAndOrderPay(DriverOrderShopBill driverOrderShopBill) {
		//若审核通过，否则无支付方式
		if ("DHS02".equals(driverOrderShopBill.getHandleStatus())) {
			driverOrderShopBill.setPayWay("DDW04");
		}
		edit(driverOrderShopBill);
		DriverOrderShop driverOrderShop=new DriverOrderShop();
		driverOrderShop.setDriverOrderShopBillId(driverOrderShopBill.getId());
		List<DriverOrderShop> driverOrderShopList = driverOrderShopService.query(driverOrderShop);
		if(CollectionUtils.isNotEmpty(driverOrderShopList)){
			for(DriverOrderShop orderShop : driverOrderShopList){
				//若审核通过，则修改运单状态和余额及日志
				if("DHS02".equals(driverOrderShopBill.getHandleStatus())) {
					//依次修改运单状态
					driverOrderShopService.editStatusByHql(orderShop.getId(),driverOrderShopBill.getHandleStatus());
					MbBalance mbBalance = mbBalanceService.addOrGetDriverBalance(orderShop.getDriverAccountId());
					MbBalanceLog mbBalanceLog = new MbBalanceLog();
					mbBalanceLog.setBalanceId(mbBalance.getId());
					//余额日志类型id 为运单的id
					mbBalanceLog.setRefId(orderShop.getId() + "");
					//门店手动结算
					mbBalanceLog.setRefType("BT151");
					mbBalanceLog.setAmount(orderShop.getAmount());
					mbBalanceLog.setReason(String.format("骑手[ID:%1$s]完成运单[ID:%2$s]结算转入", orderShop.getDriverAccountId(), orderShop.getId()));
					mbBalanceLogService.addAndUpdateBalance(mbBalanceLog);
				}else{
					driverOrderShopService.editStatusByHql(orderShop.getId(),driverOrderShopBill.getHandleStatus());
				}
			}
		}

	}

}
