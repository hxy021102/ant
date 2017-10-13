package com.mobian.service.impl;

import com.mobian.absx.F;
import com.mobian.dao.MbItemStockDaoI;
import com.mobian.exception.ServiceException;
import com.mobian.model.TmbItemStock;
import com.mobian.pageModel.*;
import com.mobian.service.*;
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
public class MbItemStockServiceImpl extends BaseServiceImpl<MbItemStock> implements MbItemStockServiceI {

	@Autowired
	private MbItemStockDaoI mbItemStockDao;

	@Autowired
	private MbItemStockLogServiceI mbItemStockLogService;

	@Autowired
	private MbWarehouseServiceI mbWarehouseService;

	@Autowired
	private MbItemServiceI mbItemService;
	@Autowired
	private RedisUserServiceImpl redisUserService;
	@Autowired
	private MbBalanceServiceI mbBalanceService;
	@Autowired
	private MbBalanceLogServiceI mbBalanceLogService;

	@Override
	public DataGrid dataGrid(MbItemStock mbItemStock, PageHelper ph) {
		List<MbItemStock> ol = new ArrayList<MbItemStock>();
		String hql = " from TmbItemStock t ";
		DataGrid dg = dataGridQuery(hql, ph, mbItemStock, mbItemStockDao);
		@SuppressWarnings("unchecked")
		List<TmbItemStock> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TmbItemStock t : l) {
				MbItemStock o = new MbItemStock();
				BeanUtils.copyProperties(t, o);

				addWarehouseAndItemInfo(o);

				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}

	public DataGrid dataGridWithOrderSum(MbItemStock mbItemStock, PageHelper ph) {
		DataGrid dataGrid = dataGrid(mbItemStock, ph);
		List<MbItemStock> stocksList = dataGrid.getRows();
		Integer orderQuantity;
		for (MbItemStock stock : stocksList) {
			if (stock.getWarehouseId() == null) continue;
			MbWarehouse tmbWarehouse = mbWarehouseService.getFromCache(stock.getWarehouseId());
			if (tmbWarehouse != null && "WT03".equals(tmbWarehouse.getWarehouseType())) {
				orderQuantity = redisUserService.getOrderQuantity(stock.getWarehouseId() + ":" + stock.getItemId());
				stock.setOrderQuantity(orderQuantity);
				stock.setOd15Quantity(redisUserService.getOrderQuantity(stock.getWarehouseId() + ":" + stock.getItemId() + ":OD15"));
				stock.setWarehouseType("WT03");
			}
			MbItem mbItem = mbItemService.getFromCache(stock.getItemId());
			if (mbItem != null) {
				stock.setItemCode(mbItem.getCode());
			}
		}
		dataGrid.setRows(stocksList);
		return dataGrid;
	}

	@Override
	public void addWarehouseAndItemInfo(MbItemStock mbItemStock) {
		if (mbItemStock.getWarehouseId() != null) {
			MbWarehouse mbWarehouse = mbWarehouseService.getFromCache(mbItemStock.getWarehouseId());
			if (mbWarehouse != null) {
				mbItemStock.setWarehouseCode(mbWarehouse.getCode());
				mbItemStock.setWarehouseName(mbWarehouse.getName());
			}
		}

		if (mbItemStock.getItemId() != null) {
			MbItem mbItem = mbItemService.getFromCache(mbItemStock.getItemId());
			if (mbItem != null) {
				mbItemStock.setItemName(mbItem.getName());
			}
		}
	}


	protected String whereHql(MbItemStock mbItemStock, Map<String, Object> params) {
		String whereHql = "";	
		if (mbItemStock != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(mbItemStock.getTenantId())) {
				whereHql += " and t.tenantId = :tenantId";
				params.put("tenantId", mbItemStock.getTenantId());
			}
			if (!F.empty(mbItemStock.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", mbItemStock.getIsdeleted());
			}		
			if (!F.empty(mbItemStock.getItemId())) {
				whereHql += " and t.itemId = :itemId";
				params.put("itemId", mbItemStock.getItemId());
			}		
			if (!F.empty(mbItemStock.getWarehouseId())) {
				whereHql += " and t.warehouseId = :warehouseId";
				params.put("warehouseId", mbItemStock.getWarehouseId());
			}		
			if (!F.empty(mbItemStock.getQuantity())) {
				whereHql += " and t.quantity = :quantity";
				params.put("quantity", mbItemStock.getQuantity());
			}
			if (!F.empty(mbItemStock.getItemIds())) {
				String[] itemIdsStr = mbItemStock.getItemIds().split(",");
				Integer[] itemIds = new Integer[itemIdsStr.length];
				for (int i = 0; i < itemIds.length; i++) {
					itemIds[i] = Integer.parseInt(itemIdsStr[i]);
				}
				whereHql += " and t.itemId in (:itemIds)  ";
				params.put("itemIds", itemIds);
			}
			if (mbItemStock.getItemIdNumbers() != null) {
				whereHql += " and t.itemId in (:itemIds)  ";
				params.put("itemIds", mbItemStock.getItemIdNumbers());
			}
			if (!F.empty(mbItemStock.getSafe())) {
				if(!mbItemStock.getSafe()) {
					whereHql += " and t.quantity < t.safeQuantity ";
				}else {
					whereHql += " and t.quantity >= t.safeQuantity ";
				}
			}
		}
		return whereHql;
	}

	@Override
	public void add(MbItemStock mbItemStock) {
		addAndReturnId(mbItemStock);
	}

	@Override
	public Integer addAndReturnId(MbItemStock mbItemStock) {
		TmbItemStock t = new TmbItemStock();
		BeanUtils.copyProperties(mbItemStock, t);
		t.setIsdeleted(false);
		return (Integer) mbItemStockDao.save(t);
	}

	@Override
	public void addAndInsertLog(MbItemStock mbItemStock, String loginId) {
		Integer mbItemStockId = addAndReturnId(mbItemStock);
		insertLog(mbItemStockId, mbItemStock.getQuantity(), loginId, mbItemStock.getLogType(), mbItemStock.getReason());
	}

	private int insertLog(Integer mbItemStockId, Integer quantity, String loginId,String logType,String reason) {
		MbItemStockLog mbItemStockLog = new MbItemStockLog();
		mbItemStockLog.setItemStockId(mbItemStockId);
		mbItemStockLog.setQuantity(quantity);
		mbItemStockLog.setLoginId(loginId);
		mbItemStockLog.setLogType(logType);
		mbItemStockLog.setReason(reason);
		mbItemStockLogService.add(mbItemStockLog);
		return mbItemStockLog.getId();
	}

	@Override
	public MbItemStock get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TmbItemStock t = mbItemStockDao.get("from TmbItemStock t  where t.id = :id", params);
		MbItemStock o = new MbItemStock();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public MbItemStock getByWareHouseIdAndItemId(Integer wareHouseId, Integer itemId) {
		Map<String, Object> params = new HashMap<String, Object>();
		if(F.empty(wareHouseId)){
			throw new ServiceException("仓库ID是空的");
		}
		params.put("wareHouseId", wareHouseId);
		params.put("itemId", itemId);
		TmbItemStock t = mbItemStockDao.get("from TmbItemStock t  where t.warehouseId = :wareHouseId and t.itemId = :itemId and t.isdeleted = 0", params);
		MbItemStock o;
		if (t != null) {
			o = new MbItemStock();
			BeanUtils.copyProperties(t, o);
		}else{
			o = new MbItemStock();
			o.setWarehouseId(wareHouseId);
			o.setItemId(itemId);
			o.setQuantity(0);
			o.setAveragePrice(0);
			int id = addAndReturnId(o);
			o.setId(id);
		}
		return o;
	}

	@Override
	public void edit(MbItemStock mbItemStock) {
		TmbItemStock t = mbItemStockDao.get(TmbItemStock.class, mbItemStock.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(mbItemStock, t, new String[]{"id", "addtime", "isdeleted", "updatetime"}, true);
		}
	}

	@Override
	public Integer editAndInsertLog(MbItemStock mbItemStock, String loginId) {
		if (mbItemStock.getAdjustment() == null) {
			throw new ServiceException("调整量不能为null");
		} else if (mbItemStock.getAdjustment() == 0) {
			return null;
		}
		/**
		 * 库存修改存在并发场景，一定要考虑并发情况
		 */
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("adjustment", mbItemStock.getAdjustment());
		params.put("id", mbItemStock.getId());
		int i = mbItemStockDao.executeHql("update TmbItemStock t set t.quantity = t.quantity+:adjustment where t.id = :id", params);
		if (i > 0) {
			int logId = insertLog(mbItemStock.getId(), mbItemStock.getAdjustment(), loginId, mbItemStock.getLogType(), mbItemStock.getReason());
			return logId;
		}
		return null;
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		mbItemStockDao.executeHql("update TmbItemStock t set t.isdeleted = 1 where t.id = :id",params);
	}

	@Override
	public boolean isWarehouseItemPairExist(Integer warehouseId, Integer itemId) {
		Map<String, Object> params = new HashMap<>();
		params.put("warehouseId", warehouseId);
		params.put("itemId", itemId);
		List<TmbItemStock> warehouseItemPairList = mbItemStockDao.find("from TmbItemStock t where warehouseId = :warehouseId and itemId = :itemId", params);
		if (warehouseItemPairList.size() == 0) {
			return false;
		}
		return true;
	}
	public List<TmbItemStock> queryItemStockListByItemIdAndWarehouseIds(Integer itemId,Integer[] ids){
		Map<String, Object> idParams = new HashMap<String, Object>();
		idParams.put("itemId", itemId);
		idParams.put("warehouseIds", ids);
		List<TmbItemStock> tmbItemStockList = mbItemStockDao.find("from TmbItemStock t  where t.isdeleted = 0 and t.itemId = :itemId and t.warehouseId in (:warehouseIds)", idParams);
       return tmbItemStockList;
	}

	@Override
	public void addBatchAndUpdateItemStock(List<MbItemStock> mbItemStocks, String loginId) {
		for (MbItemStock mbItemStock : mbItemStocks) {
			MbItemStock stock = getByWareHouseIdAndItemId(mbItemStock.getWarehouseId(), mbItemStock.getItemId());
			if (stock != null) {
				int adjustment = mbItemStock.getQuantity() - stock.getQuantity();
				stock.setAdjustment(adjustment);
				stock.setLogType("SL04");
				stock.setReason("批量盘点");
				stock.setReason(String.format("批量盘点，库存：%s",stock.getQuantity()+adjustment));
				editAndInsertLog(stock,loginId);
			}
		}
	}

	@Override
	public boolean editItemStockAveragePrice(MbItemStock mbItemStock) {
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("averagePrice",mbItemStock.getAveragePrice());
		params.put("id", mbItemStock.getId());
		int result = mbItemStockDao.executeHql("update TmbItemStock t set t.averagePrice = :averagePrice where t.id = :id", params);
		return result>0;
	}

	@Override
	public DataGrid dataGridReport(MbItemStock mbItemStock, PageHelper ph) {
		if (!F.empty(mbItemStock.getIsPack())) {
			mbItemStock = getStockItemIdNumbersValue(mbItemStock);
			if (mbItemStock.getItemIdNumbers() == null)
				return new DataGrid();
		}
		DataGrid dataGrid = dataGrid(mbItemStock, ph);
		List<MbItemStock> mbItemStocks = dataGrid.getRows();
		if (!CollectionUtils.isEmpty(mbItemStocks)) {
			Integer totalQuantity = 0, sumPrice = 0;
			for (MbItemStock itemStock : mbItemStocks) {
				itemStock.setTotalPrice(itemStock.getQuantity() * itemStock.getAveragePrice());
				totalQuantity += itemStock.getQuantity();
			 	sumPrice += itemStock.getTotalPrice();
			}
			List<MbItemStock> footer = new ArrayList<MbItemStock>();
			MbItemStock totalRow = new MbItemStock();
			totalRow.setWarehouseCode("合计");
			totalRow.setQuantity(totalQuantity);
			totalRow.setTotalPrice(sumPrice);
			footer.add(totalRow);
			dataGrid.setFooter(footer);
			dataGrid.setRows(mbItemStocks);
		}
		return dataGrid;
	}

	@Override
	public DataGrid dataGridEmptyBucket(MbItemStock mbItemStock, PageHelper ph) {
		MbItem mbItem = new MbItem();
		mbItem.setIspack(true);
		List<MbItem> mbItems = mbItemService.query(mbItem);
		Integer[] itemIds = new Integer[mbItems.size()];
		Integer i = 0;
		for (MbItem item : mbItems) {
			itemIds[i] = item.getId();
			i++;
		}
		mbItemStock.setItemIdNumbers(itemIds);
		DataGrid dataGrid = dataGridWithOrderSum(mbItemStock, ph);
		return dataGrid;
	}

	@Override
	public void editStockAndBalance(MbItemStock mbItemStock, String loginId) {
		Integer logId = editAndInsertLog(mbItemStock, loginId);
		MbItem mbItem = mbItemService.getFromCache(mbItemStock.getItemId());
		Integer adjustment = mbItem.getMarketPrice() * mbItemStock.getAdjustment();
		MbBalance mbBalance = mbBalanceService.addOrGetMbBalanceCash(mbItemStock.getShopId());
		MbBalanceLog log = new MbBalanceLog();
		log.setBalanceId(mbBalance.getId());
		log.setAmount(-adjustment);
		log.setRefType("BT019"); // 绑定主店-余额转出
		if(logId!=null) {
			log.setRefId(logId + "");
		}
		log.setReason(mbItemStock.getReason());
		mbBalanceLogService.addAndUpdateBalance(log);
	}


	@Override
	public List<TmbItemStock> queryItemStockListByWarehouseId(Integer warehouseId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("warehouseId", warehouseId);
		List<TmbItemStock> tmbItemStockList = mbItemStockDao.find("from TmbItemStock t  where t.isdeleted = 0 and t.warehouseId = :warehouseId", params);
		return tmbItemStockList;
	}

	@Override
	public MbItemStock getStockItemIdNumbersValue(MbItemStock mbItemStock) {
		MbItemQuery mbItemQuery = new MbItemQuery();
		mbItemQuery.setIspack(mbItemStock.getIsPack());
		if (F.empty(mbItemStock.getItemIds())) {
			List<MbItem> mbItems = mbItemService.query(mbItemQuery);
			Integer[] itemIdNumbers;
			int i = 0;
			if (!CollectionUtils.isEmpty(mbItems)) {
				itemIdNumbers = new Integer[mbItems.size()];
				for (MbItem item : mbItems) {
					itemIdNumbers[i] = item.getId();
					i++;
				}
				mbItemStock.setItemIdNumbers(itemIdNumbers);
			}
			return mbItemStock;
		}
		if (!F.empty(mbItemStock.getItemIds())) {
			String[] itemIdsStr = mbItemStock.getItemIds().split(",");
			Integer[] itemIds = new Integer[itemIdsStr.length];
			for (int j = 0; j < itemIds.length; j++) {
				itemIds[j] = Integer.parseInt(itemIdsStr[j]);
			}
			mbItemQuery.setItemIds(itemIds);
			List<MbItem> mbItemList = mbItemService.query(mbItemQuery);
			Integer[] itemIdNumbers;
			int i = 0;
			if (!CollectionUtils.isEmpty(mbItemList)) {
				itemIdNumbers = new Integer[mbItemList.size()];
				for (MbItem item : mbItemList) {
					itemIdNumbers[i] = item.getId();
					i++;
				}
				mbItemStock.setItemIdNumbers(itemIdNumbers);
				mbItemStock.setItemIds(null);
			} else {
				mbItemStock.setItemIdNumbers(null);
			}
			return mbItemStock;
		}
		return null;
	}
}
