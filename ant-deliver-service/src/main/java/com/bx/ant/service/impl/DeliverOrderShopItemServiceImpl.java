package com.bx.ant.service.impl;

import com.bx.ant.dao.DeliverOrderShopItemDaoI;
import com.bx.ant.model.TdeliverOrderShopItem;
import com.bx.ant.pageModel.*;
import com.bx.ant.service.*;
import com.mobian.absx.F;
import com.mobian.exception.ServiceException;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbItem;
import com.mobian.pageModel.PageHelper;
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
public class DeliverOrderShopItemServiceImpl extends BaseServiceImpl<DeliverOrderShopItem> implements DeliverOrderShopItemServiceI {

	@Autowired
	private DeliverOrderShopItemDaoI deliverOrderShopItemDao;

	@Autowired
	private ShopItemServiceI shopItemService;

	@javax.annotation.Resource
	private MbItemServiceI mbItemService;

	@Autowired
	private DeliverOrderShopServiceI  deliverOrderShopService;

	@Autowired
	private DeliverOrderShopItemServiceImpl deliverOrderShopItemService;

	@Autowired
	private DeliverOrderShopPayServiceI deliverOrderShopPayService;

	@Override
	public DataGrid dataGrid(DeliverOrderShopItem deliverOrderShopItem, PageHelper ph) {
		List<DeliverOrderShopItem> ol = new ArrayList<DeliverOrderShopItem>();
		String hql = " from TdeliverOrderShopItem t ";
		DataGrid dg = dataGridQuery(hql, ph, deliverOrderShopItem, deliverOrderShopItemDao);
		@SuppressWarnings("unchecked")
		List<TdeliverOrderShopItem> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TdeliverOrderShopItem t : l) {
				DeliverOrderShopItem o = new DeliverOrderShopItem();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(DeliverOrderShopItem deliverOrderShopItem, Map<String, Object> params) {
		String whereHql = "";	
		if (deliverOrderShopItem != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(deliverOrderShopItem.getTenantId())) {
				whereHql += " and t.tenantId = :tenantId";
				params.put("tenantId", deliverOrderShopItem.getTenantId());
			}		
			if (!F.empty(deliverOrderShopItem.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", deliverOrderShopItem.getIsdeleted());
			}		
			if (!F.empty(deliverOrderShopItem.getDeliverOrderShopId())) {
				whereHql += " and t.deliverOrderShopId = :deliverOrderShopId";
				params.put("deliverOrderShopId", deliverOrderShopItem.getDeliverOrderShopId());
			}		
			if (!F.empty(deliverOrderShopItem.getDeliverOrderId())) {
				whereHql += " and t.deliverOrderId = :deliverOrderId";
				params.put("deliverOrderId", deliverOrderShopItem.getDeliverOrderId());
			}		
			if (!F.empty(deliverOrderShopItem.getShopId())) {
				whereHql += " and t.shopId = :shopId";
				params.put("shopId", deliverOrderShopItem.getShopId());
			}		
			if (!F.empty(deliverOrderShopItem.getItemId())) {
				whereHql += " and t.itemId = :itemId";
				params.put("itemId", deliverOrderShopItem.getItemId());
			}		
			if (!F.empty(deliverOrderShopItem.getPrice())) {
				whereHql += " and t.price = :price";
				params.put("price", deliverOrderShopItem.getPrice());
			}		
			if (!F.empty(deliverOrderShopItem.getInPrice())) {
				whereHql += " and t.inPrice = :inPrice";
				params.put("inPrice", deliverOrderShopItem.getInPrice());
			}		
			if (!F.empty(deliverOrderShopItem.getFreight())) {
				whereHql += " and t.freight = :freight";
				params.put("freight", deliverOrderShopItem.getFreight());
			}
			if (!F.empty(deliverOrderShopItem.getDeliverOrderShopIds())) {
				Long[] orderShopIds = new Long[deliverOrderShopItem.getDeliverOrderShopIds().split(",").length];
				int i = 0;
				for (String orderShopId : deliverOrderShopItem.getDeliverOrderShopIds().split(",")) {
					orderShopIds[i++] = Long.valueOf(orderShopId).longValue();
				}
				whereHql += " and t.deliverOrderShopId in (:deliverOrderShopIds)";
				params.put("deliverOrderShopIds", orderShopIds);
			}

			if (!F.empty(deliverOrderShopItem.getDeliverOrderIds())) {
				Long[] deliverOrderIds = new Long[deliverOrderShopItem.getDeliverOrderIds().length()];
				int i = 0;
				for (String deliverOrder : deliverOrderShopItem.getDeliverOrderIds().split(",")) {
					deliverOrderIds[i++] = Long.parseLong(deliverOrder);
				}
				whereHql += " and t.deliverOrderId in(:deliverOrderIds)";
				params.put("deliverOrderIds", deliverOrderIds);
			}
		}
		return whereHql;
	}

	@Override
	public void add(DeliverOrderShopItem deliverOrderShopItem) {
		TdeliverOrderShopItem t = new TdeliverOrderShopItem();
		BeanUtils.copyProperties(deliverOrderShopItem, t);
		//t.setId(jb.absx.UUID.uuid());
		if(F.empty(deliverOrderShopItem.getIsdeleted())) t.setIsdeleted(false);
		deliverOrderShopItemDao.save(t);
	}

	@Override
	public DeliverOrderShopItem get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TdeliverOrderShopItem t = deliverOrderShopItemDao.get("from TdeliverOrderShopItem t  where t.id = :id", params);
		DeliverOrderShopItem o = new DeliverOrderShopItem();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(DeliverOrderShopItem deliverOrderShopItem) {
		TdeliverOrderShopItem t = deliverOrderShopItemDao.get(TdeliverOrderShopItem.class, deliverOrderShopItem.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(deliverOrderShopItem, t, new String[] { "id" , "addtime", "isdeleted","updatetime" },true);
		}
	}

	@Override
	public void delete(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		deliverOrderShopItemDao.executeHql("update TdeliverOrderShopItem t set t.isdeleted = 1 where t.id = :id",params);
		//deliverOrderShopItemDao.delete(deliverOrderShopItemDao.get(TdeliverOrderShopItem.class, id));
	}

	@Override
	public void addByDeliverOrderItemList(List<DeliverOrderItem> deliverOrderItems, DeliverOrderShop deliverOrderShop) {
		if (F.empty(deliverOrderShop.getId()) || F.empty(deliverOrderShop.getShopId()))
			throw new ServiceException("数据传递不完整");
		if (CollectionUtils.isNotEmpty(deliverOrderItems)) {
			int amount = 0;
			Long deliverOrderId = null;
			for (DeliverOrderItem d : deliverOrderItems) {
				//记录deliverOrderId
				deliverOrderId = d.getDeliverOrderId();

				ShopItem shopItem = shopItemService.getByShopIdAndItemId(deliverOrderShop.getShopId(), d.getItemId(), true, "SIS02");
				// TODO 代送处理
				if(ShopDeliverApplyServiceI.DELIVER_WAY_AGENT.equals(deliverOrderShop.getDeliveryType())|| ShopDeliverApplyServiceI.DELIVER_WAY_CUSTOMER_AGENT.equals(deliverOrderShop.getDeliveryType())) {
					if(shopItem == null) {
						shopItem = new ShopItem();
						shopItem.setShopId(deliverOrderShop.getShopId());
						shopItem.setItemId(d.getItemId());
						shopItem.setPrice(deliverOrderShop.getFreight());
						shopItem.setInPrice(0);
						shopItem.setFreight(deliverOrderShop.getFreight());
						shopItem.setOnline(true);
						shopItem.setStatus("SIS02");
						shopItemService.add(shopItem);
					}
				} else {
					if (shopItem == null) throw new ServiceException("无法找到门店对应商品");
					if (!DeliverOrderServiceI.DELIVER_TYPE_FORCE.equals(deliverOrderShop.getDeliveryType())
							&& (F.empty(shopItem.getQuantity()) || d.getQuantity() > shopItem.getQuantity()))
						throw new ServiceException("门店对应商品库存不足");

					//扣除库存
					ShopItem shopItemN = new ShopItem();
					shopItemN.setId(shopItem.getId());
					shopItemN.setQuantity( - d.getQuantity());
					shopItemService.updateQuantity(shopItemN);
				}

				//添加deliverOrderShopItem
				DeliverOrderShopItem deliverOrderShopItem = new DeliverOrderShopItem();
				deliverOrderShopItem.setDeliverOrderId(d.getDeliverOrderId());
				deliverOrderShopItem.setDeliverOrderShopId(deliverOrderShop.getId());
				deliverOrderShopItem.setFreight(shopItem.getFreight());
				deliverOrderShopItem.setPrice(shopItem.getPrice() == null ? 0 : shopItem.getPrice());
				deliverOrderShopItem.setInPrice(shopItem.getInPrice() == null ? 0 : shopItem.getInPrice());
				deliverOrderShopItem.setShopId(deliverOrderShop.getShopId());
				deliverOrderShopItem.setItemId(d.getItemId());
				deliverOrderShopItem.setQuantity(d.getQuantity() == null ? 0 : d.getQuantity());
				add(deliverOrderShopItem);

				//计算总金额
				//TODO 用户自提未作处理,现在只是统一到门店自己配送
				amount += deliverOrderShopItem.getPrice() * deliverOrderShopItem.getQuantity();

			}

			//编辑门店订单金额
			deliverOrderShop.setAmount(amount);
			deliverOrderShopService.edit(deliverOrderShop);

//			//添加deliverOrderShopPay
//			DeliverOrderShopPay deliverOrderShopPay = new DeliverOrderShopPay();
//			deliverOrderShopPay.setDeliverOrderId(deliverOrderId);
//			deliverOrderShopPay.setDeliverOrderShopId(deliverOrderShop.getId());
//			deliverOrderShopPay.setShopId(deliverOrderShop.getShopId());
//			deliverOrderShopPay.setStatus(DeliverOrderServiceI.SHOP_PAY_STATUS_NOT_PAY);
//			deliverOrderShopPay.setAmount(amount);
//			deliverOrderShopPayService.add(deliverOrderShopPay);

		}
	}

	@Override
	public List<DeliverOrderShopItem> list(DeliverOrderShopItem deliverOrderShopItem) {
		List<DeliverOrderShopItem> ol = new ArrayList<DeliverOrderShopItem>();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from TdeliverOrderShopItem t ";
		String where = whereHql(deliverOrderShopItem, params);
		List<TdeliverOrderShopItem> l = deliverOrderShopItemDao.find(hql + where, params);
		if (CollectionUtils.isNotEmpty(l)) {
			for (TdeliverOrderShopItem t : l) {
				DeliverOrderShopItemExt o = new DeliverOrderShopItemExt();
				BeanUtils.copyProperties(t, o);
				fillInfo(o);
				ol.add(o);
			}
		}
		return ol;
	}

	protected void fillInfo(DeliverOrderShopItemExt deliverOrderShopItemExt) {
		fillItemInfo(deliverOrderShopItemExt);
	}

	protected void fillItemInfo(DeliverOrderShopItemExt deliverOrderShopItemExt) {
		if (!F.empty(deliverOrderShopItemExt.getItemId())) {
			MbItem item = mbItemService.getFromCache(deliverOrderShopItemExt.getItemId());
			if (item != null) {
				deliverOrderShopItemExt.setItemName(item.getName());
				deliverOrderShopItemExt.setItemCode(item.getCode());
				deliverOrderShopItemExt.setPictureUrl(item.getUrl());
				deliverOrderShopItemExt.setQuantityUnitName(item.getQuantityUnitName());
			}
		}
	}

	@Override
	public DataGrid dataGridView(DeliverOrderShopItem deliverOrderShopItem, PageHelper ph) {
		DeliverOrderShop deliverOrderShop =deliverOrderShopService.getDeliverOrderShop(deliverOrderShopItem.getDeliverOrderId(),"DSS02,DSS04,DSS06");
		DataGrid dg = new DataGrid();
		if (deliverOrderShop!=null) {
			deliverOrderShopItem.setDeliverOrderShopId(deliverOrderShop.getId());
			DataGrid dataGrid = dataGrid(deliverOrderShopItem, ph);
			List<DeliverOrderShopItem> deliverOrderShopItems = dataGrid.getRows();
			if (CollectionUtils.isNotEmpty(deliverOrderShopItems)) {
				List<DeliverOrderShopItemExt> deliverOrderShopItemExts = new ArrayList<DeliverOrderShopItemExt>();
				for (DeliverOrderShopItem orderShopItem : deliverOrderShopItems) {
					DeliverOrderShopItemExt deliverOrderShopItemExt = new DeliverOrderShopItemExt();
					BeanUtils.copyProperties(orderShopItem, deliverOrderShopItemExt);
					MbItem mbItem = mbItemService.getFromCache(orderShopItem.getItemId());
					deliverOrderShopItemExt.setItemCode(mbItem.getCode());
					deliverOrderShopItemExt.setItemName(mbItem.getName());
					deliverOrderShopItemExts.add(deliverOrderShopItemExt);
				}
				dg.setRows(deliverOrderShopItemExts);
				dg.setTotal(dataGrid.getTotal());
				return dg;
			}
		}
		return dg;
	}

	@Override
	public Map<Integer, DeliverOrderShopItem> queryOrderShopItem(String deliverOrderShopIds) {
		DeliverOrderShopItem deliverOrderShopItem = new DeliverOrderShopItem();
		deliverOrderShopItem.setDeliverOrderShopIds(deliverOrderShopIds);
		List<DeliverOrderShopItem> deliverOrderShopItems = list(deliverOrderShopItem);
		if (CollectionUtils.isNotEmpty(deliverOrderShopItems)) {
			Map<Integer, DeliverOrderShopItem> deliverOrderShopItemMap = new HashMap<Integer, DeliverOrderShopItem>();
			for (DeliverOrderShopItem orderShopItem : deliverOrderShopItems) {
				if (deliverOrderShopItemMap.get(orderShopItem.getItemId()) == null) {
					deliverOrderShopItemMap.put(orderShopItem.getItemId(), orderShopItem);
				} else {
					DeliverOrderShopItem shopItem = deliverOrderShopItemMap.get(orderShopItem.getItemId());
					shopItem.setQuantity(shopItem.getQuantity() + orderShopItem.getQuantity());
					deliverOrderShopItemMap.put(orderShopItem.getItemId(), shopItem);
				}
			}
			return deliverOrderShopItemMap;
		}
		return null;
	}

	@Override
	public DataGrid dataGridByDeliverOrderIds(String deliverOrderIds) {
        DeliverOrderShopItem deliverOrderShopItem = new DeliverOrderShopItem();
        deliverOrderShopItem.setDeliverOrderIds(deliverOrderIds);
        List<DeliverOrderShopItem> deliverOrderShopItems = list(deliverOrderShopItem);
        DataGrid dg = new DataGrid();
        if (CollectionUtils.isNotEmpty(deliverOrderShopItems)) {
            List<DeliverOrderShopItemExt> deliverOrderShopItemExts = new ArrayList<DeliverOrderShopItemExt>();
            Map<Integer,Integer> map=new HashMap<Integer, Integer>();
            for (DeliverOrderShopItem orderShopItem : deliverOrderShopItems) {
            	Integer key=orderShopItem.getItemId();
            	Integer itemIdValue=map.get(orderShopItem.getItemId());
				if (itemIdValue == null) {
					map.put(key, orderShopItem.getQuantity());
					DeliverOrderShopItemExt deliverOrderShopItemExt = new DeliverOrderShopItemExt();
					BeanUtils.copyProperties(orderShopItem, deliverOrderShopItemExt);
					MbItem mbItem = mbItemService.getFromCache(orderShopItem.getItemId());
					deliverOrderShopItemExt.setItemCode(mbItem.getCode());
					deliverOrderShopItemExt.setItemName(mbItem.getName() + "(规格：" + mbItem.getQuantityUnitName()+")");
					deliverOrderShopItemExts.add(deliverOrderShopItemExt);
				} else {
					map.put(key, itemIdValue += orderShopItem.getQuantity());
				}
			}
			for (DeliverOrderShopItemExt orderShopItemExt : deliverOrderShopItemExts) {
				orderShopItemExt.setQuantity(map.get(orderShopItemExt.getItemId()));
			}
            dg.setRows(deliverOrderShopItemExts);
            return dg;
        }
        return dg;
    }

}
