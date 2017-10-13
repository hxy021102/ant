package com.bx.ant.service.impl;

import com.bx.ant.dao.ShopDeliverApplyDaoI;
import com.bx.ant.model.TshopDeliverApply;
import com.bx.ant.pageModel.ShopDeliverApplyQuery;
import com.bx.ant.service.ShopDeliverApplyServiceI;
import com.mobian.absx.F;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbShop;
import com.mobian.pageModel.PageHelper;
import com.mobian.pageModel.ShopDeliverApply;
import com.mobian.service.MbShopServiceI;
import com.mobian.util.MyBeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class ShopDeliverApplyServiceImpl extends BaseServiceImpl<ShopDeliverApply> implements ShopDeliverApplyServiceI {

	@Autowired
	private ShopDeliverApplyDaoI shopDeliverApplyDao;

	@Resource
	private MbShopServiceI mbShopService;

	@Override
	public DataGrid dataGrid(ShopDeliverApply shopDeliverApply, PageHelper ph) {
		List<ShopDeliverApply> ol = new ArrayList<ShopDeliverApply>();
		String hql = " from TshopDeliverApply t ";
		DataGrid dg = dataGridQuery(hql, ph, shopDeliverApply, shopDeliverApplyDao);
		@SuppressWarnings("unchecked")
		List<TshopDeliverApply> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TshopDeliverApply t : l) {
				ShopDeliverApply o = new ShopDeliverApply();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(ShopDeliverApply shopDeliverApply, Map<String, Object> params) {
		String whereHql = "";	
		if (shopDeliverApply != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(shopDeliverApply.getTenantId())) {
				whereHql += " and t.tenantId = :tenantId";
				params.put("tenantId", shopDeliverApply.getTenantId());
			}		
			if (!F.empty(shopDeliverApply.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", shopDeliverApply.getIsdeleted());
			}		
			if (!F.empty(shopDeliverApply.getShopId())) {
				whereHql += " and t.shopId = :shopId";
				params.put("shopId", shopDeliverApply.getShopId());
			}		
			if (!F.empty(shopDeliverApply.getDeliveryWay())) {
				whereHql += " and t.deliveryWay = :deliveryWay";
				params.put("deliveryWay", shopDeliverApply.getDeliveryWay());
			}		
			if (!F.empty(shopDeliverApply.getOnline())) {
				whereHql += " and t.online = :online";
				params.put("online", shopDeliverApply.getOnline());
			}		
			if (!F.empty(shopDeliverApply.getResult())) {
				whereHql += " and t.result = :result";
				params.put("result", shopDeliverApply.getResult());
			}		
			if (!F.empty(shopDeliverApply.getStatus())) {
				whereHql += " and t.status = :status";
				params.put("status", shopDeliverApply.getStatus());
			}		
			if (!F.empty(shopDeliverApply.getAccountId())) {
				whereHql += " and t.accountId = :accountId";
				params.put("accountId", shopDeliverApply.getAccountId());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(ShopDeliverApply shopDeliverApply) {
		TshopDeliverApply t = new TshopDeliverApply();
		BeanUtils.copyProperties(shopDeliverApply, t);
		//t.setId(jb.absx.UUID.uuid());
		t.setIsdeleted(false);
		if(F.empty(shopDeliverApply.getStatus())) t.setStatus(DAS_01);
		shopDeliverApplyDao.save(t);
	}

	@Override
	public ShopDeliverApply get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TshopDeliverApply t = shopDeliverApplyDao.get("from TshopDeliverApply t  where t.id = :id", params);
		ShopDeliverApply o = new ShopDeliverApply();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(ShopDeliverApply shopDeliverApply) {
		TshopDeliverApply t = shopDeliverApplyDao.get(TshopDeliverApply.class, shopDeliverApply.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(shopDeliverApply, t, new String[] { "id" , "addtime", "isdeleted","updatetime" },true);
		}
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		shopDeliverApplyDao.executeHql("update TshopDeliverApply t set t.isdeleted = 1 where t.id = :id",params);
		//shopDeliverApplyDao.delete(shopDeliverApplyDao.get(TshopDeliverApply.class, id));
	}

	@Override
	public ShopDeliverApply getByAccountId(Integer accountId) {
		ShopDeliverApply o = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("accountId", accountId);
		TshopDeliverApply t = shopDeliverApplyDao.get("from TshopDeliverApply t where t.isdeleted = 0 and t.accountId = :accountId", params);
		if(t != null) {
			o = new ShopDeliverApply();
			BeanUtils.copyProperties(t, o);
		}

		return o;
	}

	@Override
	public DataGrid dataGridWithName(ShopDeliverApply shopDeliverApply, PageHelper ph) {
		DataGrid dataGrid = dataGrid(shopDeliverApply, ph);
		List<ShopDeliverApply> shopDeliverApplies = dataGrid.getRows();
		if (CollectionUtils.isNotEmpty(shopDeliverApplies)) {
			List<ShopDeliverApplyQuery> shopDeliverApplyQueries = new ArrayList<ShopDeliverApplyQuery>();
			for (ShopDeliverApply deliverApply : shopDeliverApplies) {
				ShopDeliverApplyQuery shopDeliverApplyQuery = new ShopDeliverApplyQuery();
				BeanUtils.copyProperties(deliverApply, shopDeliverApplyQuery);
				MbShop shop = mbShopService.getFromCache(deliverApply.getShopId());
				shopDeliverApplyQuery.setShopName(shop.getName());
				shopDeliverApplyQueries.add(shopDeliverApplyQuery);
			}
			DataGrid dg = new DataGrid();
			dg.setRows(shopDeliverApplyQueries);
			dg.setTotal(dataGrid.getTotal());
			return dg;
		}
		return dataGrid;
	}

	@Override
	public ShopDeliverApplyQuery getViewMessage(Integer id) {
		ShopDeliverApply shopDeliverApply = get(id);
		ShopDeliverApplyQuery shopDeliverApplyQuery = new ShopDeliverApplyQuery();
		BeanUtils.copyProperties(shopDeliverApply, shopDeliverApplyQuery);
		MbShop shop = mbShopService.getFromCache(shopDeliverApply.getShopId());
		shopDeliverApplyQuery.setShopName(shop.getName());
		return shopDeliverApplyQuery;
	}

	@Override
	public List<ShopDeliverApply> getAvailableAndWorkShop() {
		ShopDeliverApply shopDeliverApply = new ShopDeliverApply();
		PageHelper ph = new PageHelper();
		ph.setHiddenTotal(true);
		shopDeliverApply.setOnline(true);
		shopDeliverApply.setStatus(DAS_02);
		DataGrid dataGrid = dataGrid(shopDeliverApply, ph);
		List<ShopDeliverApply> shopDeliverApplyList = dataGrid.getRows();
		Iterator<ShopDeliverApply> iterator = shopDeliverApplyList.iterator();
		while (iterator.hasNext()) {
			ShopDeliverApply deliverApply = iterator.next();
			MbShop shop = mbShopService.getFromCache(deliverApply.getShopId());
			boolean flag = false;
			if (MbShopServiceI.AS_02.equals(shop.getAuditStatus()) && (MbShopServiceI.ST01.equals(shop.getShopType()) || MbShopServiceI.ST03.equals(shop.getShopType()))) {
				if (shop.getLatitude() != null && shop.getLongitude() != null) {
					deliverApply.setMbShop(shop);
					flag = true;
				}
			}
			//不满足的删除掉
			if (!flag) {
				iterator.remove();
			}
		}
		return shopDeliverApplyList;
	}

	@Override
	public List<ShopDeliverApply> query(ShopDeliverApply shopDeliverApply) {
		List<ShopDeliverApply> ol = new ArrayList<ShopDeliverApply>();
		String hql = " from TshopDeliverApply t ";
		Map<String, Object> params = new HashMap<String, Object>();
		String where = whereHql(shopDeliverApply, params);
		List<TshopDeliverApply> l = shopDeliverApplyDao.find(hql + where, params);
		if (CollectionUtils.isNotEmpty(l)) {
			for (TshopDeliverApply t : l) {
				ShopDeliverApply o= new ShopDeliverApply();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}
}
