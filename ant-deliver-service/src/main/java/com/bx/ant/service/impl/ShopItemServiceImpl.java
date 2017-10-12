package com.bx.ant.service.impl;

import com.bx.ant.dao.ShopItemDaoI;
import com.bx.ant.model.TshopItem;
import com.bx.ant.pageModel.ShopItemQuery;
import com.bx.ant.service.ShopItemServiceI;
import com.mobian.absx.F;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbItem;
import com.mobian.pageModel.PageHelper;
import com.mobian.pageModel.ShopItem;
import com.mobian.service.MbItemServiceI;
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
public class ShopItemServiceImpl extends BaseServiceImpl<ShopItem> implements ShopItemServiceI {

	@Autowired
	private ShopItemDaoI shopItemDao;
	@javax.annotation.Resource
	private MbItemServiceI mbItemService;

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

	@Override
	public ShopItem getByShopIdAndItemId(Integer shopId, Integer itemId, boolean isOnline) {
		Map<String, Object> params = new HashMap<String, Object>();
		ShopItem shopItem = new ShopItem();
		shopItem.setShopId(shopId);
		shopItem.setItemId(itemId);
		shopItem.setOnline(isOnline);
		String where = whereHql(shopItem, params);
		TshopItem t = shopItemDao.get("from TshopItem t " + where , params);
		ShopItem o = new ShopItem();
		if (t != null) {
			BeanUtils.copyProperties(t, o);
		}else{
			o=null;
		}
		return o;
	}

	@Override
	public ShopItem getByShopIdAndItemId(Integer shopId, Integer itemId) {
		ShopItem shopItem = new ShopItem();
		shopItem = getByShopIdAndItemId(shopId, itemId, true);
		if (shopItem != null) return shopItem ;
		shopItem = getByShopIdAndItemId(shopId, itemId, false);
		return shopItem;
	}

	@Override
	public List<ShopItem> query(ShopItem shopItem) {
		List<ShopItem> ol = new ArrayList<ShopItem>();
		String hql = " from TshopItem t ";
		Map<String, Object> params = new HashMap<String, Object>();
		String where = whereHql(shopItem, params);
		List<TshopItem> l = shopItemDao.find(hql + where, params);
		if (CollectionUtils.isNotEmpty(l)) {
			for (TshopItem t : l) {
				ShopItem s = new ShopItem();
				BeanUtils.copyProperties(t, s);
				ol.add(s);
			}
		}
		return ol;
	}

	@Override
	public void updateBatchItemOnline(String itemIds, Integer shopId) {
		if (!F.empty(itemIds)) {
			ShopItem shopItem = new ShopItem();
			shopItem.setIsdeleted(false);
			shopItem.setShopId(shopId);
			List<ShopItem> shopItemList = query(shopItem);
			if (CollectionUtils.isNotEmpty(shopItemList)) {
				List<Integer> itemIdValues = new ArrayList<Integer>();
				for (ShopItem item : shopItemList) {
					itemIdValues.add(item.getItemId());
				}
				String[] itemIdArray = itemIds.split(",");
				int i = 0;
				for (ShopItem item : shopItemList) {
					if (itemIdValues.contains(Integer.parseInt(itemIdArray[i]))) {
						i++;
						if ("true".equals(item.getOnline()))
							continue;
						else if ("false".equals(item.getOnline())) {
							item.setOnline(true);
							edit(item);
						}
					} else {
						ShopItem shop = new ShopItem();
						shop.setItemId(Integer.parseInt(itemIdArray[i]));
						shop.setShopId(shopId);
						shop.setOnline(true);
						add(shop);
						i++;
					}
					if (i > itemIdArray.length - 1)
						break;
				}
			}
		}
	}


	@Override
	public void updateItemOnline(Integer itemId, Integer shopId) {
		ShopItem shopItem = getByShopIdAndItemId(shopId, itemId);
		if (shopItem != null) {
			if ("false".equals(shopItem.getOnline()) && "false".equals(shopItem.getIsdeleted())) {
				shopItem.setOnline(true);
				edit(shopItem);
			} else if ("true".equals(shopItem.getIsdeleted())) {
				ShopItem item = new ShopItem();
				item.setOnline(true);
				item.setItemId(itemId);
				item.setShopId(shopId);
				add(item);
			}
		} else {
			ShopItem item = new ShopItem();
			item.setOnline(true);
			item.setItemId(itemId);
			item.setShopId(shopId);
			add(item);
		}

	}

	@Override
	public void updateBatchShopItemOffline(String itemIds, Integer shopId) {
		if (!F.empty(itemIds)) {
			String[] itemIdArray = itemIds.split(",");
			for (int i = 0; i < itemIdArray.length; i++) {
				ShopItem shopItem = getByShopIdAndItemId(shopId, Integer.parseInt(itemIdArray[i]));
				shopItem.setOnline(false);
				edit(shopItem);
			}
		}
	}

	@Override
	public void updateShopItemOffline(Integer shopItemId) {
		ShopItem shopItem = get(shopItemId);
		shopItem.setOnline(false);
		edit(shopItem);
	}

	@Override
	public void deleteBatchShopItem(String itemIds, Integer shopId) {
		if (!F.empty(itemIds)) {
			String[] itemIdArray = itemIds.split(",");
			for (int i = 0; i < itemIdArray.length; i++) {
				ShopItem shopItem = getByShopIdAndItemId(shopId, Integer.parseInt(itemIdArray[i]));
				delete(shopItem.getId());
			}
		}
	}

	@Override
	public void updateShopItemQuantity( Integer shopItemId, Integer quantity) {
		ShopItem shopItem=get(shopItemId);
		shopItem.setQuantity(quantity);
		edit(shopItem);
	}

	@Override
	public DataGrid dataGridWithItemName(ShopItem shopItem, PageHelper ph) {
		DataGrid dataGrid = dataGrid(shopItem, ph);
		List<ShopItem> shopItemList = dataGrid.getRows();
		if (CollectionUtils.isNotEmpty(shopItemList)) {
			List<ShopItemQuery> shopItemQueries = new ArrayList<ShopItemQuery>();
			for (ShopItem shop : shopItemList) {
				ShopItemQuery shopItemQuery = new ShopItemQuery();
				BeanUtils.copyProperties(shop, shopItemQuery);
				MbItem item = mbItemService.getFromCache(shopItemQuery.getItemId());
				if(F.empty(shop.getQuantity())){
					shopItemQuery.setQuantity(0);
				}
				shopItemQuery.setName(item.getName());
				shopItemQuery.setQuantityUnitName(item.getQuantityUnitName());
				shopItemQuery.setUrl(item.getUrl());
				shopItemQueries.add(shopItemQuery);
			}
			DataGrid dg = new DataGrid();
			dg.setRows(shopItemQueries);
			dg.setTotal(dataGrid.getTotal());
			return dg;

		}
		return dataGrid;
	}

	@Override
	public DataGrid dataGridWithQuantity(MbItem mbItem, PageHelper ph, Integer shopId) {
		DataGrid dataGrid = mbItemService.dataGrid(mbItem, ph);
		List<MbItem> mbItems = dataGrid.getRows();
		if (CollectionUtils.isNotEmpty(mbItems)) {
			List<MbItem> mbItemList = new ArrayList<MbItem>();
			ShopItem shopItem = new ShopItem();
			shopItem.setIsdeleted(false);
			shopItem.setOnline(true);
			shopItem.setShopId(shopId);
			List<ShopItem> shopItems = query(shopItem);
			if (CollectionUtils.isNotEmpty(shopItems)) {
				for (MbItem item : mbItems) {
					for (ShopItem sItem : shopItems) {
						if (sItem.getItemId() == item.getId()) {
							item.setQuantity(sItem.getQuantity());
							mbItemList.add(item);
						} else {
							item.setQuantity(0);
							mbItemList.add(item);
						}
					}
				}
			} else {
				for (MbItem item : mbItems) {
					item.setQuantity(0);
					mbItemList.add(item);
				}
			}
			DataGrid dg = new DataGrid();
			dg.setRows(mbItemList);
			return dg;
		}
		return dataGrid;
	}

}
