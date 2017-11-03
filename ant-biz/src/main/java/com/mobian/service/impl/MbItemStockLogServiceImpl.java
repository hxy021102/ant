package com.mobian.service.impl;

import com.mobian.absx.F;
import com.mobian.dao.MbItemStockLogDaoI;
import com.mobian.model.TmbItemStock;
import com.mobian.model.TmbItemStockLog;
import com.mobian.model.TmbOrderItem;
import com.mobian.pageModel.*;
import com.mobian.service.*;
import com.mobian.util.DateUtil;
import com.mobian.util.MyBeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MbItemStockLogServiceImpl extends BaseServiceImpl<MbItemStockLog> implements MbItemStockLogServiceI {

	@Autowired
	private MbItemStockLogDaoI mbItemStockLogDao;

	@Autowired
	private UserServiceI userService;
	@Autowired
	private MbItemServiceI mbItemService;
	@Autowired
	private MbItemStockServiceI mbItemStockService;
	@Autowired
	private MbWarehouseServiceI mbWarehouseService;

	@Override
	public DataGrid dataGrid(MbItemStockLog mbItemStockLog, PageHelper ph) {
		List<MbItemStockLog> ol = new ArrayList<MbItemStockLog>();
		String hql = " from TmbItemStockLog t ";
		DataGrid dg = dataGridQuery(hql, ph, mbItemStockLog, mbItemStockLogDao);
		@SuppressWarnings("unchecked")
		List<TmbItemStockLog> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TmbItemStockLog t : l) {
				MbItemStockLog o = new MbItemStockLog();
				BeanUtils.copyProperties(t, o);

				if (o.getLoginId() != null) {
					User user = userService.getFromCache(String.valueOf(o.getLoginId()));
					if (user != null) {
						o.setLoginName(user.getName());
					}
				}

				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(MbItemStockLog mbItemStockLog, Map<String, Object> params) {
		String whereHql = "";	
		if (mbItemStockLog != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(mbItemStockLog.getTenantId())) {
				whereHql += " and t.tenantId = :tenantId";
				params.put("tenantId", mbItemStockLog.getTenantId());
			}		
			if (!F.empty(mbItemStockLog.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", mbItemStockLog.getIsdeleted());
			}		
			if (!F.empty(mbItemStockLog.getItemStockId())) {
				whereHql += " and t.itemStockId = :itemStockId";
				params.put("itemStockId", mbItemStockLog.getItemStockId());
			}
			if (!F.empty(mbItemStockLog.getQuantity())) {
				whereHql += " and t.quantity = :quantity";
				params.put("quantity", mbItemStockLog.getQuantity());
			}
			if(mbItemStockLog.getQuantity()!=null && mbItemStockLog.getQuantity()==0) {
				whereHql += " and t.quantity != :quantity";
				params.put("quantity", mbItemStockLog.getQuantity());
			}
			if (!F.empty(mbItemStockLog.getLoginId())) {
				whereHql += " and t.loginId = :loginId";
				params.put("loginId", mbItemStockLog.getLoginId());
			}		
			if (!F.empty(mbItemStockLog.getLogType())) {
				whereHql += " and t.logType = :logType";
				params.put("logType", mbItemStockLog.getLogType());
			}		
			if (!F.empty(mbItemStockLog.getReason())) {
				whereHql += " and t.reason = :reason";
				params.put("reason", mbItemStockLog.getReason());
			}		
			if (!F.empty(mbItemStockLog.getRemark())) {
				whereHql += " and t.remark = :remark";
				params.put("remark", mbItemStockLog.getRemark());
			}
			if (mbItemStockLog.getStockLogStartTime() != null) {
				whereHql += " and t.addtime >= :stockLogStartTime";
				params.put("stockLogStartTime", mbItemStockLog.getStockLogStartTime());
			}
			if (mbItemStockLog.getStockLogEndTime() != null) {
				whereHql += " and t.addtime <= :stockLogEndTime";
				params.put("stockLogEndTime", mbItemStockLog.getStockLogEndTime());
			}
			if (mbItemStockLog.getItemStockIds() != null) {
				whereHql += " and t.itemStockId in (:itemStockIds) ";
				params.put("itemStockIds", mbItemStockLog.getItemStockIds());
			}
		}	
		return whereHql;
	}

	@Override
	public void add(MbItemStockLog mbItemStockLog) {
		TmbItemStockLog t = new TmbItemStockLog();
		BeanUtils.copyProperties(mbItemStockLog, t);
		//t.setId(jb.absx.UUID.uuid());
		t.setIsdeleted(false);
		mbItemStockLogDao.save(t);
		mbItemStockLog.setId(t.getId());
	}

	@Override
	public MbItemStockLog get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TmbItemStockLog t = mbItemStockLogDao.get("from TmbItemStockLog t  where t.id = :id", params);
		MbItemStockLog o = new MbItemStockLog();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(MbItemStockLog mbItemStockLog) {
		TmbItemStockLog t = mbItemStockLogDao.get(TmbItemStockLog.class, mbItemStockLog.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(mbItemStockLog, t, new String[]{"id", "addtime", "isdeleted", "updatetime"}, true);
		}
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		mbItemStockLogDao.executeHql("update TmbItemStockLog t set t.isdeleted = 1 where t.id = :id",params);
		//mbItemStockLogDao.delete(mbItemStockLogDao.get(TmbItemStockLog.class, id));
	}

	@Override
	public DataGrid dataGridStockInLog(MbItemStockLog mbItemStockLog, PageHelper ph) {
		List<TmbItemStock> tmbItemStocks = mbItemStockService.queryItemStockListByWarehouseId(mbItemStockLog.getWarehouseId());
		int i = 0;
		Integer[] itemStockIds;
		if (!CollectionUtils.isEmpty(tmbItemStocks)) {
			itemStockIds = new Integer[tmbItemStocks.size()];
			for (TmbItemStock mbItemStock : tmbItemStocks) {
				itemStockIds[i] = mbItemStock.getId();
				i++;
			}
			mbItemStockLog.setItemStockIds(itemStockIds);
		}
		DataGrid dataGrid = dataGrid(mbItemStockLog, ph);
		List<MbItemStockLog> mbItemStockLogs = dataGrid.getRows();
		if (!CollectionUtils.isEmpty(mbItemStockLogs)) {
			for (MbItemStockLog stockLog : mbItemStockLogs) {
				MbItemStock mbItemStock = mbItemStockService.get(stockLog.getItemStockId());
				if (mbItemStock != null) {
					MbItem mbItem = mbItemService.getFromCache(mbItemStock.getItemId());
					if (mbItem != null) {
						stockLog.setItemName(mbItem.getName());
					}
					MbWarehouse warehouse = mbWarehouseService.getFromCache(mbItemStock.getWarehouseId());
					if (warehouse != null) {
						stockLog.setWarehouseName(warehouse.getName());
					}
				}
			}
		}
		dataGrid.setRows(mbItemStockLogs);
		return dataGrid;
	}

	@Override
	public List<MbItemStockLog> queryListByWithoutCostPrice() {
		List<MbItemStockLog> ol = new ArrayList<MbItemStockLog>();
		/*String sql = "from TmbItemStockLog t where t.isdeleted = 0 and t.costPrice is null and t.addtime >= :updatetimeBegin";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("updatetimeBegin", DateUtil.addDayToDate(new Date(), -1));*/
		String sql = "from TmbItemStockLog t where t.isdeleted = 0 and (t.costPrice is null or (t.reason like '入库ID%' and t.endQuantity is null))";
		List<TmbItemStockLog> l =  mbItemStockLogDao.find(sql, 1,200);
		if (CollectionUtils.isNotEmpty(l)) {
			for (TmbItemStockLog t : l) {
				MbItemStockLog o = new MbItemStockLog();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

}
