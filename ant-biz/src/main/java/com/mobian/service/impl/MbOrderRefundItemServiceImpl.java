package com.mobian.service.impl;

import com.mobian.absx.F;
import com.mobian.dao.MbOrderRefundItemDaoI;
import com.mobian.model.TmbOrderRefundItem;
import com.mobian.pageModel.*;
import com.mobian.service.*;
import com.mobian.service.impl.order.OrderState;
import com.mobian.util.ConfigUtil;
import com.mobian.util.MyBeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.*;

@Service
public class MbOrderRefundItemServiceImpl extends BaseServiceImpl<MbOrderRefundItem> implements MbOrderRefundItemServiceI {

	@Autowired
	private MbOrderRefundItemDaoI mbOrderRefundItemDao;

	@Autowired
	private MbItemServiceI mbItemService;
	@Autowired
	private UserServiceI userService;
	@Autowired
	private MbOrderServiceI mbOrderService;
	@Autowired
	private MbItemStockServiceI mbItemStockService;
	@Autowired
	private MbShopServiceI mbShopService;
	@Autowired
	private MbBalanceServiceI mbBalanceService;
	@Autowired
	private MbBalanceLogServiceI mbBalanceLogService;
	@Autowired
	private MbOrderItemServiceI mbOrderItemService;
	@Autowired
	private MbPaymentItemServiceI mbPaymentItemService;
	@Autowired
	private MbPaymentServiceI  mbPaymentService;
	@Autowired
	private MbOrderLogServiceI mbOrderLogService;
	@Autowired
	private MbOrderRefundLogServiceI mbOrderRefundLogService;
	@Override
	public DataGrid dataGrid(MbOrderRefundItem mbOrderRefundItem, PageHelper ph) {
		List<MbOrderRefundItem> ol = new ArrayList<MbOrderRefundItem>();
		String hql = " from TmbOrderRefundItem t ";
		DataGrid dg = dataGridQuery(hql, ph, mbOrderRefundItem, mbOrderRefundItemDao);
		@SuppressWarnings("unchecked")
		List<TmbOrderRefundItem> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TmbOrderRefundItem t : l) {
				MbOrderRefundItem o = new MbOrderRefundItem();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	protected String whereHql(MbOrderRefundItem mbOrderRefundItem, Map<String, Object> params) {
		String whereHql = "";	
		if (mbOrderRefundItem != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(mbOrderRefundItem.getTenantId())) {
				whereHql += " and t.tenantId = :tenantId";
				params.put("tenantId", mbOrderRefundItem.getTenantId());
			}		
			if (!F.empty(mbOrderRefundItem.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", mbOrderRefundItem.getIsdeleted());
			}		
			if (!F.empty(mbOrderRefundItem.getOrderId())) {
				whereHql += " and t.orderId = :orderId";
				params.put("orderId", mbOrderRefundItem.getOrderId());
			}		
			if (!F.empty(mbOrderRefundItem.getItemId())) {
				whereHql += " and t.itemId = :itemId";
				params.put("itemId", mbOrderRefundItem.getItemId());
			}		
			if (!F.empty(mbOrderRefundItem.getQuantity())) {
				whereHql += " and t.quantity = :quantity";
				params.put("quantity", mbOrderRefundItem.getQuantity());
			}		
			if (!F.empty(mbOrderRefundItem.getType())) {
				whereHql += " and t.type = :type";
				params.put("type", mbOrderRefundItem.getType());
			}		
			if (!F.empty(mbOrderRefundItem.getLoginId())) {
				whereHql += " and t.loginId = :loginId";
				params.put("loginId", mbOrderRefundItem.getLoginId());
			}		
			if (!F.empty(mbOrderRefundItem.getRemark())) {
				whereHql += " and t.remark = :remark";
				params.put("remark", mbOrderRefundItem.getRemark());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(MbOrderRefundItem mbOrderRefundItem) {
		TmbOrderRefundItem t = new TmbOrderRefundItem();
		BeanUtils.copyProperties(mbOrderRefundItem, t);
		//t.setId(jb.absx.UUID.uuid());
		t.setIsdeleted(false);
		mbOrderRefundItemDao.save(t);
	}

	@Override
	public MbOrderRefundItem get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TmbOrderRefundItem t = mbOrderRefundItemDao.get("from TmbOrderRefundItem t  where t.id = :id", params);
		MbOrderRefundItem o = new MbOrderRefundItem();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(MbOrderRefundItem mbOrderRefundItem) {
		TmbOrderRefundItem t = mbOrderRefundItemDao.get(TmbOrderRefundItem.class, mbOrderRefundItem.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(mbOrderRefundItem, t, new String[]{"id", "addtime", "isdeleted", "updatetime"}, true);
		}
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		mbOrderRefundItemDao.executeHql("update TmbOrderRefundItem t set t.isdeleted = 1 where t.id = :id",params);
		//mbOrderRefundItemDao.delete(mbOrderRefundItemDao.get(TmbOrderRefundItem.class, id));
	}

	/**
	 * 根据mbOrderRefundItem查找所有回桶List<MbOrderRefundItem>
	 * @param
	 * @return
	 */
	@Override
	public List<MbOrderRefundItem> query(MbOrderRefundItem mbOrderRefundItem){
		String hql = " from TmbOrderRefundItem t ";
		Map<String , Object> params = new HashMap<String,Object>();
		String where = whereHql(mbOrderRefundItem,params);
		List<TmbOrderRefundItem> tmbOrderRefundItems = mbOrderRefundItemDao.find(hql + where,params);
		List<MbOrderRefundItem> mbOrderRefundItems = new ArrayList<MbOrderRefundItem>();
		for (TmbOrderRefundItem tmbOrderRefundItem: tmbOrderRefundItems
			 ) {
			MbOrderRefundItem orderRefundItem = new MbOrderRefundItem();
			BeanUtils.copyProperties(tmbOrderRefundItem,orderRefundItem);
			mbOrderRefundItems.add(orderRefundItem);
		}
		return mbOrderRefundItems;
	}

	@Override
	public List<MbOrderRefundItem> queryListByOrderIds(Integer[] orderIds) {
		List<MbOrderRefundItem> ol = new ArrayList<MbOrderRefundItem>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("alist", orderIds);
		List<TmbOrderRefundItem> l = mbOrderRefundItemDao.find("from TmbOrderRefundItem t where t.orderId in (:alist) and t.isdeleted=0", params);
		if (CollectionUtils.isNotEmpty(l)) {
			for (TmbOrderRefundItem t : l) {
				MbOrderRefundItem o = new MbOrderRefundItem();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;


	}

	@Override
	public void addRefund(MbOrderRefundItem mbOrderRefundItem) {
		add(mbOrderRefundItem);
		//退回商品:总仓+ 分仓-
		MbOrder mbOrderOld = mbOrderService.get(mbOrderRefundItem.getOrderId());
		MbShop mbShop = mbShopService.getFromCache(mbOrderOld.getShopId());
		if (mbOrderOld.getDeliveryWarehouseId() != null) {
			//总仓增加商品
			MbItemStock mbItemStock = mbItemStockService.getByWareHouseIdAndItemId(mbOrderOld.getDeliveryWarehouseId(), mbOrderRefundItem.getItemId());
			MbItemStock change = new MbItemStock();
			change.setId(mbItemStock.getId());
			change.setAdjustment(mbOrderRefundItem.getQuantity());
			change.setLogType("SL02");
			change.setReason(String.format("订单ID:%s退货入库，库存：%s", mbOrderRefundItem.getOrderId(), mbItemStock.getQuantity() + mbOrderRefundItem.getQuantity()));
			mbItemStockService.editAndInsertLog(change, mbOrderOld.getLoginId());
		}

		//分仓减少商品
		MbItemStock mbItemStockShop = mbItemStockService.getByWareHouseIdAndItemId(mbShop.getWarehouseId(), mbOrderRefundItem.getItemId());
		MbItemStock changeShop = new MbItemStock();
		changeShop.setId(mbItemStockShop.getId());
		changeShop.setAdjustment(-mbOrderRefundItem.getQuantity());
		changeShop.setLogType("SL03");
		changeShop.setReason(String.format("订单ID:%s退货出库，库存：%s", mbOrderOld.getId(), mbItemStockShop.getQuantity() - mbOrderRefundItem.getQuantity()));
		mbItemStockService.editAndInsertLog(changeShop, mbOrderRefundItem.getLoginId());
		//分仓空桶减少
		MbItem mbItem = mbItemService.getFromCache(mbOrderRefundItem.getItemId());
		if (mbItem != null && mbItem.getPackId() != null) {
			mbItemStockShop = mbItemStockService.getByWareHouseIdAndItemId(mbShop.getWarehouseId(), mbItem.getPackId());
			changeShop = new MbItemStock();
			changeShop.setId(mbItemStockShop.getId());
			changeShop.setAdjustment(-mbOrderRefundItem.getQuantity());
			changeShop.setLogType("SL03");
			changeShop.setReason(String.format("订单ID：%s退货出库，库存:%s", mbOrderOld.getId(), mbItemStockShop.getQuantity() - mbOrderRefundItem.getQuantity()));
			mbItemStockService.editAndInsertLog(changeShop, mbOrderRefundItem.getLoginId());
			//加桶钱
			MbBalance mbBalance = mbBalanceService.addOrGetMbBalanceCash(mbOrderOld.getShopId());
			MbItem packItem = mbItemService.getFromCache(mbItem.getPackId());
			MbBalanceLog mbBalanceLog = new MbBalanceLog();
			mbBalanceLog.setAmount(packItem.getMarketPrice() * mbOrderRefundItem.getQuantity());
			mbBalanceLog.setRefId(mbOrderOld.getId() + "");
			mbBalanceLog.setRefType("BT017");
			mbBalanceLog.setBalanceId(mbBalance.getId());
			mbBalanceLog.setReason(String.format("订单ID：%s退货出库 商品[%s],数量[%s]", mbOrderOld.getId(), packItem.getName(), mbOrderRefundItem.getQuantity()));
			mbBalanceLogService.addAndUpdateBalance(mbBalanceLog);
		}
		//根据退货商品计算退款金额
		Integer refundAmount = 0;
		MbOrderItem mbOrderItem = new MbOrderItem();
		mbOrderItem.setOrderId(mbOrderOld.getId());
		mbOrderItem.setItemId(mbOrderRefundItem.getItemId());

		List<MbOrderItem> mbOrderItems = mbOrderItemService.query(mbOrderItem);
		if (mbOrderItems != null && mbOrderItems.size() > 0) {
			Collections.sort(mbOrderItems, new Comparator<MbOrderItem>() {
				@Override
				public int compare(MbOrderItem o1, MbOrderItem o2) {
					return o1.getBuyPrice() - o2.getBuyPrice();
				}
			});
			int refundQuantity = mbOrderRefundItem.getQuantity();
			for (MbOrderItem orderItem : mbOrderItems) {
				if (refundQuantity > 0) {
					int reduce;
					if (refundQuantity > orderItem.getQuantity()) {
						reduce = orderItem.getQuantity();
					} else {
						reduce = refundQuantity;
					}
					refundQuantity = refundQuantity - reduce;
					refundAmount += reduce * orderItem.getBuyPrice();
				} else {
					break;
				}
			}
		}
			//将退款金额写入退款记录
			if ("PS05".equals(mbOrderOld.getPayStatus()) && refundAmount > 0) {
				MbPayment mbPayment = mbPaymentService.getByOrderId(mbOrderOld.getId());
				if (mbPayment != null) {
					List<MbPaymentItem> mbPaymentItems = mbPaymentItemService.getByPaymentId(mbPayment.getId());
					if (CollectionUtils.isNotEmpty(mbPaymentItems)) {
						//TODO 支付明细 暂时只有一种
						MbPaymentItem mbPaymentItem = mbPaymentItems.get(0);
						MbOrderLog mbOrderLog = new MbOrderLog();
						mbOrderLog.setContent("订单部分商品退货，退相应金额");
						mbOrderLog.setLoginId(mbOrderOld.getLoginId());
						mbOrderLog.setLogType("LT006");
						mbOrderLog.setOrderId(mbOrderOld.getId());
						mbOrderLogService.add(mbOrderLog);
						MbOrderRefundLog mbOrderRefundLog = new MbOrderRefundLog();
						mbOrderRefundLog.setAmount(refundAmount);
						mbOrderRefundLog.setPayWay(mbPaymentItem.getPayWay());
						mbOrderRefundLog.setPaymentItemId(mbPaymentItem.getId());
						mbOrderRefundLog.setOrderId(mbPayment.getOrderId());
						mbOrderRefundLog.setOrderType(mbPayment.getOrderType());
						mbOrderRefundLog.setReason("订单部分商品退货，退相应金额");
						//退余额
						mbOrderRefundLog.setRefundWay("RW02");
						mbOrderRefundLogService.add(mbOrderRefundLog);
						MbBalance balance = mbBalanceService.addOrGetMbBalance(mbOrderOld.getShopId());
						MbBalanceLog log = new MbBalanceLog();
						log.setBalanceId(balance.getId());
						log.setAmount(refundAmount);
						log.setRefType("BT006"); //订单部分退货，退余额
						log.setRefId(mbOrderRefundLog.getId() + "");
						mbBalanceLogService.addAndUpdateBalance(log);
					}
				}
			}
	}
}