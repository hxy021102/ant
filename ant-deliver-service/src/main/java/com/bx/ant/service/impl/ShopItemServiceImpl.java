package com.bx.ant.service.impl;

import com.bx.ant.dao.ShopItemDaoI;
import com.bx.ant.model.TshopItem;
import com.bx.ant.pageModel.*;
import com.bx.ant.service.ShopDeliverApplyServiceI;
import com.bx.ant.service.ShopItemServiceI;
import com.mobian.absx.F;
import com.mobian.exception.ServiceException;
import com.mobian.pageModel.*;
import com.mobian.service.MbContractItemServiceI;
import com.mobian.service.MbContractServiceI;
import com.mobian.service.MbItemServiceI;
import com.mobian.service.MbShopServiceI;
import com.mobian.util.MyBeanUtils;
import net.sf.json.JSONArray;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class ShopItemServiceImpl extends BaseServiceImpl<ShopItem> implements ShopItemServiceI {

	public static final int INT = 0;
	@Autowired
	private ShopItemDaoI shopItemDao;
	@javax.annotation.Resource
	private MbItemServiceI mbItemService;

	@Autowired
	private DeliverOrderShopItemServiceImpl deliverOrderShopItemService;

	@Autowired
	private ShopItemServiceImpl shopItemService;
	@Resource
	private MbShopServiceI mbShopService;

	@Resource
	private MbContractServiceI mbContractService;
	@Resource
	private MbContractItemServiceI mbContractItemService;
	@Resource
	private ShopDeliverApplyServiceI shopDeliverApplyService;

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
			if (shopItem.getItemIds() != null) {
				whereHql += " and t.itemId in (:itemIds)";
				params.put("itemIds", shopItem.getItemIds());
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
			if (!F.empty(shopItem.getStatus())) {
				whereHql += " and t.status = :status";
				params.put("status", shopItem.getStatus());
			}
		}
		return whereHql;
	}

	@Override
	public void add(ShopItem shopItem) {
		Map<String, Object> params = new HashMap<String, Object>();
		ShopItem oldShopItem = new ShopItem();
		oldShopItem.setShopId(shopItem.getShopId());
		oldShopItem.setItemId(shopItem.getItemId());
		oldShopItem.setIsdeleted(false);
		String where = whereHql(oldShopItem, params);
		TshopItem t = shopItemDao.get("from TshopItem t " + where, params);
		if (t == null) {
			t = new TshopItem();
			BeanUtils.copyProperties(shopItem, t);
			t.setIsdeleted(false);
			if (shopItem.getOnline() == null) t.setOnline(false);
			shopItemDao.save(t);
		}
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
			MyBeanUtils.copyProperties(shopItem, t, new String[]{"id", "addtime", "isdeleted", "updatetime"}, true);
		}
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		shopItemDao.executeHql("update TshopItem t set t.isdeleted = 1 where t.id = :id", params);
	}

	@Override
	public ShopItem getByShopIdAndItemId(Integer shopId, Integer itemId, boolean isOnline) {
		return getByShopIdAndItemId(shopId, itemId, isOnline, null);
	}

	@Override
	public ShopItem getByShopIdAndItemId(Integer shopId, Integer itemId, boolean isOnline, String status) {
		Map<String, Object> params = new HashMap<String, Object>();
		ShopItem shopItem = new ShopItem();
		shopItem.setShopId(shopId);
		shopItem.setItemId(itemId);
		shopItem.setOnline(isOnline);
		shopItem.setStatus(status);
		shopItem.setIsdeleted(false);
		String where = whereHql(shopItem, params);
		TshopItem t = shopItemDao.get("from TshopItem t " + where, params);
		ShopItem o = new ShopItem();
		if (t != null) {
			BeanUtils.copyProperties(t, o);
		} else {
			o = null;
		}
		return o;
	}

	@Override
	public ShopItem getByShopIdAndItemId(Integer shopId, Integer itemId) {
		ShopItem shopItem = new ShopItem();
		shopItem = getByShopIdAndItemId(shopId, itemId, true);
		if (shopItem != null) return shopItem;
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
			String[] itemIdArray = itemIds.split(",");
			Integer[] itemIdValues = new Integer[itemIdArray.length];
			int j;
			for (j = 0; j < itemIdValues.length; j++) {
				itemIdValues[j] = Integer.parseInt(itemIdArray[j]);
			}
			ShopItem shopItem = new ShopItem();
			shopItem.setIsdeleted(false);
			shopItem.setStatus("SIS02");
			shopItem.setShopId(shopId);
			shopItem.setItemIds(itemIdValues);
			List<ShopItem> shopItemList = query(shopItem);
			//上架的商品门店包含时
			if (CollectionUtils.isNotEmpty(shopItemList)) {
				Map<Integer, ShopItem> itemMap = new HashMap<Integer, ShopItem>();
				for (ShopItem item : shopItemList) {
					if (!itemMap.containsKey(item.getItemId())) {
						itemMap.put(item.getItemId(), item);
					}
				}
				for (int i = 0; i < itemIdArray.length; i++) {
					int itemId = Integer.parseInt(itemIdArray[i]);
					if (itemMap.containsKey(itemId)) {
						ShopItem item = itemMap.get(itemId);
						if (item.getOnline())
							continue;
						else {
							item.setOnline(true);
							edit(item);
						}
					} else {
						ShopItem shop = new ShopItem();
						shop.setItemId(Integer.parseInt(itemIdArray[i]));
						shop.setShopId(shopId);
						add(shop);
					}
				}
			} else {
				for (Integer itemId : itemIdValues) {
					ShopItem shop = new ShopItem();
					shop.setItemId(itemId);
					shop.setShopId(shopId);
					add(shop);
				}
			}
		}
	}


	@Override
	public void updateItemOnline(Integer itemId, Integer shopId) {
		ShopItem shopItem = getByShopIdAndItemId(shopId, itemId);
		if (shopItem != null&&shopItem.getStatus().equals("SIS02")) {
			if (!shopItem.getOnline()) {
				shopItem.setOnline(true);
				edit(shopItem);
			}
		} else {
			ShopItem item = new ShopItem();
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
                if(shopItem.getStatus().equals("SIS02")) {
                    shopItem.setOnline(false);
                    edit(shopItem);
                }
			}
		}
	}

	@Override
	public void updateShopItemOffline(Integer itemId, Integer shopId) {
		ShopItem shopItem = getByShopIdAndItemId(shopId, itemId, true);
		if (shopItem != null&&shopItem.getStatus().equals("SIS02")) {
			shopItem.setOnline(false);
			edit(shopItem);
		}
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
	public void updateShopItemQuantity(Integer itemId, Integer shopId, Integer quantity) {
		ShopItem shopItem = getByShopIdAndItemId(shopId, itemId);
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
				if (F.empty(shop.getQuantity())) {
					shopItemQuery.setQuantity(0);
				}
				MbShop mbShop = mbShopService.getFromCache(shop.getShopId());
				shopItemQuery.setShopName(mbShop.getName());
				shopItemQuery.setStatusName(shop.getStatus());
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
			Integer[] itemIds = new Integer[mbItems.size()];
			int index = 0;
			for (MbItem item : mbItems) {
				itemIds[index++] = item.getId();
			}
			ShopItem shopItem = new ShopItem();
//			shopItem.setStatus("SIS02");
			shopItem.setShopId(shopId);
			shopItem.setItemIds(itemIds);
			List<ShopItem> shopItems = query(shopItem);
			Map<Integer, ShopItem> quantityMap = new HashMap<Integer, ShopItem>();
			if (CollectionUtils.isNotEmpty(shopItems)) {
				for (ShopItem item : shopItems) {
					if (!quantityMap.containsKey(item.getItemId())) {
						quantityMap.put(item.getItemId(), item);
					}
				}
			}

			for (MbItem item : mbItems) {
				shopItem = quantityMap.get(item.getId());
				if (shopItem != null) {
					item.setOnline(shopItem.getOnline());
					item.setStatus(shopItem.getStatus());
					if (shopItem.getOnline() && "SIS02".equals(shopItem.getStatus())) {
						item.setQuantity(shopItem.getQuantity());
					} else {
						item.setQuantity(0);
					}
				} else {
					item.setQuantity(0);
				}
			}
		}
		return dataGrid;
	}

	@Override
	public void deleteShopItem(Integer itemId, Integer shopId) {
		ShopItem shopItem = getByShopIdAndItemId(shopId, itemId);
		delete(shopItem.getId());
	}

	@Override
	public void updateForRefundByDeliverOrder(DeliverOrder deliverOrder) {
		if (!F.empty(deliverOrder.getId()) && !F.empty(deliverOrder.getShopId())) {
			//通过deliverOrder获取deliverOrderShopItem
			DeliverOrderShopItem deliverOrderShopItem = new DeliverOrderShopItem();
			deliverOrderShopItem.setDeliverOrderId(deliverOrder.getId());
			deliverOrderShopItem.setShopId(deliverOrder.getShopId());
			List<DeliverOrderShopItem> items = deliverOrderShopItemService.list(deliverOrderShopItem);
			//遍历deliverOrderShopItem找到对应的shopItem
			for (DeliverOrderShopItem orderShopItem : items) {
				ShopItem shopItem = new ShopItem();
				shopItem.setShopId(deliverOrder.getShopId());
				shopItem.setItemId(orderShopItem.getItemId());
				List<ShopItem> shopItems = shopItemService.query(shopItem);
				//通过shopItem修改对应的数量
				if (CollectionUtils.isNotEmpty(shopItems)) {
					ShopItem sItem = shopItems.get(0);
					ShopItem si = new ShopItem();
					si.setId(sItem.getId());
					si.setQuantity(orderShopItem.getQuantity());
					updateQuantity(si);
				}
			}
		}
	}

	@Override
	public void updateQuantity(ShopItem shopItem) {
		if (!F.empty(shopItem.getId()) && !F.empty(shopItem.getQuantity())) {
			int i = shopItemDao.executeHql("update TshopItem t set t.quantity=quantity+" + shopItem.getQuantity() + " where t.id=" + shopItem.getId());
			if (i != 1) {
				throw new ServiceException("门店库存更新失败");
			}
		} else {
			throw new ServiceException("门店ID或数量不能为空");
		}
	}

	@Override
	public void updateBatchFright(String shopItemList, Integer freight) {
		JSONArray json = JSONArray.fromObject(shopItemList);
		if (!F.empty(shopItemList)) {
			//把json字符串转换成对象
			List<ShopItem> itemList = (List<ShopItem>) JSONArray.toCollection(json, ShopItem.class);
			for (ShopItem shopItem : itemList) {
				shopItem.setFreight(freight);
				shopItem.setPrice(shopItem.getInPrice()+freight);
				edit(shopItem);
			}
		}
	}

	@Override
	public void editBatchAuditState(String shopItemList, String status,String reviewerId) {
		JSONArray json = JSONArray.fromObject(shopItemList);
		if (!F.empty(shopItemList)) {
			//把json字符串转换成对象
			List<ShopItem> itemList = (List<ShopItem>) JSONArray.toCollection(json, ShopItem.class);
			for (ShopItem shopItem : itemList) {
				if (shopItem.getOnline() != true) {
					if ("SIS02".equals(status)) {
						shopItem.setOnline(true);
						shopItem.setReviewerId(reviewerId);
						shopItemService.edit(shopItem);
					} else if ("SIS03".equals(status)) {
						shopItemService.delete(shopItem.getId());
					}
					shopItem.setStatus(status);
					edit(shopItem);
				}
			}
		}
	}

	@Override
	public void addShopItemAllocation() {
		/*
		 * 依赖于一个门店只允许出现一份有效合同,
		 * 1、查询门店商品中的门店（s）
		 * 2、通过存在门店商品的门店查询合同(s)，
		 * 3、通过合同查询合同商品,
		 * Map<Integer, List<Integer>> shopItemMap = new HashMap<Integer, List<Integer>>() ，key为shopId,value为门店所对应的所有商品
		 * Map<Integer, List<MbContractItem>> mbContractItemMap = new HashMap<Integer, List<MbContractItem>>(),key为contractId,value为门店所对应的所有商品
		 * 4、遍历查询出来的合同，通过contractId取List<MbContractItem>作为mbContractMap的value，对应的shopId作为mbContractMap的键
		 * Map<Integer, List<MbContractItem>> mbContractMap = new HashMap<Integer, List<MbContractItem>>()，key为shopId，value为门店所对应的所有商品
		 */
		ShopItem shopItemQuery = new ShopItem();
		List<ShopItem> shopItemList = query(shopItemQuery);
		if (CollectionUtils.isNotEmpty(shopItemList)) {
			Map<Integer, List<Integer>> shopItemMap = new HashMap<Integer, List<Integer>>();
			for (ShopItem shopItem : shopItemList) {
				List<Integer> list = shopItemMap.get(shopItem.getShopId());
				if (list == null) {
					list = new ArrayList<Integer>();
					list.add(shopItem.getItemId());
					shopItemMap.put(shopItem.getShopId(), list);
				} else {
					list.add(shopItem.getItemId());
					shopItemMap.put(shopItem.getShopId(), list);
				}
			}
			Integer[] shopIds = new Integer[shopItemMap.size()];
			Integer i = 0;
			for (Integer key : shopItemMap.keySet()) {
				shopIds[i++] = key;
			}
			//根据门店id获取已签订合同的商品信息
			MbContract mbContract = new MbContract();
			mbContract.setValid(true);
			mbContract.setShopIds(shopIds);
			List<MbContract> mbContractList = mbContractService.query(mbContract);
			if (CollectionUtils.isNotEmpty(mbContractList)) {
				Integer[] contractIds = new Integer[mbContractList.size()];
				int j = 0;
				for (MbContract contract : mbContractList) {
					contractIds[j++] = contract.getId();
				}
				MbContractItem mbContractItem = new MbContractItem();
				mbContractItem.setContractIds(contractIds);
				List<MbContractItem> mbContractItemList = mbContractItemService.query(mbContractItem);
				if (CollectionUtils.isNotEmpty(mbContractItemList)) {
					Map<Integer, List<MbContractItem>> mbContractItemMap = new HashMap<Integer, List<MbContractItem>>();
					for (MbContractItem contractItem : mbContractItemList) {
						List<MbContractItem> list = mbContractItemMap.get(contractItem.getContractId());
						if (list == null) {
							list = new ArrayList<MbContractItem>();
							list.add(contractItem);
							mbContractItemMap.put(contractItem.getContractId(), list);
						} else {
							list.add(contractItem);
							mbContractItemMap.put(contractItem.getContractId(), list);
						}
					}
					Map<Integer, List<MbContractItem>> mbContractMap = new HashMap<Integer, List<MbContractItem>>();
					for (MbContract contract : mbContractList) {
						mbContractMap.put(contract.getShopId(), mbContractItemMap.get(contract.getId()));
					}
					//获取门店默认运费
					ShopDeliverApply shopDeliverApply = new ShopDeliverApply();
					shopDeliverApply.setShopIds(shopIds);
					List<ShopDeliverApply> shopDeliverApplyList = shopDeliverApplyService.query(shopDeliverApply);
					Map<Integer, Integer> shopDeliverApplyMap = new HashMap<Integer, Integer>();
					if (CollectionUtils.isNotEmpty(shopDeliverApplyList)) {
						for (ShopDeliverApply deliverApply : shopDeliverApplyList) {
							shopDeliverApplyMap.put(deliverApply.getShopId(), deliverApply.getFreight());
						}
					}
					//若门店已签订的商品，门店商品中不存在，则进行新增
					for (Integer key : shopItemMap.keySet()) {
						List<Integer> itemIds = shopItemMap.get(key);
						if (mbContractMap.get(key) != null) {
							Integer freight = shopDeliverApplyMap.get(key) == null ? INT : shopDeliverApplyMap.get(key);
							for (MbContractItem contractItem : mbContractMap.get(key)) {
								if (!itemIds.contains(contractItem.getItemId())) {
									ShopItem shopItem = new ShopItem();
									shopItem.setShopId(key);
									shopItem.setItemId(contractItem.getItemId());
									shopItem.setFreight(freight);
									shopItem.setInPrice(contractItem.getPrice());
									shopItem.setPrice(shopItem.getInPrice());
									add(shopItem);
								}
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void addShoItemFromContract(Integer shopId) {
		ShopItem shopItem = new ShopItem();
		shopItem.setShopId(shopId);
		List<ShopItem> shopItemList = query(shopItem);
		if (CollectionUtils.isNotEmpty(shopItemList)) {
			List<Integer> itemIds = new ArrayList<Integer>();
			for (ShopItem item : shopItemList) {
				itemIds.add(item.getItemId());
			}
			MbContract mbContract = new MbContract();
			mbContract.setShopId(shopId);
			List<MbContract> mbContractList = mbContractService.query(mbContract);
			if (CollectionUtils.isNotEmpty(mbContractList)) {
				MbContract contract = mbContractList.get(0);
				MbContractItem mbContractItem = new MbContractItem();
				mbContractItem.setContractId(contract.getId());
				List<MbContractItem> mbContractItemList = mbContractItemService.query(mbContractItem);
				if (CollectionUtils.isNotEmpty(mbContractItemList)) {
					ShopDeliverApply shopDeliverApply = new ShopDeliverApply();
					shopDeliverApply.setShopId(shopId);
					List<ShopDeliverApply> shopDeliverApplyList = shopDeliverApplyService.query(shopDeliverApply);
					Integer freight = INT;
					if (CollectionUtils.isNotEmpty(shopDeliverApplyList)) {
						freight = shopDeliverApplyList.get(0).getFreight();
					}
					for (MbContractItem contractItem : mbContractItemList) {
						if (!itemIds.contains(contractItem.getItemId())) {
							ShopItem shopItemNew = new ShopItem();
							shopItemNew.setShopId(shopId);
							shopItemNew.setFreight(freight);
							shopItemNew.setItemId(contractItem.getItemId());
							shopItemNew.setInPrice(contractItem.getPrice());
							shopItemNew.setPrice(shopItemNew.getInPrice());
							add(shopItemNew);
						}
					}
				}
			}
		}
	}
}
