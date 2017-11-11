package com.bx.ant.service.impl;

import com.bx.ant.dao.ShopOrderBillDaoI;
import com.bx.ant.model.TshopOrderBill;
import com.bx.ant.pageModel.*;
import com.bx.ant.service.*;
import com.mobian.absx.F;
import com.mobian.pageModel.*;
import com.mobian.service.MbBalanceLogServiceI;
import com.mobian.service.MbBalanceServiceI;
import com.mobian.service.MbShopServiceI;
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
public class ShopOrderBillServiceImpl extends BaseServiceImpl<ShopOrderBill> implements ShopOrderBillServiceI {

	 @Autowired
	 private ShopOrderBillDaoI shopOrderBillDao;
	 @Resource
	 private MbShopServiceI mbShopService;
	 @Autowired
	 private DeliverOrderServiceI deliverOrderService;
	 @Autowired
	 private DeliverOrderShopPayServiceI deliverOrderShopPayService;
	 @Resource
	 private UserServiceI userService;
     @Resource
	 private MbBalanceServiceI mbBalanceService;
     @Resource
	 private MbBalanceLogServiceI mbBalanceLogService;
     @Autowired
	 private DeliverOrderShopServiceI deliverOrderShopService;

	@Override
	public DataGrid dataGrid(ShopOrderBill shopOrderBill, PageHelper ph) {
		List<ShopOrderBill> ol = new ArrayList<ShopOrderBill>();
		String hql = " from TshopOrderBill t ";
		DataGrid dg = dataGridQuery(hql, ph, shopOrderBill, shopOrderBillDao);
		@SuppressWarnings("unchecked")
		List<TshopOrderBill> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TshopOrderBill t : l) {
				ShopOrderBill o = new ShopOrderBill();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(ShopOrderBill shopOrderBill, Map<String, Object> params) {
		String whereHql = "";	
		if (shopOrderBill != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(shopOrderBill.getTenantId())) {
				whereHql += " and t.tenantId = :tenantId";
				params.put("tenantId", shopOrderBill.getTenantId());
			}		
			if (!F.empty(shopOrderBill.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", shopOrderBill.getIsdeleted());
			}		
			if (!F.empty(shopOrderBill.getShopId())) {
				whereHql += " and t.shopId = :shopId";
				params.put("shopId", shopOrderBill.getShopId());
			}		
			if (!F.empty(shopOrderBill.getStatus())) {
				whereHql += " and t.status = :status";
				params.put("status", shopOrderBill.getStatus());
			}		
			if (!F.empty(shopOrderBill.getRemark())) {
				whereHql += " and t.remark = :remark";
				params.put("remark", shopOrderBill.getRemark());
			}		
			if (!F.empty(shopOrderBill.getAmount())) {
				whereHql += " and t.amount = :amount";
				params.put("amount", shopOrderBill.getAmount());
			}		
			if (!F.empty(shopOrderBill.getPayWay())) {
				whereHql += " and t.payWay = :payWay";
				params.put("payWay", shopOrderBill.getPayWay());
			}
			if (shopOrderBill.getStartDate() != null) {
				whereHql += " and t.addtime >= :startDate ";
				params.put("startDate", shopOrderBill.getStartDate());
			}
			if (shopOrderBill.getEndDate() != null) {
				whereHql += " and t.addtime <= :endDate ";
				params.put("endDate", shopOrderBill.getEndDate());

			}

		}	
		return whereHql;
	}

	@Override
	public void add(ShopOrderBill shopOrderBill) {
		TshopOrderBill t = new TshopOrderBill();
		BeanUtils.copyProperties(shopOrderBill, t);
		//t.setId(jb.absx.UUID.uuid());
		t.setIsdeleted(false);
		shopOrderBillDao.save(t);
		shopOrderBill.setId(t.getId());
	}

	@Override
	public ShopOrderBill get(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TshopOrderBill t = shopOrderBillDao.get("from TshopOrderBill t  where t.id = :id", params);
		ShopOrderBill o = new ShopOrderBill();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(ShopOrderBill shopOrderBill) {
		TshopOrderBill t = shopOrderBillDao.get(TshopOrderBill.class, shopOrderBill.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(shopOrderBill, t, new String[] { "id" , "addtime", "isdeleted","updatetime" },true);
		}
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		shopOrderBillDao.executeHql("update TshopOrderBill t set t.isdeleted = 1 where t.id = :id",params);
		//shopOrderBillDao.delete(shopOrderBillDao.get(TshopOrderBill.class, id));
	}

	@Override
	public DataGrid dataGridWithName(ShopOrderBill shopOrderBill, PageHelper ph) {
		DataGrid dataGrid = dataGrid(shopOrderBill, ph);
		List<ShopOrderBill> shopOrderBills = dataGrid.getRows();
		if (CollectionUtils.isNotEmpty(shopOrderBills)) {
			List<ShopOrderBillQuery> shopOrderBillQueries = new ArrayList<ShopOrderBillQuery>();
			for (ShopOrderBill shopOrderBill1 : shopOrderBills) {
				ShopOrderBillQuery shopOrderBillQuery = new ShopOrderBillQuery();
				BeanUtils.copyProperties(shopOrderBill1, shopOrderBillQuery);
				MbShop mbShop = mbShopService.getFromCache(shopOrderBill1.getShopId());
				if (mbShop != null) {
					shopOrderBillQuery.setShopName(mbShop.getName());
				}
				shopOrderBillQuery.setStatusName(shopOrderBill1.getStatus());
				shopOrderBillQueries.add(shopOrderBillQuery);
			}
			DataGrid dg = new DataGrid();
			dg.setRows(shopOrderBillQueries);
			dg.setTotal(dataGrid.getTotal());
			return dg;
		}
		return dataGrid;
	}

	@Override
	public String addShopOrderBillAndShopPay(ShopOrderBillQuery shopOrderBillQuery) {
		ShopOrderBill shopOrderBill = new ShopOrderBill();
		DeliverOrderShopPayQuery shopPayQuery = new DeliverOrderShopPayQuery();
		shopPayQuery.setDeliverOrderIds(shopOrderBillQuery.getDeliverOrderIds());
		String[] statusArray = {"SPS01", "SPS02", "SPS04"};
		shopPayQuery.setStatusArray(statusArray);
		List<DeliverOrderShopPay> orderShopPays = deliverOrderShopPayService.query(shopPayQuery);
		if (CollectionUtils.isEmpty(orderShopPays)) {
			BeanUtils.copyProperties(shopOrderBillQuery, shopOrderBill);
			shopOrderBill.setStatus("BAS01");
			add(shopOrderBill);
			shopOrderBillQuery.setId(shopOrderBill.getId());
			DeliverOrderQuery deliverOrderQuery = new DeliverOrderQuery();
			deliverOrderQuery.setStartDate(shopOrderBill.getStartDate());
			deliverOrderQuery.setEndDate(shopOrderBill.getEndDate());
			deliverOrderQuery.setShopId(shopOrderBill.getShopId());
			deliverOrderQuery.setStatus("DOS30");
			List<DeliverOrderShop> deliverOrderShops = shopOrderBillQuery.getDeliverOrderShopList();
			if (CollectionUtils.isNotEmpty(deliverOrderShops)) {
				for (DeliverOrderShop deliverOrderShop : deliverOrderShops) {
					DeliverOrderShopPay orderShopPay = new DeliverOrderShopPay();
					orderShopPay.setAmount(deliverOrderShop.getAmount());
					orderShopPay.setShopId(shopOrderBill.getShopId());
					orderShopPay.setDeliverOrderId(deliverOrderShop.getDeliverOrderId());
					orderShopPay.setStatus("SPS01");
					orderShopPay.setShopOrderBillId(shopOrderBill.getId());
					orderShopPay.setDeliverOrderShopId(deliverOrderShop.getId());
					deliverOrderShopPayService.add(orderShopPay);
				}
			}
			return null;
		} else {
			String result="运单ID:";
			for(DeliverOrderShopPay deliverOrderShopPay:orderShopPays){
				result+=deliverOrderShopPay.getDeliverOrderId().toString()+"  ";
			}
			return result;
		}
	}

	@Override
	public void addAndPayShopOrderBillAndShopPay(ShopOrderBillQuery shopOrderBillQuery) {
		addShopOrderBillAndShopPay(shopOrderBillQuery);
		if(!F.empty(shopOrderBillQuery.getId())) {
			shopOrderBillQuery.setStatus("BAS02");
			shopOrderBillQuery.setRemark("到期自动结算");
			edit(shopOrderBillQuery);
		}
	}

	@Override
	public ShopOrderBillQuery getViewShopOrderBill(Long id) {
		ShopOrderBill shopOrderBill = get(id);
		if (shopOrderBill != null) {
			ShopOrderBillQuery shopOrderBillQuery = new ShopOrderBillQuery();
			BeanUtils.copyProperties(shopOrderBill, shopOrderBillQuery);
			shopOrderBillQuery.setPayWayName(shopOrderBill.getPayWay());
			User user = userService.get(shopOrderBill.getReviewerId());
			if (user != null) {
				shopOrderBillQuery.setReviewerName(user.getName());
			}
			return shopOrderBillQuery;
		}
		return null;
	}

	@Override
	public void editBillStatusAndPayStatus(ShopOrderBill shopOrderBill) {
		edit(shopOrderBill);
		DeliverOrderShopPay deliverOrderShopPay = new DeliverOrderShopPay();
		deliverOrderShopPay.setShopOrderBillId(shopOrderBill.getId());
		deliverOrderShopPay.setStatus("SPS01");
		List<DeliverOrderShopPay> deliverOrderShopPays = deliverOrderShopPayService.query(deliverOrderShopPay);
		if(CollectionUtils.isNotEmpty(deliverOrderShopPays)) {
			for (DeliverOrderShopPay orderShopPay : deliverOrderShopPays) {
				if("BAS02".equals(shopOrderBill.getStatus())){
					orderShopPay.setStatus("SPS04");
					orderShopPay.setPayWay("DPW01");
					//审核通过 进行支付
					DeliverOrderExt orderExt = new DeliverOrderExt();
					orderExt.setId(orderShopPay.getDeliverOrderId());
					orderExt.setShopId(shopOrderBill.getShopId());
					orderExt.setBalanceLogType("BT061");
					orderExt.setPayWay(shopOrderBill.getPayWay());
					orderExt.setStatus(DeliverOrderServiceI.STATUS_CLOSED);
					orderExt.setOrderShopId(orderShopPay.getDeliverOrderShopId());
					deliverOrderService.transform(orderExt);
				}else {
					orderShopPay.setStatus("SPS03");
				}
				deliverOrderShopPayService.edit(orderShopPay);
			}
		}

	}

	@Override
	public void editDeliverOrderStatusAndShopBalance(ShopOrderBill shopOrderBill) {
		/*edit(shopOrderBill);
		DeliverOrderShopPay deliverOrderShopPay = new DeliverOrderShopPay();
		deliverOrderShopPay.setStatus("SPS01");
		deliverOrderShopPay.setShopOrderBillId(shopOrderBill.getId());
		List<DeliverOrderShopPay> deliverOrderShopPays =deliverOrderShopPayService.query(deliverOrderShopPay);
		if(CollectionUtils.isNotEmpty(deliverOrderShopPays)){
			for(DeliverOrderShopPay orderShopPay : deliverOrderShopPays){
				DeliverOrderExt orderExt = new DeliverOrderExt();
				orderExt.setId(orderShopPay.getDeliverOrderId());
				orderExt.setShopId(shopOrderBill.getShopId());
				orderExt.setBalanceLogType("BT061");
				orderExt.setPayWay(shopOrderBill.getPayWay());
				orderExt.setStatus(DeliverOrderServiceI.STATUS_CLOSED);
				deliverOrderService.transform(orderExt);
			}
		}*/
	}

}
