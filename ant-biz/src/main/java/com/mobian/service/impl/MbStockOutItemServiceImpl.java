package com.mobian.service.impl;

import com.mobian.absx.F;
import com.mobian.dao.MbStockOutItemDaoI;
import com.mobian.model.TmbStockOutItem;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbItem;
import com.mobian.pageModel.MbStockOutItem;
import com.mobian.pageModel.PageHelper;
import com.mobian.service.MbItemServiceI;
import com.mobian.service.MbStockOutItemServiceI;
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
public class MbStockOutItemServiceImpl extends BaseServiceImpl<MbStockOutItem> implements MbStockOutItemServiceI {

	@Autowired
	private MbStockOutItemDaoI mbStockOutItemDao;
	@Autowired
	private MbItemServiceI mbItemService;

	@Override
	public DataGrid dataGrid(MbStockOutItem mbStockOutItem, PageHelper ph) {
		List<MbStockOutItem> ol = new ArrayList<MbStockOutItem>();
		String hql = " from TmbStockOutItem t ";
		DataGrid dg = dataGridQuery(hql, ph, mbStockOutItem, mbStockOutItemDao);
		@SuppressWarnings("unchecked")
		List<TmbStockOutItem> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TmbStockOutItem t : l) {
				MbStockOutItem o = new MbStockOutItem();
				BeanUtils.copyProperties(t, o);
				if (!F.empty(o.getItemId())) {
					MbItem mbItem = mbItemService.getFromCache(o.getItemId());
					o.setItemName(mbItem.getName());
				}
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(MbStockOutItem mbStockOutItem, Map<String, Object> params) {
		String whereHql = "";	
		if (mbStockOutItem != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(mbStockOutItem.getTenantId())) {
				whereHql += " and t.tenantId = :tenantId";
				params.put("tenantId", mbStockOutItem.getTenantId());
			}		
			if (!F.empty(mbStockOutItem.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", mbStockOutItem.getIsdeleted());
			}		
			if (!F.empty(mbStockOutItem.getMbStockOutId())) {
				whereHql += " and t.mbStockOutId = :mbStockOutId";
				params.put("mbStockOutId", mbStockOutItem.getMbStockOutId());
			}		
			if (!F.empty(mbStockOutItem.getItemId())) {
				whereHql += " and t.itemId = :itemId";
				params.put("itemId", mbStockOutItem.getItemId());
			}		
			if (!F.empty(mbStockOutItem.getQuantity())) {
				whereHql += " and t.quantity = :quantity";
				params.put("quantity", mbStockOutItem.getQuantity());
			}		
		}	
		return whereHql;
	}

	@Override
	public List<MbStockOutItem> queryStockOutItem(MbStockOutItem mbStockOutItem) {
		List<MbStockOutItem> ol = new ArrayList<MbStockOutItem>();
		String hql = " from TmbStockOutItem t ";
		Map<String, Object> params = new HashMap<String, Object>();
		String where = whereHql(mbStockOutItem, params);
		List<TmbStockOutItem> l = mbStockOutItemDao.find(hql  + where, params);
		if (CollectionUtils.isNotEmpty(l)) {
			for (TmbStockOutItem t : l) {
				MbStockOutItem o = new MbStockOutItem();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

	@Override
	public void add(MbStockOutItem mbStockOutItem) {
		TmbStockOutItem t = new TmbStockOutItem();
		BeanUtils.copyProperties(mbStockOutItem, t);
		//t.setId(jb.absx.UUID.uuid());
		t.setIsdeleted(false);
		mbStockOutItemDao.save(t);
	}

	@Override
	public MbStockOutItem get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TmbStockOutItem t = mbStockOutItemDao.get("from TmbStockOutItem t  where t.id = :id", params);
		MbStockOutItem o = new MbStockOutItem();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(MbStockOutItem mbStockOutItem) {
		TmbStockOutItem t = mbStockOutItemDao.get(TmbStockOutItem.class, mbStockOutItem.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(mbStockOutItem, t, new String[]{"id", "addtime", "isdeleted", "updatetime"}, true);
		}
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		mbStockOutItemDao.executeHql("update TmbStockOutItem t set t.isdeleted = 1 where t.id = :id",params);
		//mbStockOutItemDao.delete(mbStockOutItemDao.get(TmbStockOutItem.class, id));
	}

}
