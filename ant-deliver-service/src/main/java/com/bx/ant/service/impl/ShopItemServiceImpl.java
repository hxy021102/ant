package com.bx.ant.service.impl;

import com.mobian.absx.F;
import com.bx.ant.dao.ShopItemDaoI;
import com.bx.ant.model.TshopItem;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;
import com.mobian.pageModel.ShopItem;
import com.bx.ant.service.ShopItemServiceI;
import com.mobian.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShopItemServiceImpl extends BaseServiceImpl<ShopItem> implements ShopItemServiceI {

	@Autowired
	private ShopItemDaoI shopItemDao;

	@Override
	public DataGrid dataGrid(ShopItem shopItem, PageHelper ph) {
		List<ShopItem> ol = new ArrayList<ShopItem>();
		String hql = " from TshopItem t ";
		DataGrid dg = dataGridQuery(hql, ph, shopItem, shopItemDao);
		@SuppressWarnings("unchecked")
		List<TshopItem> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TshopItem t : l) {
				ShopItem o = new ShopItem();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(ShopItem shopItem, Map<String, Object> params) {
		String whereHql = "";	
		if (shopItem != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(shopItem.getTenantId())) {
				whereHql += " and t.tenantId = :tenantId";
				params.put("tenantId", shopItem.getTenantId());
			}		
			if (!F.empty(shopItem.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", shopItem.getIsdeleted());
			}		
			if (!F.empty(shopItem.getShopId())) {
				whereHql += " and t.shopId = :shopId";
				params.put("shopId", shopItem.getShopId());
			}		
			if (!F.empty(shopItem.getItemId())) {
				whereHql += " and t.itemId = :itemId";
				params.put("itemId", shopItem.getItemId());
			}		
			if (!F.empty(shopItem.getPrice())) {
				whereHql += " and t.price = :price";
				params.put("price", shopItem.getPrice());
			}		
			if (!F.empty(shopItem.getInPrice())) {
				whereHql += " and t.inPrice = :inPrice";
				params.put("inPrice", shopItem.getInPrice());
			}		
			if (!F.empty(shopItem.getFreight())) {
				whereHql += " and t.freight = :freight";
				params.put("freight", shopItem.getFreight());
			}		
			if (!F.empty(shopItem.getQuantity())) {
				whereHql += " and t.quantity = :quantity";
				params.put("quantity", shopItem.getQuantity());
			}		
			if (!F.empty(shopItem.getOnline())) {
				whereHql += " and t.online = :online";
				params.put("online", shopItem.getOnline());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(ShopItem shopItem) {
		TshopItem t = new TshopItem();
		BeanUtils.copyProperties(shopItem, t);
		//t.setId(jb.absx.UUID.uuid());
		t.setIsdeleted(false);
		shopItemDao.save(t);
	}

	@Override
	public ShopItem get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TshopItem t = shopItemDao.get("from TshopItem t  where t.id = :id", params);
		ShopItem o = new ShopItem();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(ShopItem shopItem) {
		TshopItem t = shopItemDao.get(TshopItem.class, shopItem.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(shopItem, t, new String[] { "id" , "addtime", "isdeleted","updatetime" },true);
		}
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		shopItemDao.executeHql("update TshopItem t set t.isdeleted = 1 where t.id = :id",params);
		//shopItemDao.delete(shopItemDao.get(TshopItem.class, id));
	}

}
