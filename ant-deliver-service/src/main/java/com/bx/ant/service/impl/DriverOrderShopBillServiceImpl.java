package com.bx.ant.service.impl;

import com.bx.ant.dao.DriverOrderShopBillDaoI;
import com.bx.ant.model.TdriverOrderShopBill;
import com.bx.ant.pageModel.*;
import com.bx.ant.service.*;
import com.mobian.absx.F;
import com.mobian.pageModel.*;
import com.mobian.service.MbBalanceLogServiceI;
import com.mobian.service.MbBalanceServiceI;
import com.mobian.service.UserServiceI;
import com.mobian.util.ConvertNameUtil;
import com.mobian.util.DateUtil;
import com.mobian.util.MyBeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import java.util.*;


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
	@Resource
	private DriverOrderPayServiceI driverOrderPayService;
	@Resource
	private HibernateTransactionManager transactionManager;

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
			if (driverOrderShopBill instanceof DriverOrderShopBillView) {
				DriverOrderShopBillView driverOrderShopBillView = (DriverOrderShopBillView) driverOrderShopBill;
				if (driverOrderShopBillView.getAddtimeBegin() != null) {
					whereHql += " and t.addtime >= :addtimeBegin";
					params.put("addtimeBegin", driverOrderShopBillView.getAddtimeBegin());
				}
				if (driverOrderShopBillView.getAddtimeEnd() != null) {
					whereHql += " and t.addtime <= :addtimeEnd";
					params.put("addtimeEnd", driverOrderShopBillView.getAddtimeEnd());
				}
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
			MyBeanUtils.copyProperties(driverOrderShopBill, t, new String[]{"id", "addtime", "isdeleted", "updatetime"}, true);
		}
	}

	@Override
	public void delete(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		driverOrderShopBillDao.executeHql("update TdriverOrderShopBill t set t.isdeleted = 1 where t.id = :id", params);
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
			for (int i = 0; i < size; i++) {
				DriverOrderShopBillView o = new DriverOrderShopBillView();
				BeanUtils.copyProperties(driverOrderShopBills.get(i), o);
				DriverAccount driverAccount = driverAccountService.getFromCache(o.getDriverAccountId());
				o.setUserName(driverAccount.getUserName());
				fillUserInfo(o);
				ol.add(o);
			}
			dataGrid.setRows(ol);
		}
		return dataGrid;
	}

	protected void fillUserInfo(DriverOrderShopBillView driverOrderShopBillView) {
		if (!F.empty(driverOrderShopBillView.getHandleLoginId())) {
			User user = userService.getFromCache(driverOrderShopBillView.getHandleLoginId());
			if (user != null) {
				driverOrderShopBillView.setHandleLoginName(user.getName());
			}
		}
	}

	@Override
	public String updateDriverOrderShopBillandPay(DriverOrderShopBillView driverOrderShopBillView) {
		DriverOrderPayQuery driverOrderPayQuery = new DriverOrderPayQuery();
		//判断骑手运单所对应的账单是否重复创建， 通过所选择的运单id和driverOrderPay的状态进行查询
		driverOrderPayQuery.setDriverOrderShopIds(driverOrderShopBillView.getOrderShopIds());
		String[] statusArray = {"DDPS01", "DDPS02", "DDPS03"};
		driverOrderPayQuery.setStatusArray(statusArray);
		List<DriverOrderPay> driverOrderPayList = driverOrderPayService.query(driverOrderPayQuery);
		if (CollectionUtils.isEmpty(driverOrderPayList)) {
			//创建骑手账单
			DriverAccount driverAccount = new DriverAccount();
			driverOrderShopBillView.setDriverAccountId(driverOrderShopBillView.getDriverOrderShopList().get(0).getDriverAccountId());
			driverOrderShopBillView.setStartDate(driverOrderShopBillView.getAddtimeBegin());
			driverOrderShopBillView.setEndDate(driverOrderShopBillView.getAddtimeEnd());
			add(driverOrderShopBillView);
			//根据骑手运单信息创建对应的DriverOrderPay
			List<DriverOrderShop> driverOrderShops = driverOrderShopBillView.getDriverOrderShopList();
			for (DriverOrderShop driverOrderShop : driverOrderShops) {
				//添加Pay
				DriverOrderPay driverOrderPay = new DriverOrderPay();
				driverOrderPay.setAmount(driverOrderShop.getAmount());
				driverOrderPay.setDriverAccountId(driverOrderShop.getDriverAccountId());
				driverOrderPay.setStatus("DDPS01");
				driverOrderPay.setDriverOrderShopBillId(driverOrderShopBillView.getId());
				driverOrderPay.setDeliverOrderShopId(driverOrderShop.getDeliverOrderShopId());
				driverOrderPay.setDriverOrderShopId(driverOrderShop.getId());
				driverOrderPayService.add(driverOrderPay);

				//修改订单状态为待支付
				DriverOrderShop orderShop = new DriverOrderShop();
				orderShop.setPayStatus(DriverOrderShopServiceI.PAY_STSTUS_WAITE_AUDIT);
				driverOrderShopService.edit(driverOrderShop);
			}
			return null;
		} else {
			String result = "运单ID:";
			for (DriverOrderPay driverOrderPay : driverOrderPayList) {
				result += driverOrderPay.getDriverOrderShopId().toString() + " ";
			}
			return result;
		}
	}

	@Override
	public void editDriverShopBillAndOrderPay(DriverOrderShopBill driverOrderShopBill) {
		//若审核通过，否则无支付方式
		if ("DHS02".equals(driverOrderShopBill.getHandleStatus())) {
			driverOrderShopBill.setPayWay("DDPW04");
		}
		edit(driverOrderShopBill);
		DriverOrderPay driverOrderPay = new DriverOrderPay();
		DriverOrderShop driverOrderShop = new DriverOrderShop();

		driverOrderPay.setDriverOrderShopBillId(driverOrderShopBill.getId());
		driverOrderPay.setStatus("DDPS01");
		List<DriverOrderPay> driverOrderPayList = driverOrderPayService.query(driverOrderPay);
		if (CollectionUtils.isNotEmpty(driverOrderPayList)) {
			for (DriverOrderPay orderPay : driverOrderPayList) {
				//若审核通过，则修改运单状态和余额及日志
				if ("DHS02".equals(driverOrderShopBill.getHandleStatus())) {
					//修改为支付成功和余额支付
					orderPay.setStatus("DDPS02");
					orderPay.setPayWay("DDPW04");

					//状态翻转
					driverOrderShop.setId(orderPay.getDriverOrderShopId());
					driverOrderShop.setStatus(DriverOrderShopServiceI.STATUS_SETTLEED);
					driverOrderShopService.transform(driverOrderShop);

					MbBalance mbBalance = mbBalanceService.addOrGetDriverBalance(orderPay.getDriverAccountId());
					MbBalanceLog mbBalanceLog = new MbBalanceLog();
					mbBalanceLog.setBalanceId(mbBalance.getId());
					//余额日志类型id 为运单的id
					mbBalanceLog.setRefId(orderPay.getId() + "");

					//门店手动结算
					mbBalanceLog.setRefType("BT151");
					mbBalanceLog.setAmount(orderPay.getAmount());
					mbBalanceLog.setReason(String.format("骑手[ID:%1$s]完成运单[ID:%2$s]结算转入", orderPay.getDriverAccountId(), orderPay.getDriverOrderShopId()));
					mbBalanceLogService.addAndUpdateBalance(mbBalanceLog);

				} else {
					//修改为审核拒绝
					orderPay.setStatus("DDPS04");
					driverOrderShop.setId(driverOrderPay.getDriverOrderShopId());
					driverOrderShop.setStatus(DriverOrderShopServiceI.PAY_STATUS_NOT_PAY);
				}
				driverOrderShopService.edit(driverOrderShop);
				driverOrderPayService.edit(orderPay);
			}
		}
    }

	@Override
	public void addPayOperation() {
		if (Integer.valueOf(ConvertNameUtil.getString("DDSV102")) != -1) {
			DriverOrderShopView driverOrderShopView = new DriverOrderShopView();
			//1.自动结算的订单条件
			driverOrderShopView.setPayStatus(DriverOrderShopServiceI.PAY_STATUS_NOT_PAY);
			driverOrderShopView.setStatus(DriverOrderShopServiceI.STATUS_DELIVERED);

			Date expireDate = DateUtil.addDayToDate(new Date(), -Integer.valueOf(ConvertNameUtil.getString("DDSV102", "1")));
			driverOrderShopView.setAddtimeEnd(expireDate);
			//2.获取数据
			PageHelper ph = new PageHelper();
			ph.setHiddenTotal(true);
			List<DriverOrderShop> driverOrderShops = driverOrderShopService.dataGrid(driverOrderShopView, ph).getRows();
			if (CollectionUtils.isNotEmpty(driverOrderShops)) {
				//3根据骑手对订单进行分类
				Map<Integer, DriverOrderShopBillView> driverOrderShopBillMap = new HashMap<Integer, DriverOrderShopBillView>();
				for (DriverOrderShop driverOrderShop : driverOrderShops) {
					//获取骑手配置信息
					DriverOrderShopBillView driverOrderShopBillView = new DriverOrderShopBillView();
					//2.1初始化一个账单
					if (!driverOrderShopBillMap.containsKey(driverOrderShop.getDriverAccountId())) {
						List<DriverOrderShop> l = new ArrayList<DriverOrderShop>();
						l.add(driverOrderShop);
						driverOrderShopBillView.setAmount(driverOrderShop.getAmount());
						driverOrderShopBillView.setDriverAccountId(driverOrderShop.getDriverAccountId());
						driverOrderShopBillView.setPayWay("DPW01");
						driverOrderShopBillView.setHandleStatus("DHS02");
						driverOrderShopBillView.setDriverOrderShopList(l);
						driverOrderShopBillView.setEndDate(expireDate);
						//2.2 填充账单
					} else {
						driverOrderShopBillView = driverOrderShopBillMap.get(driverOrderShop.getDriverAccountId());
						//2.2.1 计算金额并填充信息
						driverOrderShopBillView.setAmount(driverOrderShop.getAmount() + driverOrderShopBillView.getAmount());
						driverOrderShopBillView.getDriverOrderShopList().add(driverOrderShop);
					}
					driverOrderShopBillMap.put(driverOrderShop.getDriverAccountId(), driverOrderShopBillView);

				}

				//获取符合支付要求的运单列表
				for (DriverOrderShopBillView v : driverOrderShopBillMap.values()) {
					//先判断骑手是否配置为自动结算，否则跳出自动结算环节
					DriverAccount driverAccount = driverAccountService.get(v.getDriverAccountId());
					if (!driverAccount.getAutoPay()) continue;
					if (CollectionUtils.isEmpty(v.getDriverOrderShopList())) continue;
						DefaultTransactionDefinition def = new DefaultTransactionDefinition();
						def.setPropagationBehavior(DefaultTransactionDefinition.PROPAGATION_REQUIRES_NEW);
						TransactionStatus status = transactionManager.getTransaction(def);
						try {
							//创建骑手运单bill
							add(v);
							for (DriverOrderShop driverOrderShop : v.getDriverOrderShopList()) {
								//创建骑手运单所对应的pay
								DriverOrderPay orderPay = new DriverOrderPay();
								orderPay.setAmount(driverOrderShop.getAmount());
								orderPay.setDriverAccountId(driverOrderShop.getDriverAccountId());
								orderPay.setStatus("DDPS02");
								orderPay.setPayWay("DDPW04");
								orderPay.setDriverOrderShopBillId(v.getId());
								orderPay.setDeliverOrderShopId(driverOrderShop.getDeliverOrderShopId());
								orderPay.setDriverOrderShopId(driverOrderShop.getId());
								orderPay = driverOrderPayService.update(orderPay);

								//状态翻转
								driverOrderShop.setStatus(DriverOrderShopServiceI.STATUS_SETTLEED);
								driverOrderShopService.transform(driverOrderShop);

								MbBalance mbBalance = mbBalanceService.addOrGetDriverBalance(orderPay.getDriverAccountId());
								MbBalanceLog mbBalanceLog = new MbBalanceLog();
								mbBalanceLog.setBalanceId(mbBalance.getId());
								//余额日志类型id 为运单的id
								mbBalanceLog.setRefId(orderPay.getId() + "");
								//门店自动结算
								mbBalanceLog.setRefType("BT150");
								mbBalanceLog.setAmount(orderPay.getAmount());
								mbBalanceLog.setReason(String.format("骑手[ID:%1$s]完成运单[ID:%2$s]结算转入", orderPay.getDriverAccountId(), orderPay.getDriverOrderShopId()));
								mbBalanceLogService.addAndUpdateBalance(mbBalanceLog);
								transactionManager.commit(status);
							}
						} catch (Exception e) {
							transactionManager.rollback(status);
							continue;
						}
					}
				}

		}
	}
}
