package com.mobian.service.impl;

import com.mobian.absx.F;
import com.mobian.dao.MbSupplierStockInItemDaoI;
import com.mobian.model.TmbSupplierStockInItem;
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
public class MbSupplierStockInItemServiceImpl extends BaseServiceImpl<MbSupplierStockInItem> implements MbSupplierStockInItemServiceI {

	@Autowired
	private MbSupplierStockInItemDaoI mbSupplierStockInItemDao;
	@Autowired
	private MbItemServiceI mbItemService;
	@Autowired
	private MbSupplierStockInServiceI mbSupplierStockInService;
	@Autowired
	private MbSupplierOrderServiceI mbSupplierOrderService;
	@Autowired
	private MbSupplierServiceI mbSupplierService;

	@Override
	public DataGrid dataGrid(MbSupplierStockInItem mbSupplierStockInItem, PageHelper ph) {
		List<MbSupplierStockInItem> ol = new ArrayList<MbSupplierStockInItem>();
		String hql = " from TmbSupplierStockInItem t ";
		DataGrid dg = dataGridQuery(hql, ph, mbSupplierStockInItem, mbSupplierStockInItemDao);
		@SuppressWarnings("unchecked")
		List<TmbSupplierStockInItem> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TmbSupplierStockInItem t : l) {
				MbSupplierStockInItem o = new MbSupplierStockInItem();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(MbSupplierStockInItem mbSupplierStockInItem, Map<String, Object> params) {
		String whereHql = "";	
		if (mbSupplierStockInItem != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(mbSupplierStockInItem.getTenantId())) {
				whereHql += " and t.tenantId = :tenantId";
				params.put("tenantId", mbSupplierStockInItem.getTenantId());
			}		
			if (!F.empty(mbSupplierStockInItem.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", mbSupplierStockInItem.getIsdeleted());
			}		
			if (!F.empty(mbSupplierStockInItem.getSupplierStockInId())) {
				whereHql += " and t.supplierStockInId = :supplierStockInId";
				params.put("supplierStockInId", mbSupplierStockInItem.getSupplierStockInId());
			}		
			if (!F.empty(mbSupplierStockInItem.getItemId())) {
				whereHql += " and t.itemId = :itemId";
				params.put("itemId", mbSupplierStockInItem.getItemId());
			}		
			if (!F.empty(mbSupplierStockInItem.getQuantity())) {
				whereHql += " and t.quantity = :quantity";
				params.put("quantity", mbSupplierStockInItem.getQuantity());
			}		
			if (!F.empty(mbSupplierStockInItem.getPrice())) {
				whereHql += " and t.price = :price";
				params.put("price", mbSupplierStockInItem.getPrice());
			}
			if(mbSupplierStockInItem.getUpdatetimeBegin()!=null){
				whereHql += " and t.updatetime >= :updatetimeBegin";
				params.put("updatetimeBegin", mbSupplierStockInItem.getUpdatetimeBegin());
			}
			if(mbSupplierStockInItem.getUpdatetimeEnd()!=null){
				whereHql += " and t.updatetime <= :updatetimeEnd";
				params.put("updatetimeEnd", mbSupplierStockInItem.getUpdatetimeEnd());
			}
		}	
		return whereHql;
	}

	@Override
	public void add(MbSupplierStockInItem mbSupplierStockInItem) {
		TmbSupplierStockInItem t = new TmbSupplierStockInItem();
		BeanUtils.copyProperties(mbSupplierStockInItem, t);
		//t.setId(jb.absx.UUID.uuid());
		t.setIsdeleted(false);
		mbSupplierStockInItemDao.save(t);
	}

	@Override
	public MbSupplierStockInItem get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TmbSupplierStockInItem t = mbSupplierStockInItemDao.get("from TmbSupplierStockInItem t  where t.id = :id", params);
		MbSupplierStockInItem o = new MbSupplierStockInItem();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(MbSupplierStockInItem mbSupplierStockInItem) {
		TmbSupplierStockInItem t = mbSupplierStockInItemDao.get(TmbSupplierStockInItem.class, mbSupplierStockInItem.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(mbSupplierStockInItem, t, new String[]{"id", "addtime", "isdeleted", "updatetime"}, true);
		}
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		mbSupplierStockInItemDao.executeHql("update TmbSupplierStockInItem t set t.isdeleted = 1 where t.id = :id",params);
		//mbSupplierStockInItemDao.delete(mbSupplierStockInItemDao.get(TmbSupplierStockInItem.class, id));
	}
	@Override
	public List<MbSupplierStockInItem> query(MbSupplierStockInItem mbSupplierStockInItem) {
		List<MbSupplierStockInItem> ol = new ArrayList<MbSupplierStockInItem>();
		String hql = " from TmbSupplierStockInItem t ";
		Map<String, Object> params = new HashMap<String, Object>();
		String where = whereHql(mbSupplierStockInItem, params);
		List<TmbSupplierStockInItem> l = mbSupplierStockInItemDao.find(hql + where, params);
		if (CollectionUtils.isNotEmpty(l)) {
			for (TmbSupplierStockInItem t : l) {
				MbSupplierStockInItem o = new MbSupplierStockInItem();
				BeanUtils.copyProperties(t, o);
				Integer id = o.getItemId();
				MbItem mbItem = mbItemService.getFromCache(id);
				String name = mbItem.getName();
				o.setProductName(name);
				ol.add(o);
			}
		}
		return ol;
	}

	@Override
	public List<TmbSupplierStockInItem> getStockInItemListByStockInId(Integer stockInId) {
		Map<String,Object>  params=new HashMap<String,Object>();
		params.put("stockId",stockInId);
		List<TmbSupplierStockInItem> tmbSupplierStockInItemList=mbSupplierStockInItemDao.find("from TmbSupplierStockInItem where isdeleted = 0 and  supplierStockInId =:stockId ",params);
		return tmbSupplierStockInItemList;
	}

	@Override
	public List<MbSupplierStockInItem> getListByStockInIds(Integer[] stockInIds) {
		List<MbSupplierStockInItem> ol = new ArrayList<MbSupplierStockInItem>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("alist", stockInIds);
		List<TmbSupplierStockInItem> l = mbSupplierStockInItemDao.find("from TmbSupplierStockInItem t where t.isdeleted = 0 and t.supplierStockInId in (:alist)", params);
		if (CollectionUtils.isNotEmpty(l)) {
			for (TmbSupplierStockInItem t : l) {
				MbSupplierStockInItem o = new MbSupplierStockInItem();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

	@Override
	public DataGrid dataGridStockInItem(MbSupplierStockInItem mbSupplierStockInItem, PageHelper ph) {
		List<MbSupplierStockInItem> ol = new ArrayList<MbSupplierStockInItem>();
		String hql = " from TmbSupplierStockInItem t ";
		DataGrid dg = dataGridQuery(hql, ph, mbSupplierStockInItem, mbSupplierStockInItemDao);
		@SuppressWarnings("unchecked")
		List<TmbSupplierStockInItem> l = dg.getRows();
		if (l != null && l.size() > 0) {
			double totalPrice = 0;
			for (TmbSupplierStockInItem t : l) {
				MbSupplierStockInItem o = new MbSupplierStockInItem();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
				if(o.getSupplierStockInId() != null) {
					MbSupplierStockIn mbSupplierStockIn = mbSupplierStockInService.get(o.getSupplierStockInId());
					o.setSupplierOrderId(mbSupplierStockIn.getSupplierOrderId());
					MbSupplierOrder mbSupplierOrder = mbSupplierOrderService.get(mbSupplierStockIn.getSupplierOrderId());
					MbSupplier mbSupplier = mbSupplierService.get(mbSupplierOrder.getSupplierId());
					o.setSupplierName(mbSupplier.getName());
				}
				if(o.getItemId() != null) {
					MbItem mbItem = mbItemService.get(o.getItemId());
					o.setProductName(mbItem.getName());
					o.setCode(mbItem.getCode());
				}
				o.setTotalPrice(o.getQuantity()*o.getPrice());
				totalPrice +=o.getTotalPrice();
			}
			List<MbSupplierStockInItem> footer = new ArrayList<MbSupplierStockInItem>();
			MbSupplierStockInItem totalRow = new MbSupplierStockInItem();
            totalRow.setSupplierName("合计");
            totalRow.setTotalPrice(totalPrice);
            footer.add(totalRow);
            dg.setFooter(footer);
		}
		dg.setRows(ol);
		return dg;
	}
}
